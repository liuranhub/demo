package com.unisinsight.demo.support.question;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class FourArithmetic {
    private static List<String> SYMBOL = new ArrayList<>();
    static {
        SYMBOL.add("+");
        SYMBOL.add("-");
        SYMBOL.add("*");
        SYMBOL.add("/");
        SYMBOL.add("(");
        SYMBOL.add(")");
        SYMBOL.add("[");
        SYMBOL.add("]");
        SYMBOL.add("{");
        SYMBOL.add("}");
    }

    public static void main(String[] args) {
        doing("2+ [3.14*(4+5) + 6]*5");
    }

    public static float doing(String line){

        getArray(line);

        return 0f;
    }

    public static List<String> getArray(String line){
        List<String> result = new ArrayList<>();
        line = line.replace(" ", "");

        StringBuilder ch = new StringBuilder();
        char[] array = line.toCharArray();

        for (int index = 0; index < array.length ; index ++){
            String c = String.valueOf(array[index]);
            if (SYMBOL.contains(c)){
                if (!StringUtils.isEmpty(ch.toString())){
                    result.add(ch.toString());
                }
                result.add(c);
                ch = new StringBuilder();
            } else {
                ch.append(c);
                if (index == array.length - 1){
                    result.add(ch.toString());
                }
            }
        }

        return result;
    }

    private enum Symbol{
        ADD("+", 1),
        SUB("-", 1),
        MUL("*", 2),
        DIV("/", 2),

        PARENTHESES_LEFT("(", 3),
        PARENTHESES_RIGHT(")", 3),

        BRACKETS_LEFT("[", 4),
        BRACKETS_RIGHT("]", 4),

        BRACES_LEFT("{", 5),
        BRACES_RIGHT("}", 5),
        ;
        private Integer level;
        private String ch;

        Symbol(String ch, Integer level){
            this.ch = ch;
            this.level = level;
        }
    }
}
