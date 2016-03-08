package com.jsrush.security.admin.dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * test spring jdbcTemplate
 * @author sunburst
 *
 */
public class JdbcTemplateDemoDao {

	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void addData(String text) {
		jdbcTemplate.execute("insert into test(text) values('"+text+"') ");
	}
	
	public List<Map<String, Object>> loadTestData(){
		return jdbcTemplate.queryForList("select * from test");
	}
	
}
