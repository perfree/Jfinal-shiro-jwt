package com.perfree.common;

/**
 * ajax结果集简单封装
 * @author Perfree
 *
 */
public class AjaxResult {
	
	/**成功*/
	public static final int SUCCESS = 1;
	/**出错*/
	public static final int ERROR = -1;
	/**失败*/
	public static final int FAILD = -2;
	
	/**
	 * ajax响应
	 * @param state 状态
	 * @param msg 信息
	 * @param data 数据
	 */
	public AjaxResult(Integer state,String msg,Object data) {
		this.state = state;
		this.msg = msg;
		this.data = data;
	}
	
	/**
	 * ajax响应
	 * @param state 状态
	 * @param msg 信息
	 */
	public AjaxResult(Integer state,String msg) {
		this.state = state;
		this.msg = msg;
	}
	
	/**
	 * ajax响应
	 * @param state 状态
	 */
	public AjaxResult(Integer state) {
		this.state = state;
	}
	
	private Integer state;
	private String msg;
	private Object data;
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
