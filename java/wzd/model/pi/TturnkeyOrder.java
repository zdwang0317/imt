package wzd.model.pi;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "zz_turnkey_order")
@DynamicUpdate(true)
public class TturnkeyOrder implements java.io.Serializable{
	private int id;
	private String status;
	private String serialNumber;
	private String type;
	private String ipn;
	private String cpn;
	private String fabSite;
	private String createdUserName;
	private Timestamp createdDate;
	private String tpn;
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(length = 50)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	@Column(length = 50)
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	@Column(length = 50)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column(length = 50)
	public String getIpn() {
		return ipn;
	}
	public void setIpn(String ipn) {
		this.ipn = ipn;
	}
	@Column(length = 50)
	public String getCpn() {
		return cpn;
	}
	public void setCpn(String cpn) {
		this.cpn = cpn;
	}
	@Column(length = 50)
	public String getCreatedUserName() {
		return createdUserName;
	}
	public void setCreatedUserName(String createdUserName) {
		this.createdUserName = createdUserName;
	}
	@Column(length = 50)
	public String getFabSite() {
		return fabSite;
	}
	public void setFabSite(String fabSite) {
		this.fabSite = fabSite;
	}
	@Column(length = 50)
	public String getTpn() {
		return tpn;
	}
	public void setTpn(String tpn) {
		this.tpn = tpn;
	}
	
	
	
	
	
}
