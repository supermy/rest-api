package com.supermy.designpatterns.visitor;

/**
 * Created by moyong on 17/2/22.
 */
public interface Visitable
{
    public void accept(Visitor visitor);
}
