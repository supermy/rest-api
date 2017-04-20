package com.supermy.designpatterns.strategy;

/**
 * Created by moyong on 17/2/22.
 */
public class RepTempRuleOne extends RepTempRule{


    public void replace() throws Exception{

        //replaceFirst是jdk1.4新特性
        newString= this.oldString.replaceFirst("aaa", "bbb");
        System.out.println("this is replace one:"+newString);

    }


}