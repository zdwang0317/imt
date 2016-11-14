package wzd.model.pi;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/*@Entity
@Table(name="z_tpn_stage")*/
public class TtpnStage implements java.io.Serializable{
	
	private String firm;
	private String stage;
	private int stageOrder;
	private Ttpn tpn;
	
	@Id
	@Column(length = 50)
	public String getFirm() {
		return firm;
	}
	public void setFirm(String firm) {
		this.firm = firm;
	}
	@Id
	@Column(length = 50)
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	
	@OneToOne
	@JoinColumn(name="tpn")
	public Ttpn getTpn() {
		return tpn;
	}
	public void setTpn(Ttpn tpn) {
		this.tpn = tpn;
	}
	public int getStageOrder() {
		return stageOrder;
	}
	public void setStageOrder(int stageOrder) {
		this.stageOrder = stageOrder;
	}
	
	
	
}
