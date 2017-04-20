package com.supermy.designpatterns.visitor;

/**
 * Created by moyong on 17/2/22.
 * 行为与数据分离；在antlr 有用
 */
public interface Visitable
{
    public void accept(Visitor visitor);
}
