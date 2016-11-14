package wzd.service.impl.sys;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wzd.dao.IBaseDao;
import wzd.model.sys.Tresource;
import wzd.model.sys.Trole;
import wzd.model.sys.Tuser;
import wzd.pageModel.DataGrid;
import wzd.pageModel.Role;
import wzd.pageModel.Tree;
import wzd.pageModel.User;
import wzd.service.IUserService;
import wzd.util.ConnUtil;
import wzd.util.Encrypt;
import wzd.util.UtilValidate;

@Service("userService")
public class UserServiceImpl implements IUserService {

	private IBaseDao<Tuser> userDao;

	public IBaseDao<Tuser> getUserDao() {
		return userDao;
	}

	@Autowired
	public void setUserDao(IBaseDao<Tuser> userDao) {
		this.userDao = userDao;
	}
	
	private IBaseDao<Trole> roleDao;
	
	public IBaseDao<Trole> getRoleDao() {
		return roleDao;
	}
	@Autowired
	public void setRoleDao(IBaseDao<Trole> roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	public User save(User user) {
		Tuser t = new Tuser();
		BeanUtils.copyProperties(user, t, new String[] { "pwd" });
		t.setId(UUID.randomUUID().toString());
		t.setCreatedatetime(new Date());
		t.setPwd(Encrypt.e(user.getPwd()));
		List<Trole> roles;
		if (UtilValidate.isNotEmpty(user.getIds())) {
			roles = new ArrayList<Trole>();
			for (String roleId : user.getIds().split(",")) {
				Trole r = roleDao.get(Trole.class, roleId.trim());
				roles.add(r);
			}
			t.setTroles(new HashSet<Trole>(roles));
		}
		userDao.save(t);
		BeanUtils.copyProperties(t, user);
		return user;
	}

	@Override
	public User login(User user) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pwd", Encrypt.e(user.getPwd()));
		params.put("name", user.getName());
		Tuser t = userDao.get(
				"from Tuser t where t.name = :name and t.pwd = :pwd", params);
		if (t != null) {
			BeanUtils.copyProperties(t, user);
			return user;
		}
		return null;
	}

	@Override
	public DataGrid datagrid(User user) {
		DataGrid dg = new DataGrid();
		String hql = "from Tuser t ";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhere(user, hql, params);
		String totalHql = "select count(*) " + hql;
		hql = addOrder(user, hql);
		List<Tuser> l = userDao.find(hql, params, user.getPage(),
				user.getRows());
		List<User> nl = new ArrayList<User>();
		changeModel(l, nl);
		dg.setTotal(userDao.count(totalHql, params));
		dg.setRows(nl);
		return dg;
	}

	private String addOrder(User user, String hql) {
		if (user.getSort() != null) {
			hql += " order by " + user.getSort() + " " + user.getOrder();
		}
		return hql;
	}

	private String addWhere(User user, String hql, Map<String, Object> params) {
		if (user.getName() != null && !user.getName().trim().equals("")) {
			hql += " where t.name like :name";
			params.put("name", "%%" + user.getName().trim() + "%%");
		}
		return hql;
	}

	private void changeModel(List<Tuser> l, List<User> nl) {
		if (l != null && l.size() > 0) {
			for (Tuser t : l) {
				User u = new User();
				BeanUtils.copyProperties(t, u);
				nl.add(u);
			}
		}
	}

	@Override
	public void remove(String ids) {
		String[] nids = ids.split(",");
		for (String id:nids) {
			userDao.delete(userDao.get(Tuser.class, id));
		}
	}

	@Override
	public void edit(User user){
		// TODO Auto-generated method stub
		Tuser t = userDao.get(Tuser.class, user.getId());
		BeanUtils.copyProperties(user,t,new String[]{"pwd"});
		List<Trole> roles = new ArrayList<Trole>();
		for (String roleId : user.getIds().split(",")) {
			Trole r = roleDao.get(Trole.class, roleId.trim());
			roles.add(r);
		}
		t.setTroles(new HashSet<Trole>(roles));
	}

	@Override
	public boolean checkDuplicateUser(String name) {
		// TODO Auto-generated method stub
		List<Tuser> t = userDao.find("from Tuser t where t.name='"+name+"'");
		if (UtilValidate.isNotEmpty(t)) {
			return true;
		}
		return false;
	}

	@Override
	public List<Role> getUserRoleList(String name) {
		// TODO Auto-generated method stub
		List<Tuser> userlist = userDao.find("from Tuser t where t.name='"+name+"'");
		Set<Trole> troles = userlist.get(0).getTroles();
		String hql = "from Trole t ";
		List<Trole> l = roleDao.find(hql);
		List<Role> nl = new ArrayList<Role>();
		if (l != null && l.size() > 0) {
			for (Trole t : l) {
				Role m = new Role();
				BeanUtils.copyProperties(t, m);
				for(Trole r : troles)
				{
					if(t.getId().equals(r.getId()))
					{
						m.setChecked(true);
						break;
					}
				}
				nl.add(m);
			}
		}
		return nl;
	}

	@Override
	public List<Tree> getUserMenuFromUserId(String id) {
		// TODO Auto-generated method stub
		Set<Tree> returnlist = new HashSet<Tree>();
		Tuser t = userDao.get(Tuser.class, id);
		for(Trole r : t.getTroles()){
			for(Tresource res : r.getTresources()){
				Tree m = new Tree();
				BeanUtils.copyProperties(res, m);
				if (res.getTresource() != null) {
					m.setPid(res.getTresource().getId());
				}
				m.setText(res.getName());
				m.setIconCls(res.getIcon());
				Map<String, Object> attr = new HashMap<String, Object>();
				attr.put("url", res.getUrl());
				m.setAttributes(attr);
				returnlist.add(m);
			}
		}
		return new ArrayList<Tree>(returnlist);
	}

	@Override
	public User getUserNameFromPi(String loginName) {
		User user = new User();
		// TODO Auto-generated method stub
		ConnUtil connUtil = new ConnUtil();
		Connection conn = connUtil.getMysqlConnection();
		PreparedStatement pst=null;
		ResultSet rst=null;
		try {
			pst = conn.prepareStatement("select realName,name from tuser where name='"+loginName+"'");
			rst = pst.executeQuery();
			while(rst.next()){
				user.setName(rst.getString("realName"));
				user.setId(rst.getString("name"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			ConnUtil.close(rst, pst);
			ConnUtil.closeConn(conn);
		}
		return user;
	}

	@Override
	public User login2(User user) {
		// TODO Auto-generated method stub
		User returnUser = new User();
		PreparedStatement pst = null;
		ResultSet rst = null;
		ConnUtil connUtil = new ConnUtil();
		Connection conn = connUtil.getMysqlConnection();
		String sql = "select name,realName from tuser where name = ? and password =?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, user.getName());
			pst.setString(2, user.getPwd());
			rst = pst.executeQuery();
			while(rst.next()){
				returnUser.setName(rst.getString("realName"));
				returnUser.setId(rst.getString("name"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			ConnUtil.close(rst, pst);
			ConnUtil.closeConn(conn);
		}
		
		return returnUser;
	}

}
