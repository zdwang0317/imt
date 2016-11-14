package wzd.model.pi;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/*@Entity
@Table(name="z_tpn_product")*/
public class TtpnProduct implements java.io.Serializable{
	
	private String pn;
	private String nm;
	private int remLayer;
	private Ttpn tpn;
	
	@Id
	@Column(length = 50)
	public String getPn() {
		return pn;
	}
	public void setPn(String pn) {
		this.pn = pn;
	}
	@Id
	@Column(length = 20)
	public String getNm() {
		return nm;
	}
	public void setNm(String nm) {
		this.nm = nm;
	}
	@Id
	public int getRemLayer() {
		return remLayer;
	}
	public void setRemLayer(int remLayer) {
		this.remLayer = remLayer;
	}
	@OneToOne
	@JoinColumn(name="tpn")
	public Ttpn getTpn() {
		return tpn;
	}
	public void setTpn(Ttpn tpn) {
		this.tpn = tpn;
	}
	

	
	
	
	
	
	
	
}
