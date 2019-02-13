package com.thoughtworks.graphx.util;

import java.io.*;
import java.net.URISyntaxException;
import java.util.logging.Logger;

/**
 * 序列化 反序列化工具类
 * Created by songchanghui on 2019/2/13.
 */
public class SerializeUtil {
    public static Logger logger = Logger.getLogger(SerializeUtil.class.toString());
    /**
     * 将对象序列化到指定位置
     *
     * @param o
     * @param filePath
     */
    public static void serialize(Object o, String filePath) throws IOException, URISyntaxException {
        String fileDir = SerializeUtil.class.getClassLoader().getResource("").getPath();
        File file = new File(fileDir+filePath);
        logger.info("序列化:"+Object.class.getName()+"-->"+filePath);
        //删除已存在的序列化文件
        if(file.exists()){
            file.delete();
        }
        //创建序列化输出流
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(o);
        oos.close();
    }

    /**
     * 将指定位置文件，反序列化
     *
     * @param filePath
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static <T> T unSerialize(String filePath,Class<T> clazz)  {
        logger.info("反序列化:"+clazz.getName()+"-->"+filePath);
        String fileDir = SerializeUtil.class.getClassLoader().getResource("").getPath();
        File file = new File(fileDir+filePath);
        //反序列化对象
        ObjectInputStream ois = null;
        Object obj = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
            //接收对象是Object
            obj = ois.readObject();
            ois.close();
        } catch (IOException e) {
            logger.info("反序列化文件不存在");
            logger.info(e.getMessage());
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            logger.info("反序列化类不存在>>"+clazz.getName());
            logger.info(e.getMessage());
            e.printStackTrace();
            return null;
        }
        return  clazz.cast(obj);
    }
}
