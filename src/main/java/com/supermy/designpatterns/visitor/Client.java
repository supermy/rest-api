package com.supermy.designpatterns.visitor;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by moyong on 17/2/22.
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Visitor visitor = new ConcreteVisitor();

        StringElement stringE = new StringElement("I am a String");
        visitor.visitString(stringE);

        Collection list = new ArrayList();
        list.add(new StringElement("I am a String1"));
        list.add(new StringElement("I am a String2"));
        list.add(new FloatElement(new Float(12)));
        list.add(new StringElement("I am a String3"));
        visitor.visitCollection(list);


    }
}
