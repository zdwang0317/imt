package wzd.service.impl.sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wzd.dao.IBaseDao;
import wzd.model.sys.Tresource;
import wzd.pageModel.Tree;
import wzd.service.IResourceService;

@Service("resourceService")
public class ResourceServiceImpl implements IResourceService{
	
	private IBaseDao<Tresource> resourceDao;
	

	public IBaseDao<Tresource> getResourceDao() {
		return resourceDao;
	}

	@Autowired
	public void setResourceDao(IBaseDao<Tresource> resourceDao) {
		this.resourceDao = resourceDao;
	}


	@Override
	public List<Tree> allTrees() {
		List<Tree> nl = new ArrayList<Tree>();
		String hql = "from Tresource t where t.tresourcetype.id='0'";
		List<Tresource> l = resourceDao.find(hql);
		if (l != null && l.size() > 0) {
			for (Tresource t : l) {
				Tree m = new Tree();
				BeanUtils.copyProperties(t, m);
				if (t.getTresource() != null) {
					m.setPid(t.getTresource().getId());
				}
				m.setText(t.getName());
				m.setIconCls(t.getIcon());
				Map<String, Object> attr = new HashMap<String, Object>();
				attr.put("url", t.getUrl());
				m.setAttributes(attr);
				nl.add(m);
			}
		}
		return nl;
	}

}
