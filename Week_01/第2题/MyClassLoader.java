package week1;

import java.io.*;

public class MyClassLoader extends ClassLoader {

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] myClassFileData = loadClassFile(name);
        return defineClass(name, myClassFileData, 0, myClassFileData.length);
    }

    private byte[] loadClassFile(String name) {

        byte[] bytes = null;

        InputStream in = null;
        OutputStream out = null;

        try {
            //此处绝对地址省略一部分
            in = new FileInputStream(new File("xx/src/week1/" + name + ".xlass"));
            out = new ByteArrayOutputStream();
            int length;
            while (-1 != (length = in.read())) {
                length = 255 - length;
                out.write(length);
            }
            bytes = ((ByteArrayOutputStream) out).toByteArray();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bytes;
    }
}
