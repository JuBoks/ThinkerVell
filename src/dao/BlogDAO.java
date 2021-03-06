package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.JdbcUtil;
import vo.BlogBean;
import vo.UserBean;

import static db.JdbcUtil.*;

public class BlogDAO {
	// -------------------------------------------------------------------
	// 싱글톤 디자인 패턴을 활용하여 1개의 인스턴스를 생성하여 공유
	private BlogDAO() {}
	private static BlogDAO instance;
	public static BlogDAO getInstance() {
		// BoardDAO 객체를 저장하는 변수 instance 가 null 일 때만 인스턴스 생성
		if(instance == null) {
			instance = new BlogDAO();
		}
		
		return instance;
	}
	// -------------------------------------------------------------------
	
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public void setConnection(Connection con) {
		this.con = con; // Service 클래스로부터 전달받은 DB 연결 객체(Connection 객체)를 멤버변수에 저장
	}
	
	
	
	// 글 등록 처리 : insertArticle() => BoardBean 객체 전달받음 => 실행 결과 레코드 수를 리턴
	public int insertArticle(BlogBean blogBean) {
		int num=0;
		int insertCount = 0;
		String sql="select MAX(blog_num) from blog";
		
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				num=rs.getInt(1)+1;
			}
		
		
		
		sql = "INSERT INTO blog VALUES(?,?,?,?,?,?,now(),?,?)";
		
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			pstmt.setString(2, blogBean.getBlog_writer());
			pstmt.setString(3, blogBean.getBlog_subject());
			pstmt.setString(4, blogBean.getBlog_content());
			pstmt.setInt(5, blogBean.getBlog_readcount());
			pstmt.setString(6, blogBean.getBlog_file());
			pstmt.setInt(7, blogBean.getBlog_like());
			pstmt.setString(8, blogBean.getBlog_content1());
			insertCount = pstmt.executeUpdate(); // INSERT 실행 결과를 int 타입으로 리턴 받음
		} catch (SQLException e) {
//			e.printStackTrace();
			System.out.println("INSERT 에러 : " + e.getMessage());
		} finally {
			close(rs);
			close(pstmt); // 자원 반환
		}
		
		return insertCount;
		
	}
	
	
	// 글 목록 갯수 구하기
	public int selectListCount() {
//		System.out.println("selectListCount()");
		
		int listCount = 0;
		
		// SELECT 구문 사용하여 게시물 수 카운트하여 listCount 에 저장
		String sql = "SELECT count(*) FROM blog";
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				listCount = rs.getInt("count(*)"); // 조회된 목록 갯수 저장
			}
			
		} catch (SQLException e) {
//			e.printStackTrace();
			System.out.println("selectListCount() 실패! : " + e.getMessage());
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return listCount;
	}
	
	
	// 글 목록 가져오기
	public ArrayList<BlogBean> selectArticleList(int page, int limit) {
//		System.out.println("selectArticleList()");
		
		ArrayList<BlogBean> articleList = new ArrayList<BlogBean>();
		BlogBean blogBean = null;
		
		String sql = "SELECT * FROM blog ORDER BY blog_num desc limit ? , ?";
		// => 참조글번호 내림차순 & 답글순서번호 오름차순 정렬
		// => 지정 row 번호부터 10개 조회
		
		try {
			pstmt = con.prepareStatement(sql);
			int startRow = (page - 1) * limit; // 읽기 시작할 row 번호
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				// 1개 게시물 레코드 읽어와서 BoardBean 객체에 저장
				blogBean = new BlogBean();
				
				blogBean.setBlog_num(rs.getInt("blog_num"));
				blogBean.setBlog_writer(rs.getString("blog_writer"));
				blogBean.setBlog_subject(rs.getString("blog_subject"));
				blogBean.setBlog_content1(rs.getString("blog_content1"));
				blogBean.setBlog_readcount(rs.getInt("blog_readcount"));
				blogBean.setBlog_file(rs.getString("blog_file"));
				blogBean.setBlog_date(rs.getDate("blog_date"));
				blogBean.setBlog_like(rs.getInt("blog_like"));
				blogBean.setBlog_content(rs.getString("blog_content1"));
				
				articleList.add(blogBean); // ArrayList 객체에 레코드 단위로 저장
				
//				System.out.println(rs.getInt("board_num"));
			}
		} catch (SQLException e) {
//			e.printStackTrace();
			System.out.println("selectArticleList() 실패! : " + e.getMessage());
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return articleList;
	}

	// 댓글쓰기 창에서 유저정보 가져오기
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
		
	// 글번호(board_num) 에 해당하는 레코드 정보 조회 => BoardBean 객체에 저장하여 리턴
	public BlogBean selectArticle(int blog_num) {
		BlogBean blogBean = null;
		
		String sql = "SELECT * FROM blog WHERE blog_num=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, blog_num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				blogBean = new BlogBean();
				
				blogBean.setBlog_num(rs.getInt("blog_num"));
				blogBean.setBlog_writer(rs.getString("blog_writer"));
				blogBean.setBlog_subject(rs.getString("blog_subject"));
				blogBean.setBlog_content1(rs.getString("blog_content1"));
				blogBean.setBlog_readcount(rs.getInt("blog_readcount"));
				blogBean.setBlog_file(rs.getString("blog_file"));
				blogBean.setBlog_date(rs.getDate("blog_date"));
				blogBean.setBlog_like(rs.getInt("blog_like"));
				blogBean.setBlog_content(rs.getString("blog_content"));
			}
			
		} catch (SQLException e) {
//			e.printStackTrace();
			System.out.println("selectArticle() 실패! : " + e.getMessage());
		} finally {
			close(rs);
			close(pstmt);
		}
				
		return blogBean;
	}
	
	
	// 게시물 조회수 업데이트 => 기존 readcount 값을 1 증가시킨 후 결과값을 리턴
	public int updateReadcount(int blog_num) {
		int updateCount = 0;
		
		// board_num 에 해당하는 레코드의 board_readcount 값을 1 증가시키기
		String sql = "UPDATE blog SET blog_readcount=blog_readcount+1 WHERE blog_num=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, blog_num);
			updateCount = pstmt.executeUpdate();
//			System.out.println(updateCount);
		} catch (SQLException e) {
			System.out.println("selectArticle() 실패! : " + e.getMessage());
		} finally {
			close(pstmt);
		}
		
		return updateCount;
	}
	
	
	// 게시물 작성자 본인 확인 - 게시물 번호와 입력된 패스워드를 읽어와서 확인 후 true/false 리턴
	public void isArticleBoardWriter(int blog_num) {
//		System.out.println("BoardDAO - isArticleBoardWriter");
		// 전체 레코드에서 글번호(board_num) 이 일치하는 레코드 찾기
		// => 조회된 레코드에서 패스워드(board_pass) 가 전달받은 패스워드와 일치하면 isWriter 변수를 true 변경
		
		
		String sql = "SELECT * FROM blog WHERE blog_num=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, blog_num);
			rs = pstmt.executeQuery();
			
			
			
		} catch (SQLException e) {
			System.out.println("isArticleBoardWriter() 실패! : " + e.getMessage());
		} finally {
			close(rs);
			close(pstmt);
		}
		
		
	}

	// 글 수정
	public int updateArticle(BlogBean article) {
		int updateCount = 0;
		
		// BoardBean 객체의 board_num 에 해당하는 레코드를 수정
		// => 글제목(board_subject), 글내용(content) 수정
		String sql = "UPDATE blog SET blog_subject=?,blog_writer=?,blog_content=?,blog_file=?,blog_content1=? WHERE blog_num=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, article.getBlog_subject());
			pstmt.setString(2, article.getBlog_writer());
			pstmt.setString(3, article.getBlog_content());
			pstmt.setString(4, article.getBlog_file());
			pstmt.setString(5, article.getBlog_content1());
			pstmt.setInt(6, article.getBlog_num());
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("updateArticle() 실패! : " + e.getMessage());
		} finally {
			close(pstmt);
		}
		
		return updateCount;
	}


	public int deleteArticle(int blog_num) {
		int deleteCount=0;
		
		String sql="delete from blog where blog_num=?";
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, blog_num);
			deleteCount=pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("deleteArticle() 실패!"+e.getMessage());
		}finally {
			close(pstmt);
		}
		
		return deleteCount;
	}
	
	public ArrayList<BlogBean> selectArticleList(int page, int limit, String option, String keyword) {
	//	System.out.println("selectArticleList");
		ArrayList<BlogBean> articleList = new ArrayList<BlogBean>();
		BlogBean blogBean=null;
		
//		select blog_num, blog_subject, blog_writer from blog where blog_subject like '%스턴%' and blog_subject like '%행위%';
		int startRow = (page-1)*10;
		try {
			String[] searches = keyword.split("\\s");
			String sql = "select * from blog where "+option+" like '%"+searches[0]+"%'";
			//System.out.println("length 값 : " +searches.length);
			//System.out.println("화이팅");
			int j=1;
			while(j<searches.length) {
				sql+=" and "+ option +" like ? ";
				j++;
				//System.out.println("j값 : "+j);
			}
			sql+=" order by blog_date desc limit ?, ?";
			pstmt = con.prepareStatement(sql);
			//System.out.println("나이따");
			int i=1;
			for(;i<=searches.length;i++) {
				pstmt.setString(i, "%"+searches[i-1]+"%");
				//System.out.println("searches값 : " + searches[i-1]);
				//System.out.println("i값 : " + i);
			}
			pstmt.setInt(i-1, startRow);
			pstmt.setInt(i, limit);
			
		//	System.out.println("pstmt sql : "+pstmt);
		//	System.out.println("startRow : " + startRow);
		//	System.out.println("limit : "+limit);
			//System.out.println("for문 바깥 i값 : "+i);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				//System.out.println("sql문 성공");
				blogBean= new BlogBean();
				
				blogBean.setBlog_num(rs.getInt("blog_num"));
				blogBean.setBlog_writer(rs.getString("blog_writer"));
				blogBean.setBlog_subject(rs.getString("blog_subject"));
				blogBean.setBlog_content1(rs.getString("blog_content1"));
				blogBean.setBlog_readcount(rs.getInt("blog_readcount"));
				blogBean.setBlog_file(rs.getString("blog_file"));
				blogBean.setBlog_date(rs.getDate("blog_date"));
				blogBean.setBlog_like(rs.getInt("blog_like"));
				blogBean.setBlog_content(rs.getString("blog_content1"));
				
				
				
				articleList.add(blogBean);
				//System.out.println(blogBean.getBlog_subject());
				
			}
			
		} catch (SQLException e) {
			System.out.println("selectArticleList() 실패"+e.getMessage());
		}finally {
			close(rs);
			close(pstmt);
		}
		return articleList;
	}
	public int selectListCount(String option, String keyword) {
		int listCount = 0;
		
		try {			
			String[] searches = keyword.split("\\s");
			String sql = "select count(*) from blog where "+option+" like '%"+searches[0]+"%'";
			//System.out.println("첫단어 : "+searches[0]);
			//System.out.println("sql : "+sql);
			//System.out.println("length 값 : " +searches.length);
			int j=1;
			while(j<searches.length) {
			//	System.out.println("option : "+option);
				sql+=" and "+ option +" like ? ";
				j++;
			//	System.out.println("j값 : "+j);
			}
			pstmt = con.prepareStatement(sql);
//			int i=2;
//			for(;i<=searches.length;i++) {
//				pstmt.setString(i, "%"+searches[i-1]+"%");
//				System.out.println("searches값 : " + searches[i-1]);
//				System.out.println("i값 : " + i);
//			}
//			for(초기값;조건;값오르는거)
			int i=2;
			for(;i<=searches.length;i++) {
				pstmt.setString(i-1, "%"+searches[i-1]+"%");
			//	System.out.println("searches값 : " + searches[i-1]);
			//	System.out.println("i값 : " + i);
			}
			
			//System.out.println("select list count sql : "+pstmt);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				//System.out.println("selectListCount():");
				listCount = rs.getInt("count(*)");
			}
		} catch (SQLException e) {
		//	System.out.println("여기");
			System.out.println("selectListCount() 실패"+e.getMessage());
		}finally {
			close(rs);
			close(pstmt);
		}
		return listCount;
	}
}



	
