package wzd.action.pi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
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
import wzd.pageModel.Json;
import wzd.pageModel.pi.TurnkeyOrder;
import wzd.pageModel.pi.TurnkeyOrderItem;
import wzd.service.IPoService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Namespace("/")
@Action(value = "orderAction")
public class OrderAction extends BaseAction implements ModelDriven<TurnkeyOrder> {

	
	private TurnkeyOrder turnkeyOrder = new TurnkeyOrder();
	
	@Override
	public TurnkeyOrder getModel() {
		// TODO Auto-generated method stub
		return turnkeyOrder;
	}
	
	private IPoService poService;
	
	@Autowired
	public void setPoService(IPoService poService) {
		this.poService = poService;
	}
	public IPoService getPoService() {
		return poService;
	}

	public void datagrid() {
		super.writeJson(poService.datagrid(turnkeyOrder));
	}
	
	public void datagridItem(){
		super.writeJson(poService.datagridItem(turnkeyOrder));
	}
	public void datagridTpnTestFlows(){
		super.writeJson(poService.datagridTpnTestFlows(turnkeyOrder));
	}
	public void datagridOfChangeList() {
		super.writeJson(poService.datagridOfChangeList(turnkeyOrder));
	}
	public void getItemDetailOfOrder(){
		
	}

	public void datagridOfTurnkeyDetail() {
		super.writeJson(poService.datagridOfTurnkeyDetail(turnkeyOrder));
	}
	
	public void exportToExcel(){
		List<TurnkeyOrderItem> list = poService.datagridItem(turnkeyOrder).getRows();
		ActionContext ctx = ActionContext.getContext();       
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);       
		HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
		try {
			response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode("WipData", "UTF-8"))));
			response.setHeader("Connection", "close");
			response.setHeader("Content-Type", "application/vnd.ms-excel");
			String title = "Work Order";
	        String[] hearders = new String[] {"Fab Site","CP Site", "Prod", "IPN(W)", "CPN","IPN(CP)","Lot","Qty","Wafer No.","CP Test flow","Tran. WO (Y/N)","Stage","Before/After","Remark"};//表头数组
	        String[] fields = new String[] {"fabSite","cpSite", "pn_new", "ipn", "cpn_new","ipn_new","lid","qty","wid","cpTestFlow","field1","field2","field3","remark"};//People对象属性数组
	        List<TurnkeyOrderItem> newList = new ArrayList<TurnkeyOrderItem>();
	        for(TurnkeyOrderItem item:list){
	        	item.setFabSite(turnkeyOrder.getFabSite());
	        	item.setField1(turnkeyOrder.getField1());
	        	item.setField2(turnkeyOrder.getField2());
	        	item.setField3(turnkeyOrder.getField3());
	        	newList.add(item);
	        }
	        TableData td = ExcelUtils.createTableData(newList, ExcelUtils.createTableHeader(hearders),fields);
	        JsGridReportBase report = new JsGridReportBase(request, response);
	        report.exportToExcel("WorkOrder_"+turnkeyOrder.getSerialNumber(), turnkeyOrder.getSerialNumber(), td,"单号:");
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
	
	public void deletePoFromId(){
		String result = poService.deletePoFromId(turnkeyOrder);
		Json j = new Json();
		j.setMsg(result);
		j.setSuccess(true);
		super.writeJson(j);
	}
	
	public void deletePoItemFromIdAndSeqId(){
		String result = poService.deletePoItemFromIdAndSeqId(turnkeyOrder);
		Json j = new Json();
		j.setMsg(result);
		j.setSuccess(true);
		super.writeJson(j);
	}
	
	public void huanyuanPoItemFromIdAndSeqId(){
		String result = poService.huanyuanPoItemFromIdAndSeqId(turnkeyOrder);
		Json j = new Json();
		j.setMsg(result);
		j.setSuccess(true);
		super.writeJson(j);
	}
	
	public void deletePoItemFromIdAndSeqIdByChange(){
		poService.deletePoItemFromIdAndSeqIdByChange(turnkeyOrder);
		Json j = new Json();
		j.setMsg("变更成功!");
		j.setSuccess(true);
		super.writeJson(j);
	}
	
	
	public void completedProductOrder(){
		List<String> list = poService.completedProductOrder(turnkeyOrder);
		Json j = new Json();
		/*if(list.size()>0){
			j.setMsg("成功!但是部分Wafer PI信息中无记录,末更新,请注意查收邮件查看详细.");
		}else{
			j.setMsg("成功!");
		}*/
		j.setMsg("成功!");
		j.setSuccess(true);
		super.writeJson(j);
	}
	
	public void completedProductOrderAndInvokeWebService(){
		poService.completedProductOrderAndInvokeWebService(turnkeyOrder);
		Json j = new Json();
		/*if(list.size()>0){
			j.setMsg("成功!但是部分Wafer PI信息中无记录,末更新,请注意查收邮件查看详细.");
		}else{
			j.setMsg("成功!");
		}*/
		j.setMsg("成功!");
		j.setSuccess(true);
		super.writeJson(j);
	}
	
	public void cancelProductOrder(){
		poService.cancelProductOrder(turnkeyOrder);
		Json j = new Json();
		j.setMsg("成功!");
		j.setSuccess(true);
		super.writeJson(j);
	}
	
	public void deletePoItemDetailsFromFid(){
		String result = poService.deletePoItemDetailsFromFid(turnkeyOrder);
		Json j = new Json();
		j.setMsg(result);
		j.setSuccess(true);
		super.writeJson(j);
	}
	
	public void deletePoItemDetailsFromFidByChange(){
		poService.deletePoItemDetailsFromFidByChange(turnkeyOrder);
		Json j = new Json();
		j.setMsg("变更成功!");
		j.setSuccess(true);
		super.writeJson(j);
	}
}
