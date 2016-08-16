package wzd.action.pi;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import wzd.action.BaseAction;
import wzd.pageModel.Json;
import wzd.pageModel.pi.OptionContent;
import wzd.service.IPoService;

import com.opensymphony.xwork2.ModelDriven;

@Namespace("/")
@Action(value = "poAction")
public class PoAction extends BaseAction implements ModelDriven<OptionContent> {

	private OptionContent optionContent = new OptionContent();
	
	@Override
	public OptionContent getModel() {
		// TODO Auto-generated method stub
		return optionContent;
	}
	
	private IPoService poService;
	
	@Autowired
	public void setPoService(IPoService poService) {
		this.poService = poService;
	}
	public IPoService getPoService() {
		return poService;
	}
	
	public synchronized void createPo(){
		String po = poService.createProductOrder(optionContent);
		Json j = new Json();
		j.setMsg("创建成功：工单流水号为"+po);
		j.setSuccess(true);
		super.writeJson(j);
	}
	
	public synchronized void createPoAndValidateTpn(){
		String po = poService.createProductOrderAndValidateIpn(optionContent);
//		poService.createProductOrder(optionContent);
		Json j = new Json();
		j.setMsg(po);
		j.setSuccess(true);
		super.writeJson(j);
	}
	
	public synchronized void createPoItem(){
		poService.createProductOrderItem(optionContent);
		Json j = new Json();
		j.setMsg("添加成功");
		j.setSuccess(true);
		super.writeJson(j);
	}
	
	public void PassIpnToPi(){
		poService.passIpnOfTurnkeyOrderToPi();
		Json j = new Json();
		j.setMsg("成功");
		j.setSuccess(true);
		super.writeJson(j);
	}
	
	public void PassOptionsByStr(){
		super.writeJson(poService.passOptionsByStr(optionContent.getIpn_ipn()));
	}
	
	public void PassNandOptionsByStr(){
		super.writeJson(poService.passNandOptions());
	}
	

	

}
