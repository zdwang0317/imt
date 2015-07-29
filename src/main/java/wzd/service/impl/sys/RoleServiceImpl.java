package wzd.service.impl.sys;

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
import wzd.service.IRoleService;
import wzd.util.Encrypt;

@Service("roleService")
public class RoleServiceImpl implements IRoleService {
	
	private IBaseDao<Trole> roleDao;
	
	public IBaseDao<Trole> getRoleDao() {
		return roleDao;
	}

	@Autowired
	public void setRoleDao(IBaseDao<Trole> roleDao) {
		this.roleDao = roleDao;
	}
	
	private IBaseDao<Tresource> resourceDao;
	
	public IBaseDao<Tresource> getResourceDao() {
		return resourceDao;
	}
	@Autowired
	public void setResourceDao(IBaseDao<Tresource> resourceDao) {
		this.resourceDao = resourceDao;
	}

	@Override
	public DataGrid datagrid(Role role) {
		// TODO Auto-generated method stub
		DataGrid dg = new DataGrid();
		String hql = "from Trole t ";
		Map<String, Object> params = new HashMap<String, Object>();
		String totalHql = "select count(*) " + hql;
		List<Trole> l = roleDao.find(hql, params, role.getPage(), role.getRows());
		List<Role> nl = new ArrayList<Role>();
		if (l != null && l.size() > 0) {
			for (Trole t : l) {
				Role m = new Role();
				BeanUtils.copyProperties(t, m);
				nl.add(m);
			}
		}
		dg.setTotal(roleDao.count(totalHql, params));
		dg.setRows(nl);
		return dg;
	}

	@Override
	public void remove(String id) {
		// TODO Auto-generated method stub
		String hql = "delete Trole t where t.id='"+id+"'";
		roleDao.executeHql(hql);
	}

	@Override
	synchronized public Role save(Role role) {
		// TODO Auto-generated method stub
		Trole t = new Trole();
		BeanUtils.copyProperties(role, t,new String[]{});
		t.setId(UUID.randomUUID().toString());
		roleDao.save(t);
		BeanUtils.copyProperties(t, role);
		return role;
	}

	@Override
	synchronized public Role edit(Role role) {
		// TODO Auto-generated method stub
		Trole t = roleDao.get(Trole.class, role.getId());
		BeanUtils.copyProperties(role, t);
		return role;
	}

	@Override
	public List<Role> getRoleList() {
		// TODO Auto-generated method stub
		String hql = "from Trole t ";
		List<Trole> l = roleDao.find(hql);
		List<Role> nl = new ArrayList<Role>();
		if (l != null && l.size() > 0) {
			for (Trole t : l) {
				Role m = new Role();
				BeanUtils.copyProperties(t, m);
				nl.add(m);
			}
		}
		return nl;
	}

	@Override
	public void grant(Role role) {
		// TODO Auto-generated method stub
		Trole t = roleDao.get(Trole.class, role.getId());
		if (role.getResourceIds() != null && !role.getResourceIds().equalsIgnoreCase("")) {
			String ids = "";
			boolean b = false;
			for (String id : role.getResourceIds().split(",")) {
				if (b) {
					ids += ",";
				} else {
					b = true;
				}
				ids += "'" + id + "'";
			}
			t.setTresources(new HashSet<Tresource>(resourceDao.find("select distinct t from Tresource t where t.id in (" + ids + ")")));
		} else {
			t.setTresources(null);
		}
	}

	@Override
	public List<String> getRoleRes(Role role) {
		// TODO Auto-generated method stub
		Set<Tresource> list = roleDao.get(Trole.class, role.getId()).getTresources();
		List<String> returnlist = new ArrayList<String>();
		for(Tresource r : list){
			returnlist.add(r.getId());
		}
		return returnlist;
	}

	


	

}
