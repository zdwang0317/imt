package wzd.pageModel.pi;

import java.sql.Timestamp;
import java.util.Date;


public class WipDetail {
	private int page;
	private int rows;
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
	private Date foTime;
	private String remLayer;
	private Date holdDate;
	private String holdRemark;
	private String location;
	private String firm;
	private Timestamp sendDate;
	private String fileName;
	//tiptop接收日期格式
	private String erpDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
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
	
	public String getRemLayer() {
		return remLayer;
	}
	public void setRemLayer(String remLayer) {
		this.remLayer = remLayer;
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
	public String getFirm() {
		return firm;
	}
	public void setFirm(String firm) {
		this.firm = firm;
	}
	public Timestamp getSendDate() {
		return sendDate;
	}
	public void setSendDate(Timestamp sendDate) {
		this.sendDate = sendDate;
	}
	public String getErpDate() {
		return erpDate;
	}
	public void setErpDate(String erpDate) {
		this.erpDate = erpDate;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	
}
