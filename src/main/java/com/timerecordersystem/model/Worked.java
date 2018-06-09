package com.timerecordersystem.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Classe que representa o dia trabalhado pelo funcionário.
 * 
 * @author ezequias.oliveira
 *
 */
@Entity
@Table(name = "worked")
public class Worked extends AbstractEntity{

	private static final long serialVersionUID = 1868284541090263513L;
	@ManyToOne
	private Employee employee;
	@Column(name="momment", nullable = false)
	private LocalDate momment;
	@OneToMany(mappedBy = "worked", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<TimeRecorder> records;
	
	public Worked() {
		super();
	}
	
	public Worked(Employee employee, LocalDate momment) {
		super();
		this.employee = employee;
		this.momment = momment;
	}

	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public LocalDate getMomment() {
		return momment;
	}
	public void setMomment(LocalDate momment) {
		this.momment = momment;
	}
	public List<TimeRecorder> getRecords() {
		return records;
	}
	public void setRecords(List<TimeRecorder> records) {
		this.records = records;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((employee == null) ? 0 : employee.hashCode());
		result = prime * result + ((momment == null) ? 0 : momment.hashCode());
		result = prime * result + ((records == null) ? 0 : records.hashCode());
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
		Worked other = (Worked) obj;
		if (employee == null) {
			if (other.employee != null)
				return false;
		} else if (!employee.equals(other.employee))
			return false;
		if (momment == null) {
			if (other.momment != null)
				return false;
		} else if (!momment.equals(other.momment))
			return false;
		if (records == null) {
			if (other.records != null)
				return false;
		} else if (!records.equals(other.records))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Worked [employee=" + employee + ", momment=" + momment + ", records=" + records + "]";
	}
	
}
