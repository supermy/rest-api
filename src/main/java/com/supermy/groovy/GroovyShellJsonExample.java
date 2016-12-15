package com.supermy.groovy;

/**
 * Created by moyong on 16/11/24.
 */

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 测试内存是否存在问题
 * 很长时间之后
 * Exception: java.lang.OutOfMemoryError thrown from the UncaughtExceptionHandler in thread "main"
 *
 * 位置前移解决问题
 *     static Map<String, Object> scriptCache = new ConcurrentHashMap<String, Object>();
 *
 */
public class GroovyShellJsonExample {
    static final  Map<String, Object> scriptCache = new ConcurrentHashMap<String, Object>();

    public static void main(String args[]) {

//        oral();
        //JSON 的测试例子
        int i=0;
        while (true){
            i++;
        String json="{ \"name\": \"James Mo"+i+"\" }";
        Binding binding = new Binding();
        binding.setVariable("p1", "200");
        binding.setVariable("p2", json);


            try {
//            String script = "println\"Welcome to $language\"; y = x * 2; z = x * 3; return x ";
//            Object hello = GroovyShellJsonExample.getShell("hello", script, binding);
                String scriptname="jsontest";
                String script = "import groovy.json.*\n" +
                        "\n" +
                        "def (value1, value2) = '1128-2'.tokenize( '-' )\n"+
                        "println value1 \n"+
                        "println value2 \n"+
                        "def jsonSlurper = new JsonSlurper();\n" +
                        "def object = jsonSlurper.parseText(p2);\n" +
                        "\n" +
                        "assert object instanceof Map;\n" +
                        //"assert object.name == 'James Mo';\n" +
                        "\n" +
                        "println p1;\n" +
                        "println p2;\n" +
                        "\n" +
                        "return object.name=='James Mo';";
//            File script = new ClassPathResource("/groovy/flume-rule.groovy").getFile();

                Object hello = GroovyShellJsonExample.getShell(scriptname, script, binding);

                System.out.println(hello);
                System.out.println(i);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * 为Groovy Script增加缓存
     * 解决Binding的线程安全问题
     *
     * @return
     */
    public static Object getShell(String cacheKey, String script,Binding binding) {




        Object scriptObject = null;
        try {


            Script shell = null;
            if (scriptCache.containsKey(cacheKey)) {
                shell = (Script) scriptCache.get(cacheKey);
            } else {
                System.out.println("===============");
                shell = new GroovyShell().parse(script);
                scriptCache.put(cacheKey, shell);
//                shell = cache(cacheKey, script);
            }

            //shell.setBinding(binding);
            //scriptObject = (Object) shell.run();

            scriptObject = (Object) InvokerHelper.createScript(shell.getClass(), binding).run();

            // clear
            binding.getVariables().clear();
            binding = null;

            // Cache
            if (!scriptCache.containsKey(cacheKey)) {
                //shell.setBinding(null);
                scriptCache.put(cacheKey, shell);
            }
        } catch (Throwable t) {
            t.printStackTrace();
            //System.out.println("groovy script eval error. script: " + script, t);
        }

        return scriptObject;
    }

    /**
     * 传统调用方式；
     */
    private static void oral() {
        Binding binding = new Binding();

        binding.setVariable("x", 10);

        binding.setVariable("language", "Groovy");

        GroovyShell shell = new GroovyShell(binding);

        Object value = shell.evaluate
                ("println\"Welcome to $language\"; y = x * 2; z = x * 3; return x ");

        assert value.equals(10);

        assert binding.getVariable("y").equals(20);

        assert binding.getVariable("z").equals(30);
    }
}
