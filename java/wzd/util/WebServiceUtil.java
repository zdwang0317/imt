package wzd.util;

public class WebServiceUtil {
	
	public static String invokeWebService(String targetEendPoint,String function,Object[] obj){
		String result = null;
		try{
	        org.apache.axis.client.Service service =  new  org.apache.axis.client.Service();   
	        org.apache.axis.client.Call call = (org.apache.axis.client.Call) service.createCall();               
	        call.setOperationName( new  javax.xml.namespace.QName(targetEendPoint, function));   
	        call.setTargetEndpointAddress( new  java.net.URL(targetEendPoint));   
	        result = (String) call.invoke(obj);
	        //System.out.println(result);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
}
