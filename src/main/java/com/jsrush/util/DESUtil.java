package com.jsrush.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DESUtil {
	private static Logger log = LoggerFactory.getLogger(DESUtil.class);

	private static final String DES = "DES";
	private static final String DES_CBC = "DES/CBC/PKCS5Padding";
	private static final String DES_ECB = "DES/ECB/NoPadding";//ISO10126Padding

	public static byte[] generateKey(String algorithm) throws NoSuchAlgorithmException {
		// DES算法要求有一个可信任的随机数源  
		SecureRandom sr = new SecureRandom();
		// 为我们选择的DES算法生成一个KeyGenerator对象 
		KeyGenerator kg = KeyGenerator.getInstance(algorithm);
		kg.init(sr);
		// 生成密匙  
		SecretKey key = kg.generateKey();
		return key.getEncoded();
	}

	// 保存生成的密钥  
	public static void storeSecretKey(String keyFile) {
		FileOutputStream out = null;
		ObjectOutputStream oout = null;
		try {
			SecureRandom sr = new SecureRandom();
			KeyGenerator keyGenerator = KeyGenerator.getInstance(DES);
			keyGenerator.init(sr);
			SecretKey secretKey = keyGenerator.generateKey();
			out = new FileOutputStream(keyFile);
			oout = new ObjectOutputStream(out);
			oout.writeObject(secretKey);
		} catch (Exception e) {
			log.error(LogUtil.stackTraceToString(e));
		} finally {
			try {
				oout.close();
				out.close();
			} catch (IOException e) {
				log.error(LogUtil.stackTraceToString(e));
			}
		}
	}

	// 获取保存的密钥  
	public static SecretKey loadSecretKey(String keyFile) {
		SecretKey secretKey = null;
		try {
			FileInputStream in = new FileInputStream(keyFile);
			ObjectInputStream oin = new ObjectInputStream(in);
			secretKey = (SecretKey) oin.readObject();
			oin.close();
		} catch (Exception e) {
			log.error(LogUtil.stackTraceToString(e));
		}
		return secretKey;
	}

	/**
	 * 加密函数
	 *
	 * @param data
	 *            加密数据
	 * @param key
	 *            密钥
	 * @return 返回加密后的数据
	 */
	public static byte[] encrypt(byte[] data, byte[] key) {
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();
			// 从原始密钥数据创建DESKeySpec对象
			DESKeySpec dks = new DESKeySpec(key);
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成
			// 一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
			SecretKey secretKey = keyFactory.generateSecret(dks);
			// using DES in ECB mode
			Cipher cipher = Cipher.getInstance(DES_ECB);
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);
			// 执行加密操作
			byte encryptedData[] = cipher.doFinal(data);
			return encryptedData;
		} catch (Exception e) {
			log.error("DES算法，加密数据出错!");
			log.error(LogUtil.stackTraceToString(e));
		}
		return null;
	}

	/**
	* 解密函数
	*
	* @param data
	*            解密数据
	* @param key
	*            密钥
	* @return 返回解密后的数据
	*/
	public static byte[] decrypt(byte[] data, byte[] key) {
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();
			// byte rawKeyData[] = /* 用某种方法获取原始密匙数据 */;
			// 从原始密匙数据创建一个DESKeySpec对象
			DESKeySpec dks = new DESKeySpec(key);
			// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
			// 一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
			SecretKey secretKey = keyFactory.generateSecret(dks);
			// using DES in ECB mode
			Cipher cipher = Cipher.getInstance(DES_ECB, "BC");
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);
			System.out.println(cipher.getBlockSize());
			// 正式执行解密操作
			byte decryptedData[] = cipher.doFinal(data);
			return decryptedData;
		} catch (Exception e) {
			log.error("DES算法，解密出错。");
			log.error(LogUtil.stackTraceToString(e));
		}
		return null;
	}

	/**
	* 加密函数
	*
	* @param data
	*            加密数据
	* @param key
	*            密钥
	* @return 返回加密后的数据
	*/
	public static byte[] CBCEncrypt(byte[] data, byte[] key, byte[] iv) {
		try {
			// 从原始密钥数据创建DESKeySpec对象
			DESKeySpec dks = new DESKeySpec(key);
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成
			// 一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
			SecretKey secretKey = keyFactory.generateSecret(dks);
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance(DES_CBC);
			// 若采用NoPadding模式，data长度必须是8的倍数
			// Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");
			// 用密匙初始化Cipher对象
			IvParameterSpec param = new IvParameterSpec(iv);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, param);
			// 执行加密操作
			byte encryptedData[] = cipher.doFinal(data);
			return encryptedData;
		} catch (Exception e) {
			log.error("DES算法，加密数据出错!");
			log.error(LogUtil.stackTraceToString(e));
		}
		return null;
	}

	/**
	* 解密函数
	*
	* @param data
	*            解密数据
	* @param key
	*            密钥
	* @return 返回解密后的数据
	*/
	public static byte[] CBCDecrypt(byte[] data, byte[] key, byte[] iv) {
		try {
			// 从原始密匙数据创建一个DESKeySpec对象
			DESKeySpec dks = new DESKeySpec(key);
			// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
			// 一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
			SecretKey secretKey = keyFactory.generateSecret(dks);
			// using DES in CBC mode
			Cipher cipher = Cipher.getInstance(DES_CBC);
			// 若采用NoPadding模式，data长度必须是8的倍数
			// Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");
			// 用密匙初始化Cipher对象
			IvParameterSpec param = new IvParameterSpec(iv);
			cipher.init(Cipher.DECRYPT_MODE, secretKey, param);
			// 正式执行解密操作
			byte decryptedData[] = cipher.doFinal(data);
			return decryptedData;
		} catch (Exception e) {
			log.error("DES算法，解密出错。");
			log.error(LogUtil.stackTraceToString(e));
		}
		return null;
	}
}