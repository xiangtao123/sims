package com.jsrush.security.admin.util;

import java.io.Serializable;

public class PageInfo implements Serializable {
	
    private static final long serialVersionUID = 587754556498974978L;
    
    // 页数
    private int pageNo = 1;

    // 每页记录数
    private int pageSize = 20;
    
    // 总记录数
    private int total=0;
    
    // 起始记录数
    private int offset=0;
    
	public int getPageNo() {
		return pageNo;
	}
	
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getOffset() {
		if (this.pageNo < 1) {
			this.pageNo = 1;
		}
		if (this.pageSize < 1) {
			this.pageSize = 10;
		}
		this.offset = (pageNo-1) * getPageSize();
		return offset;
	}
	
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
}
