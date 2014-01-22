package com.htlab.mis.util;

import java.util.List;
import com.htlab.mis.common.BaseObject;
/**
 * 用户封装分页查询数据的类
 * 通过构造方法来初始化一个分页信息对象供JSP页面使用
 */
@SuppressWarnings("serial")
public class PageInfo extends BaseObject {
	private int pageIndex; // 当前第几页数据
	private int pageSize; 	//每页要显示的最大记录个数	
	private int recordeCount; // 满足查询条件的总记录数
	private int pageCount; // 满足条件的总页数
	@SuppressWarnings("rawtypes")
	private List pageData; // 当前查询的分页数据
    private boolean hasPreviousPage;  //是否有上一页

    private boolean hasNextPage; //是否有下一页

	
	/**
	 * 唯一可用的初始化分页信息的构造方法
	 * @param pageData	分页后的查询数据
	 * @param recordeCount	//满足查询条件的总记录数
	 * @param pageIndex	当前查询的是第几页的数据
	 * @param pageSize	每页要显示的最大记录个数
	 */
	@SuppressWarnings("rawtypes")
	public PageInfo(List pageData, int recordeCount, int pageIndex, int pageSize) {
		this.pageData = pageData;
		this.recordeCount = recordeCount;
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		
		//计算总页数
		pageCount = (recordeCount + pageSize - 1) / pageSize;
		
		//对于最后一页，计算实际的记录个数，因此最后一页的记录个数不一定等于设定的最大记录个数
		if(pageIndex == pageCount) {
			this.pageSize = recordeCount - ((pageCount - 1) * pageSize);
		}
		this.setHasNextPage(this.isNextPageEnable());
		this.setHasPreviousPage(this.isPreviousPageEnable());
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getRecordeCount() {
		return recordeCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	@SuppressWarnings("rawtypes")
	public List getPageData() {
		return pageData;
	}

	public boolean isNextPageEnable() {
		return this.setHasNextPage(pageIndex < pageCount);
	}

	public boolean isPreviousPageEnable() {
		return this.setHasPreviousPage(pageIndex > 1);
	}

	public boolean setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
		return hasPreviousPage;
	}

	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	public boolean setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
		return hasNextPage;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	
}
