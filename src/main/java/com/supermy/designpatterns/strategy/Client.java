package com.supermy.designpatterns.strategy;


import com.supermy.designpatterns.visitor.ConcreteVisitor;
import com.supermy.designpatterns.visitor.FloatElement;
import com.supermy.designpatterns.visitor.StringElement;
import com.supermy.designpatterns.visitor.Visitor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by moyong on 17/2/22.
 */
public class Client {
    public static void main(String[] args) throws Exception {
        //使用第一套替代方案
        RepTempRuleSolve solver=new RepTempRuleSolve(new RepTempRuleOne());
        solver.getNewContext("site","算法1aaac");

        //使用第二套

        solver=new RepTempRuleSolve(new RepTempRuleTwo());
        solver.getNewContext("site","算法2aaaa");


    }
}
