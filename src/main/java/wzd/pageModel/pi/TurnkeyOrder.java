package wzd.pageModel.pi;

public class TurnkeyOrder {
	private int id;
	private int seqId;
	private String fids;
	private String ipn;
	private String tpn;
	private String status;
	private String serialNumber;
	private String createdUserName;
	private String lid;
	private String fabSite;
	private String field1;
	private String field2;
	private String field3;
	
	private int page;
	private int rows;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public int getSeqId() {
		return seqId;
	}
	public void setSeqId(int seqId) {
		this.seqId = seqId;
	}
	public String getIpn() {
		return ipn;
	}
	public void setIpn(String ipn) {
		this.ipn = ipn;
	}
	public String getFids() {
		return fids;
	}
	public void setFids(String fids) {
		this.fids = fids;
	}
	public String getLid() {
		return lid;
	}
	public void setLid(String lid) {
		this.lid = lid;
	}
	public String getCreatedUserName() {
		return createdUserName;
	}
	public void setCreatedUserName(String createdUserName) {
		this.createdUserName = createdUserName;
	}
	public String getFabSite() {
		return fabSite;
	}
	public void setFabSite(String fabSite) {
		this.fabSite = fabSite;
	}
	public String getTpn() {
		return tpn;
	}
	public void setTpn(String tpn) {
		this.tpn = tpn;
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
