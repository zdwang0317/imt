package wzd.model.pi;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "zz_wip_gonghuo")
public class TwipGonghuo implements java.io.Serializable{
	private String id;
	private String month;
	private String area;
	private int field1;
	private int field2;
	private int field3;
	private int field4;
	private int field5;
	private int field6;
	
	public TwipGonghuo() {
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
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public int getField1() {
		return field1;
	}
	public void setField1(int field1) {
		this.field1 = field1;
	}
	public int getField2() {
		return field2;
	}
	public void setField2(int field2) {
		this.field2 = field2;
	}
	public int getField3() {
		return field3;
	}
	public void setField3(int field3) {
		this.field3 = field3;
	}
	public int getField4() {
		return field4;
	}
	public void setField4(int field4) {
		this.field4 = field4;
	}
	public int getField5() {
		return field5;
	}
	public void setField5(int field5) {
		this.field5 = field5;
	}
	public int getField6() {
		return field6;
	}
	public void setField6(int field6) {
		this.field6 = field6;
	}
	@Id
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
	
	
}
