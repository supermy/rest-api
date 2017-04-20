package com.supermy.designpatterns.chainofresponsibility;

/**
 * Created by moyong on 17/2/22.
 */
public class Request{
    private String type;

    public Request(String type){this.type=type;}

    public String getType(){return type;}

    public void execute(){
        //request真正具体行为代码
        System.out.println("execute request......");
        }
}
