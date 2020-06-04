package com.ctgtmo.sshr.model.response;

import java.io.Serializable;

public class SchedulShiftTime implements Serializable {
    private static final long serialVersionUID = 1L;

    private String begin;

    private String end;
    
    private String workOnScope;
    
    private String workOutScope;

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

	public String getWorkOnScope() {
		return workOnScope;
	}

	public void setWorkOnScope(String workOnScope) {
		this.workOnScope = workOnScope;
	}

	public String getWorkOutScope() {
		return workOutScope;
	}

	public void setWorkOutScope(String workOutScope) {
		this.workOutScope = workOutScope;
	}
    
    
    
    

}
