package cn.et.food.entity;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class Result {
	/**
	 * 0表示失败
	 * 1表示成功
	 */
	private int code;
	/**
	 * 失败的消息
	 */
	private String message;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		if(message!=null && message.length()>500){
			return message.substring(0,500);
		}
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * 重写setMessage方法将异常的堆栈信息设置到message属性上
	 * @param msg
	 */
	public void setMessage(Exception msg) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		msg.printStackTrace(new PrintStream(baos));
		this.message = new String(baos.toByteArray());
	}
	
}
