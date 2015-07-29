package wzd.model.tpn;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "z_tpn_pn_relation")
public class TtpnPnRelation implements java.io.Serializable{
	
	public int getRuleId() {
		return ruleId;
	}
	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}
	@Id
	@Column(length = 50)
	public String getPn() {
		return pn;
	}
	public void setPn(String pn) {
		this.pn = pn;
	}
	private int ruleId;
	private String pn;
	
	
}
