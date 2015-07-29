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
import wzd.model.pi.Twip;
import wzd.pageModel.DataGrid;
import wzd.pageModel.Json;
import wzd.pageModel.pi.Series;
import wzd.pageModel.pi.Wip;
import wzd.pageModel.pi.WipCompare;
import wzd.service.IWipService;
import wzd.util.UtilValidate;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Namespace("/")
@Action(value = "wipAction")
public class WipAction extends BaseAction implements ModelDriven<Wip>{
	
	//private WipDetail wipDetail = new WipDetail();
	private Wip wip = new Wip();
	
	@Override
	public Wip getModel() {
		// TODO Auto-generated method stub
		return wip;
	}
	
	private IWipService wipService;
	
	
	public IWipService getWipService() {
		return wipService;
	}
	@Autowired
	public void setWipService(IWipService wipService) {
		this.wipService = wipService;
	}
	
	/*public void datagrid() {
		super.writeJson(wipService.datagrid(wipDetail));
	}*/
	
	public void datagrid() {
//		super.writeJson(wipService.datagrid(wip));
		if(UtilValidate.isNotEmpty(wip.getErpDate())){
			super.writeJson(wipService.datagridBySql(wip));
		}else{
			super.writeJson(new DataGrid());
		}
	}
	
	public void datagridOfDetail() {
		super.writeJson(wipService.datagridOfDetail(wip));
	}
	
	public void datagridOfWip() {
		super.writeJson(wipService.datagridOfWip(wip));
	}
	
	public void datagridOfWipDetail() {
		super.writeJson(wipService.datagridOfWipDetail(wip));
	}
	
	public void analysisProductNo() {
		Json j = new Json();
//		int updateRows = wipService.analysisProductNoForSql();
		int updateRows = wipService.analysisProductNoForSqlByNewRule();
		j.setObj(updateRows);
		j.setMsg("更新行数："+updateRows);
		j.setSuccess(true);
		super.writeJson(j);
	}
	
	public void updateProductNoOfPi() {
		Json j = new Json();
		wipService.updateProductNoOfPi();
		j.setObj(null);
		j.setMsg("成功");
		j.setSuccess(true);
		super.writeJson(j);
	}
	
	public void updateProductNoOfPi2() {
		Json j = new Json();
		wipService.updateProductNoOfPi2();
		j.setObj(null);
		j.setMsg("成功");
		j.setSuccess(true);
		super.writeJson(j);
	}
	
	public void CopyDataOfWipToDbOfHistory() {
		Json j = new Json();
		wipService.CopyDataOfWipToDbOfHistoryDaily();
		j.setObj(null);
		j.setMsg("成功");
		j.setSuccess(true);
		super.writeJson(j);
	}
	
	public void showHighChart(){
		List<Series> a = wipService.getDataToCharts();
		Json j = new Json();
		j.setMsg(wipService.getTpnToXAxis());
		j.setSuccess(true);
		//j.setDataList();
		j.setDataList(a);
		super.writeJson(j);
	}
	
	public void showLogicHighChart(){
		List<Series> a = wipService.getLogicDataToCharts();
		Json j = new Json();
		j.setMsg(wipService.getLogicTpnToXAxis());
		j.setSuccess(true);
		//j.setDataList();
		j.setDataList(a);
		super.writeJson(j);
	}
	
	public void excelTest(){
		List<Twip> list = wipService.getListOfTwip(wip);
		ActionContext ctx = ActionContext.getContext();       
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);       
		HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
		try {
			response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode("WipData", "UTF-8"))));
			response.setHeader("Connection", "close");
			response.setHeader("Content-Type", "application/vnd.ms-excel");
			String title = "wip";
	        String[] hearders = new String[] {"Lot Id", "PartNo", "CPN", "IPN","Qty","Wafer Id","Stage","Status","RemLayer","HoldDate","HoldRemark","Location","Firm","Product No","TPN FLOW"};//表头数组
	        String[] fields = new String[] {"lid", "pn", "cpn", "ipn","qty","wid","stage","status","remLayer","holdDate","holdRemark","location","firm","productNo","tpnFlow"};//People对象属性数组
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
	
	public void excelToWipCompare(){
		List<WipCompare> list = wipService.getWipCompareData(wip);
		ActionContext ctx = ActionContext.getContext();       
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);       
		HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
		try {
			response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode("WipData", "UTF-8"))));
			response.setHeader("Connection", "close");
			response.setHeader("Content-Type", "application/vnd.ms-excel");
			String title = wip.getErpDate().replace("/", "-");
	        String[] hearders = new String[] {"Part No","Lot Id","Wafer ID","New Stage","Old Stage"};//表头数组
	        String[] fields = new String[] {"currentPn", "currentLid", "currentWid", "currentStage","oldStage"};//People对象属性数组
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

	public void getContentOfOption(){
		super.writeJson(wipService.getListOfOptions());
	}
	
	public void getContentOfProd(){
		super.writeJson(wipService.getListOfProd());
	}
	
	public void getContentOfCpn(){
		super.writeJson(wipService.getContentOfCpn(wip.getPn()));
	}
	

	
}
