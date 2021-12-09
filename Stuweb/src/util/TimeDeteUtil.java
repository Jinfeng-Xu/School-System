package util;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeDeteUtil {
	
//	2.
//	yyyy-MM-dd HH:mm:ss 指定签到时间 str1
//	yyyy-MM-dd HH:mm:ss 实际学生签到时间 str2
//	count = 0 / 1 
//
//	 (int) args:string string int 
//
//	if str2 in (str1, str1 + 10min & count = 0) ?   int return 3 <—‘签到成功’—>
//	       ：count !=0 ? int return 4 <—“请勿重复签到”—>
//	        :  int return 5 <— “不在签到时间内”—>
//	
	// 𤛭𤛭𤛭
	
	
	
	public static boolean dateEqual(String date1, String date2) {// This method is used to determine whether the class is held today.
		boolean c;	
		Format f = new SimpleDateFormat("yyyy-MM-dd");
		
		date1 = f.format(new Date());
        date2 = f.format(new Date());
        
        c = date1.equals(date2);
		return c;
	}
	
	
	public static boolean timeCompare(String time1, String time2) throws ParseException {//Compare two time directly. 	
		//if t1 early than t2: True. 反之. if equal true(如果时间一样的话，应该也算在签到时间内？
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		Date dt1 = df.parse(time1);
		Date dt2 = df.parse(time2);
		
		if (dt1.getTime() > dt2.getTime()) {
			return true;
		} else if (dt1.getTime() < dt2.getTime()) {
			return false;
		} else {		
			return true;
		}
	}
	
	public static String addTime(String time, int min) {//This function is to increase the minutes.
		
		//time's format: yyyy-MM-dd hh:mm:ss
		
		String[] t = time.split(":");// t: yyyy-MM-dd hh, mm, ss
		int newMinute = Integer.parseInt(t[1]) + min;
		String newTime;
		
		if(newMinute == 60) {
			int newHour = Integer.parseInt(t[0]) + 1;	
			newTime = newHour +":"+ "00" +":"+ t[2];
		}else {
			newTime = t[0] +":"+ newMinute +":"+ t[2];
		}
		return newTime;
			
	}
	
	public static String getDate (String str) {
		
		String[] DT = str.split(" ");
		String date = DT[0];
		
		return date;
	}
	
	public static int timeJudgement (String str1, String str2, int count) throws ParseException {//str1 签到时间 str2是给定的签到时间 这是你要的那个函数
		String str3 = addTime(str2, 10);		
		if(count != 0) {
			return 4;//You've already sign-in!
		}
		else {	
			if(dateEqual(getDate(str1),getDate(str2))) {
				System.out.println("我进入了语句");
				if(!timeCompare(str1, str3) && timeCompare(str1, str2) ) {	
					return 3;//Sign-in succeed!
				}
				else {
					return 5;//Not in sign-in time!
				}
			}
			else {
				return 5;//Not in sign-in time!
			}
		}
	}
	
	public static int timeJudgement2 (String str1, String str2) throws ParseException {//str1 签到设置时间 str2开课时间 这是你要的那个函数
		String str3 = addTime(str2, 60);	
		if(dateEqual(getDate(str1), getDate(str2))) {
			if(!timeCompare(str1, str3)&&timeCompare(str1, str2)) {
				return 1;
			}return 2;
		}return 2;
	}
}
