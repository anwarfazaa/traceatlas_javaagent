package org.traceatlas;


import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.*;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Callable;

public class JavaAgent {

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        new AgentBuilder.Default()
                .type(ElementMatchers.any())
                .transform((builder, type, classLoader, module, x_value) -> {
                    System.out.println("X Says: " + x_value);
                    return builder.method(ElementMatchers.any())
                            .intercept(MethodDelegation.to(JavaAgent.class));
                })
                .installOn(instrumentation);
    }

    public static void intercept(@Origin Method method,
                                 @AllArguments Object[] args,
                                 @SuperCall Callable<?> zuper,
                                 @This Object target) {
        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();
        System.out.println("Class: " + className);
        System.out.println("Method: " + methodName);
        System.out.println("Arguments: " + Arrays.toString(args));
    }

    public static void main(String[] args) {
        System.out.println("Trace Atlas Agent loading...");
    }
}