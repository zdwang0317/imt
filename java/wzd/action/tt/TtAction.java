package wzd.action.tt;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import wzd.action.BaseAction;
import wzd.pageModel.Json;
import wzd.pageModel.tt.Tt;
import wzd.service.ITtService;

import com.opensymphony.xwork2.ModelDriven;

@Namespace("/")
@Action(value = "ttAction")
public class TtAction extends BaseAction implements ModelDriven<Tt> {

	// private WipDetail wipDetail = new WipDetail();
	private Tt tt = new Tt();

	@Override
	public Tt getModel() {
		// TODO Auto-generated method stub
		return tt;
	}

	private ITtService ttService;

	public ITtService getTtService() {
		return ttService;
	}

	@Autowired
	public void setTtService(ITtService ttService) {
		this.ttService = ttService;
	}

	public void dataResolve() {
		Json j = new Json();
		int updateRows = ttService.dataResolve();
		String msg = "分解行数：" + updateRows;
		j.setObj(updateRows);
		j.setMsg(msg);
		j.setSuccess(true);
		super.writeJson(j);
	}
	
	public void dataResolveForPo() {
		Json j = new Json();
		int updateRows = ttService.dataResolveForPo();
		String msg = "分解行数：" + updateRows;
		j.setObj(updateRows);
		j.setMsg(msg);
		j.setSuccess(true);
		super.writeJson(j);
	}

	public synchronized void passCpWip() {
		Json j = new Json();
		int updateRows = ttService.passCpWipLatest();
		j.setObj(updateRows);
		j.setMsg("更新行数：" + updateRows);
		j.setSuccess(true);
		super.writeJson(j);
	}

	public void passWaferWip() {
		Json j = new Json();
		int updateRows = ttService.passWaferWip();
		j.setObj(updateRows);
		j.setMsg("更新行数：" + updateRows);
		j.setSuccess(true);
		super.writeJson(j);
	}

	public void updateBjGooddie() {
		Json j = new Json();
		int updateRows = ttService.updateBjGooddie();
		j.setObj(updateRows);
		j.setMsg("更新行数：" + updateRows);
		j.setSuccess(true);
		super.writeJson(j);
	}

	public void updateHkGooddie() {
		Json j = new Json();
		int updateRows = ttService.updateHkGooddie();
		j.setObj(updateRows);
		j.setMsg("更新行数：" + updateRows);
		j.setSuccess(true);
		super.writeJson(j);
	}
	
	public void updateGooddie() {
		Json j = new Json();
		int updateRows = ttService.updateGooddie(tt);
		j.setObj(updateRows);
		j.setMsg("更新行数：" + updateRows);
		j.setSuccess(true);
		super.writeJson(j);
	}
	
	public void cancelShipTo() {
		Json j = new Json();
		int updateRows = ttService.CancelShipTo(tt.getTc_cpj01(),tt.getDb(),tt.getMode());
		j.setObj(updateRows);
		if(updateRows>0){
			j.setMsg("取消成功");
		}else{
			j.setMsg("取消失败");
		}
		j.setSuccess(true);
		super.writeJson(j);
	}
	
	public void deleteRepetitiveData(){
		Json j = new Json();
		int rows = ttService.deleteRepetitiveData();
		j.setObj(rows);
		j.setMsg("删除重复数据完成");
		j.setSuccess(true);
		super.writeJson(j);
	}
	
	public void helperOfWorkOrder(){
		Json j = new Json();
		ttService.helperOfWorkOrder();
		j.setMsg("完成");
		j.setSuccess(true);
		super.writeJson(j);
	}
	
	public void passDataResolveToChart(){
		Json j = new Json();
		ttService.PassDataResolveToChart();
		j.setMsg("完成");
		j.setSuccess(true);
		super.writeJson(j);
	}
}
