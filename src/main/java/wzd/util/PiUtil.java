package wzd.util;

import java.util.List;


public class PiUtil {
	/*
	 * analysis ProductNo,取4位连续数字与后一位字母
	 */
	public static String filterByProductNo(String productNo) {
		String returnStr = null;
		int n = 0;
		for (int i = 0; i < productNo.length(); i++) {
			char cr = productNo.charAt(i);
			boolean check = String.valueOf(cr).matches("^[0-9]");
			if (check) {
				n++;
			} else {
				n = 0;
			}
			if (n == 4) {
				if (productNo.length() <= (i + 2)) {
					returnStr = productNo.substring(i - 3);
				} else {
					returnStr = productNo.substring(i - 3, i + 2);
				}
				break;
			}
		}
		return returnStr;
	}
	
	public static String filterByProductNoIncludeW(String productNo){
		String returnStr = null;
		int i = productNo.toUpperCase().indexOf("W");
		if(i>=0){
			boolean isNum = true;
			isNum = String.valueOf(productNo.charAt(i+1)).matches("^[0-9]");
			isNum = String.valueOf(productNo.charAt(i+2)).matches("^[0-9]");
			isNum = String.valueOf(productNo.charAt(i+3)).matches("^[0-9]");
			if(productNo.length()>=(i+5)&&isNum){
				returnStr = productNo.substring(i,i+5);
			}
		}
		return returnStr;
	}
	
	public static String filterByProductNoFirstAOrR(String productNo){
		String returnStr = null;
		String first_ = productNo.toUpperCase().substring(0, 1);
		if(first_.equals("A")||first_.equals("R")){
			returnStr = productNo;
		}
		return returnStr;
	}
	
	public static String processSpecialStage(String stage){
		String tpnflow = null;
		if(stage.equals("BAKE")){
			tpnflow = "BAK1";
		}else if(stage.equals("DIEBANK")){
			tpnflow = "DIEBANK1";
		}else if(stage.equals("DIEBANK1")){
			tpnflow = "DIEBANK2";
		}else if(stage.equals("WPOE")){
			tpnflow = "DIEBANK1";
		}else if(stage.equals("WPOE2")){
			tpnflow = "DIEBANK2";
		}else if(stage.equals("FPOE")){
			tpnflow = "DIEBANK1";
		}else if(stage.equals("FPOE2")){
			tpnflow = "DIEBANK2";
		}
		return tpnflow;
	}
	
	public static String getWidFromList(List<String> array){
		StringBuilder returnStr = new StringBuilder();
		String lastStr = "";
		boolean isLian = false;
		for(String str:array){
			if("".equals(lastStr)){
				returnStr.append(str);
				lastStr = str;
			}else{
				if((Integer.parseInt(str)-1)==Integer.parseInt(lastStr)){
					isLian = true;
				}else{
					if(isLian){
						returnStr.append("-"+lastStr);
						isLian = false;
					}
					returnStr.append(","+str);
					
				}
				lastStr = str;
			}
		}
		if(isLian){
			returnStr.append("-"+lastStr);
		}
		return returnStr.toString();
	}
	
}
