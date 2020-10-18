package week1;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyClassLoaderTest {

    public static void main(String[] args) {
        MyClassLoader myClassLoader = new MyClassLoader();
        try {
            Object object = myClassLoader.loadClass("Hello").newInstance();
            Method method = object.getClass().getMethod("hello");
            method.invoke(object);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
