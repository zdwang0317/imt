package wzd.model.pi;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "zz_wip_baofei")
public class TwipBaoFei implements java.io.Serializable{
	private String id;
	private String type;
	private int qty;
	
	
	public TwipBaoFei() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Id
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	
	
}
