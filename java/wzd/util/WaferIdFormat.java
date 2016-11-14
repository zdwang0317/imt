package wzd.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WaferIdFormat {
	// 输入格式 String c = "01020304050607080910111213141516171819202122232425";
	public static List<String> getWaferIdFromChipmos(List<String> list,
			String str) {
		int length = str.length();
		if (length == 2) {
			list.add(str);
		} else {
			list.add(str.substring(0, 2));
			getWaferIdFromChipmos(list, str.substring(2));
		}
		return list;
	}
	
	public static List<String> getWaferIdFromSmic(String str) {
		List<String> list = new ArrayList<String>();
		for(String s:str.split("\\.")){
			list.add(s);
		}
		return list;
	}
	
	public static List<String> getWaferIdFromHlmc(String str) {
		List<String> list = new ArrayList<String>();
		for(String s:str.split(";")){
			list.add(s);
		}
		return list;
	}

	// 输入格式 String b = "1-3,5,6,11-16";
	public static List<String> getWaferIdList(String str) {
		List<String> widlist = new ArrayList<String>();
		if (str.contains(",")) {
			String[] c = str.split(",");
			for (String s : c) {
				analysis(widlist, s);
			}
		}else{
			analysis(widlist, str);
		}
		return widlist;
	}

	public static void analysis(List<String> array, String str) {
		if (str.contains("-")) {
			String[] nums = str.split("-");
			int firstNum = Integer.valueOf(nums[0]);
			int lastNum = Integer.valueOf(nums[1]);
			for (int i = firstNum; i <= lastNum; i++) {
				array.add(ToolsUtil.formatString(String.valueOf(i)));
			}
		} else {
			array.add(ToolsUtil.formatString(str));
		}
	}
	
	public static Map<String,String> getWaferIdMap(String str) {
		Map<String,String> widmap = new HashMap<String,String>();
		String[] c = str.split(",");
		for (String s : c) {
			analysis(widmap, s);
		}
		return widmap;
	}
	public static void analysis(Map<String,String> widmap, String str) {
		if (str.contains("-")) {
			String[] nums = str.split("-");
			int firstNum = Integer.valueOf(nums[0]);
			int lastNum = Integer.valueOf(nums[1]);
			for (int i = firstNum; i <= lastNum; i++) {
				widmap.put(ToolsUtil.formatString(String.valueOf(i)),"BingGo");
			}
		} else {
			widmap.put(ToolsUtil.formatString(str),"BingGo");
		}
	}
	
	public static String getMainLot(String lid){
		int pointNumber = lid.indexOf(".");
		if(pointNumber>=0){
			lid = lid.substring(0, lid.indexOf("."));
		}
		return lid;
	}
}
