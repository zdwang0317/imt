package wzd.model.pi;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "zz_turnkey_detail")
public class TturnkeyDetail implements java.io.Serializable{
	
	private int id;
	private String lid;
	private String parent_lid;
	private String wid;
	private String pn;
	private String cpn;
	private String ipn;
	private String ipn_new;
	private String tpn;
	private String tpnFlow;
	private String id_;
	private int qty;
	private String status;
	
	@Id
	public String getId_() {
		return id_;
	}
	public void setId_(String id_) {
		this.id_ = id_;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(length = 50)
	public String getLid() {
		return lid;
	}
	public void setLid(String lid) {
		this.lid = lid;
	}
	@Column(length = 50)
	public String getWid() {
		return wid;
	}
	public void setWid(String wid) {
		this.wid = wid;
	}
	@Column(length = 50)
	public String getPn() {
		return pn;
	}
	public void setPn(String pn) {
		this.pn = pn;
	}
	@Column(length = 50)
	public String getCpn() {
		return cpn;
	}
	public void setCpn(String cpn) {
		this.cpn = cpn;
	}
	@Column(length = 50)
	public String getIpn() {
		return ipn;
	}
	public void setIpn(String ipn) {
		this.ipn = ipn;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	@Column(length = 50)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(length = 50)
	public String getIpn_new() {
		return ipn_new;
	}
	public void setIpn_new(String ipn_new) {
		this.ipn_new = ipn_new;
	}
	@Column(length = 50)
	public String getParent_lid() {
		return parent_lid;
	}
	public void setParent_lid(String parent_lid) {
		this.parent_lid = parent_lid;
	}
	@Column(length = 50)
	public String getTpn() {
		return tpn;
	}
	public void setTpn(String tpn) {
		this.tpn = tpn;
	}
	@Column(length = 50)
	public String getTpnFlow() {
		return tpnFlow;
	}
	public void setTpnFlow(String tpnFlow) {
		this.tpnFlow = tpnFlow;
	}
	
	
}
