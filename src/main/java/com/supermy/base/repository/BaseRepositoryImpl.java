package com.supermy.base.repository;

import com.google.common.base.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * Created by moyong on 16/10/26.
 * 定义好自定义的方法后，我们现在通过一个基本的Repository类来实现该方法：

 首先添加BaseRepositoryImpl类，继承SimpleJpaRepository类，使其拥有Jpa Repository的基本方法。

 我们发现Repository有两个构造函数：

 SimpleJpaRepository(JpaEntityInformation entityInformation, EntityManager entityManager)
 SimpleJpaRepository(Class domainClass, EntityManager em)
 这里我们实现第二个构造函数，拿到domainClass和EntityManager两个对象。因为我们要实现的是知道某个Repository是否支持某个领域对象的类型，因此在实现构造函数时我们将domainClass的信息保留下来。

 最后实现support方法，其参数是领域对象的类型，将其和domainClass对比，如果相等，则该Repository支持该类型的领域对象：
 */
public class BaseRepositoryImpl<T, ID extends Serializable>
        extends SimpleJpaRepository<T, ID>
        implements BaseRepository<T, ID> {

    private final Class<T> domainClass;
    private final EntityManager entityManager;

    public BaseRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.entityManager=entityManager;
        this.domainClass = domainClass;
    }

    @Override
    public boolean support(String modelType) {
        return domainClass.getName().equals(modelType);
    }

    @Transactional
    @Override
    public Optional<T> deleteById(ID id) {
        T deleted = entityManager.find(this.getDomainClass(), id);
        Optional<T> returned = Optional.absent(); //需要验证

        if (deleted != null) {
            entityManager.remove(deleted);
            returned = Optional.of(deleted);
        }
        return returned;
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        System.out.println("over ride find all ......");

        if (null == pageable) {
            return new PageImpl<T>(findAll());
        }
        System.out.println(pageable.toString());
//        System.out.println(findAll(null, pageable));
        return super.findAll(pageable);
    }

}