package com.supermy.designpatterns.strategy;

/**
 * Created by moyong on 17/2/22.
 */
public class RepTempRuleTwo extends RepTempRule{


    public void replace() throws Exception{

        newString= this.oldString.replaceFirst("aaa", "ccc");
        System.out.println("this is replace Two:"+newString);
    }


}
