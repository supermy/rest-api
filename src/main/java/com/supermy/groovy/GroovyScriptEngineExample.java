package com.supermy.groovy;

/**
 * Created by moyong on 16/11/24.
 */
import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;

public class GroovyScriptEngineExample {


    public static void main(String args[]) {
        try {
            GroovyScriptEngine engine = new GroovyScriptEngine("");

            Binding binding = new Binding();

            binding.setVariable("language", "Groovy");

            Object value =engine.run("/groovy/SimpleScript.groovy", binding);

            assert value.equals("The End");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
