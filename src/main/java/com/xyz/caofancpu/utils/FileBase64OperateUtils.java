package com.xyz.caofancpu.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

/**
 * Created by caofanCPU on 2018/2/26.
 */
public class FileBase64OperateUtils {
    
    /**
     * LOG
     */
    private static final Logger logger = LoggerFactory.getLogger(FileBase64OperateUtils.class);
    
    /**
     * 生成唯一序列标识
     *
     * @return
     */
    public static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    
    /**
     * 将文件转成base64 字符串
     *
     * @param path 文件路径
     * @return
     * @throws Exception
     */
    public static String encodeBase64(String path)
            throws Exception {
        byte[] buffer = FileUtils.readFileToByteArray(new File(path));
        return Base64.encodeBase64String(buffer);
    }
    
    /**
     * 保存字符串到指定文件
     *
     * @param content
     * @param fileFullPath
     * @throws FileNotFoundException
     */
    public static void writeStringToFile(String content, String fileFullPath)
            throws IOException {
        File file = new File(fileFullPath);
        FileUtils.writeStringToFile(file, content);
    }
    
    /**
     * 复制文件
     *
     * @param sourceFileFullPath
     * @param destFileFullPath
     * @throws IOException
     */
    public static void copyFile(String sourceFileFullPath, String destFileFullPath)
            throws IOException {
        File sourceFile = new File(sourceFileFullPath);
        File destFile = new File(destFileFullPath);
        FileUtils.copyFile(sourceFile, destFile);
    }
    
    public static String readFileToString(String fileFullPath)
            throws IOException {
        File file = new File(fileFullPath);
        return FileUtils.readFileToString(file);
    }
    
    /**
     * 将base64字符解码保存文件
     *
     * @param base64Code
     * @param fileFullPath
     * @throws Exception
     */
    
    public static void decodeBase64WithSave(String base64Code, String fileFullPath)
            throws IOException {
        byte[] buffer = Base64.decodeBase64(base64Code);
        FileUtils.writeByteArrayToFile(new File(fileFullPath), buffer);
    }
    
    /**
     * 文件反序列化到对象
     *
     * @param fileFullPath
     * @param claszz
     * @param <T>
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     * @deprecated 应从Redis中获取对象, 反序列化
     */
    @Deprecated
    public static <T> T deserializeFromFile(String fileFullPath, Class<T> claszz)
            throws IOException, ClassNotFoundException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileFullPath));
        Object sourceObj = ois.readObject();
        IOUtils.closeQuietly(ois);
        T result = BeanConvertUtil.copyProperties(sourceObj, claszz);
        return result;
    }
    
    /**
     * 对象序列化到文件
     *
     * @param obj
     * @param fileFullPath
     * @throws IOException
     * @deprecated 应使用fast-json序列化, 保存在Redis中
     */
    @Deprecated
    public static void serializeToFile(Object obj, String fileFullPath)
            throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileFullPath));
        oos.writeObject(obj);
        IOUtils.closeQuietly(oos);
    }
    
    public static void main(String[] args) {
    
    }
    
}
