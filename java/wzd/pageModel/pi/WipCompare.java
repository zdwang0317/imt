package wzd.pageModel.pi;

public class WipCompare {
	String currentPn;
	String currentLid;
	String currentWid;
	String currentStage;
	String oldStage;
	
	public WipCompare(String currentPn, String currentLid, String currentWid,
			String currentStage, String oldStage) {
		super();
		this.currentPn = currentPn;
		this.currentLid = currentLid;
		this.currentWid = currentWid;
		this.currentStage = currentStage;
		this.oldStage = oldStage;
	}
	public String getCurrentPn() {
		return currentPn;
	}
	public void setCurrentPn(String currentPn) {
		this.currentPn = currentPn;
	}
	public String getCurrentLid() {
		return currentLid;
	}
	public void setCurrentLid(String currentLid) {
		this.currentLid = currentLid;
	}
	public String getCurrentWid() {
		return currentWid;
	}
	public void setCurrentWid(String currentWid) {
		this.currentWid = currentWid;
	}
	public String getCurrentStage() {
		return currentStage;
	}
	public void setCurrentStage(String currentStage) {
		this.currentStage = currentStage;
	}
	public String getOldStage() {
		return oldStage;
	}
	public void setOldStage(String oldStage) {
		this.oldStage = oldStage;
	}
	
}
