package com.supermy.designpatterns.state;

import java.awt.*;

/**
 * Created by moyong on 17/2/22.
 */
public class RedState extends State{

    public void handlepush(Context c){
         //根据push方法"如果是blue状态的切换到green" ;
         c.setState(new BlueState());

        }
    public void handlepull(Context c){

         //根据pull方法"如果是blue状态的切换到red" ;
        c.setState(new BlackState());

        }

    public Color getcolor(){ return Color.red;}

}
