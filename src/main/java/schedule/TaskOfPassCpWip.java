package schedule;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import wzd.service.ITtService;
public class TaskOfPassCpWip extends QuartzJobBean{

	private ITtService ttService;

	public ITtService getTtService() {
		return ttService;
	}

	public void setTtService(ITtService ttService) {
		this.ttService = ttService;
	}
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		// TODO Auto-generated method stub
//		ttService.passCpWip();
		ttService.passCpWipLatest();
	}

	
	
}
