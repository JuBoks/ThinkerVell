package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.QnaDAO;
import svc.QnaReplyProService;
import vo.ActionForward;
import vo.QnaBean;

public class QnaReplyProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//System.out.println("QnaReplyProAction");
		QnaBean qnaBean = new QnaBean();
		ActionForward forward = null;
		HttpSession session = request.getSession();
		
		String qna_re_writer = request.getParameter("qna_reply_writer");
		String id = (String)session.getAttribute("id");
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		if (id == null) {
			out.println("<script>");
			out.println("alert('로그인 후 이용하실 수 있습니다.')");
			out.println("location.href='index.in'");
			out.println("</script>");
			return null;
		} 
		
		String qna_writer = request.getParameter("qna_writer");
		int qna_num = Integer.parseInt(request.getParameter("qna_num"));
		int qna_item_num = Integer.parseInt(request.getParameter("qna_item_num"));
		QnaReplyProService qnaReplyInsertProService = new QnaReplyProService();
		System.out.println(qna_writer);
		
		qnaBean.setQna_item_num(qna_item_num);
		qnaBean.setQna_writer(qna_writer);
		qnaBean.setQna_re_writer(qna_re_writer);
		qnaBean.setQna_subject(request.getParameter("qna_reply_subject"));
		qnaBean.setQna_content(request.getParameter("qna_reply_content"));
		
		boolean isInsertSuccess = qnaReplyInsertProService.insertReply(qnaBean, qna_num);
		
		
		if(!isInsertSuccess) {
			out.println("<script>"); // 자바스크립트 시작 태그
			out.println("alert('답글 등록 실패!')"); // 오류 메세지 다이얼로그 표시
			out.println("history.back()"); // 이전 페이지로 돌아가기
			out.println("</script>"); // 자바스크립트 종료 태그
		} else {
			// true 이면 ActionForward 객체를 사용하여 이동
			// => ActionForward 객체 생성, qnaList.bo 서블릿주소 지정, isRedirect 변수 값을 true 로 설정
			// => qnaList.bo 페이지로 이동하면서 주소가 변경되므로(새로운 요청이 발생하므로) Redirect 방식으로 포워딩
			forward = new ActionForward();
			forward.setPath("itemSingle.em?item_num="+qna_item_num);
			forward.setRedirect(true);
		}
		
		return forward;
	}

}
