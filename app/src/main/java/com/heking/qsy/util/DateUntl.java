
package com.heking.qsy.util;

import java.util.ArrayList;

public class DateUntl {

/**
 * 计算年
 * @return
 */
public static ArrayList<String> getYear(int y){
	ArrayList<String> yearlist=new ArrayList<String>();
	for(int i=1900;i<=y;i++){
		yearlist.add(i+"");
	}
	return yearlist;
}

/**
 * 计算月
 * @return 
 */
public static  ArrayList<String> getMonth(){
	ArrayList<String> monthlist=new ArrayList<String>();
	for(int i=1;i<=12;i++){
		if(i<10){
			
			monthlist.add("0"+i);
		}else{
		monthlist.add(i+"");
		}
	}
	return monthlist;
}
/**
 * 计算日
 * @param year 年
 * @param mont  月
 * @return
 */
public  static  ArrayList<String> getDaytime(String year,String mont){
	int mYear=Integer.parseInt(year);
	int mMonthlist=Integer.parseInt(mont);
	int max=(mMonthlist==1||mMonthlist==3||mMonthlist==5||mMonthlist==7||mMonthlist==8||mMonthlist==10||mMonthlist==12)?31:(mMonthlist==2)?(mYear%4==0)?29:28:30;
			ArrayList<String> daytimelist=new ArrayList<String>();
	for(int i=1;i<=max;i++){
		if(i<10){
			
			daytimelist.add("0"+i);
		}else{
		daytimelist.add(i+"");
		}
	}
	if(max==29){
		daytimelist.add("");
	}
	return daytimelist;
}

}
