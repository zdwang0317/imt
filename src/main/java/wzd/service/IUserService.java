package wzd.service;

import java.util.List;

import wzd.pageModel.DataGrid;
import wzd.pageModel.Role;
import wzd.pageModel.Tree;
import wzd.pageModel.User;


public interface IUserService {
	public User login(User user);
	public User login2(User user);
	public DataGrid datagrid(User user);
	public User save(User user);
	public void remove(String ids);
	public void edit(User user);
	public boolean checkDuplicateUser(String name);
	public List<Role> getUserRoleList(String name);
	public List<Tree> getUserMenuFromUserId(String id);
	User getUserNameFromPi(String loginName);
}
