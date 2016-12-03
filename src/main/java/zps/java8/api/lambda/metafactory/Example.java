package zps.java8.api.lambda.metafactory;

import java.lang.invoke.*;
import java.lang.reflect.Method;

public class Example {
    public static void main(String[] args) throws Throwable {

        MethodHandles.Lookup caller = MethodHandles.lookup();

        MethodType methodType = MethodType.methodType(void.class);

        MethodType invokedType = MethodType.methodType(Runnable.class);

        Method implementation = Example.class.getDeclaredMethod("runnable");

        implementation.setAccessible(true);

        CallSite callSite = LambdaMetafactory.metafactory(caller,
                "run",
                invokedType,
                methodType,
                caller.unreflect(implementation),
                methodType);

        MethodHandle lambdaFactory = callSite.getTarget();

        Runnable runnable = (Runnable) lambdaFactory.invoke();

        runnable.run();

    }

    private static void runnable() {
        System.out.println("Example.runnable");
    }
}
