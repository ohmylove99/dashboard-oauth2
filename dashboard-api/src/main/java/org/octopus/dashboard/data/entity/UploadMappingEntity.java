package org.octopus.dashboard.data.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * The persistent class for the db_task database table.
 * 
 */
@Entity
@Table(name = "db_upload_mapping")
public class UploadMappingEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	@Column(name = "table_name")
	private String tableName;

	@Column(name = "table_id")
	private Long tableId;

	private UploadEntity upload;

	public UploadMappingEntity() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	// bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name = "upload_id")
	public UploadEntity getUpload() {
		return this.upload;
	}

	public void setUpload(UploadEntity upload) {
		this.upload = upload;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}