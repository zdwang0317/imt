package wzd.model.tpn;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "z_tpn_rule_header")
public class TtpnRuleHeader implements java.io.Serializable{
	
	private int ruleId;
	private String ruleName;
	private String status;
	private String ruleTypeId;
	
	public TtpnRuleHeader() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Id
	public int getRuleId() {
		return ruleId;
	}
	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}
	
	@Column(length = 150)
	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	@Column(length = 50)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@Column(length = 50)
	public String getRuleTypeId() {
		return ruleTypeId;
	}

	public void setRuleTypeId(String ruleTypeId) {
		this.ruleTypeId = ruleTypeId;
	}
	
	
}
