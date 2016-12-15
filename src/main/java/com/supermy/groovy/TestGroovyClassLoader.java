package com.supermy.groovy;

/**
 * Created by moyong on 16/12/13.
 */
import groovy.lang.GroovyClassLoader;
import groovy.lang.Script;

import java.io.IOException;

/**
 * @author jamesmo
 *
 * 压力测试 一段时间之后  java.lang.OutOfMemoryError
 *
 * GroovyShell.parse()内部其实也就是调用GroovyClassLoader.parseClass()去解析Groovy脚本并生成Class实例（
 * 会是groovy.lang.Script的子类），
 * 然后调用Class.newInstance()构造出一个新的实例以Script类型的引用返回出来。
 *
 *
 */
public class TestGroovyClassLoader {
    // see if the number of loaded class keeps growing when
    // using GroovyClassLoader.parseClass
    public static void test() {
        String scriptText = "def mul(x, y) { x * y }\nprintln mul(5, 7)";

        while (true) {
            GroovyClassLoader loader = new GroovyClassLoader();
            Class<?> newClazz = loader.parseClass(scriptText);
            try {
                Object obj = newClazz.newInstance();
                Script script = (Script) obj;
                script.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        test();
    }
}