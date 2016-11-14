package wzd.model.tpn;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "z_tpn_rule_item")
public class TtpnRuleItem implements java.io.Serializable{
	
	private int ruleId;
	private String tpn;
	private int remLayer;
	
	@Id
	public int getRuleId() {
		return ruleId;
	}
	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}
	@Id
	public String getTpn() {
		return tpn;
	}
	public void setTpn(String tpn) {
		this.tpn = tpn;
	}
	@Id
	public int getRemLayer() {
		return remLayer;
	}
	public void setRemLayer(int remLayer) {
		this.remLayer = remLayer;
	}
	
	
}
