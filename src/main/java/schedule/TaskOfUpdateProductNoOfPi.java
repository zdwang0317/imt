package schedule;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import wzd.service.IWipService;

public class TaskOfUpdateProductNoOfPi extends QuartzJobBean{
	
	private IWipService wipService;

	public IWipService getWipService() {
		return wipService;
	}

	public void setWipService(IWipService wipService) {
		this.wipService = wipService;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		wipService.updateProductNoOfPi();
	}

}
