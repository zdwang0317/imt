package wzd.pageModel.tpn;


public class TpnRule implements java.io.Serializable{

	private int ruleId;
	private String ruleName;
	private String status;
	private String ruleTypeId;
	private String pn;
	private int page;
	private int rows;
	public int getRuleId() {
		return ruleId;
	}

	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
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

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRuleTypeId() {
		return ruleTypeId;
	}

	public void setRuleTypeId(String ruleTypeId) {
		this.ruleTypeId = ruleTypeId;
	}

	
	public TpnRule() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getPn() {
		return pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}
	
	
	
}
