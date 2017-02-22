package com.supermy.designpatterns.state;

/**
 * Created by moyong on 17/2/22.
 *
 * 通过状态机解决 if esle 语句问题
 */
public class Context{

    private State state=null; //我们将原来的 Color state 改成了新建的State state;

    //setState是用来改变state的状态 使用setState实现状态的切换
    public void setState(State state){
        this.state=state;
        }

    public void push(){

        //状态的切换的细节部分,在本例中是颜色的变化,已经封装在子类的handlepush中实现,这里无需关心
        state.handlepush(this);
        
        //因为sample要使用state中的一个切换结果,使用getColor()
//        Sample sample=new Sample(state.getColor());
//        sample.operate();

        }

    public void pull(){

        state.handlepull(this);
        
//        Sample2 sample2=new Sample2(state.getColor());
//        sample2.operate();

        }

}