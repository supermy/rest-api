package com.supermy.designpatterns.state;

import java.awt.*;

/**
 * Created by moyong on 17/2/22.
 */
public abstract class State{

    /*
        开
     */
    public abstract void handlepush(Context c);
    /*
        关
     */
    public abstract void handlepull(Context c);
    public abstract Color getcolor();

}
