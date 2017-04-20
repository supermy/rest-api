package com.supermy.designpatterns.chainofresponsibility;

/**
 * Created by moyong on 17/2/22.
 */
public class PrintRequest extends Request{


    public PrintRequest(String type) {
        super(type);
    }

    public void execute(){
        //request真正具体行为代码
        System.out.println("execute PrintRequest......");
    }
}
