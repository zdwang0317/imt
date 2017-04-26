package wzd.model.pi;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "zz_wip_cycletime")
public class TwipCycleTime implements java.io.Serializable{
	
	private String id;
	private int days;
	
	public TwipCycleTime() {
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
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
}
