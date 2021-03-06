package svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.QnaDAO;
import vo.QnaBean;
import vo.UserBean;


public class QnaModifyProService {
	
//	// 게시물 작성자 일치 여부 확인을 위해 게시물 번호와 패스워드를 전달받아 확인 작업 수행
//	public boolean isArticleWriter(int qna_num, String pass) throws Exception {
//		System.out.println("qnaModifyProService - isArticleWriter()");
//		boolean isArticleWriter = false;
//		
//		// Connection 객체 가져오기
//		Connection con = getConnection();
//		
//		// qnaDAO 인스턴스 얻어오기 => setConnection() 메서드를 호출하여 Connection 객체 전달
//		qnaDAO QnaDAO = qnaDAO.getInstance();
//		QnaDAO.setConnection(con);
//
//		isArticleWriter = QnaDAO.isArticleqnaWriter(qna_num, pass); // 본인 확인(패스워드 일치여부 판별)
//		    
//		close(con);
//		
//		return isArticleWriter;
//	}

	public boolean modifyArticle(QnaBean article) {
		boolean isModifySuccess = false;
		
		// DB 작업 전 DB 접속을 위해 JdbcUtil 클래스의 static 메서드 getConnection() 를 호출하여 DB 접속
		Connection con = getConnection();
		
		// 싱글톤 디자인 패턴으로 생성된 qnaDAO 인스턴스를 얻어오기
		QnaDAO qnaDAO = QnaDAO.getInstance();
		qnaDAO.setConnection(con); // Connection 객체를 qnaDAO 객체에 전달
		int updateCount = qnaDAO.updateArticle(article); // 글 수정 처리(결과를 int형으로 전달받음)
		
		// insertCount 가 0보다 크면 트랜잭션 Commit, 아니면 트랜잭션 Rollback 수행
		if(updateCount > 0) {
			commit(con);
			isModifySuccess = true; // 성공 표시
		} else {
			rollback(con);
		}
		
		// DB 접속 해제(Connection 자원 반환)
		close(con);
		
		return isModifySuccess;
	}
	
	public UserBean getUserInfo(String name, String id) {
		UserBean userBean = new UserBean();
		
		Connection con = getConnection();
		QnaDAO qnaDAO = QnaDAO.getInstance();
		qnaDAO.setConnection(con);
		
		userBean = qnaDAO.getUserInfo(id);
		
		if(userBean !=null && userBean.getUser_name().equals(name)) {
			commit(con);
		} else {
			rollback(con);
		}
		
		close(con);
		
		return userBean;
	}

	
}

