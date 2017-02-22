package com.supermy.designpatterns.visitor;

import java.util.Collection;

/**
 * Created by moyong on 17/2/22.
 */
public interface Visitor
{

     public void visitString(StringElement stringE);
     public void visitFloat(FloatElement floatE);
     public void visitCollection(Collection collection);

}
