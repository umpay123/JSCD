package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class GenFormatRandom {
	/**
	 * 获得与非负整数num位数相同且不大于num的非负整数随机数
	 * 
	 */
	public static String run(String num) {
		String formatNum = num;
		int d = 0;
		StringBuilder sb = null;
		Random r = new Random();
		if (formatNum != null && !formatNum.isEmpty()) {
			// 判断是否为0或以0开头
			while (!formatNum.matches("^(0|[1-9][0-9]*)$")) {
				formatNum = formatNum.substring(1);
			}
			;
		}
		// 获得位数
		if (formatNum != null && !formatNum.equals("0")) {
			d = formatNum.length();
		}
		// 生成随机数
		while (sb == null
				|| (Double.parseDouble(sb.toString()) > Double
						.parseDouble(formatNum))) {
			sb = new StringBuilder();
			if (d == 0) {
				sb.append(0);
			}
			for (int i = 0; i < d; i++) {
				if (i == 0) {
					sb.append(r.nextInt(8) + 1);
				} else {
					sb.append(r.nextInt(9));
				}
			}
		}

		return sb.toString();
	}

	/**
	 * 获得与非负整数num位数相同且不大于num的非负整数随机数
	 * 
	 */
	public static String genIDcardNO() {
		String id = "";
		// 地址码 1-6
		String addressCodes[] = { "340101", "320102", "110102", "110105",
				"110101" };
		String addressCode = addressCodes[new Random()
				.nextInt(addressCodes.length - 1)];

		// 随机生成出生年月(19601230-19921230) 7-14
		String birth = randomDate("19601230", "19921230");

		// 随机生成顺序号 15-17
		String no = run("999");

		// 随机生成校验码 18
		String str = addressCode + birth + no;
		String verifyNO = getVerify(str);
//		char[] c = str.toCharArray();
//		int checkCode = (c[0] * 7 + c[1] * 9 + c[2] * 10 + c[3] * 5 + c[4] * 8
//				+ c[5] * 4 + c[6] * 2 + c[7] * 1 + c[8] * 6 + c[9] * 3 + c[10]
//				* 7 + c[11] * 9 + c[12] * 10 + c[13] * 5 + c[14] * 8 + c[15]
//				* 4 + c[16] * 2) % 11;
//		System.out.println(checkCode);
//		String checkCodeStr = "X";
//		switch (checkCode) {
//		case 0:
//			checkCodeStr = "1";
//			break;
//		case 1:
//			checkCodeStr = "0";
//			break;
//		case 2:
//			checkCodeStr = "X";
//			break;
//		case 3:
//			checkCodeStr = "9";
//			break;
//		case 4:
//			checkCodeStr = "8";
//			break;
//		case 5:
//			checkCodeStr = "7";
//			break;
//		case 6:
//			checkCodeStr = "6";
//			break;
//		case 7:
//			checkCodeStr = "5";
//			break;
//		case 8:
//			checkCodeStr = "4";
//			break;
//		case 9:
//			checkCodeStr = "3";
//			break;
//		case 10:
//			checkCodeStr = "2";
//			break;
//		}

		// 拼接身份证号码
		id = addressCode + birth + no + verifyNO;

		return id;
	}

	/**
	 * 根据17位身份证号获取验证码
	 * 
	 * @param cardId
	 *            17位身份证号
	 * @return 验证码
	 * @create by szq at 2009-7-9
	 */
	private static String getVerify(String cardId) {
		String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4",
				"3", "2" };
		String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
				"9", "10", "5", "8", "4", "2" };
		int TotalmulAiWi = 0;
		for (int i = 0; i < 17; i++) {
			TotalmulAiWi = TotalmulAiWi
					+ Integer.parseInt(String.valueOf(cardId.charAt(i)))
					* Integer.parseInt(Wi[i]);
		}
		int modValue = TotalmulAiWi % 11;
		String strVerifyCode = ValCodeArr[modValue];

		return strVerifyCode;
	}

	private static String randomDate(String beginDate, String endDate) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			Date start = format.parse(beginDate);// 开始日期
			Date end = format.parse(endDate);// 结束日期
			if (start.getTime() >= end.getTime()) {
				return null;
			}
			long date = random(start.getTime(), end.getTime());

			return format.format(new Date(date));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static long random(long begin, long end) {
		long rtnn = begin + (long) (Math.random() * (end - begin));
		if (rtnn == begin || rtnn == end) {
			return random(begin, end);
		}
		return rtnn;
	}

	public static void main(String[] args) {
		 while(true){
			 System.out.println(genIDcardNO());
		 }
	}
}
