package practice1_2;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HelloClassLoader extends ClassLoader {
    public static void main(String[] args) {
        try {
            Class<?> helloClass = new HelloClassLoader().findClass("Hello");
            Method helloMethod = helloClass.getMethod("hello");
            Object obj = helloClass.newInstance();
            helloMethod.invoke(obj);//Hello, classLoader!
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] helloBytes = Files.readAllBytes(Paths.get("D:\\WorkSpace\\Java-training-camp\\JAVA-000\\Week_01\\Hello.xlass"));
            byte[] bytes = new byte[helloBytes.length];
            for (int i = 0; i < helloBytes.length; i++) {
                bytes[i] = (byte) (255 - helloBytes[i]);
            }
            return defineClass(name, bytes, 0, helloBytes.length);
        } catch (IOException e) {
            e.printStackTrace();
            return super.findClass(name);
        }
    }

    /**
     * $ javap -c HelloClassLoader.class
     * ========================================================================
     * Compiled from "HelloClassLoader.java"
     * public class practice1_2.HelloClassLoader extends java.lang.ClassLoader {
     *   public practice1_2.HelloClassLoader();
     *     Code:
     *        0: aload_0
     *        1: invokespecial #1                  // Method java/lang/ClassLoader."<init>":()V
     *        4: return
     *
     *   public static void main(java.lang.String[]);
     *     Code:
     *        0: new           #2                  // class practice1_2/HelloClassLoader
     *        3: dup
     *        4: invokespecial #3                  // Method "<init>":()V
     *        7: ldc           #4                  // String Hello
     *        9: invokevirtual #5                  // Method findClass:(Ljava/lang/String;)Ljava/lang/Class;
     *       12: astore_1
     *       13: aload_1
     *       14: ldc           #6                  // String hello
     *       16: iconst_0
     *       17: anewarray     #7                  // class java/lang/Class
     *       20: invokevirtual #8                  // Method java/lang/Class.getMethod:(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
     *       23: astore_2
     *       24: aload_1
     *       25: invokevirtual #9                  // Method java/lang/Class.newInstance:()Ljava/lang/Object;
     *       28: astore_3
     *       29: aload_2
     *       30: aload_3
     *       31: iconst_0
     *       32: anewarray     #10                 // class java/lang/Object
     *       35: invokevirtual #11                 // Method java/lang/reflect/Method.invoke:(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
     *       38: pop
     *       39: goto          47
     *       42: astore_1
     *       43: aload_1
     *       44: invokevirtual #17                 // Method java/lang/ReflectiveOperationException.printStackTrace:()V
     *       47: return
     *     Exception table:
     *        from    to  target type
     *            0    39    42   Class java/lang/InstantiationException
     *            0    39    42   Class java/lang/IllegalAccessException
     *            0    39    42   Class java/lang/ClassNotFoundException
     *            0    39    42   Class java/lang/NoSuchMethodException
     *            0    39    42   Class java/lang/reflect/InvocationTargetException
     *
     *   protected java.lang.Class<?> findClass(java.lang.String) throws java.lang.ClassNotFoundException;
     *     Code:
     *        0: ldc           #18                 // String D:\WorkSpace\Java-training-camp\JAVA-000\Week_01\Hello.xlass
     *        2: iconst_0
     *        3: anewarray     #19                 // class java/lang/String
     *        6: invokestatic  #20                 // Method java/nio/file/Paths.get:(Ljava/lang/String;[Ljava/lang/String;)Ljava/nio/file/Path;
     *        9: invokestatic  #21                 // Method java/nio/file/Files.readAllBytes:(Ljava/nio/file/Path;)[B
     *       12: astore_2
     *       13: aload_2
     *       14: arraylength
     *       15: newarray       byte
     *       17: astore_3
     *       18: iconst_0
     *       19: istore        4
     *       21: iload         4
     *       23: aload_2
     *       24: arraylength
     *       25: if_icmpge     47
     *       28: aload_3
     *       29: iload         4
     *       31: sipush        255
     *       34: aload_2
     *       35: iload         4
     *       37: baload
     *       38: isub
     *       39: i2b
     *       40: bastore
     *       41: iinc          4, 1
     *       44: goto          21
     *       47: aload_0
     *       48: aload_1
     *       49: aload_3
     *       50: iconst_0
     *       51: aload_2
     *       52: arraylength
     *       53: invokevirtual #22                 // Method defineClass:(Ljava/lang/String;[BII)Ljava/lang/Class;
     *       56: areturn
     *       57: astore_2
     *       58: aload_2
     *       59: invokevirtual #24                 // Method java/io/IOException.printStackTrace:()V
     *       62: aload_0
     *       63: aload_1
     *       64: invokespecial #25                 // Method java/lang/ClassLoader.findClass:(Ljava/lang/String;)Ljava/lang/Class;
     *       67: areturn
     *     Exception table:
     *        from    to  target type
     *            0    56    57   Class java/io/IOException
     * }
     */
}
