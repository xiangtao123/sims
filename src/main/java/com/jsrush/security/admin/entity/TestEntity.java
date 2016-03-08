package com.jsrush.security.admin.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * mybatis test entity
 * @author sunburst
 *
 */
@Entity
@Table(name="test")
public class TestEntity implements Serializable {

	private static final long serialVersionUID = 9072706662072822175L;

	private Long id;
	
	private String text;

	public TestEntity() {
	}
	
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public int hashCode() {
		return this.id == null ? 0 : this.id.intValue();
	}
	
	@Override
	public boolean equals(Object otherObj) {
		if (otherObj == null) 
			return false;
		if (!(otherObj instanceof TestEntity)) 
			return false;
		TestEntity otherTest = (TestEntity) otherObj;
		Long otherId = otherTest.getId();
		if (this.id == null)
			return false;
		if (otherId == null)
			return false;
		if (this.id==otherId)
			return true;
		return false;
	}
	
	@Override
	public String toString() {
		return (this.id == null ? "null" : this.id) + "@" + (this.text == null ? "null" : this.text);
	}
	
}
