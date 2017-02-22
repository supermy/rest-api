package com.supermy.designpatterns.visitor;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by moyong on 17/2/22.
 */
public class ConcreteVisitor implements Visitor {
    //在本方法中,我们实现了对Collection的元素的成功访问
    public void visitCollection(Collection collection) {
        Iterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if (o instanceof Visitable)
                ((Visitable) o).accept(this);
        }
    }
    public void visitString (StringElement stringE){
        System.out.println("'" + stringE.getValue() + "'");
    }

    public void visitFloat(FloatElement floatE) {
        System.out.println(floatE.getValue().toString() + "f");
    }

}