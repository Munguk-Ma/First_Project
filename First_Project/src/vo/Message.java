package vo;

public class Message {
	String seq;
	String sendID;
	String content;
	String reID;
	String date;
	String status;
	
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Message() {
		// TODO Auto-generated constructor stub
	}




	public Message(String seq, String sendID, String content, String reID, String date, String status) {
		super();
		this.seq = seq;
		this.sendID = sendID;
		this.content = content;
		this.reID = reID;
		this.date = date;
		this.status = status;
	}


	public String getSeq() {
		return seq;
	}

	
	public void setSeq(String seq) {
		this.seq = seq;
	}


	public String getSendID() {
		return sendID;
	}


	public void setSendID(String sendID) {
		this.sendID = sendID;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getReID() {
		return reID;
	}


	public void setReID(String reID) {
		this.reID = reID;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	@Override
	public String toString() {
		return "Message [seq=" + seq + ", sendID=" + sendID + ", content=" + content + ", reID=" + reID + ", date="
				+ date + ", status=" + status + "]";
	}


	
	
}
