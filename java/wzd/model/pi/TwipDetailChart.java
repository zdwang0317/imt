package wzd.model.pi;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "z_wip_detail_chart")
public class TwipDetailChart implements java.io.Serializable{
	
	private int id;
	private String lid;
	private int qty;
	private String wid;
	private String status;
	private String erpDate;
	private String tpnFlow;
	private String productNo;

	/** default constructor */
	public TwipDetailChart() {
	}

	public TwipDetailChart(Object o) {
		// TODO Auto-generated constructor stub
	}

	@Id
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	
	
}
