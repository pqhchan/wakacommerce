package com.wakacommerce.wechat.domain.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@MappedSuperclass
public abstract class BaseEntity {
	
	@Id
    @GeneratedValue(generator = "BaseEntityId")
    @GenericGenerator(
        name="BaseEntityId",
        strategy="com.wakacommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="AdminModuleImpl"),
            @Parameter(name="entity_name", value="com.wakacommerce.openadmin.server.security.domain.AdminModuleImpl")
        }
    )
	@Column(name = "ID")
	protected Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	protected Date createdDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
}
