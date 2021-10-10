package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static void main(String[] args) {
		System.out.println("안녕");
	}
	/**
	 * 날짜, 시간 출력하기
	 * @param fm 날짜 출력 형식
	 * @return 날짜 출력

	 */
	public static String getDateTime(String fm) {
		
		Date today = new Date();
		System.out.println(today);
		
		SimpleDateFormat date = new SimpleDateFormat(fm);
		
		return date.format(today);
	}
	
	/**
	 * 날짜, 시간 출력하기
	 * @return 기본값은 년, 월, 일
	 */
	public static String getDateTime() {

		return getDateTime("yyyy.MM.dd/hh:mm:ss");

	}
}
