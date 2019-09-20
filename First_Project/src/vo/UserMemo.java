package vo;

public class UserMemo {

	private String memoseq;
	private String title;
	private String content;
	private String indate;
	private String id;

	public UserMemo(String memoseq, String title, String content, String indate, String id) {
		super();
		this.memoseq = memoseq;
		this.title = title;
		this.content = content;
		this.indate = indate;
		this.id = id;
	}

	public UserMemo() {
		super();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIndate() {
		return indate;
	}

	public void setIndate(String indate) {
		this.indate = indate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "메모일련번호=" + memoseq + ", 제목=" + title + ", 작성일=" + indate;
	}

}
