package wzd.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import wzd.pageModel.Json;
import wzd.pageModel.Role;
import wzd.service.IRoleService;

import com.opensymphony.xwork2.ModelDriven;

@Namespace("/")
@Action(value = "roleAction")
public class RoleAction extends BaseAction implements ModelDriven<Role> {

	private Role role = new Role();

	@Override
	public Role getModel() {
		// TODO Auto-generated method stub
		return role;
	}

	private IRoleService roleService;

	public IRoleService getRoleService() {
		return roleService;
	}

	@Autowired
	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	public void datagrid() 
	{
		super.writeJson(roleService.datagrid(role));
	}
	
	public void getRoleList() 
	{
		super.writeJson(roleService.getRoleList());
	}
	

	public void remove() 
	{
		roleService.remove(role.getId());
	}
	
	public void add()
	{
		Json j = new Json();
		try {
			Role r = roleService.save(role);
			j.setSuccess(true);
			j.setObj(r);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		super.writeJson(j);
	}
	
	public void edit()
	{
		Json j = new Json();
		Role r = roleService.edit(role);
		j.setObj(r);
		super.writeJson(j);
	}
	
	public void grant()
	{
		Json j = new Json();
		roleService.grant(role);
		super.writeJson(j);
	}
	
	public void getRoleRes()
	{
		super.writeJson(roleService.getRoleRes(role));
	}
}
