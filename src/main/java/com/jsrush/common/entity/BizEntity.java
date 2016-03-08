package com.jsrush.common.entity;

import java.sql.Timestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.jsrush.util.TimestampJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 业务数据实体基类
 * @author sunburst
 *
 */
@MappedSuperclass
public class BizEntity {
	
	protected Long id;

	protected Timestamp createTime;
	
	protected Timestamp updateTime;
	
	protected Integer del = 0; // 逻辑删除标识 是否删除 1:删除
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonSerialize(using=TimestampJsonSerializer.class)
	public Timestamp getCreateTime() {
		return createTime;
	}

	@JsonSerialize(using=TimestampJsonSerializer.class)
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public Integer getDel() {
		return del;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public void setDel(Integer del) {
		this.del = del;
	}
	
}
