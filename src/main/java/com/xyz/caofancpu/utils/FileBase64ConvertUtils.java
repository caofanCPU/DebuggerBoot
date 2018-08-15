package com.xyz.caofancpu.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.UUID;

/**
 * Created by caofanCPU on 2018/2/26.
 */
public class FileBase64ConvertUtils {

    /**生成唯一序列标识
     * @return
     */
    public static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    /**
     * 将文件转成base64 字符串
     * @param path 文件路径
     * @return
     * @throws Exception
     */

    public static String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return Base64.encodeBase64String(buffer);
    }

    /**
     * 将base64字符解码保存文件
     * @param base64Code
     * @param targetPath
     * @throws Exception
     */

    public static void decoderBase64File(String base64Code, String targetPath)
            throws Exception {
        byte[] buffer = Base64.decodeBase64(base64Code);
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }

    /**
     * 将base64字符保存文本文件
     * @param base64Code
     * @param targetPath
     * @throws Exception
     */

    public static void toFile(String base64Code, String targetPath)
            throws Exception {
        byte[] buffer = base64Code.getBytes();
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }

    public static void main(String[] args) {
        try {

            //生成签名的base64字符串
            String base64Code = encodeBase64File("C:\\Users\\caofanCPU\\Desktop\\OOKK\\江西百悦农资供应链有限公司(新).png");
            // 去除换行符
            String newBase64CodeStr = base64Code.replaceAll("\r|\n", "");
            System.out.println(newBase64CodeStr);
//            decoderBase64File(newBase64CodeStr, "C:\\Users\\caofanCPU\\Desktop\\Base64\\fuTmp\\借款合同.html");
//            toFile(newBase64CodeStr, "C:\\Users\\caofanCPU\\Desktop\\Base64\\fuTmp\\（编码字符串）借款合同.html");

//            System.out.println(!(true ^ true));

//            //生成模板文件的base64字符串
//            String base64Code = encodeBase64File("C:\\Users\\caofanCPU\\Desktop\\Base64\\征信授权书.html");
//            // 去除换行符
//            String newBase64CodeStr = base64Code.replaceAll("\r|\n", "");
//            System.out.println(newBase64CodeStr);
//            decoderBase64File(newBase64CodeStr, "C:\\Users\\caofanCPU\\Desktop\\Base64\\fuTmp\\（解码）征信授权书.txt");
//            toFile(newBase64CodeStr, "C:\\Users\\caofanCPU\\Desktop\\Base64\\fuTmp\\（编码字符串）征信授权书.txt");

//            List<String> testList = new ArrayList<>();
//            testList.add(null);
//            testList.add(null);
//            testList.add("121");
//            for (String areaId : testList)
//            {
//                if (areaId != null && Integer.parseInt(areaId) > 0)
//                {
//                    System.out.println(areaId);
//                    break;
//                }
//            }
//            //生成64位uuid
//            String projectCode = getUuid();
//            System.out.println(projectCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
