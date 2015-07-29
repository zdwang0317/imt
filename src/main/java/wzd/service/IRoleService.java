package wzd.service;

import java.util.List;

import wzd.pageModel.DataGrid;
import wzd.pageModel.Role;

public interface IRoleService {
	public DataGrid datagrid(Role role);
	public void remove(String id);
	public Role save(Role role);
	public Role edit(Role role);
	public List<Role> getRoleList();
	public void grant(Role role);
	public List<String> getRoleRes(Role role);
}
