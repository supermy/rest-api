package com.supermy.designpatterns.command;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moyong on 17/2/22.
 */
public class Producer {
    public static List produceRequests() {
        List queue = new ArrayList();
        queue.add( new Engineer() );
        queue.add( new Politician() );
        queue.add( new Programmer() );
        return queue;
        }
}
