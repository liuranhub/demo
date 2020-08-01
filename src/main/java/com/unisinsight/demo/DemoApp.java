package com.unisinsight.demo;

import com.unisinsight.demo.vos.Person;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ApplicationObjectSupport;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@ComponentScan(basePackages = "com.unisinsight.*")
public class DemoApp {
    private static ApplicationContext applicationContext;
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        applicationContext = SpringApplication.run(DemoApp.class, args);

//        Person person = new Person("test");
//        invokeAllGetMethod(person);
    }

    public static ApplicationContext applicationContext(){
        return applicationContext;
    }

    public static Map<Boolean, Map<String, List<InvokeResult>>>  invokeAllGetMethod(final Object obj){
        Stream<Field> fieldStream = Stream.<Field>builder().build();

        Class c = obj.getClass();
        while (!c.equals(Object.class)) {
            fieldStream = Stream.concat(fieldStream, Stream.of(c.getDeclaredFields()));
            c = c.getSuperclass();
        }

        Map<Boolean, Map<String, List<InvokeResult>>> invokeResultMap =  fieldStream
                .map(DemoApp::getGetMethodNameByField)
                .map((String methodName) -> {
                    InvokeResult result = new InvokeResult();
                    result.methodName = methodName;
                    try {
                        result.value = obj.getClass().getMethod(methodName).invoke(obj);
                    } catch (NoSuchMethodException | IllegalAccessException
                            | InvocationTargetException e) {
                        result.value = null;
                    }
                    return result;
                })
                .collect(Collectors.groupingBy(invokeResult -> invokeResult.value != null,
                        Collectors.groupingBy((val) -> val.methodName)));
        return invokeResultMap;
    }

    private static String getGetMethodNameByField(Field field) {
        String firstChar = field.getName().substring(0, 1).toUpperCase();
        return "get" + firstChar + field.getName().substring(1);
    }

    private static class InvokeResult{
        private String methodName;
        private Object value;
    }


    @Test
    public void tableSizeFor() {
        Integer n = 0;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        System.out.println(1 & 2);
        System.out.println(Integer.toBinaryString(n));
    }
}
