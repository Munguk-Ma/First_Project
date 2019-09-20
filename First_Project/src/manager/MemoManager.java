package manager;

import java.util.ArrayList;

import dao.MemoDaoManager;
import vo.Message;
import vo.UserAccount;
import vo.UserMemo;

public class MemoManager {

	private MemoDaoManager mdm = null;
	private String id;// 로그인한 ID
	private String name;// 로그인한 이용자 이름

	public MemoManager() {

		this.mdm = new MemoDaoManager();
	}

	public boolean insertAccount(UserAccount user) {
		// TODO Auto-generated method stub
		boolean result = false;
		result = mdm.insertAccount(user);
		return result;
	}

	public UserAccount loginAccount(UserAccount user) {
		// TODO Auto-generated method stub
		UserAccount result = null;
		result = mdm.loginAccount(user);
		if (result != null) {
			this.id = result.getId();
			this.name = result.getName();
		}
		return result;
	}

	public boolean deleteAccount(UserAccount user) {
		// TODO Auto-generated method stub
		boolean result = false;
		result = mdm.deleteAccount(user);
		return result;
	}

	public boolean insertMemo(UserMemo memo) {
		// TODO Auto-generated method stub
		boolean result = false;
		memo.setId(this.id);
		result = mdm.insertMemo(memo);
		return result;
	}

	public ArrayList<UserMemo> getMemoList() {
		// TODO Auto-generated method stub
		ArrayList<UserMemo> result = null;
		result = mdm.getMemoList(this.id);
		return result;
	}

	public ArrayList<UserMemo> searchMemo(String searchword) {
		// TODO Auto-generated method stub

		ArrayList<UserMemo> result = null;
		result = mdm.searchMemo(searchword, this.id);
		return result;
	}

	public UserMemo searchOneMemo(String seq) {
		// TODO Auto-generated method stub
		UserMemo result = null;
		result = mdm.searchOneMemo(seq, this.id);
		return result;
	}

	public boolean updateMemo(String seq,UserMemo memo) {
		// TODO Auto-generated method stub
		boolean result = false;
		memo.setId(this.id);
		result = mdm.updateMemo(seq,memo);
		return result;
	}

	public boolean deleteMemo(String seq) {
		// TODO Auto-generated method stub
		boolean result = false;
		result = mdm.deleteMemo(seq);
		return result;
	}

	public boolean deleteAllMemo(String id,String password) {
		// TODO Auto-generated method stub
		boolean result = false;
		result = mdm.deleteAllMemo(id,password);
		return result;
	}

	public boolean logout() {

		if (this.name == null) {
			return false;
		}

		this.name = null;
		this.id = null;
		return true;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean checklogin(String id2, String password) {
		// TODO Auto-generated method stub
		boolean result = false;
		UserAccount user = new UserAccount(null, id2, password);
		UserAccount temp = null;
		temp =mdm.loginAccount(user);
		if (temp != null) {result = true;}
		return result;
	}

	public boolean insertInfo(UserAccount userAccount, String hint) {
		// TODO Auto-generated method stub
		boolean result = false;
		int temp = mdm.insertInfo(hint,userAccount.getId(),userAccount.getName());
		if (temp > 0) { result = true;}
		return result;
	}

	public UserAccount findAccount(String id2, String name2, String hint) {
		// TODO Auto-generated method stub
		UserAccount result = mdm.findAccount(id2,name2,hint);
		return result;
	}

	public ArrayList<UserAccount> callMyFriend() {
		// TODO Auto-generated method stub
		ArrayList<UserAccount> temp = new ArrayList<UserAccount>();
		temp = mdm.callMyFriend(this.id);
		return temp;
	}

	public boolean addFriend(String id) {
		// TODO Auto-generated method stub
		boolean flag = false;
		
		flag = mdm.addFriend(this.id,id);
		
		return flag;
	}

	public ArrayList<UserAccount> addRequest() {
		// TODO Auto-generated method stub
		ArrayList<UserAccount> tempList = new ArrayList<UserAccount>();
		tempList = mdm.addRequest();
		return tempList;
	}

	public boolean agree(String check) {
		// TODO Auto-generated method stub
		boolean result = false;
		result = mdm.agree(check);
		return result;
	}

	public boolean deleteFriend(String friendID) {
		// TODO Auto-generated method stub
		boolean flag = false;
		flag = mdm.deleteFriend(friendID);
		return flag;
	}

	public ArrayList<Message> myBox() {
		// TODO Auto-generated method stub
		ArrayList<Message> temp = new ArrayList<Message>();
		temp = mdm.myBox(this.id);
		return temp;
	}

	public boolean sendMessage(String reid, String content) {
		// TODO Auto-generated method stub
		boolean result = false;
		result = mdm.sendMessage(reid,content);
		return result;
	}

	public void statch(String seq) {
		// TODO Auto-generated method stub
		mdm.statch(seq);
	}

	public boolean deleteMessage(String answerr) {
		// TODO Auto-generated method stub
		boolean result = false;
		result = mdm.deleteMessage(answerr);
		return result;
	}

}
