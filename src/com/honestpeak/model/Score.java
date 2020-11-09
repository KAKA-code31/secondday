package com.honestpeak.model;

import java.io.Serializable;

import com.honestpeak.result.IdEncrypt;

public class Score extends IdEncrypt implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private Long selectcourseId;

    private String reportpath;

    private String picturepath;

    private String sourcecodepath;

    private Double score;
    
    private int checkflag;

    public Long getSelectcourseId() {
        return selectcourseId;
    }

    public void setSelectcourseId(Long selectcourseId) {
        this.selectcourseId = selectcourseId;
    }

    public String getReportpath() {
        return reportpath;
    }

    public void setReportpath(String reportpath) {
        this.reportpath = reportpath == null ? null : reportpath.trim();
    }

    public String getPicturepath() {
        return picturepath;
    }

    public void setPicturepath(String picturepath) {
        this.picturepath = picturepath == null ? null : picturepath.trim();
    }

    public String getSourcecodepath() {
        return sourcecodepath;
    }

    public void setSourcecodepath(String sourcecodepath) {
        this.sourcecodepath = sourcecodepath == null ? null : sourcecodepath.trim();
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

	public int getCheckflag() {
		return checkflag;
	}

	public void setCheckflag(int checkflag) {
		this.checkflag = checkflag;
	}
    
}