package wzd.service.impl.hr;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wzd.dao.IBaseDao;
import wzd.model.hr.Tdepartment;
import wzd.pageModel.hr.Department;
import wzd.service.IDeptService;

@Service("deptService")
public class DeptServiceImpl implements IDeptService {

	private IBaseDao<Tdepartment> deptDao;
	

	public IBaseDao<Tdepartment> getDeptDao() {
		return deptDao;
	}

	@Autowired
	public void setDeptDao(IBaseDao<Tdepartment> deptDao) {
		this.deptDao = deptDao;
	}


	@Override
	public List<Department> getAllDepts() {
		// TODO Auto-generated method stub
		List<Department> nl = new ArrayList<Department>();
		String hql = "from Tdepartment t";
		List<Tdepartment> l = deptDao.find(hql);
		if (l != null && l.size() > 0) {
			for (Tdepartment t : l) {
				Department m = new Department();
				BeanUtils.copyProperties(t, m);
				m.setText(t.getName());
//				Map<String, Object> attributes = new HashMap<String, Object>();
//				attributes.put("url", t.getUrl());
//				m.setAttributes(attributes);
				Tdepartment tm = t.getTdepartment();// 获得当前节点的父节点对象
				if (tm != null) {
					m.setPid(tm.getId());
				}
				//m.setIconCls("icon-blank");
				nl.add(m);
			}
		}
		return nl;
	}

}
