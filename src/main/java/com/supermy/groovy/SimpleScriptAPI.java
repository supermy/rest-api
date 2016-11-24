package com.supermy.groovy;

/**
 * Created by moyong on 16/11/23.
 */

import javax.script.*;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 简单的脚本引擎使用方法
 *
 * @author jamesmo
 */
public class SimpleScriptAPI {
    public static void main(String[] args) throws ScriptException, IOException, NoSuchMethodException {
        SimpleScriptAPI simpleScriptEngine = new SimpleScriptAPI();

        // Part 1: 通用的脚本引擎用法
        ScriptEngine engine = simpleScriptEngine.getJavaScriptEngine();
        if (engine == null) {
            throw new RuntimeException("找不到JavaScript脚本执行引擎!");
        }
        engine.eval("var a = 12;");

        // Part 2: 不同脚本语言与java之间的对象绑定
        engine.put("name", "Alex");
        engine.eval("var message = 'hello ' + name");
        Object message = engine.get("message");
        System.out.println(message); // hello Alex

        // 当然也可以通过SimpleBinds对象来进行变量绑定或者通过脚本引擎的createBindings方法
        Bindings bindings = new SimpleBindings();
        bindings = engine.createBindings();
        bindings.put("hobby1", "java");
        bindings.put("hobby2", "dota");
        engine.eval("var message2 = 'I like ' + hobby1 + ' and ' + hobby2", bindings);
        // 使用binding来绑定的变量只能在脚本语言内部是使用，java语言内获取不到对应的变量
        System.out.println(engine.get("message2"));// null
        System.out.println(engine.get("hobby1"));// null
        System.out.println(engine.get("hobby2"));// null
        engine.put("hobby1", "java");
        engine.put("hobby2", "dota");
        engine.eval("var message2 = 'I like ' + hobby1 + ' and ' + hobby2");
        System.out.println(engine.get("message2"));   // I like java and dota
        System.out.println(engine.get("hobby1"));      // java
        System.out.println(engine.get("hobby2"));      // dota

        // Part 3: 脚本执行上下文
        // ScriptContext的setReader/setWriter/setErrorWriter可以分别设置脚本执行时候的输入来源，输出目的地和错误输出目的地
        ScriptContext context = engine.getContext();
        context.setWriter(new FileWriter("output.txt"));
        engine.eval("var a = 13");
        // ScriptContext中也有setAttribute和getAttribute方法来自定义属性。属性有不同的作用域之分。
        // 每个作用域都以一个对应的整数表示其查找顺序，该整数越小，说明查找时的顺序更优先。
        // 因此在设置属性时需显示的指定所在的作用域，在获取属性的时候可以指定查找的作用域。也可以选择根据作用域优先级
        // 自动进行查找。
        // 但是脚本执行上下文所能包含的作用域可以通过 getScopes 方法得到而不能随意指定
        System.out.println(context.getScopes());// [100, 200]
        // ScriptContext预先定义了两个作用域: ENGINE_SCOPE(当前脚本引擎) 和 GLOBAL_SCOPE(从同一引擎工厂创建出来的所有脚本引擎)，前者的优先级更高
        context.setAttribute("name", "Alex", ScriptContext.GLOBAL_SCOPE);
        context.setAttribute("name", "Bob", ScriptContext.ENGINE_SCOPE);
        System.out.println(context.getAttribute("name"));// Bob
        // ScriptContext的setbindings方法设置的语言绑定对象会影响到ScriptEngine在执行脚本时的变量解析。
        // ScriptEngine的put和get方法所操作的实际上就是ScriptContext中作用域为ENGINE_SCOPE的语言绑定对象。
        // 从ScriptContext中得到语言绑定对象之后，可以直接对这个对象进行操作。如果在ScriptEngine的eval中没有
        // 指明所使用的语言绑定对象，实际上起作用的是ScriptContext中作用域为ENGINE_SCOPE的语言绑定对象。
        Bindings binding1 = engine.createBindings();
        binding1.put("name", "Alex");
        context.setBindings(binding1, ScriptContext.GLOBAL_SCOPE);
        Bindings binding2 = engine.createBindings();
        binding2.put("name", "Bob2");
        context.setBindings(binding2, ScriptContext.ENGINE_SCOPE);
        System.out.println(engine.get("name"));// Bob2
        Bindings binding3 = context.getBindings(ScriptContext.ENGINE_SCOPE);
        binding3.put("name", "Alex2");
        System.out.println(engine.get("name"));// Alex2
        context.setAttribute("name", "Bob3", ScriptContext.ENGINE_SCOPE);
        System.out.println(engine.get("name"));// Bob3

        // Part 4: 脚本的编译
        // 脚本语言一般是解释执行的，脚本引擎在运行时需要先解析脚本之后再执行。一般来说
        // 通过解释执行的方式运行脚本的速度比先编译再运行会慢一些。所以对于需要多次执行的脚本，我们
        // 可以选择先编译，以防止重复解析。不是所有的脚本语言都支持对脚本进行编译，如果脚本支持
        // 编译，他会实现 javax.script.Compilable接口。编译的结果用CompiledScript来表示。
        if (engine instanceof Compilable) {
            CompiledScript script = ((Compilable) engine).compile("var a = 12; b = a * 3;");
            script.eval();
        }

        // Part 5: 方法调用
        // 有些脚本引擎允许使用者单独调用脚本中的某个方法。支持这种调用方法的脚本引擎可以实现
        // javax.script.Invocable 接口。通过Invocable接口既可以调用脚本中的顶层方法，也可一
        // 调用对象中的成员方法。如果脚本中的顶层方法或者对象中的成员方法实现了java中的接口，
        // 可以通过Invocable接口中的方法来获取及脚本中对应的java接口 的实现对象。这样就可以
        // 在java中定义借口，在脚本中实现接口。程序中使用该接口的其他部分代码并不知道接口是
        // 由脚本来实现的。
        String scriptText = "function greet(name) {return 'hello ' + name; }";
        engine.eval(scriptText);
        Invocable invocable = (Invocable) engine;
        System.out.println(invocable.invokeFunction("greet", "Alex"));  // hello Alex

        // 如果调用的是脚本中对象的成员方法，则需要用invokeMethod.
        scriptText = "var obj = {getGreeting: function(name) {return 'hello ' + name;}};";
        engine.eval(scriptText);
        Object scope = engine.get("obj");
        System.out.println(invocable.invokeMethod(scope, "getGreeting", "Bob"));  // hello Bob

        // 在脚本中实现接口
        scriptText = "function getGreeting(name) {return 'Hello ' + name;}";
        engine.eval(scriptText);
        Greet greet = invocable.getInterface(Greet.class);// 接口必须是public类型的
        System.out.println(greet.getGreeting("KiDe"));
    }

    private ScriptEngine getJavaScriptEngine() {
        ScriptEngineManager manager = new ScriptEngineManager();
        // PS： 通过脚本引擎管理者来获取对应引擎，有三种方法：一是通过getEngineByName（这时只能是 javascript 或者
        // JavaScript）
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        // 第二种方法是通过 getEngineByExtension (这时候只能是全小写的js)
        // engine = manager.getEngineByExtension("js");
        // 第三种方法是通过 getEngineByMimeType (这时候也必须是全小写的 text/javascript)
        // engine = manager.getEngineByMimeType("text/javascript");

        return engine;
    }
}

