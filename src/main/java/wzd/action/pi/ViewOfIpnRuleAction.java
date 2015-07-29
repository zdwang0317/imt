package wzd.action.pi;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import wzd.action.BaseAction;
import wzd.service.IWipService;

@Namespace("/")
@Action(value = "viewOfIpnRuleAction")
public class ViewOfIpnRuleAction extends BaseAction {
private IWipService wipService;
	
	
	public IWipService getWipService() {
		return wipService;
	}
	@Autowired
	public void setWipService(IWipService wipService) {
		this.wipService = wipService;
	}
	
	public void getAllOfIpnRule(){
		super.writeJson(wipService.getListOfOptions());
	}
}
