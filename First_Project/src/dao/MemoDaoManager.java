package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import vo.Message;
import vo.UserAccount;
import vo.UserMemo;

public class MemoDaoManager implements MemoInterface {
	PreparedStatement stmt = null;
	ResultSet rs = null;
	Connection con = null;
	private String tempId = null;
	private String tempNm = null;
	String callSeq = "MEMOSEQ.NEXTVAL";

	@Override
	public boolean insertAccount(UserAccount user) 
	{
		boolean temp = false;

		try 
		{
			con = ConnectionManager.getConnection();
			String sql = "select * from useraccount where id = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, user.getId());
			rs = stmt.executeQuery();
			if (rs.next()) 
			{
				return temp;
			}
			sql = "insert into useraccount values(?,?,?)";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, user.getName());
			stmt.setString(2, user.getId());
			stmt.setString(3, user.getPassword());
			int result  = stmt.executeUpdate();
			if(result > 0) 
			{
				temp = true;
			}
		}catch(Exception e) 
		{
		} finally 
		{
			try {
				if(con != null)ConnectionManager.close(con);
				if (stmt != null) stmt.close();
				if (rs != null) rs.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return temp;
	}

	

	@Override
	public UserAccount loginAccount(UserAccount user) 
	{
		UserAccount temp = new UserAccount();
		try 
		{
			con = ConnectionManager.getConnection();
			String sql = "select * from useraccount where id = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, user.getId());
			rs = stmt.executeQuery();
			while(rs.next()) 
			{
				String id = rs.getString(2);
				String name = rs.getString(1);
				String pw = rs.getString(3);
				temp = new UserAccount(name, id, pw);
			}

			if (temp != null) 
			{
				if((user.getId().equals(temp.getId())) && user.getPassword().equals(temp.getPassword())) 
				{
					this.tempId = temp.getId();
					this.tempNm = temp.getName();
					return temp;
				} else return null;
			}
			
		} catch(Exception e) 
		{
		}finally 
		{
			try {
				if(con != null)ConnectionManager.close(con);
				if (stmt != null) stmt.close();
				if (rs != null) rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return temp;
	}


	
	@Override
	public boolean deleteAccount(UserAccount user) 
	{
		String name = null;
		String pw = null;
		String id = null;
		boolean temp = false;

		try 
		{
			con = ConnectionManager.getConnection();
			String sql = "delete useraccount where id = ? ";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, user.getId());
			int result = stmt.executeUpdate();
			if(result > 0) 
			{
				temp = true;
			}
			
		} catch(Exception e) 
		{
		} finally 
		{
			try 
			{
				if(con != null)ConnectionManager.close(con);
				if (stmt != null) stmt.close();
				if (rs != null) rs.close();
			} catch (Exception e) 
			{
			}
		}
		return temp;
	}

	
	
	@Override
	public boolean insertMemo(UserMemo memo) 
	{
		boolean flag = false;
		
		try 
		{
			con = ConnectionManager.getConnection();
			String sql = "insert into usermemo(memoseq,id,title,content) values(MEMOSEQ.NEXTVAL,?,?,?)";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, memo.getId());
			stmt.setString(2, memo.getTitle());
			stmt.setString(3, memo.getContent());
			int result = stmt.executeUpdate();

			if(result > 0) 
			{
				flag = true;
			}
		} 
		catch(Exception e) 
		{
		} 
		finally 
		{
			try 
			{
				if (con != null)ConnectionManager.close(con);
				if (stmt != null) stmt.close();
				if (rs != null) rs.close();
			} catch (Exception e) 
			{
			}
		}
		return flag;
	}


	
	@Override
	public ArrayList<UserMemo> getMemoList(String id) 
	{
		ArrayList<UserMemo> temp = new ArrayList<UserMemo>();

		try 
		{
			con = ConnectionManager.getConnection();
			String sql = "select * from usermemo where id = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, id);
			rs = stmt.executeQuery();

			while(rs.next()) 
			{
				String tempseq = rs.getString(1);
				String tempid = rs.getString(2);
				String temptitle = rs.getString(3);
				String tempcont = rs.getString(4);
				String tempdate = rs.getString(5);
				UserMemo tempp = new UserMemo(tempseq,tempid,temptitle,tempcont,tempdate);
				temp.add(tempp);
			}
		} catch(Exception e) 
		{
		} finally 
		{
			try 
			{
				if(con != null)ConnectionManager.close(con);
				if (stmt != null) stmt.close();
				if (rs != null) rs.close();
			} catch (Exception e)
			{
			}
		}
		return temp;
	}



	@Override
	public ArrayList<UserMemo> searchMemo(String searchword, String id)
	{
		ArrayList<UserMemo> temp = new ArrayList<UserMemo>();
		ArrayList<UserMemo> tempp = new ArrayList<UserMemo>();
		try 
		{
			String sw = "%" +searchword+"%";
			con = ConnectionManager.getConnection();
			String sql = "select * from usermemo where id = ? and (title like ? or content like ?) ";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, id);
			stmt.setString(2, sw);
			stmt.setString(3, sw);
			rs  = stmt.executeQuery();
			while (rs.next()) 
			{
				String tempseq = rs.getString(1);
				String tempid = rs.getString(2);
				String temptitle = rs.getString(3);
				String tempcont = rs.getString(4);
				String tempdate = rs.getString(5);
				UserMemo temppp = new UserMemo(tempseq,tempid,temptitle,tempcont,tempdate);
				temp.add(temppp);
			}
		
		} catch(Exception e) 
		{
		} finally 
		{
			try 
			{
				if(con != null)ConnectionManager.close(con);
				if (stmt != null) stmt.close();
				if (rs != null) rs.close();
			} catch (Exception e) 
			{
			}
		}
		return temp;
	}


	@Override
	public UserMemo searchOneMemo(String seq, String id) 
	{
		UserMemo temp = null;
		try 
		{
			con = ConnectionManager.getConnection();
			String sql = "select * from usermemo where memoseq = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, seq);
			rs = stmt.executeQuery();
			while (rs.next()) 
			{
				String tempseq = rs.getString(1);
				String tempid = rs.getString(2);
				String temptitle = rs.getString(3);
				String tempcont = rs.getString(4);
				String tempdate = rs.getString(5);
				temp = new UserMemo(tempseq,tempid,temptitle,tempcont,tempdate);
			}
		} catch(Exception e) 
		{
		} finally 
		{
			try 
			{
				if(con != null)ConnectionManager.close(con);
				if (stmt != null) stmt.close();
				if (rs != null) rs.close();
			} 
			catch (Exception e) 
			{
			}
		}
		return temp;
	}

	@Override
	public boolean updateMemo(String seq, UserMemo memo) 
	{
		boolean flag = false;

		try 
		{
			con = ConnectionManager.getConnection();
			String sql = "update usermemo set title = ?, content = ? where memoseq = ? and id = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, memo.getTitle());
			stmt.setString(2, memo.getContent());
			stmt.setString(3, seq);
			stmt.setString(4, memo.getId());
			int result = stmt.executeUpdate();
			if (result > 0) 
			{
				flag = true;
			}
		} catch(Exception e) 
		{
		} finally 
		{
			try 
			{
				if(con != null)ConnectionManager.close(con);
				if (stmt != null) stmt.close();
				if (rs != null) rs.close();
			} catch (Exception e) 
			{
			}
		}
		return flag;
	}

	@Override
	public boolean deleteMemo(String seq)
	{
		boolean flag = false;

		try 
		{
			con = ConnectionManager.getConnection();
			String sql = "delete usermemo where memoseq = ? and id = ? ";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, seq);
			stmt.setString(2, this.tempId);
			int result = stmt.executeUpdate();
			if (result > 0) 
			{
				flag = true;
			}
		} catch(Exception e) 
		{
		} finally 
		{
			try 
			{
				if(con != null)ConnectionManager.close(con);
				if (stmt != null) stmt.close();
				if (rs != null) rs.close();
			} catch (Exception e) 
			{
				// TODO Auto-generated catch block
			}
		}
		return flag;
	}



	@Override
	public boolean deleteAllMemo(String id,String password) 
	{
		boolean flag = false;

		try 
		{
			con = ConnectionManager.getConnection();
			String sql = "delete usermemo where id = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, id);
			int result = stmt.executeUpdate();

			sql = "delete messagebox where reid = ?";
			stmt= con.prepareStatement(sql);
			stmt.setString(1, id);
			result = result + stmt.executeUpdate();

			sql = "delete friend where myid = ? or friendid = ?";
			stmt= con.prepareStatement(sql);
			stmt.setString(1, id);
			stmt.setString(2, id);
			result = result +  stmt.executeUpdate();

			if (result > 3) 
			{
				flag = true;
			}
		} catch(Exception e) 
		{
		} finally {
			try {
				if(con != null)ConnectionManager.close(con);
				if (stmt != null) stmt.close();
				if (rs != null) rs.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
		}
		return flag;
	}

	

	public int insertInfo(String hint, String id, String name) 
	{
		int result = 0;

		try 
		{
			con = ConnectionManager.getConnection();
			String sql = "insert into userinfo(id,name,hint) values(?,?,?)";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, id);
			stmt.setString(2, name);
			stmt.setString(3, hint);
			result = stmt.executeUpdate();

		} catch (Exception e) 
		{
		} finally 
		{
			try
			{
				if(con != null)ConnectionManager.close(con);
				if (stmt != null) stmt.close();
				if (rs != null) rs.close();
			} catch (Exception e) 
			{
			}
		}
		return result;
	}


	public UserAccount findAccount(String id2, String name2, String hint) 
	{
		UserAccount temp = null;

		try 
		{
			con = ConnectionManager.getConnection();
			String sql = "select * from userinfo where id = ? and name = ? and hint = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, id2);
			stmt.setString(2, name2);
			stmt.setString(3, hint);
			rs = stmt.executeQuery();
			if (!(rs.next())) 
			{
				return temp;
			}		

			sql = "select * from useraccount where id = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, id2);
			rs = stmt.executeQuery();
			while(rs.next()) 
			{
				String name = rs.getString(1);
				String id = rs.getString(2);
				String pw = rs.getString(3);
				temp = new UserAccount(name, id, pw);
			}
		} catch(Exception e) 
		{
		}  finally 
		{
			try 
			{
				if(con != null)ConnectionManager.close(con);
				if (stmt != null) stmt.close();
				if (rs != null) rs.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
		}
		return temp;
	}


	public ArrayList<UserAccount> callMyFriend(String id) 
	{
		ArrayList<UserAccount> temp = new ArrayList<UserAccount>();

		try 
		{
			con = ConnectionManager.getConnection();
			String sql = "select * from friend where (myid = ? or friendid = ? )and status = 'Y'";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, id);
			stmt.setString(2, id);
			rs = stmt.executeQuery();

			while(rs.next()) 
			{
				UserAccount tempUA = new UserAccount();
				String name = rs.getString(4);
				String tempID = rs.getString(3);
				if(tempID.equals(this.tempId)) 
				{
					tempID = rs.getString(1);
					name = rs.getString(2);
					tempUA = new UserAccount(name,tempID,null);
				} else 
				{
					tempUA = new UserAccount(name, tempID, null);
				}
				temp.add(tempUA);
			}
			
		} catch(Exception e) 
		{
		} finally 
		{
			try 
			{
				if(con != null)ConnectionManager.close(con);
				if (stmt != null) stmt.close();
				if (rs != null) rs.close();
			} 
			catch (Exception e) 
			{
			}
		}
		return temp;
	}

	

	public boolean addFriend(String id, String id2)
	{
		boolean flag = false;
		UserAccount temp = new UserAccount();
		int result = 0;
		
		try 
		{
			if (id.equals(id2)) return flag;  

			
			con = ConnectionManager.getConnection();
			String sql = "select * from friend where (myid = ? and friendid = ?) or( myid = ? and friendid = ?)";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, id);
			stmt.setString(2, id2);
			stmt.setString(3, id2);
			stmt.setString(4, id);
			rs = stmt.executeQuery();

			while(rs.next()) 
			{
				return flag;  
			}

			sql = "select * from useraccount where id = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, id2);
			rs = stmt.executeQuery();

			while(rs.next()) 
			{
				String tempName = rs.getString(1);
				String pw = rs.getString(3);
				temp = new UserAccount(tempName, id2, pw);
			} 

			sql = "insert into friend values(?,?,?,?,'W')";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, id);
			stmt.setString(2, this.tempNm);
			stmt.setString(3, id2);
			stmt.setString(4, temp.getName());

			result = stmt.executeUpdate();

			if (result > 0)
			{
				flag = true;
			}
		} 
		catch(Exception e) 
		{
		} 
		finally 
		{
			try 
			{
				if(con != null)ConnectionManager.close(con);
				if (stmt != null) stmt.close();
				if (rs != null) rs.close();
			} 
			catch (Exception e) 
			{
			}
		}
		return flag;
	}

	

	public ArrayList<UserAccount> addRequest() 
	{
		ArrayList<UserAccount> tempList = new ArrayList<UserAccount>();
		
		try 
		{
			con = ConnectionManager.getConnection();
			String sql = "select * from friend where friendid = ? and status = 'W'";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, this.tempId);
			rs = stmt.executeQuery();
			
			while(rs.next()) 
			{
				String id = rs.getString(1);
				String name = rs.getString(2);
				UserAccount temp = new UserAccount(name, id, null);
				tempList.add(temp);
			}

		} catch(Exception e) 
		{
		} 
		finally 
		{
			try 
			{
				if(con != null)ConnectionManager.close(con);
				if (stmt != null) stmt.close();
				if (rs != null) rs.close();
			} catch (Exception e) 
			{
			}
		}
		return tempList;
	}


	
	public boolean agree(String check) 
	{
		boolean flag = false;
		int result = 0;
		int result2 = 0;
		
		try 
		{
//			con = ConnectionManager.getConnection();
//			String sql = "select * from friend where myid = ? and friendid = ? and status = 'Y'";
//			stmt = con.prepareStatement(sql);
//			stmt.setString(1, check);
//			stmt.setString(2, this.tempId);
//			rs = stmt.executeQuery();
//			String tempid = "" ;
//			String friendid = "";
//			String friendname = ""; //널포인트 익셉션 처리할것;
//			while(rs.next()) 
//			{
//				tempid = rs.getString(3);
//				friendname = rs.getString(2);
//				UserAccount tempuser = new UserAccount(friendname, tempid, null);
//			}
//			if (rs.next()) return flag;
//			
//			if(tempid.equals(this.tempId)) 
//			{
//				System.out.println("Ar Fd");
//				return flag;
//			}


			con = ConnectionManager.getConnection();
			String sql = "update friend set status = 'Y' where friendid = ? and myid = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, this.tempId);
			stmt.setString(2, check);

			result = stmt.executeUpdate();
			
			if(result > 0) 
			{
				flag = true;
			}

		} 
		catch(Exception e) 
		{
		} 
		finally 
		{
			try 
			{
				if(con != null)ConnectionManager.close(con);
				if (stmt != null) stmt.close();
				if (rs != null) rs.close();
			} 
			catch (Exception e) 
			{
			}
		}
		return flag;
	}

	

	public boolean deleteFriend(String friendID)
	{
		boolean flag = false;
		
		try 
		{
			con = ConnectionManager.getConnection();
			String sql = "delete from friend where (myid = ? and friendid = ?) or (myid = ? and friendid = ?)";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, this.tempId);
			stmt.setString(2, friendID);
			stmt.setString(3, friendID);
			stmt.setString(4, this.tempId);
			int result = stmt.executeUpdate();
			
			if(result > 0) 
			{
				flag = true;
			}

		} catch(Exception e) 
		{
		} 
		finally 
		{
			try 
			{
				if(con != null)ConnectionManager.close(con);
				if (stmt != null) stmt.close();
				if (rs != null) rs.close();
			} 
			catch (Exception e) 
			{
			}
		}
		return flag;
	}


	
	public ArrayList<Message> myBox(String id)
	{
		ArrayList<Message> temp = new ArrayList<Message>();
		
		try 
		{
			con = ConnectionManager.getConnection();
			String sql = "select * from messagebox where reid = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, id);
			rs = stmt.executeQuery();
			while(rs.next()) 
			{
				String seq = rs.getString(1);
				String sendid = rs.getString(2);
				String content = rs.getString(3);
				String senddate = rs.getString(6);
				String status = rs.getString(5);
				Message tempp = new Message(seq, sendid, content, this.tempId, senddate,status);
				temp.add(tempp);
			}
			
		} 
		catch(Exception e) 
		{
		} 
		finally 
		{
			try 
			{
				if (con != null)ConnectionManager.close(con);
				if (stmt != null) stmt.close();
				if (rs != null) rs.close();
			} catch (Exception e) 
			{
			}
		}
		return temp;
	}


	
	public boolean sendMessage(String reid, String content)
	{
		boolean flag = false;
		
		try 
		{	
			con = ConnectionManager.getConnection();
			String sql = "insert into messagebox(messageseq,sendid,content,reid,status) values(meseq.nextval,?,?,?,'N')";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, this.tempId);
			stmt.setString(2, content);
			stmt.setString(3, reid);
			int result = stmt.executeUpdate();

			if(result > 0) 
			{
				flag = true;
			}
		} 
		catch(Exception e) 
		{
		} 
		finally 
		{
			try 
			{
				if(con != null)ConnectionManager.close(con);
				if (stmt != null) stmt.close();
				if (rs != null) rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("Check id");
			}
		}
		return flag;
	}


	
	public void statch(String seq) 
	{

		try 
		{
			con = ConnectionManager.getConnection();
			String sql = "update messagebox set status = 'Y' where messageseq = ? ";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, seq);
			int result = stmt.executeUpdate();
			
		} 
		catch(Exception e) 
		{
		} 
		finally 
		{
			try 
			{
				if(con != null)ConnectionManager.close(con);
				if (stmt != null) stmt.close();
				if (rs != null) rs.close();
			} 
			catch (Exception e) 
			{
			}
		}
	}



	public boolean deleteMessage(String answerr) 
	{
		boolean flag = false;
		try 
		{
			con = ConnectionManager.getConnection();
			String sql = "delete from messagebox where reid = ? and messageseq = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, this.tempId);
			stmt.setString(2, answerr);
			int result = stmt.executeUpdate();
			if (result > 0) 
			{
				flag = true;
			}
		} 
		catch(Exception e) 
		{
		} 
		finally 
		{
			try 
			{
				if (con != null)ConnectionManager.close(con);
				if (stmt != null) stmt.close();
				if (rs != null) rs.close();
			} 
			catch (Exception e) 
			{
			}
		}
		return flag;
	}
	
	
	
}
