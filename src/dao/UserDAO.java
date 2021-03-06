package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static db.JdbcUtil.*;
import vo.UserBean;

public class UserDAO {
	
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private UserDAO() {}
	
	private static UserDAO instance ;
	
	public static UserDAO getInstance() {
		
		if(instance ==null) {
			instance = new UserDAO();
		}
		return instance;
	}
	
	public void setConnection(Connection con) {
		this.con = con;
		
	}
	
	//로그인
	public boolean isRightUser(UserBean userBean) {
		boolean isRightUser = false;
		String sql = "SELECT * FROM user WHERE user_id=? AND user_pass=?";
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, userBean.getUser_id());
			pstmt.setString(2, userBean.getUser_pass());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				isRightUser = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return isRightUser;
				
	} 
	
	
	// 회원정보 수정,탈퇴 회원 확인
	public int isUpdateUser(String id) {
		int isRightUser = 0;
		String sql = "SELECT * FROM user WHERE user_id=?";
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
					isRightUser = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return isRightUser;
				
	}
	
	// 회원가입
	public boolean userInsert(UserBean userBean) {
		boolean isInsertUser = false;
		String sql = "INSERT INTO user VALUES(null,?,?,?,?,?,?,?,?,?)";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userBean.getUser_id());
			pstmt.setString(2, userBean.getUser_pass());
			pstmt.setString(3, userBean.getUser_name());
			pstmt.setString(4, userBean.getUser_age());
			pstmt.setString(5, userBean.getUser_gender());
			pstmt.setString(6, userBean.getUser_address());
			pstmt.setString(7, userBean.getUser_phone());
			pstmt.setString(8, userBean.getUser_email());
			pstmt.setString(9, userBean.getUser_post());
			pstmt.executeUpdate();
			isInsertUser = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return isInsertUser;
	}
	
	//회원탈퇴
	public boolean userDelete(String id, String pass) {
		boolean isDeleteUser = false;
		String sql = "SELECT * FROM user WHERE user_id=?";
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(pass.equals(rs.getString("user_pass"))) {
					sql = "DELETE FROM user WHERE user_id=?";
					pstmt=con.prepareStatement(sql);
					pstmt.setString(1, id);
					pstmt.executeUpdate();
					isDeleteUser = true;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return isDeleteUser;
	}
	
	//회원정보수정
	public boolean userUpdate(UserBean userBean) {
		boolean isUpdateUser = false;
		int isUpdate = 0;
		String sql = " UPDATE user SET user_pass=?,user_name=?,user_age=?,user_gender=?,user_address=?,user_phone=?,user_email=?,user_post=? WHERE user_id=?";
		try {
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, userBean.getUser_pass());
			pstmt.setString(2, userBean.getUser_name());
			pstmt.setString(3, userBean.getUser_age());
			pstmt.setString(4, userBean.getUser_gender());
			pstmt.setString(5, userBean.getUser_address());
			pstmt.setString(6, userBean.getUser_phone());
			pstmt.setString(7, userBean.getUser_email());
			pstmt.setString(8, userBean.getUser_post());
			pstmt.setString(9, userBean.getUser_id());
			isUpdate = pstmt.executeUpdate();
			
			if(isUpdate>0) {
				isUpdateUser = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return isUpdateUser;
		
	}
	
	//비밀번호 찾기 => 메일로 발송된 임시비밀번호로 사용자 비밀번호 업데이트
		public int passUpdate(String buf, String email) {
			
			int result=0;
			String sql = " UPDATE user SET user_pass=? WHERE user_email=?";
			try {
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, buf);
				pstmt.setString(2, email);
				result = pstmt.executeUpdate(); //update된 행의 갯수 반환
				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				close(pstmt);
				
			}
			return result;
			
		}
		
	// 마이페이지 정보	
	public UserBean getUserInfo(String id) {
	    UserBean userBean = null;
	    String sql = "select * from user where user_id=?";
	    try {
	      pstmt = con.prepareStatement(sql);
	      pstmt.setString(1, id);
	      rs = pstmt.executeQuery();
	      
	      if(rs.next()) {
	        userBean = new UserBean();
	        userBean.setUser_id(rs.getString("user_id"));
	        userBean.setUser_pass(rs.getString("user_pass"));
	        userBean.setUser_name(rs.getString("user_name"));
	        userBean.setUser_age(rs.getString("user_age"));
	        userBean.setUser_gender(rs.getString("user_gender"));
	        userBean.setUser_address(rs.getString("user_address"));
	        userBean.setUser_phone(rs.getString("user_phone"));
	        userBean.setUser_email(rs.getString("user_email"));
	        userBean.setUser_post(rs.getString("user_post"));
	      }
	    } catch (SQLException e) {
	      e.printStackTrace();
	    } finally {
	      close(rs);
	      close(pstmt);
	    }
	    return userBean;
	  }
	
	// 관리자페이지에서 가입회원 정보
	public ArrayList<UserBean> getUserList(){
		
		ArrayList<UserBean> userList = new ArrayList<UserBean>();
		UserBean userBean = new UserBean();
	
		String sql = "SELECT * FROM user";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			 if(rs.next()) {
			        userBean = new UserBean();
			        userBean.setUser_id(rs.getString("user_id"));
			        userBean.setUser_pass(rs.getString("user_pass"));
			        userBean.setUser_name(rs.getString("user_name"));
			        userBean.setUser_age(rs.getString("user_age"));
			        userBean.setUser_gender(rs.getString("user_gender"));
			        userBean.setUser_address(rs.getString("user_address"));
			        userBean.setUser_phone(rs.getString("user_phone"));
			        userBean.setUser_email(rs.getString("user_email"));
			        userBean.setUser_post(rs.getString("user_post"));
			        userList.add(userBean);
			      }
			    } catch (SQLException e) {
			    	
			      e.printStackTrace();
			    } finally {
			      close(rs);
			      close(pstmt);
			    }
			    return userList;
	}
	
	//아이디 찾기
	public String findId(String email,String phone) {
		String id=null;
		String sql;
		if(email!=null &&phone ==null) {
			 sql ="SELECT * FROM user WHERE user_email=?";			
			 try {
				pstmt = con.prepareStatement(sql);
				 pstmt.setString(1, email);
				 rs = pstmt.executeQuery();
				 if(rs.next()) {
					 id = rs.getString("user_id");
				 }
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(pstmt);
			}
		}else if(email==null && phone !=null) {
			sql ="SELECT * FROM user WHERE user_phone=?";	
			 try {
					pstmt = con.prepareStatement(sql);
					 pstmt.setString(1, phone);
					 rs = pstmt.executeQuery();
					 if(rs.next()) {
						 id = rs.getString("user_id");
					 }
				} catch (SQLException e) {
					e.printStackTrace();
				}finally {
					close(rs);
					close(pstmt);
				}
		}
		return id;
	}

	// 회원가입 아이디 체크
	public int checkId(String id) {
		con = getConnection();
		int re = 0;
		String sql = "select * from user where user_id=?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				re = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}
		
		return re;
	}
	
	// 회원가입 메일 체크
	public int checkMail(String email) {
		con = getConnection();
		int re = 0;
		String sql = "select * from user where user_email=?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				re = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}
		
		return re;
	}
	
	// 회원가입 아이디 체크
	public int checkPhone(String phone) {
		con = getConnection();
		int re = 0;
		String sql = "select * from user where user_phone=?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, phone);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				re = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}
		
		return re;
	}

	public int allUserCount() {

		int listCount = 0;
		
		String sql = "SELECT count(*) FROM user";

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				listCount = rs.getInt(1); 
			}

		} catch (SQLException e) {
		} finally {
			close(rs);
			close(pstmt);
		}

		return listCount;
	}
	
	public ArrayList<UserBean> getAllUserList(int page, int limit) {
		ArrayList<UserBean> articleList = new ArrayList<UserBean>();
		UserBean userBean = null;

		int startRow = (page - 1) * 10; 

		String sql = "SELECT * FROM user ORDER BY user_num desc limit ?, ?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				userBean = new UserBean();

				userBean.setUser_num(rs.getInt("user_num"));
				userBean.setUser_id(rs.getString("user_id"));
				userBean.setUser_pass(rs.getString("user_pass"));
				userBean.setUser_name(rs.getString("user_name"));
				userBean.setUser_age(rs.getString("user_age"));
				userBean.setUser_gender(rs.getString("user_gender"));
				userBean.setUser_address(rs.getString("user_address"));
				userBean.setUser_phone(rs.getString("user_phone"));
				userBean.setUser_email(rs.getString("user_email"));
				userBean.setUser_post(rs.getString("user_post"));
				articleList.add(userBean); 

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}

		return articleList;
	}

	public List<Map<String, Integer>> getSalesDayList(String month) {
		List<Map<String, Integer>> salesList = new ArrayList<Map<String, Integer>>();
		
		try {
			String sql="select sum(bi.buy_item_price * bi.buy_item_count) as Total, b.buy_buydate " + 
					"from buy_item bi, buy b " + 
					"where bi.buy_item_buy_num = b.buy_num and MONTH(b.buy_buydate) = "+ month +" " + 
					"group by b.buy_buydate " + 
					"order by b.buy_buydate asc;"; 
			
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				Map<String, Integer> map = new HashMap<String, Integer>();
				map.put(rs.getString("b.buy_buydate"), rs.getInt("Total"));
				salesList.add(map);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("getSalesDayList 실패! ( " + e.getMessage() + " )");
		}finally {
			close(rs);
			close(pstmt);
		}		
		
		return salesList;
	}
	
	public List<Map<String, Integer>> getSalesMonthList(String year) {
		List<Map<String, Integer>> salesList = new ArrayList<Map<String, Integer>>();
		
		try {
			String sql="select sum(bi.buy_item_price * bi.buy_item_count) as Total, MONTH(b.buy_buydate) as Month " + 
					"from buy_item bi, buy b " + 
					"where bi.buy_item_buy_num = b.buy_num and YEAR(b.buy_buydate) = "+ year +" " + 
					"group by MONTH(b.buy_buydate) " + 
					"order by b.buy_buydate asc;";
			
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				Map<String, Integer> map = new HashMap<String, Integer>();
				map.put(rs.getString("Month"), rs.getInt("Total"));
				salesList.add(map);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}		
		
		return salesList;
	}
	public ArrayList<Integer> getAgeList() {
		int listCount = 0;
		ArrayList<Integer> ageList = new ArrayList<Integer>();
		
		String sql = "select count(*) from user where user_age >= ? AND user_age <= ?";
		try {
			for(int i = 0; i < 10; i++) {
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, i*10);
				pstmt.setInt(2, (i*10)+9);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					listCount = rs.getInt(1); 
				}
				ageList.add(listCount);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return ageList;
	}

	public int getMaleList() {
		int maleList = 0;
		ArrayList<Integer> genderList = new ArrayList<Integer>();
		
		String sql = "select count(*) from user where user_gender = '남'";
		try {
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					maleList = rs.getInt(1); 
				}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return maleList;
	}

	public ArrayList<Integer> getAgeBuyList() {
		int ageBuyList = 0;
		ArrayList<Integer> ageBuyLists = new ArrayList<Integer>();
		
		String sql = "select sum(b.buy_count) from buy b join user u on (b.buy_user_id = u.user_id) where u.user_age between ? and ?";
		try {
			for(int i = 0; i < 10; i++) {
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, i*10);
				pstmt.setInt(2, (i*10)+9);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					ageBuyList = rs.getInt(1); 
				}
				ageBuyLists.add(ageBuyList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return ageBuyLists;
	}

	public ArrayList<Integer> getGenderFavor() {
		int genderFavor = 0;
		ArrayList<Integer> genderFavors = new ArrayList<Integer>();
		String[] sql = {
		"select sum(f.user_favor_aroma) from user_favor f join user u on (f.user_favor_user_id = u.user_id) where u.user_gender = '남'",
		"select sum(f.user_favor_acidity) from user_favor f join user u on (f.user_favor_user_id = u.user_id) where u.user_gender = '남'",
		"select sum(f.user_favor_sweetness) from user_favor f join user u on (f.user_favor_user_id = u.user_id) where u.user_gender = '남'",
		"select sum(f.user_favor_bitterness) from user_favor f join user u on (f.user_favor_user_id = u.user_id) where u.user_gender = '남'",
		"select sum(f.user_favor_body) from user_favor f join user u on (f.user_favor_user_id = u.user_id) where u.user_gender = '남'",
		"select sum(f.user_favor_aroma) from user_favor f join user u on (f.user_favor_user_id = u.user_id) where u.user_gender = '여'",
		"select sum(f.user_favor_acidity) from user_favor f join user u on (f.user_favor_user_id = u.user_id) where u.user_gender = '여'",
		"select sum(f.user_favor_sweetness) from user_favor f join user u on (f.user_favor_user_id = u.user_id) where u.user_gender = '여'",
		"select sum(f.user_favor_bitterness) from user_favor f join user u on (f.user_favor_user_id = u.user_id) where u.user_gender = '여'",
		"select sum(f.user_favor_body) from user_favor f join user u on (f.user_favor_user_id = u.user_id) where u.user_gender = '여'"};
		try {
			for(int i = 0; i < sql.length; i++) {
				pstmt = con.prepareStatement(sql[i]);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					genderFavor = rs.getInt(1);
				}
				genderFavors.add(genderFavor);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return genderFavors;
	}
	
	


	
	
}

