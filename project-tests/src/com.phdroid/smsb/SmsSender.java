package com.phdroid.smsb;

public class SmsSender {
	private String server = "127.0.0.1";
	private int port = 5554;
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setServer(String server) {
		this.server = server;
	}
	
	public String getServer() {
		return server;
	}

	public void sendSms(String from, String message) {
		Telnet t = new Telnet(this.server, this.port);
		t.sendCommand("sms send " + from + " " + message);
		t.disconnect();
	}

	public static void main(String[] args) {
		try {
			SmsSender s = new SmsSender();
			s.sendSms("380971123326", "test message from SmsSender");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}