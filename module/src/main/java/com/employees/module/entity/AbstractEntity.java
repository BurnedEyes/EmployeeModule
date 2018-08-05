package com.employees.module.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Version;

import org.springframework.data.annotation.LastModifiedDate;

@MappedSuperclass
public abstract class AbstractEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Version
	private int version;

	@LastModifiedDate
	@Column(name = "LAST_MODIFICATION")
	private LocalDateTime lastModifiedDateTime;

	public Long getId() {
		return id;
	}

	public int getVersion() {
		return version;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public LocalDateTime getLastModifiedDateTime() {
		return lastModifiedDateTime;
	}

	public void setLastModifiedDateTime(LocalDateTime lastModifiedDate) {
		this.lastModifiedDateTime = lastModifiedDate;
	}

	@PrePersist
	private void createModificationDate() {
		if (lastModifiedDateTime == null) {
			setLastModifiedDateTime(LocalDateTime.now());
		}
	}

}
