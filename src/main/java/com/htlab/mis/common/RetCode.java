package com.htlab.mis.common;

/**
 * 客户端接口返回码
 * 
 */
public class RetCode {

	/** 成功 */
	public static final String SUCCESS = "0";
	
	/** 未知错误 */
	public static final String UNKOWN_WRONG = "1";
	
	/** 无效用户会话 */
	public static final String INVALID_SESSION = "2";

	/** 请求参数不合法 */
	public static final String PARAMS_INVALID = "100";

	/** 业务验证失败 */
	public static final String FAIL = "101";
	
	
	
	//ADC接口调用错误代码说明
	
	/** 401	业务配置参数错误	业务订购配置不符合SI应用系统的约定 */
	public static final String  ERR01= "401";
	/** 402	业务功能点订购错误	订购的功能点与SI应用系统提供的不一致 */
	public static final String  ERR02= "402";
	/** 403	License数量错误	License可能不是非负的整数 */
	public static final String  ERR03= "403";
	/** 404	未知操作类型	SI应用系统无法识别操作类型（订购、取消、暂停、恢复） */
	public static final String  ERR04= "404";
	/** 405	其他错误	4**编码定义该类消息其他错误*/
	public static final String  ERR05= "405";
	/** 501	未知集团客户帐号	集团客户帐号在SI应用系统中不存在 */
	public static final String  ERR06= "501";
	/** 502	用户手机号码非法	用户手机号码错误 */
	public static final String  ERR07= "502";
	/** 503	其他错误	5**编码定义该类消息其他错误 */
	public static final String  ERR08= "503";


}
