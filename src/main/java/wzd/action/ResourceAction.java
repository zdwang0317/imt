package wzd.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import wzd.service.IResourceService;

@Namespace("/")
@Action(value = "resourceAction")
public class ResourceAction extends BaseAction{
	IResourceService resourceService;

	public IResourceService getResourceService() {
		return resourceService;
	}
	@Autowired
	public void setResourceService(IResourceService resourceService) {
		this.resourceService = resourceService;
	}
	
	public void getAllTrees() 
	{
		super.writeJson(resourceService.allTrees());
	}
	
}
