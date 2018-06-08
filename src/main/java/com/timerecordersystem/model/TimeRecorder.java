package com.timerecordersystem.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Classe que representa a batida do ponto.
 * 
 * @author ezequias.oliveira
 *
 */
@Entity
@Table(name = "time_recorder")
public class TimeRecorder extends AbstractEntity {

	private static final long serialVersionUID = 905382826752249478L;
	@ManyToOne
	private Worked worked;
	@Column(name="momment")
	@Temporal(TemporalType.TIMESTAMP)
	private Date momment;
	
	public TimeRecorder() {
		super();
	}
	
	public TimeRecorder(Worked worked, Date momment) {
		super();
		this.worked = worked;
		this.momment = momment;
	}

	public Worked getWorked() {
		return worked;
	}
	public void setWorked(Worked worked) {
		this.worked = worked;
	}
	public Date getMomment() {
		return momment;
	}
	public void setMomment(Date momment) {
		this.momment = momment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((momment == null) ? 0 : momment.hashCode());
		result = prime * result + ((worked == null) ? 0 : worked.hashCode());
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
		TimeRecorder other = (TimeRecorder) obj;
		if (momment == null) {
			if (other.momment != null)
				return false;
		} else if (!momment.equals(other.momment))
			return false;
		if (worked == null) {
			if (other.worked != null)
				return false;
		} else if (!worked.equals(other.worked))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TimeRecorder [worked=" + worked + ", momment=" + momment + "]";
	}
	
}
