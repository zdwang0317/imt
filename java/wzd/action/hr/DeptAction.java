package wzd.action.hr;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import wzd.action.BaseAction;
import wzd.pageModel.hr.Department;
import wzd.service.IDeptService;
import com.opensymphony.xwork2.ModelDriven;

@Namespace("/")
@Action(value = "deptAction")
public class DeptAction extends BaseAction implements ModelDriven<Department> {
	
	private Department department = new Department();

	@Override
	public Department getModel() {
		// TODO Auto-generated method stub
		return department;
	}
	
	private IDeptService deptService;

	public IDeptService getDeptService() {
		return deptService;
	}
	@Autowired
	public void setDeptService(IDeptService deptService) {
		this.deptService = deptService;
	}
	
	public void getAllDepts() {
		super.writeJson(deptService.getAllDepts());
	}
	
}
