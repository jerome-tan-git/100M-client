package com.jerome;

public class MessageObject {
	private String fromUser;
	private String toUser;
	private String message;
	private String msgID;
	private String messageType;
	private String fromServer;
	public String getFromServer() {
		return fromServer;
	}
	public void setFromServer(String fromServer) {
		this.fromServer = fromServer;
	}
	public String getMsgID() {
		return msgID;
	}
	public void setMsgID(String msgID) {
		this.msgID = msgID;
	}
	public String getFromUser() {
		return fromUser;
	}
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
	public String getToUser() {
		return toUser;
	}
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	public String getMessage() {
		return message;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
