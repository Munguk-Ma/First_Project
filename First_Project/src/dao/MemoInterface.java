package dao;

import java.util.ArrayList;

import vo.UserAccount;
import vo.UserMemo;


public interface MemoInterface {
	/**
	 * 새로운 UserAccount 객체를 등록한다. 이미 등록된 아이디가 존재하면 등록 결과로 false를 반환하고, 등록에 성공하면 true를
	 * 반환한다.
	 * 
	 * @param UserAccount 클래스의 객체
	 * @return 등록 성공시 true를, 실패시 false를 반환한다.
	 */
	public boolean insertAccount(UserAccount user);

	/**
	 * 등록된 UserAccount 객체를 검색한다. 로그인시 사용된다.
	 * 
	 * @param UserAccount
	 * @return UserAccount 배열에 등록된 객체들 중 파라메터로 전달된 아이디,비밀번호 와 일치되는 UserAccount 객체, 검색 결과가 없을 시
	 *         null을 반환한다.
	 */
	public UserAccount loginAccount(UserAccount user);

	/**
	 * 등록된 UserAccount 객체를 삭제한다. 회원탈퇴시 사용
	 * 
	 * @param UserAccount
	 * @return 주어진 아이디와 비밀번호가 일치되는 UserAccount 객체의 삭제 결과, UserAccount 배열에 등록된 객체들 중 파라메터로 전달된
	 *         아이디와 비밀번호가 일치되는 UserAccount 객체가 발견되어 삭제를 성공하면 true를 그렇지 않으면 false를 반환
	 */
	public boolean deleteAccount(UserAccount user);
	
	/**
	 * 새로운 UserMemo 객체를 등록한다. 실패시 등록 결과로 false를 반환하고, 등록에 성공하면 true를
	 * 반환한다.
	 * 
	 * @param UserMemo 클래스의 객체
	 * @return 등록 성공시 true를, 실패시 false를 반환한다.
	 */
	public boolean insertMemo(UserMemo memo);

	/**
	 * 등록된 UserMemo 객체를 가져온다. 전체 메모리스트 가져올때 사용
	 * 
	 * @param 메모를 작성한 사람의 아이디
	 * @return 주어진 아이디와 일치되는 UserMemo 객체의 검색 결과
	 */
	public ArrayList<UserMemo> getMemoList(String id);

	/**
	 * 등록된 UserMemo 객체들를 검색한다. 메모를 조회할때 사용
	 * 
	 * @param 메모 제목이나 내용에 들어가있는내용 작성자 아이디
	 * @return 검색 내용에 해당하는 UserMemo 객체들
	 */
	public ArrayList<UserMemo> searchMemo(String searchword,String id);
	
	/**
	 * 등록된 특정 UserMemo 객체를 검색한다. 특정메모를 조회할때 사용
	 * 
	 * @param 메모 일련번호와 작성자 아이디
	 * @return 검색 내용에 해당하는 UserMemo 객체
	 */
	public UserMemo searchOneMemo(String seq,String id);

	/**
	 * 등록된 UserMemo를 수정한다. 메모를 수정할때 사용
	 * 
	 * @param 메모의 일련번호, 수정될 내용
	 * @return 수정의 성공여부
	 */
	public boolean updateMemo(String seq,UserMemo memo);
	

	/**
	 * 등록된 UserMemo 객체를 삭제한다. 메모를 삭제할때 사용
	 * 
	 * @param 메모의 일련번호
	 * @return 삭제의 성공여부
	 */
	public boolean deleteMemo(String seq);

	/**
	 * 등록된 UserMemo 객체를 삭제한다. 회원 탈퇴 할때 사용
	 * 
	 * @param 메모의 일련번호
	 * @return 삭제의 성공여부
	 */
	public boolean deleteAllMemo(String id,String password);

}
