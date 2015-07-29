package wzd.model.pi;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "z_tpn")
public class Ttpn implements java.io.Serializable{
	private String tpn;
	private int tpnOrder;
	private String status;
	private String ruleTypeId;
	
	public Ttpn() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Id
	@Column(length = 50)
	public String getTpn() {
		return tpn;
	}
	public void setTpn(String tpn) {
		this.tpn = tpn;
	}
	public int getTpnOrder() {
		return tpnOrder;
	}
	public void setTpnOrder(int tpnOrder) {
		this.tpnOrder = tpnOrder;
	}
	@Column(length = 50)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(length = 10)
	public String getRuleTypeId() {
		return ruleTypeId;
	}
	public void setRuleTypeId(String ruleTypeId) {
		this.ruleTypeId = ruleTypeId;
	}
	
	
}
