package wzd.action.pi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import util.excel.ExcelUtils;
import util.excel.JsGridReportBase;
import util.excel.TableData;
import wzd.action.BaseAction;
import wzd.model.pi.TturnkeyDetail;
import wzd.pageModel.pi.WipDetailUnique;
import wzd.service.IWipService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Namespace("/")
@Action(value = "wipDetailUniqueAction")
public class WipDetailUniqueAction extends BaseAction implements ModelDriven<WipDetailUnique>{

	private WipDetailUnique wipDetailUnique = new WipDetailUnique();
	@Override
	public WipDetailUnique getModel() {
		// TODO Auto-generated method stub
		return wipDetailUnique;
	}

	private IWipService wipService;
	
	public IWipService getWipService() {
		return wipService;
	}
	@Autowired
	public void setWipService(IWipService wipService) {
		this.wipService = wipService;
	}
	
	public void datagrid() {
//		super.writeJson(wipService.datagridOfWipDetailUnique(wipDetailUnique));
		super.writeJson(wipService.datagridOfWipDetailUniqueByJdbc(wipDetailUnique));
	}
	
	public void addWip() {
//		super.writeJson(wipService.datagridOfWipDetailUnique(wipDetailUnique));
		super.writeJson(wipService.addWip(wipDetailUnique));
	}
	public void excelToUniqueWip(){
		List<TturnkeyDetail> list = wipService.getListOfTwipDetail(wipDetailUnique);
		ActionContext ctx = ActionContext.getContext();       
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);       
		HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
		try {
			response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode("WipData", "UTF-8"))));
			response.setHeader("Connection", "close");
			response.setHeader("Content-Type", "application/vnd.ms-excel");
			String title = "Unique Wip";
	        String[] hearders = new String[] {"Lot Id","Wafer Id","PartNo", "CPN", "IPN","IPN(new)","TPN","Status"};//表头数组
	        String[] fields = new String[] {"lid", "wid","pn", "cpn", "ipn","ipn_new","tpn","status"};//People对象属性数组
	        TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders),fields);
	        JsGridReportBase report = new JsGridReportBase(request, response);
	        report.exportToExcel(title, "admin", td,"");
			//wb.write(response.getOutputStream());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void excelToUniqueWipByJdbc(){
		List<WipDetailUnique> list = wipService.getListOfTwipDetailByJdbc(wipDetailUnique);
		ActionContext ctx = ActionContext.getContext();       
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);       
		HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
		try {
			response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode("WipData", "UTF-8"))));
			response.setHeader("Connection", "close");
			response.setHeader("Content-Type", "application/vnd.ms-excel");
			String title = "Unique Wip";
	        String[] hearders = new String[] {"Lot Id","Wafer Id","PartNo", "CPN", "IPN","IPN(new)","TPN","Status","TPN FLow","Wafer Type","Pi Status"};//表头数组
	        String[] fields = new String[] {"lid", "wid","pn", "cpn", "ipn","ipn_new","tpn","status","tpnFlow","waferType","piStatus"};//People对象属性数组
	        TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders),fields);
	        JsGridReportBase report = new JsGridReportBase(request, response);
	        report.exportToExcel(title, "admin", td,"");
			//wb.write(response.getOutputStream());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
