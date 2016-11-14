package wzd.model.sys;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * Tuser entity. @author MyEclipse Persistence Tools
 */
/*@Entity
@Table(name = "t_sys_user")
@DynamicInsert(true)
@DynamicUpdate(true)//更新被赋值字段
*/public class Tuser implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String pwd;
	private Date createdatetime;
	private Date modifydatetime;
	private Set<Trole> troles = new HashSet<Trole>(0);

	// Constructors

	/** default constructor */
	public Tuser() {
	}

	/** minimal constructor */
	public Tuser(String id, String name, String pwd) {
		this.id = id;
		this.name = name;
		this.pwd = pwd;
	}

	/** full constructor */
	public Tuser(String id, String name, String pwd, Date createdatetime, Date modifydatetime) {
		this.id = id;
		this.name = name;
		this.pwd = pwd;
		this.createdatetime = createdatetime;
		this.modifydatetime = modifydatetime;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "NAME", unique = true, nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PWD", nullable = false, length = 32)
	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATETIME", length = 7)
	public Date getCreatedatetime() {
		return this.createdatetime;
	}

	public void setCreatedatetime(Date createdatetime) {
		this.createdatetime = createdatetime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFYDATETIME", length = 7)
	public Date getModifydatetime() {
		return this.modifydatetime;
	}

	public void setModifydatetime(Date modifydatetime) {
		this.modifydatetime = modifydatetime;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_sys_user_trole", joinColumns = { @JoinColumn(name = "TUSER_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "TROLE_ID", nullable = false, updatable = false) })
	public Set<Trole> getTroles() {
		return this.troles;
	}

	public void setTroles(Set<Trole> troles) {
		this.troles = troles;
	}

}