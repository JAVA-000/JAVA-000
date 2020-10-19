package gjz.test.jvm.classloader;

import com.sun.xml.internal.ws.util.StringUtils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <pre>
 * 自定义加载器
 * </pre>
 *
 * @version 1.00.00
 * @createDate 2020/10/17 16:18
 * @author guojiazhen
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class HelloClassLoader extends ClassLoader {

    private String classPath;
    private String fileType;


    public HelloClassLoader(String classPath, String fileType) {
        this.classPath = classPath;
        this.fileType = fileType;
    }


    public static void main(String[] args) {
        try {
            // 当前类目录下查找
            Class clz = new HelloClassLoader(HelloClassLoader.class.getClassLoader().getResource("").toString(), ".xlass")
                    .findClass("Hello");

            Method method = clz.getDeclaredMethod("hello");
            method.invoke(clz.newInstance());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }



    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        if (name == null || "".equals(name)) {
            return null;
        }

        String[] names = name.split("\\.");
        String classFileName = names[names.length - 1];
        byte[] classData = getClassData(this.classPath, classFileName + fileType);

        // 文件类型为xlass时，才作处理
        if (".xlass".equals(fileType)){

            // 此文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件
            // 还原回真正的内容
            for (int i = 0; i < classData.length; i++) {
                classData[i] = (byte) (255 - classData[i]);
            }
        }

        return defineClass(name, classData, 0, classData.length);
    }

    /**
     * 获取类文件内容字节数组
     * @param classFileName 完整类文件名
     * @return
     */
    private byte[] getClassData(String classPath, String classFileName){

        String classFilePath = classPath  + classFileName;
        File classFile = new File(classFilePath);

        if (!classFile.exists()) {
            throw new RuntimeException(String.format("文件【%s】不存在，无法加法类", classFilePath));
        } else {
            try(FileInputStream fileInputStream = new FileInputStream(classFile);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()){
                byte[] buffer = new byte[1024];

                int size;
                while((size = fileInputStream.read(buffer)) != -1){
                    byteArrayOutputStream.write(buffer, 0, size);
                }

                return byteArrayOutputStream.toByteArray();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new byte[0];
    }



}

