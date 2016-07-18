package com.supermy.utils;


import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.supermy.utils.FilterMeta.Operator;


/**
 * extjs 的order json 转换为 mongodb 的 filter 查询参数
 * <p/>
 * 1.直接生成对象，属性值与参数名相同则自动进行复制：获取filter 参数；
 * 2.生成FilterMeta 对象数组；
 * 3.生成Specification，直接返回查询需要的where参数；
 *
 * @author jamesmo
 */
public class MyFilter<T> {

    private final Logger logger = LoggerFactory.getLogger(MyFilter.class);

    public void setFilter(String filter) {
        System.out.println("==========" + filter);
        this.filter = filter;
        if (!filter.isEmpty()  && filter !=""){
            parseJson(filter);//关键点
        }
    }


    private Specification<T> spec;
    private FilterMeta[] filterMeta = new FilterMeta[0];
    private String filter;

    public MyFilter() {
    }

    public MyFilter(String filterJson) {
        parseJson(filterJson);
    }

    /**
     * 解析json 字符串，得到filtermeta
     *
     * @param filterJson
     */
    FilterMeta[] parseJson(String filterJson) {

        if (null == filterJson || "".equalsIgnoreCase(filterJson)) {
            return null;
        }

        System.out.println("filter value:{}" + filterJson);

        ObjectMapper mapper = new ObjectMapper();
        try {

            this.filterMeta = mapper.readValue(filterJson, FilterMeta[].class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("============" + filterMeta.length);

        //关键
        this.spec = bySearchFilter(Arrays.asList(filterMeta));

        System.out.println("===========spec:" + spec.toString());
        return this.filterMeta;

    }


    //public Specification<T> bySearchFilter(final Collection<FilterMeta> filters, final Class<T> entityClazz) {
    public <T> Specification<T> bySearchFilter(final Collection<FilterMeta> filters) {

        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

                System.out.println("to toPredicate......");
//                root = query.from(entityClazz);//查询类
//                Path<String> nameExp = root.get("name");//获取查询字段
//                builder.like(nameExp, "%Sam%");//获取查询语句

                //只构造查询条件
                if (isNotEmpty(filters)) {

                    List<Predicate> predicates = Lists.newArrayList();

                    for (FilterMeta filter : filters) {

                        String type = filter.getType();

                        Operator comparison = filter.getComparison();

                        String field = filter.getField();
                        String value = filter.getValue();



                        Path expression = root.get(field);
                        //todo nested path translate, 如Task的名为"user.name"的filedName, 转换为Task.user.name属性
//                        String[] names = StringUtils.split(filter.fieldName, ".");
//                        Path expression = root.get(names[0]);
//                        for (int i = 1; i < names.length; i++) {
//                            expression = expression.get(names[i]);
//                        }

                        if ("string".equalsIgnoreCase(type)) {
                            predicates.add(builder.like(expression, "%" + value + "%"));
                        }

                        if ("boolean".equalsIgnoreCase(type)) {
                            if ("true".equals(value)){
                                predicates.add(builder.isTrue(expression));

                            }else{
                                predicates.add(builder.isFalse(expression));

                            }
                        }
                        //大于 小于 等于 三种情况
                        if ("date".equalsIgnoreCase(type) ) {
                            System.out.println(value);

//                            long v=Long.parseLong(value);
//                            Date y = new Date(value);
                            Date y = new Date(value);
                            System.out.println("date:"+y);

                            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");


                            // logic operator
                            switch (comparison) {
                                case eq:
                                    predicates.add(builder.equal(expression, y));
                                    break;
                                case gt:
                                    predicates.add(builder.greaterThan(expression, y));
                                    break;
                                case lt:
                                    predicates.add(builder.lessThan(expression, y));
                                    break;
                            }
                        }
                        if ("numeric".equalsIgnoreCase(type)) {
                            // logic operator
                            switch (comparison) {
                                case eq:
                                    predicates.add(builder.equal(expression,  (Comparable) value));
                                    break;
                                case gt:
                                    predicates.add(builder.greaterThan(expression, (Comparable) value));
                                    break;
                                case lt:
                                    predicates.add(builder.lessThan(expression, (Comparable) value));
                                    break;
                            }
                        }

                    }
                    // 将所有条件用 and 联合起来
                    if (!predicates.isEmpty()) {
                        return builder.and(predicates.toArray(new Predicate[predicates.size()]));
                    }
                }

                return builder.conjunction();
            }
        };
    }


    /**
     * 判断是否为空.
     */
    public static boolean isEmpty(Collection collection) {
        return (collection == null) || collection.isEmpty();
    }

    /**
     * 判断是否为空.
     */
    public static boolean isEmpty(Map map) {
        return (map == null) || map.isEmpty();
    }

    /**
     * 判断是否为空.
     */
    public static boolean isNotEmpty(Collection collection) {
        return (collection != null) && !(collection.isEmpty());
    }


    public String getFilter() {
        return filter;
    }

    public Specification<T> getSpec() {
        return spec;
    }

    public void setSpec(Specification<T> spec) {
        this.spec = spec;
    }

    public FilterMeta[] getFilterMeta() {
        return filterMeta;
    }

    public void setFilterMeta(FilterMeta[] filterMeta) {
        this.filterMeta = filterMeta;
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String filter = "[{\"type\":\"date\",\"comparison\":\"gt\",\"value\":\"2014-06-07\",\"field\":\"cdate\"}," +
                         "{\"type\":\"date\",\"comparison\":\"lt\",\"value\":\"2014-06-09\",\"field\":\"udate\"}," +
                         "{\"type\":\"string\",\"value\":\"快卡\",\"field\":\"name\"}]";

        MyFilter f = new MyFilter(filter);

        System.out.println(f.getFilter());

    }

//    Extjs Grid Filter 产生机制
//    数字类型，大于小于可以是一个，可以是两个，等于只能有一个
//    {"type":"numeric","comparison":"lt","value":5,"field":"pkId"},
//    {"type":"numeric","comparison":"gt","value":1,"field":"pkId"},
//    {"type":"numeric","comparison":"eq","value":3,"field":"pkId"},
//
//    日期，可以是一个到三个
//    {"type":"date","comparison":"lt","value":"06/26/2015","field":"createDate"},
//    {"type":"date","comparison":"gt","value":"06/26/2015","field":"createDate"},
//    {"type":"date","comparison":"eq","value":"06/24/2015","field":"createDate"}
//
//    单个字段只能有一个
//    {"type":"string","value":"test","field":"name"},
//    {"type":"string","value":"test","field":"name"},
//
//    booelan 智能有一个
//    {"type":"boolean","value":true,"field":"status"}


}

