package wzd.action.pi;

import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import wzd.action.BaseAction;
import wzd.pageModel.Json;
import wzd.pageModel.pi.OptionContent;
import wzd.service.IPoService;

@Namespace("/")
@Action("poAction")
public class PoAction extends BaseAction
  implements ModelDriven<OptionContent>
{
  private OptionContent optionContent = new OptionContent();
  private IPoService poService;

  public OptionContent getModel()
  {
    return this.optionContent;
  }

  @Autowired
  public void setPoService(IPoService poService)
  {
    this.poService = poService;
  }
  public IPoService getPoService() {
    return this.poService;
  }

  public synchronized void createPo() {
    String po = this.poService.createProductOrder(this.optionContent);
    Json j = new Json();
    j.setMsg("创建成功：工单流水号为" + po);
    j.setSuccess(true);
    super.writeJson(j);
  }

  public synchronized void createPoAndValidateTpn() {
    String po = this.poService.createProductOrderAndValidateIpn(this.optionContent);
    Json j = new Json();
    j.setMsg(po);
    j.setSuccess(true);
    super.writeJson(j);
  }

  public synchronized void createNandPoAndValidateTpn() {
    String po = this.poService.createNandProductOrderAndValidateIpn(this.optionContent);
    Json j = new Json();
    j.setMsg(po);
    j.setSuccess(true);
    super.writeJson(j);
  }

  public synchronized void createPoItem() {
    this.poService.createProductOrderItem(this.optionContent);
    Json j = new Json();
    j.setMsg("添加成功");
    j.setSuccess(true);
    super.writeJson(j);
  }

  public void PassIpnToPi() {
    this.poService.passIpnOfTurnkeyOrderToPi();
    Json j = new Json();
    j.setMsg("成功");
    j.setSuccess(true);
    super.writeJson(j);
  }

  public void PassOptionsByStr() {
    super.writeJson(this.poService.passOptionsByStr(this.optionContent.getIpn_ipn()));
  }

  public void PassNandOptionsByStr() {
    super.writeJson(this.poService.passNandOptions());
  }

  public void getNewRuleByCondition() {
    super.writeJson(this.poService.getNewRuleValue(this.optionContent));
  }
  public void getNewRuleHeader() {
    super.writeJson(this.poService.getNewRuleHeader(this.optionContent));
  }
}