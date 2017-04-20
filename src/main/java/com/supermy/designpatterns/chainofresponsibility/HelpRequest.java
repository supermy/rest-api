package com.supermy.designpatterns.chainofresponsibility;

/**
 * Created by moyong on 17/2/22.
 */
public class HelpRequest extends Request{


    public HelpRequest(String type) {
        super(type);
    }

    public void execute(){
        //request真正具体行为代码
        System.out.println("execute HelpRequest......");
    }
}
