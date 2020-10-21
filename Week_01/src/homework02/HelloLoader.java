package src.homework02;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Jiang Jining
 * @date 2020/10/20 23:22
 */
public class HelloLoader extends ClassLoader {
    public static void main(String[] args) {
        try {
            Class<?> hello = new HelloLoader().findClass("Hello");
            Object instance = hello.getDeclaredConstructor().newInstance();
            instance.getClass().getDeclaredMethod("hello").invoke(instance);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Path path = Paths.get("Week_01/src/homework02/Hello.xlass");
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int length = bytes.length;
        byte[] originalBytes = new byte[length];
        for (int i = 0; i < length; i++) {
            byte aByte = bytes[i];
            originalBytes[i] = (byte) (255 - aByte);
        }
        return defineClass(name, originalBytes, 0, length);
    }
}
