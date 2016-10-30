package com.supermy.base.repository;

import java.util.List;

import com.supermy.base.domain.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.access.prepost.PostAuthorize;
//import org.springframework.security.access.prepost.PreAuthorize;

/**
 * http://spring.io/understanding/HATEOAS
 * http://spring.io/understanding/REST
 * http://spring.io/guides/gs/accessing-data-rest/
 *
 * 自动实现rest 框架;
 * 支持Name 约定；
 * 支持hsql 查询；
 * 支持原生sql 查询；
 *
 * collectionResourceRel = "people" 替代persons
 *
 */

@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface PersonRepository extends BaseRepository<Person, Long> {


    @RestResource(path = "names", rel = "names")
    List<Person> findByLastName(@Param("name") String name);

    @Query(value = "select u from Person u where u.firstName = :lname")
    Person findByEmailAddress(@Param("lname") String firstName);

    @Query(value = "select u from Person u where u.firstName = ?1") //error fix me
    Person findByEmailAddress1( String firstName);

    //n还可以使用@Query来指定本地查询原生sql，只要设置nativeQuery为true，比如：
    //注意：当前版本的本地查询不支持翻页和动态的排序
    @Query(value = "select * from Person where firstName like %:name1 or lastName like %:name2 ",nativeQuery=true)
    List<Person> findByName(@Param("name1")String name1,@Param("name2") String name2);

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    Page<Person> findAll(Pageable pageable);

    @Override
    @PostAuthorize("returnObject.firstName == principal.username or hasRole('ROLE_ADMIN')")
    Person getOne(Long id);

    /**
     * 好用
     * http://127.0.0.1:9006/form/rest/admin <br/>
     * 跳转的登陆页面,登陆之后有权限进行访问;否则不允许访问
     *
     * @param firstName
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    List<Person> findByFirstNameLike(@Param("firstName") String firstName);

//    同样支持更新类的Query语句，添加@Modifying即可，比如：
//    @Modifying
//    @Query(value="update UserModel o set o.name=:newName where o.name like %:nn")
//    public int findByUuidOrAge(@Param("nn") String name,@Param("newName") String newName);
//    注意：
//            1：方法的返回值应该是int，表示更新语句所影响的行数
//    2：在调用的地方必须加事务，没有事务不能正常执行

}