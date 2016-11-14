package wzd.pageModel.pi;

public class LotToHold {
	private int id;
	private String pn;
	private String lid;
	private String wid;
	private int qty;
	private String remark;
	private String lotfile;
	private String lotfileContentType;
	private String lotfileFileName;
    private String savePath;
	
	private int page;
	private int rows;
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
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getLotfile() {
		return lotfile;
	}
	public void setLotfile(String lotfile) {
		this.lotfile = lotfile;
	}
	public String getLotfileContentType() {
		return lotfileContentType;
	}
	public void setLotfileContentType(String lotfileContentType) {
		this.lotfileContentType = lotfileContentType;
	}
	public String getLotfileFileName() {
		return lotfileFileName;
	}
	public void setLotfileFileName(String lotfileFileName) {
		this.lotfileFileName = lotfileFileName;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
}
