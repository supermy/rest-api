package com.supermy;

import com.supermy.domain.Person;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Created by moyong on 15/6/27.
 */
public class DateTest {
    public DateTest() {
    }
    public static void main(String[] args) throws IOException {
        Date o=new Date("06/26/2015");
        System.out.println(o);

    }
}
