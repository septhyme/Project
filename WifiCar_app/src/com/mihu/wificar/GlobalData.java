package com.mihu.wificar;

import java.math.BigInteger;

public class GlobalData {
	public static String ip = "192.168.1.1";
	public static String CameraIp = "http://192.168.1.1:8080/?action=stream";
	public static int port = Integer.valueOf("2001");
	public static String upCmd = "FF000100FF";
	public static String downCmd = "FF000200FF";
	public static String leftCmd = "FF000300FF";
	public static String rightCmd = "FF000400FF";
	public static String stopCmd = "FF000000FF";

	// 功能
	public static String TRACK = "FF090900FF";
	public static String BIZHANG = "FF090901FF";

	// 舵机控制
	public static String DUOJI_PRE = "FF01";
	public static String DUOJI_SUFFIX = "FF";

	/**
	 * 将指定字符串src，以每两个字符分割转换为16进制形式 如："2B44EFD9" –> byte[]{0x2B, 0×44, 0xEF,
	 * 0xD9}
	 * 
	 * @param src
	 *            String
	 * @return byte[]
	 */
	public static byte[] HexString2Bytes(String src) {
		byte[] ret = new byte[src.length() / 2];
		byte[] tmp = src.getBytes();
		for (int i = 0; i < tmp.length / 2; i++) {
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	}

	/**
	 * 将两个ASCII字符合成一个字节； 如："EF"--> 0xEF
	 * 
	 * @param src0
	 *            byte
	 * @param src1
	 *            byte
	 * @return byte
	 */
	public static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
				.byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
				.byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}

	public static String shi2HexStr(int progress) {
		BigInteger target = new BigInteger(String.valueOf(progress));
		if (progress < 10) {
			return "0" + target.toString(16);
		} else if (progress >= 10 && progress < 16) {
			return "0" + target.toString(16);
		}
		return target.toString(16);
	}
}
