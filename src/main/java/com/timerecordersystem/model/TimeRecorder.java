package com.timerecordersystem.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Classe que representa a batida do ponto.
 * 
 * @author ezequias.oliveira
 *
 */
@Entity
@Table(name = "TIME_RECORDER")
public class TimeRecorder extends AbstractEntity {

	private static final long serialVersionUID = 905382826752249478L;
	@NotNull(message = "Worked field is required")
	@ManyToOne
	private Worked worked;
	@NotNull(message = "Moment field is required")
	@Column(name="MOMENT", unique = true)
	private LocalDateTime moment;
	
	public TimeRecorder() {
		super();
	}
	
	public TimeRecorder(LocalDateTime moment) {
		super();
		this.moment = moment;
	}

	public TimeRecorder(Worked worked, LocalDateTime moment) {
		super();
		this.worked = worked;
		this.moment = moment;
	}

	public Worked getWorked() {
		return worked;
	}
	public void setWorked(Worked worked) {
		this.worked = worked;
	}
	public LocalDateTime getMoment() {
		return moment;
	}
	public void setMoment(LocalDateTime moment) {
		this.moment = moment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((moment == null) ? 0 : moment.hashCode());
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
		if (moment == null) {
			if (other.moment != null)
				return false;
		} else if (!moment.equals(other.moment))
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
		return "TimeRecorder [worked=" + worked + ", moment=" + moment + "]";
	}
	
}
