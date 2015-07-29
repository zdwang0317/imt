package wzd.pageModel.tpn;

public class TpnRuleItem implements java.io.Serializable{
	
	private int ruleId;
	private String tpn;
	private int remLayer;
	private int page;
	private int rows;
	
	public int getRuleId() {
		return ruleId;
	}
	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}
	public String getTpn() {
		return tpn;
	}
	public void setTpn(String tpn) {
		this.tpn = tpn;
	}
	public int getRemLayer() {
		return remLayer;
	}
	public void setRemLayer(int remLayer) {
		this.remLayer = remLayer;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	
}
