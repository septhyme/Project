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

	// ����
	public static String TRACK = "FF090900FF";
	public static String BIZHANG = "FF090901FF";

	// �������
	public static String DUOJI_PRE = "FF01";
	public static String DUOJI_SUFFIX = "FF";

	/**
	 * ��ָ���ַ���src����ÿ�����ַ��ָ�ת��Ϊ16������ʽ �磺"2B44EFD9" �C> byte[]{0x2B, 0��44, 0xEF,
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
	 * ������ASCII�ַ��ϳ�һ���ֽڣ� �磺"EF"--> 0xEF
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
