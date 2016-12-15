package com.supermy.groovy;

/**
 * Created by moyong on 16/12/13.
 */
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import javax.script.*;
import java.io.IOException;
import java.util.Date;

/**
 * @author jamesmo
 *
 * outofmemory 示例
 *
 * 在整合Groovy脚本的时候可能会遇到一类陷阱：临时加载的类未能及时被释放，进而导致PermGen OutOfMemoryError；
 * 没那么严重的时候也会引发比较频繁的full GC从而影响稳定运行时的性能。
 *
 */
public class TestGroovyShell {
    // see if the number of loaded class keeps growing when
    // using GroovyShell.parse
    public static void test() {
        GroovyShell shell = new GroovyShell();
        String scriptText = "def mul(x, y) { x * y }\nprintln mul(5, 7)";

        while (true) {
            Script script = shell.parse(scriptText);
            Object result = script.run();
        }


    }

    public static void main(String[] args) {

        while(true)
        {
        try {
            ScriptEngineManager factory = new ScriptEngineManager();
            //每次生成一个engine实例
            ScriptEngine engine = factory.getEngineByName("groovy");
            System.out.println(engine.toString());
            //assert engine != null;
            //javax.script.Bindings
            Bindings binding = engine.createBindings();
            binding.put("date", new Date());
            //如果script文本来自文件,请首先获取文件内容
            engine.eval("def getTime(){return date.getTime();}",binding);
            engine.eval("def sayHello(name,age){return 'Hello,I am ' + name + ',age' + age;}");
            Long time = (Long)((Invocable)engine).invokeFunction("getTime", null);
            System.out.println(time);
            String message = (String)((Invocable)engine).invokeFunction("sayHello", "zhangsan",new Integer(12));
            System.out.println(message);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        }

//        try {
//            System.in.read();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        test();
    }
}
