package wzd.model.pi;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "cp_wip")
@DynamicUpdate(true)
public class Twip implements java.io.Serializable{
	private int id;
	private String pn;
	private String cpn;
	private String ipn;
	private String lid;
	private int qty;
	private String wid;
	private Date startDate;
	private String stage;
	private String status;
	private Date foTime;
	private String remLayer;
	private Date holdDate;
	private String holdRemark;
	private String location;
	private Timestamp sendDate;
	private String firm;
	private String fileName;
	private String erpDate;
	private String tpnFlow;
	private String productNo;
	private String resolved;
	private String ifCp;
	private String field1;
	private String field2;
	private String field3;
	// Constructors

	/** default constructor */
	public Twip() {
	}

	public Twip(int qty, String tpnFlow, String productNo) {
		super();
		this.qty = qty;
		this.tpnFlow = tpnFlow;
		this.productNo = productNo;
	}

	public Twip(Object o) {
		// TODO Auto-generated constructor stub
	}

	@Id
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getIpn() {
		return ipn;
	}

	public void setIpn(String ipn) {
		this.ipn = ipn;
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
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public Date getFoTime() {
		return foTime;
	}


	public void setFoTime(Date foTime) {
		this.foTime = foTime;
	}

	public Date getHoldDate() {
		return holdDate;
	}

	public void setHoldDate(Date holdDate) {
		this.holdDate = holdDate;
	}

	public String getHoldRemark() {
		return holdRemark;
	}

	public void setHoldRemark(String holdRemark) {
		this.holdRemark = holdRemark;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Timestamp getSendDate() {
		return sendDate;
	}

	public void setSendDate(Timestamp sendDate) {
		this.sendDate = sendDate;
	}

	public String getFirm() {
		return firm;
	}

	public void setFirm(String firm) {
		this.firm = firm;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getErpDate() {
		return erpDate;
	}

	public void setErpDate(String erpDate) {
		this.erpDate = erpDate;
	}

	public String getTpnFlow() {
		return tpnFlow;
	}

	public void setTpnFlow(String tpnFlow) {
		this.tpnFlow = tpnFlow;
	}

	public String getRemLayer() {
		return remLayer;
	}

	public void setRemLayer(String remLayer) {
		this.remLayer = remLayer;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getResolved() {
		return resolved;
	}

	public void setResolved(String resolved) {
		this.resolved = resolved;
	}

	public String getIfCp() {
		return ifCp;
	}

	public void setIfCp(String ifCp) {
		this.ifCp = ifCp;
	}

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getField2() {
		return field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}

	public String getField3() {
		return field3;
	}

	public void setField3(String field3) {
		this.field3 = field3;
	}
	
	
}
