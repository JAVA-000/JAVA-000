package week_01;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HelloClassLoader extends ClassLoader{
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        HelloClassLoader helloClassLoader = new HelloClassLoader();
        Class helloClass = (Class) helloClassLoader.findClass("Hello");
        Method method = helloClass.getMethod("hello",null);
        method.invoke(helloClass.newInstance());
    }
    public static byte[] inputStream2byte(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inputStream.read(buff, 0, 100)) > 0) {
            byteArrayOutputStream.write(buff, 0, rc);
        }
        return byteArrayOutputStream.toByteArray();
    }
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            InputStream in  = new FileInputStream(this.getClass().getResource("/week_01/Hello.xlass").getPath());
            byte[] bytes = inputStream2byte(in);
            byte[] outBytes = new  byte[bytes.length];
            for (int i =0;i<bytes.length;i++){
                outBytes[i] = (byte) (255-bytes[i]);
            }
            return defineClass(name,outBytes,0,outBytes.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

