package com.heking.qsy.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class JsonFile {

    public String PASSWORD = "javax.crypto.spec.DESKeySpec";

    /**
     * 将json写入到文件中储存
     *
     * @param json json字符串
     * @param file 文件地址
     * @throws Exception 抛出处理异常
     */
    public void FileJson(String json, File file) throws Exception {
        byte[] byt = json.getBytes();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(encrypt(byt, getPassword(PASSWORD)));
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    public void FileJson(File file, String html) throws Exception {
        byte[] byt = html.getBytes();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(byt);
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    /**
     * 读取文件流获取json
     *
     * @param file
     * @return
     * @throws Exception
     */
    public byte[] getFlieJson(File file) throws Exception {
        byte[] byt = new byte[1024 * 4];
        int len = 0;
        FileInputStream fileInputStream = new FileInputStream(file);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while ((len = fileInputStream.read(byt)) != -1) {
            byteArrayOutputStream.write(byt, 0, len);
        }
        fileInputStream.close();
        byte[] data = byteArrayOutputStream.toByteArray();

        return decrypt(data, getPassword(PASSWORD));
    }

    /**
     * DES 加密
     *
     * @param datasource byte[]
     * @param password   String
     * @return byte[]
     */
    public byte[] encrypt(byte[] datasource, String password) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            //创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            //Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            //用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            //现在，获取数据并加密
            //正式执行加密操作
            return cipher.doFinal(datasource);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * DES 解密
     *
     * @param src      byte[]
     * @param password String
     * @return byte[]
     * @throws Exception
     */
    public byte[] decrypt(byte[] src, String password) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom random = new SecureRandom();
        // 创建一个DESKeySpec对象
        DESKeySpec desKey = new DESKeySpec(password.getBytes());
        // 创建一个密匙工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        // 将DESKeySpec对象转换成SecretKey对象
        SecretKey securekey = keyFactory.generateSecret(desKey);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, random);
        // 真正开始解密操作
        return cipher.doFinal(src);
    }

    private String getPassword(String password) {
        int pa = password.hashCode();
        int ms = pa + "".length();
        return pa % 8 == 0 ? pa + "" : (pa * 8000000) + "";
    }

}
