package com.supermy.designpatterns.visitor;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by moyong on 17/2/22.
 */
public class Client {
    public static void main(String[] args) throws IOException {
        /**
         * 构造一个访问者
         */
        Visitor visitor = new ConcreteVisitor();

        /**
         * 构造一个字符串数据
         */
        StringElement stringE = new StringElement("I am a String");
        visitor.visitString(stringE);

        /**
         * 构造一个集合数据
         */
        Collection list = new ArrayList();
        list.add(new StringElement("I am a String1"));
        list.add(new StringElement("I am a String2"));
        list.add(new FloatElement(new Float(12)));
        list.add(new StringElement("I am a String3"));
        visitor.visitCollection(list);


    }
}
