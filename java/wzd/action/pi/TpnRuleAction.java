package wzd.action.pi;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import wzd.action.BaseAction;
import wzd.pageModel.Json;
import wzd.pageModel.pi.Series;
import wzd.pageModel.tpn.TpnRule;
import wzd.service.ITpnService;
import wzd.util.UtilValidate;

import com.opensymphony.xwork2.ModelDriven;

@Namespace("/")
@Action(value = "tpnRuleAction")
public class TpnRuleAction extends BaseAction implements ModelDriven<TpnRule>{
	
	

	//private WipDetail wipDetail = new WipDetail();
	private TpnRule tpnRule = new TpnRule();
	
	@Override
	public TpnRule getModel() {
		// TODO Auto-generated method stub
		return tpnRule;
	}
	
	private ITpnService tpnService;
	public ITpnService getTpnService() {
		return tpnService;
	}
	@Autowired
	public void setTpnService(ITpnService tpnService) {
		this.tpnService = tpnService;
	}
	

	public void datagrid() {
		super.writeJson(tpnService.datagrid(tpnRule));
	}
	
	public void datagridItem() {
		super.writeJson(tpnService.datagridItem(tpnRule));
	}
	
	public void datagridPnRule() {
		super.writeJson(tpnService.datagridPn(tpnRule));
	}
	
	public void datagridOfTpnFlow() {
		super.writeJson(tpnService.datagridOfTpnFlow(tpnRule));
	}
	
	public void createOfUpdateTpnRuleItem() {
		tpnService.createTpnRuleItem(tpnRule);
		Json j = new Json();
		j.setMsg("成功!");
		j.setSuccess(true);
		super.writeJson(j);
	}
	
	public void createPnRelation() {
		tpnService.createPnRelation(tpnRule);
		Json j = new Json();
		j.setMsg("成功!");
		j.setSuccess(true);
		super.writeJson(j);
	}
	
	public void deletePnRelation() {
		tpnService.deletePnRelation(tpnRule);
		Json j = new Json();
		j.setMsg("成功!");
		j.setSuccess(true);
		super.writeJson(j);
	}
	
	public void getRuleHeaderFromRuleTypeId() {
		super.writeJson(tpnService.getRuleHeaderFromRuleTypeId(tpnRule));
	}
	
	public void showHighChartByNewRule(){
		String xAxis = tpnService.getTpnToXAxis(tpnRule);
		List<Series> a = new ArrayList<Series>();
		if(UtilValidate.isNotEmpty(xAxis)){
			a = tpnService.getDataToChartsByNewRule(tpnRule,xAxis);
		}
		Json j = new Json();
		j.setMsg(xAxis);
		j.setSuccess(true);
		j.setDataList(a);
		super.writeJson(j);
	}
	
}
