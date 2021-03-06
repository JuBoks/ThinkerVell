package svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.NoticeDAO;

public class NoticeDeleteProService {
	
	// 게시물 번호에 해당하는 글 삭제 작업 수행
	public boolean removeArticle(int notice_num) {
		boolean isRemoveSuccess = false;
		
		Connection con = getConnection();
		
		NoticeDAO noticeDAO = NoticeDAO.getInstance();
		noticeDAO.setConnection(con);
		
		int deleteSuccess = noticeDAO.deleteArticle(notice_num);
		
		// 삭제 성공 여부 값(deleteSuccess) 가 0보다 크면 commit, isRemoveSuccess 를 true 로 변경
		if(deleteSuccess > 0) {
			commit(con);
			isRemoveSuccess = true;
		} else {
			// 0이면 rollback
			rollback(con);
		}
		
		close(con);
		
		return isRemoveSuccess;
	}


	}

