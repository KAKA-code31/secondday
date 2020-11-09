package com.honestpeak.utils;

import java.util.List;

/**
 * 分页基本实现
 * 
 * @author elvis.xu
 * @since 2015-05-19 20:35
 */
public class Page<T> implements Pagination {

	public static final Integer DEFAULT_PAGE_SIZE = 10;
	//前台显示15条
	public static final Integer FRONT_PAGE_SIZE = 15;
	
	/** 页码 */
	protected int pageNo;
	/** 每页记录条数 */
	protected int pageCount;
	/** 总页数 */
	protected int totalPage;
	/** 总记录条数 */
	protected int totalCount = -1;

	/** 用于存放查询结果 */
	protected List<T> resultList;

	public Page(int pageNo, int pageCount) {
		if (pageNo <= 0) {
			pageNo = 1;//直接置为1
			/*throw new IllegalArgumentException("pageNo must be greater than 0.");*/
		}
		if (pageCount <= 0) {
			pageCount = 1;//直接置为1
			/*throw new IllegalArgumentException("pageCount must be greater than 0.");*/
		}
		this.pageNo = pageNo;
		this.pageCount = pageCount;
	}

	@Override
	public int getPageNo() {
		return pageNo;
	}

	@Override
	public int getPageCount() {
		return pageCount;
	}
	
	@Override
	public int getTotalPage() {
		return totalPage;
	}

	@Override
	public int getTotalCount() {
		return totalCount;
	}
	
	@Override
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		if (totalCount < 0) { // 如果总数为负数, 表未设置
			totalPage = 0;
		} else { // 计算总页数
			totalPage = (totalCount / pageCount) + (totalCount % pageCount == 0 ? 0 : 1);
		}
	}

	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}
}
