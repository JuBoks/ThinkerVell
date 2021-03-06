package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import svc.DeleteMemberProService;
import vo.ActionForward;
import vo.UserBean;

public class DeleteMemberProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		boolean isDeleteSuccess = false;
		
		
		String id = request.getParameter("id");
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		if (id == null  ) {
			out.println("<script>");
			out.println("alert('잘못된 접근입니다.')");
			out.println("location.href='index.in'");
			out.println("</script>");
			return null;
		} 
		String pass = request.getParameter("pass");
		
		UserBean userBean = new UserBean();
		userBean.setUser_id(id);
		userBean.setUser_pass(pass);
		
		DeleteMemberProService deleteMemberProService = new DeleteMemberProService();
		isDeleteSuccess = deleteMemberProService.deleteMember(userBean);
		
		if(!isDeleteSuccess) {
			out.println("<script>");
			out.println("alert('회원탈퇴 실패!')");
			out.println("history.back()");
			out.println("</script>");
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("id", null);
			
			out.println("<script>");
			out.println("alert('탈퇴되었습니다')");
			out.println("location.href='index.in'");
			out.println("</script>");
			
			forward = new ActionForward();
		}
		
		
		return null;
	}

}
