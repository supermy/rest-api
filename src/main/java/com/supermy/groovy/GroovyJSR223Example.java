package com.supermy.groovy;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.springframework.core.io.ClassPathResource;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by moyong on 16/11/23.
 */
public class GroovyJSR223Example {
    public static void main(String args[]) {
        Binding binding = new Binding();
        GroovyShell shell = new GroovyShell(binding);
        //直接方法调用
        //shell.parse(new File(//))
//        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("/groovy/json.groovy")));

        File groovyfile = null;
        try {
            groovyfile = new ClassPathResource("/groovy/hello.groovy").getFile();
            //Script script = shell.parse(groovyfile);
//            script.invokeMethod();

            Script script = shell.parse(groovyfile);
            String joinString = (String)script.invokeMethod("join", new String[]{"A1","B2","C3"});
            System.out.println(joinString);
            ////脚本可以为任何格式,可以为main方法,也可以为普通方法
            //1) def call(){...};call();
            //2) call(){...};
            script = shell.parse("static void main(String[] args){i = i * 2;}");
            script.setProperty("i", new Integer(10));
            script.run();//运行,
            System.out.println(script.getProperty("i"));
            //the same as
            System.out.println(script.getBinding().getVariable("i"));
            script = null;
            shell = null;
        } catch (IOException  e) {
            e.printStackTrace();
            //logger.error("Database reader cound not be initialized. ", e);
        }




    }
}
