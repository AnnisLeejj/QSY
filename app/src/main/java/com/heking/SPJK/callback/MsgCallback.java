/**
 * 
 */
package com.heking.SPJK.callback;

/**
 * 消息回调接口
 * @author zhoudaihui
 * 
 */
public interface MsgCallback {
	/**
	 * @param msgId 消息id
	 * @param data  回调返回的数据
	 */
	void onInfoResponse(int msgId, Object data);
}
