package cn.edu.zucc.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashKIt { //加密的类
    private static java.security.SecureRandom random=new java.security.SecureRandom();
    public static String md5(String srcStr)
    {
        return Hash("MD5",srcStr);
    }
    public static String sha1(String srcStr)
    {
        return Hash("SHA-1",srcStr);
    }
    public static String sha256(String srcStr)
    {
        return Hash("SHA-256",srcStr);
    }
    public static String sha384(String srcStr)
    {
        return Hash("SHA-384",srcStr);
    }
    public static String sha512(String srcStr)
    {
        return Hash("SHA-512",srcStr);
    }

    public static String Hash(String algorithm,String srcStr)
    {
        try
        {
            StringBuilder result=new StringBuilder();
            MessageDigest md=MessageDigest.getInstance(algorithm);
            byte[] bytes=md.digest(srcStr.getBytes("utf-8"));
            for(byte b:bytes)
            {
                String hex= Integer.toHexString(b&0xFF);
                if(hex.length()==1)
                {
                    result.append("0");
                }
                result.append(hex);
            }
            return result.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static String toHex(byte[] bytes)
    {
        StringBuilder result=new StringBuilder();
        for(byte b:bytes)
        {
            String hex=Integer.toHexString(b&0xFF);
            if(hex.length()==1)
            {
                result.append("0");
            }
            result.append(hex);
        }
        return result.toString();
    }
    public static String generateSalt(int numberOfBytes)
    {
        byte[] salt=new byte[numberOfBytes];
        random.nextBytes(salt);
        return toHex(salt);
    }
}
