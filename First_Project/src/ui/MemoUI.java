package ui;

import java.text.Format;
import java.util.ArrayList;
import java.util.Scanner;

import manager.MemoManager;
import vo.Message;
import vo.UserAccount;
import vo.UserMemo;

public class MemoUI {

	private MemoManager manager = new MemoManager(); // 요청과 관련된 처리를 하기 위해 생성한
	// MemoManager 클래스의 객체
	private Scanner sc = new Scanner(System.in);
	private Scanner scLine = new Scanner(System.in);
	private boolean accountflag = true;
	private boolean memoflag = true;

	/**
	 * <pre>
	 * 메모 관리 프로그램의 사용자 화면을 구성하고 사용자 입력을 대기한다.
	 * 프로그램은 종료 메뉴를 선택하기 전까지 무한 반복하여 처리 된다.
	 * </pre>
	 */
	public MemoUI() {

		while (accountflag) {

			try {

				memoflag = true;
				mainMenu();

				switch (sc.nextInt()) {
				case 1:
					insertAccount();  //끝
					break;
				case 2:
					deleteAccount();  //끝
					break;
				case 3:
					findAccount();    //끝
					break;
				case 4:
					loginAccount();   //끝

					while (memoflag) {
						subMenu();
						switch (sc.nextInt()) {
						case 1:
							insertMemo();
							break;
						case 2:
							searchMemo();
							break;
						case 3:
							searchAllMemo();
							break;
						case 4:
							searhOneMemo();
							break;
						case 5:
							updateMemo();
							break;
						case 6:
							deleteMemo();
							break;
						case 7:
							callmegrey();
							break;
						case 9:
							memoflag = false;
							break;
						}
					}
					break;
				case 9:
					accountflag = false;
					break;
				}
			} catch (Exception e) 
			{
				sc.nextLine();
				System.out.println("오류 !!");
			}
		}

	}



	private void callmegrey() {
		// TODO Auto-generated method stub
		while (true) 
		{
			try 
			{
				callmymenu();
				String choice = scLine.nextLine();

				switch(choice) 
				{
				case"1": callMyFriend();   //끝
				break;
				case"2": addRequest();     //끝
				break;
				case"3": addFriend();      //끝
				break;
				case"4": deleteFriend();
				break;
				case"5": myBox();
				break;
				case"6": sendMessage();
				break;
				case"9": return;
				}
			} catch(Exception e) 
			{
				e.printStackTrace();
			}
		}
	}



	private void sendMessage() {
		// TODO Auto-generated method stub
		boolean flag = false;
		while(true) 
		{
			System.out.print("받을사람 ID : ");
			String reid = scLine.next();
			System.out.print("내용을 입력하세요 :");
			String content = scLine.next();

			flag = manager.sendMessage(reid,content);

			if(flag) 
			{
				System.out.println("발신 성공");
				break;
			} else {
				System.out.println("발신 실패");
				break;
			}
		}
	}



	private void myBox() {
		// TODO Auto-generated method stub
		ArrayList<Message> temp = new ArrayList<Message>();
		while(true) 
		{
			System.out.println("1. 모든 수신 메세지 보기");
			System.out.println("2. 읽지않은 메세지 보기");
			System.out.println("3. 메세지 자세히 보기");
			System.out.println("4. 메시지 삭제 하기");
			System.out.println("5. 뒤로");
			String choice = scLine.next();
			switch(choice)
			{
			case"1":
				temp = manager.myBox();

				if(temp.size() < 1) 
				{
					System.out.println("메세지가 없습니다.");
					break;
				}
				System.out.printf("%-10s%-10s%-10s\n","Num","ID","DATE");
				for (Message m : temp) 
				{
					System.out.printf("%-10s%-10s%-10s\n",m.getSeq(),m.getSendID(),m.getDate());
				}
				break;
			case"2": 
				temp = manager.myBox();
				System.out.printf("%-10s%-10s%-10s\n","NUM","ID","STATUS");
				for (Message m : temp) 
				{
					if(m.getStatus().equals("N")) 
					{
						System.out.printf("%-10s%-10s%-10s\n",m.getSeq(),m.getSendID(),m.getStatus());
					}
				}
				break;

			case"3":
				System.out.print("조회하실 메세지 번호 : ");
				String answer = scLine.next();
				temp = manager.myBox();

				for (int i = 0; i < temp.size(); i++) 
				{
					if(temp.get(i).getSeq().equals(answer)) 
					{
						System.out.printf("%-10s%-20s%-10s\n","ID","DATE","CONTENT");
						System.out.printf("%-10s%-20s%-10s\n",temp.get(i).getSendID(),temp.get(i).getDate(),temp.get(i).getContent());
						manager.statch(temp.get(i).getSeq());
						break;
					} 
					}
				break;

			case"4": 
				System.out.println("삭제하실 메시지 번호 :");
				String answerr = scLine.next();
				
				boolean rresult = manager.deleteMessage(answerr);
				
				if(rresult) 
				{
					System.out.println("메세지를 삭제하였습니다.");
					break;
				} else System.out.println("메세지 삭제를 실패하였습니다."); break;
				
				
			case "5": return;
			default : scLine.nextLine();
			return;
			}
		}
	}



	private void deleteFriend() {
		// TODO Auto-generated method stub
		boolean flag = false;

		while(true) 
		{
			System.out.print("삭제할 친구 ID : ");
			String friendID = scLine.nextLine();
			flag = manager.deleteFriend(friendID);

			if(flag) 
			{
				System.out.println("성공!!");
				break;
			} else 
			{
				System.out.println("실패 !!");
				break;
			}
		}
	}



	private void addRequest() 
	{
		// TODO Auto-generated method stub
		ArrayList<UserAccount> tempList = new ArrayList<UserAccount>();
		tempList = manager.addRequest();

		if(tempList.size() < 1) 
		{
			System.out.println("친구 요청이 없습니다.");
			return;
		}
		for (UserAccount u : tempList) 
		{
			System.out.println("ID : "+u.getId() + " Name : "+u.getName());
		}
		System.out.print("친구요청을 수락하시겠습니까?[Y/N] : ");
		String answer = scLine.nextLine();
		if(answer.equals("N")||answer.equals("n")) { return;}

		System.out.print("수락할 친구 ID : ");
		String id = scLine.nextLine();
		String check = null;

		for (int i = 0; i < tempList.size();i++) 
		{
			if(tempList.get(i).getId().equals(id)) 
			{
				check = id;
			}
		}
		if(check == null) 
		{
			System.out.println("[오류]ID를 확인하세요");
			return;
		}
		boolean result = false;
		result = manager.agree(check);
		if(result) { System.out.println("친구 등록 완료"); return;}
		else {System.out.println("친구 등록 실패");}
	}



	private void addFriend() {
		// TODO Auto-generated method stub
		boolean result = false;

		System.out.print("추가할 친구 ID : ");
		String id = scLine.nextLine();
		result = manager.addFriend(id);

		if(result) {
			System.out.println("친구 신청 성공!");
		} else { System.out.println("친구 신청 실패!");}
	}



	private void callMyFriend() {
		// TODO Auto-generated method stub
		ArrayList<UserAccount> temp = manager.callMyFriend();

		if (temp != null) 
		{
			for(UserAccount u : temp) 
			{
				System.out.println("ID : " + u.getId()+ "  Name : " + u.getName());
			}
		} else {System.out.println("등록된 친구가 없습니다");}
	}



	private void callmymenu() {
		// TODO Auto-generated method stub
		System.out.println("┌──────────────────────────────┐");
		System.out.println("│ Soft Engineer Memo   Addmenu │");
		System.out.println("└──────────────────────────────┘");
		System.out.println("┌──────────────────────────────┐");
		System.out.println("│1.친구 목록 조회                                  │");
		System.out.println("│2.친구 요청 조회                                  │");
		System.out.println("│3.친구 추가                                        │");
		System.out.println("│4.친구 삭제                                        │");
		System.out.println("│5.메세지 함                                        │");
		System.out.println("│6.메세지 보내기                                   │");
		System.out.println("│9.Exit                        │");
		System.out.println("└──────────────────────────────┘");
		System.out.print  (" => ");


	}



	public void mainMenu() {
		System.out.println("┌──────────────────────────────┐");
		System.out.println("│ Soft Engineer Memo   계정          │");
		System.out.println("└──────────────────────────────┘");
		System.out.println("┌──────────────────────────────┐");
		System.out.println("│1.회원가입                                            │");
		System.out.println("│2.회원탈퇴                                            │");
		System.out.println("│3.비밀번호 찾기                                      │");
		System.out.println("│4.로그인                                               │");
		System.out.println("│9.종료                                                  │");
		System.out.println("└──────────────────────────────┘");
		System.out.print(" 메뉴 번호를 선택하세요=> ");
	}

	public void subMenu() {
		System.out.println("┌──────────────────────────────┐");
		System.out.println("│ Soft Engineer Memo   메모          │");
		System.out.println("└──────────────────────────────┘");
		System.out.println("┌──────────────────────────────┐");
		System.out.println("│1.메모등록                                            │");
		System.out.println("│2.메모검색(내용+제목)             │");
		System.out.println("│3.메모전체보기                                      │");
		System.out.println("│4.메모상세보기                                      │");
		System.out.println("│5.메모수정                                            │");
		System.out.println("│6.메모삭제                                            │");
		System.out.println("│7.상세 메뉴                                           │");
		System.out.println("│9.종료                                                  │");
		System.out.println("└──────────────────────────────┘");
		System.out.print(" 메뉴 번호를 선택하세요=> ");
	}

	
	
	public void loginAccount() {
		while (true) {
			System.out.println("ID를 입력 해주세요 => ");
			String id = scLine.nextLine();
			
			System.out.println("비밀번호를 입력 해주세요 => ");
			String password = scLine.nextLine();
			
			if ((id == null || id.equals("")) || (password == null || password.equals(""))) {
				System.out.println("******ERROR 다시입력해주세요.");
			} else {
				UserAccount result = manager.loginAccount(new UserAccount(null, id, password));
				if (result != null) {
					System.out.println("로그인 완료 " + manager.getId() + "(" + manager.getName() + "님) 환영합니다.");
					break;
				} else {
					System.out.println("******ERROR 아이디/비밀번호가 일치하지 않습니다.");
				}
			}
		}
	}

	
	
	public void insertAccount() {
		while (true) {
			System.out.println("이름을 입력 해주세요 => ");
			String name = scLine.nextLine();
			System.out.println("ID를 입력 해주세요 => ");
			String id = scLine.nextLine();
			System.out.println("비밀번호를 입력 해주세요 => ");
			String password = scLine.nextLine();
			System.out.println("[비밀번호 힌트] 첫 사랑 이름은? ");
			String hint = scLine.nextLine();
			if ((name == null || name.equals("")) || (id == null || id.equals(""))
					|| (password == null || password.equals(""))) {
				System.out.println("******ERROR 다시입력해주세요.");
			} else {
				boolean result2 = manager.insertInfo(new UserAccount(name,id,password),hint);
				boolean result = manager.insertAccount(new UserAccount(name, id, password));
				if (result) {
					System.out.println("계정등록 완료");
					break;
				} else {
					System.out.println("******ERROR 같은아이디가 존재합니다.");
				}
			}
		}
	}

	
	
	public void deleteAccount() {
		while (true) {
			System.out.println("ID를 입력 해주세요 => ");
			String id = scLine.nextLine();
			System.out.println("비밀번호를 입력 해주세요 => ");
			String password = scLine.nextLine();
			if ((id == null || id.equals("")) || (password == null || password.equals(""))) {
				System.out.println("******ERROR 다시입력해주세요.");
			} else {
				boolean check = manager.checklogin(id,password);
				if (!check) 
				{
					System.out.println("ID와 PW를 확인하세요");
					break;
				}
				manager.deleteAllMemo(id,password);
				boolean result = manager.deleteAccount(new UserAccount(null, id, password));
				if (result) {
					System.out.println("계정삭제 완료");
					break;
				} else {
					System.out.println("******ERROR 삭제실패.");
				}
			}
		}
	}

	
	
	public void insertMemo() {

		while (true) {
			System.out.println("제목을 입력 해주세요 => ");
			String title = scLine.nextLine();
			
			System.out.println("내용을 입력 해주세요 => ");
			String content = scLine.nextLine();
			
			if ((title == null || title.equals("")) || (content == null || content.equals(""))) {
				System.out.println("******ERROR 다시입력해주세요.");
			} else {
				boolean result = manager.insertMemo(new UserMemo(null, title, content, null, null));
				if (result) {
					System.out.println("메모등록 완료");
					break;
				} else {
					System.out.println("******ERROR 메모등록에 실패했습니다.");
				}
			}
		}
	}

	
	
	public void searchMemo() {
		while (true) {
			System.out.println("제목또는 내용을 입력 해주세요 => ");
			String searchWord = scLine.nextLine();
			if ((searchWord == null || searchWord.equals(""))) {
				System.out.println("******ERROR 다시입력해주세요.");
			} else {
				ArrayList<UserMemo> result = manager.searchMemo(searchWord);
				if (result != null) {
					printAllMemo(result);
					break;
				} else {
					System.out.println("******ERROR 메모검색에 실패했습니다.");
				}
			}
		}
	}



	public void searchAllMemo() {
		while (true) {

			ArrayList<UserMemo> result = manager.getMemoList();
			if (result != null) {
				printAllMemo(result);
				break;
			} else {
				System.out.println("******ERROR 메모조회에 실패했습니다.");
			}
		}
	}

	
	
	public void searhOneMemo() {
		while (true) {
			System.out.println("메모 일련번호를 입력 해주세요 => ");
			String memoseq = scLine.nextLine();
			if ((memoseq == null || memoseq.equals(""))) {
				System.out.println("******ERROR 다시입력해주세요.");
			} else {
				UserMemo result = manager.searchOneMemo(memoseq);
				if (result != null) {
					System.out.println("제목 : "+result.getTitle());
					System.out.println("내용 : "+result.getContent());
					break;
				} else {
					System.out.println("******ERROR 메모검색에 실패했습니다.");
				}
			}
		}
	}

	
	
	public void deleteMemo() {
	
		while (true) {
			System.out.println("삭제할 메모 일련번호를 입력 해주세요 => ");
			String memoseq = scLine.nextLine();
			if ((memoseq == null || memoseq.equals(""))) {
				System.out.println("******ERROR 다시입력해주세요.");
			} else {
				boolean result = manager.deleteMemo(memoseq);
				if (result) {
					System.out.println("삭제에 성공했습니다.");
					break;
				} else {
					System.out.println("******ERROR 메모삭제에 실패했습니다.");
				}
			}
		}
	}

	
	
	public void updateMemo(){
		while (true) {
			System.out.println("수정할 메모 일련번호를 입력 해주세요 => ");
			String memoseq = scLine.nextLine();
			if ((memoseq == null || memoseq.equals(""))) {
				System.out.println("******ERROR 다시입력해주세요.");
			} else {

				System.out.println("제목을 입력 해주세요 => ");
				String title = scLine.nextLine();
				System.out.println("내용을 입력 해주세요 => ");
				String content = scLine.nextLine();
				if ((title == null || title.equals("")) || (content == null || content.equals(""))) {
					System.out.println("******ERROR 다시입력해주세요.");
				} else {
					boolean result = manager.updateMemo(memoseq,new UserMemo(null, title, content, null, null));
					if (result) {
						System.out.println("메모수정 완료");
						break;
					} else {
						System.out.println("******ERROR 메모수정에 실패했습니다.");
					}
				}
			}
		}
	}

	
	
	public void findAccount() 
	{
		while (true) 
		{
			try {
				System.out.print("ID 입력 : ");
				String id = scLine.nextLine();
				System.out.print("NAME 입력 : ");
				String name = scLine.nextLine();
				System.out.println("Hint 입력 : ");
				String hint = scLine.nextLine();

				UserAccount temp = manager.findAccount(id,name,hint);

				if (temp == null) 
				{
					System.out.println("찾으시는 정보가 없습니다.");
					break;
				} else 
				{
					System.out.println(temp);
					break;
				}
			}catch (Exception e) 
			{
				System.out.println("입력을 확인하세요");
				scLine.nextLine();
				break;
			}
		}
	}


	
	public void printAllMemo(ArrayList<UserMemo> memos) {
		for (UserMemo memo : memos) {
			System.out.println(memo);
		}
	}
}
