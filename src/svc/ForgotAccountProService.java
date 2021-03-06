package svc;

import java.sql.Connection;
import static db.JdbcUtil.*;
import dao.UserDAO;

public class ForgotAccountProService {
	
	public String getId(String email, String phone) throws Exception{
		String getId;
		
		Connection con = getConnection();
		UserDAO userDAO = UserDAO.getInstance();
		userDAO.setConnection(con);
		getId = userDAO.findId(email, phone);
	
		
		if(getId!=null) {
			commit(con);
		} else {
			rollback(con);
		}
		
		close(con);
		
		return getId;
	}
}
