package com.supermy.groovy;

/**
 * Created by moyong on 16/11/24.
 */
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;


import java.io.File;


/**
 * dsl or dyn script 脚本；
 */
public class GroovyClassLoaderExample2{


    public static void main(String args[]) {

        try {

            GroovyClassLoader loader= new GroovyClassLoader();

            Class groovyClass =loader.parseClass(new File("Square.groovy"));

            GroovyObject object =(GroovyObject) groovyClass.newInstance();

            object.invokeMethod("setX", 10);

            Shape shape = (Shape)object;

            assert shape.calculateArea() == 100;

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}