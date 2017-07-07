package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyTest {
    public static void main(String[] args) {

        Person person = new Person("Albert Einstein");

        Nameable proxy = (Nameable) Proxy.newProxyInstance(PassthroughInvocationHandler.class.getClassLoader(), new Class<?>[]{Nameable.class}, new PassthroughInvocationHandler(person));
        System.out.println("Person: " + proxy.getName());
    }
}

interface Nameable {
    String getName();
}

class Person implements Nameable {
    private String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "[name=" + name + "]";
    }
}

class PassthroughInvocationHandler implements InvocationHandler {
    private Object target;

    public PassthroughInvocationHandler(Object target) {
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Invoke: " + method);
        return method.invoke(target, args);
    }
}
