package wzd.model.hr;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/*@Entity
@Table(name = "t_hr_dept")*/
public class Tdepartment implements java.io.Serializable {

	private String id;
	private Tdepartment tdepartment;
	private String name;
	private String spellName;
	private String officeId;
	private String managerId;
	private Date createdatetime;
	private Date modifydatetime;
	private Set<Tdepartment> tdepartments = new HashSet<Tdepartment>(0);

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(length = 30)
	public String getSpellName() {
		return spellName;
	}

	public void setSpellName(String spellName) {
		this.spellName = spellName;
	}

	@Column(length = 40)
	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	@Column(length = 40)
	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(length = 7)
	public Date getCreatedatetime() {
		return createdatetime;
	}

	public void setCreatedatetime(Date createdatetime) {
		this.createdatetime = createdatetime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(length = 7)
	public Date getModifydatetime() {
		return modifydatetime;
	}

	public void setModifydatetime(Date modifydatetime) {
		this.modifydatetime = modifydatetime;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pid")
	public Tdepartment getTdepartment() {
		return tdepartment;
	}

	public void setTdepartment(Tdepartment tdepartment) {
		this.tdepartment = tdepartment;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tdepartment")
	public Set<Tdepartment> getTdepartments() {
		return tdepartments;
	}

	public void setTdepartments(Set<Tdepartment> tdepartments) {
		this.tdepartments = tdepartments;
	}

}
