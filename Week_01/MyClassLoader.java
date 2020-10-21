package com.skycomm.websocket.demo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;

public class MyClassLoader extends ClassLoader{
	//指定路径
    private String path;
    public MyClassLoader(String classPath){
        path=classPath;
    }
 
    /**
     * 重写findClass方法
     * @param name 是我们这个类的全路径
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class log = null;
        // 获取该class文件字节码数组
        byte[] classData = getData();
        for(int i=0;i<classData.length;i++){
        	classData[i]=(byte) (255-classData[i]);
        }
        if (classData != null) {
            // 将class的字节码数组转换成Class类的实例
            log = defineClass(name, classData, 0, classData.length);
        }
        return log;
    }
 
    /**
     * 将class文件转化为字节码数组
     * @return
     */
    private byte[] getData() {
        File file = new File(path);
        if (file.exists()){
            FileInputStream in = null;
            ByteArrayOutputStream out = null;
            try {
                in = new FileInputStream(file);
                out = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int size = 0;
                while ((size = in.read(buffer)) != -1) {
                    out.write(buffer, 0, size);
                }
 
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return out.toByteArray();
        }else{
            return null;
        }
    }
    public static void main(String[] args) throws Exception {
    	 String helloPath ="H:/homework/Hello/Hello.xlass";
    	 MyClassLoader myClassLoader = new MyClassLoader(helloPath);
         Class<?> cla = myClassLoader.loadClass("Hello");
         Method method = cla.getDeclaredMethod("hello", null);
         Object obj = cla.newInstance();
         method.invoke(obj, null);

    }
}
