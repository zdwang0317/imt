package wzd.model.pi;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "zz_turnkey_order_itemdetail")
@DynamicUpdate(true)
public class TturnkeyOrderItemDetail implements java.io.Serializable{
	private int id;
	private int seqId;
	private String lid;
	private String wid;
	private String status;
	private int fid;
	private String fid_;
	@Id
	@Column(length = 50)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Id
	@Column(length = 50)
	public int getSeqId() {
		return seqId;
	}
	public void setSeqId(int seqId) {
		this.seqId = seqId;
	}
	@Id
	@Column(length = 50)
	public String getWid() {
		return wid;
	}
	public void setWid(String wid) {
		this.wid = wid;
	}
	@Column(length = 50)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(length = 50)
	public String getLid() {
		return lid;
	}
	public void setLid(String lid) {
		this.lid = lid;
	}
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
	@Column(length = 50)
	public String getFid_() {
		return fid_;
	}
	public void setFid_(String fid_) {
		this.fid_ = fid_;
	}
	
	
	
	
	
	
}
