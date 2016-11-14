package wzd.pageModel;

import java.util.ArrayList;
import java.util.List;

import wzd.pageModel.pi.Series;

public class Json implements java.io.Serializable {

	private boolean success = false;

	private String msg = "";

	private Object obj = null;
	
	private List<Series> dataList = new ArrayList<Series>();

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}

}
