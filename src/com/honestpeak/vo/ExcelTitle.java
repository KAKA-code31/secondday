package com.honestpeak.vo;

public class ExcelTitle {
	
	private String title;
	private int colspan = 1;
	private int rowspan = 1;
	private float pointWidth;
	private float pointHeight;

	/**
	 * 
	 */
	public ExcelTitle() {
	}

	/**
	 * @param title 标题内容
	 */
	public ExcelTitle(String title) {
		this.title = title;
	}

	/**
	 * @param title 标题内容
	 * @param colspan 合并列数
	 */
	public ExcelTitle(String title, int colspan) {
		this.title = title;
		this.colspan = colspan;
	}

	/**
	 * @param title 标题内容
	 * @param colspan 合并列数
	 * @param rowspan 合并行数
	 */
	public ExcelTitle(String title, int colspan, int rowspan) {
		this.title = title;
		this.colspan = colspan;
		this.rowspan = rowspan;
	}

	/**
	 * @param title 标题内容
	 * @param colspan 合并列数
	 * @param pointHeight 单元格高度
	 */
	public ExcelTitle(String title, int colspan, float pointHeight) {
		super();
		this.title = title;
		this.colspan = colspan;
		this.pointHeight = pointHeight;
	}

	/**
	 * @param title 标题内容
	 * @param colspan 合并列数
	 * @param pointWidth 单元格宽度
	 * @param pointHeight 单元格高度
	 */
	public ExcelTitle(String title, int colspan, float pointWidth, float pointHeight) {
		this.title = title;
		this.colspan = colspan;
		this.pointWidth = pointWidth;
		this.pointHeight = pointHeight;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getColspan() {
		return colspan;
	}

	public void setColspan(int colspan) {
		this.colspan = colspan;
	}

	public int getRowspan() {
		return rowspan;
	}

	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}

	public float getPointWidth() {
		return pointWidth;
	}

	public void setPointWidth(float pointWidth) {
		this.pointWidth = pointWidth;
	}

	public float getPointHeight() {
		return pointHeight;
	}

	public void setPointHeight(float pointHeight) {
		this.pointHeight = pointHeight;
	}

}
