package wzd.util;

import java.util.ArrayList;
import java.util.List;

public class ToolsUtil {
	
	private static List<String> waferIdList = new ArrayList<String>();
	static{
		for(int i=1;i<=25;i++){
			waferIdList.add(formatString(String.valueOf(i)));
		}
	}
	
	public static String formatString(String str){
		String rs = "";
		int arg = (int)Double.parseDouble(str);
		if(arg<10){
			rs = 0+String.valueOf(arg);
		}else{
			rs = String.valueOf(arg);
		}
		return rs;
	}

	public static List<String> getWaferIdList() {
		return waferIdList;
	}

	public static void setWaferIdList(List<String> waferIdList) {
		ToolsUtil.waferIdList = waferIdList;
	}
	
	
}
