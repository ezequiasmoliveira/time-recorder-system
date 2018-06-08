package com.timerecordersystem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Classe que representa as informações do funcionário.
 * 
 * @author ezequias.oliveira
 *
 */
@Entity
@Table(name = "employee")
public class Employee extends AbstractEntity {

	private static final long serialVersionUID = 6474470904398381369L;
	
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	@Column(name = "pis", nullable = false, length = 12)
	private String pis;
	
	public Employee() {
		super();
	}
	
	public Employee(String name, String pis) {
		super();
		this.name = name;
		this.pis = pis;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPis() {
		return pis;
	}
	public void setPis(String pis) {
		this.pis = pis;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pis == null) ? 0 : pis.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pis == null) {
			if (other.pis != null)
				return false;
		} else if (!pis.equals(other.pis))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + ", pis=" + pis + "]";
	}

}
