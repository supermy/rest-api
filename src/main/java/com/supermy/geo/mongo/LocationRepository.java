package com.supermy.geo.mongo;

import org.springframework.data.geo.Box;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by moyong on 16/5/13.
 * todo : 需要外挂一层controller 进行调用;
 */
public interface LocationRepository extends MongoRepository<Location, String> {

    /**
     *
     * near与within方法区别,near方法查询后会对结果集对distance进行排序且有大小限制,而within是无序的也无大小限制.
     *
     * @param c
     * @return
     */
    public List<Location> findByPositionWithin(Circle c);

    public List<Location> findByPositionWithin(Box b);

    public List<Location> findByPositionNear(Point p, Distance d);
}