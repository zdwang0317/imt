package wzd.model.pi;

import java.sql.Date;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "z_wip_detail")
public class TwipDetail implements java.io.Serializable{
	
	private int id;
	private int pid;
	private String pn;
	private String cpn;
	private String ipn;
	private String lid;
	private int qty;
	private String wid;
	private Date startDate;
	private String stage;
	private String status;
//	private Date foTime;
//	private String remLayer;
//	private Date holdDate;
//	private String holdRemark;
	private String location;
	private Timestamp sendDate;
	private String firm;
//	private String fileName;
	private String erpDate;
	private String tpnFlow;
	private String productNo;

	/** default constructor */
	public TwipDetail() {
	}

	public TwipDetail(Object o) {
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	
}
