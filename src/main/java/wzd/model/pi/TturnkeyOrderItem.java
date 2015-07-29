package wzd.model.pi;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "zz_turnkey_order_item")
@DynamicUpdate(true)
public class TturnkeyOrderItem implements java.io.Serializable{
	private int id;
	private int seqId;
	private String pn;
	private String pn_new;
	private String cpn;
	private String cpn_new;
	private String ipn;
	private String ipn_new;
	private String lid;
	private int qty;
	private String wid;
	private String cpSite;
	private String fabSite;
	private String cpTestFlow;
	private String remark;
	
	@Id
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Id
	public int getSeqId() {
		return seqId;
	}
	public void setSeqId(int seqId) {
		this.seqId = seqId;
	}
	public String getPn() {
		return pn;
	}
	public void setPn(String pn) {
		this.pn = pn;
	}
	public String getCpn() {
		return cpn;
	}
	public void setCpn(String cpn) {
		this.cpn = cpn;
	}
	
	public String getLid() {
		return lid;
	}
	public void setLid(String lid) {
		this.lid = lid;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public String getWid() {
		return wid;
	}
	public void setWid(String wid) {
		this.wid = wid;
	}
	public String getCpSite() {
		return cpSite;
	}
	public void setCpSite(String cpSite) {
		this.cpSite = cpSite;
	}
	public String getCpTestFlow() {
		return cpTestFlow;
	}
	public void setCpTestFlow(String cpTestFlow) {
		this.cpTestFlow = cpTestFlow;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIpn() {
		return ipn;
	}
	public void setIpn(String ipn) {
		this.ipn = ipn;
	}
	public String getIpn_new() {
		return ipn_new;
	}
	public void setIpn_new(String ipn_new) {
		this.ipn_new = ipn_new;
	}
	public String getPn_new() {
		return pn_new;
	}
	public void setPn_new(String pn_new) {
		this.pn_new = pn_new;
	}
	public String getCpn_new() {
		return cpn_new;
	}
	public void setCpn_new(String cpn_new) {
		this.cpn_new = cpn_new;
	}
	public String getFabSite() {
		return fabSite;
	}
	public void setFabSite(String fabSite) {
		this.fabSite = fabSite;
	}
	
	
	
}
