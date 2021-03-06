package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.w3c.dom.events.MutationEvent;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import svc.BlogWriteProService;
import svc.ReviewWriteProService;
import vo.ActionForward;
import vo.ReviewBean;

// XXXAction 클래스는 Action 인터페이스를 상속받아 추상메서드로 공통 메서드인 execute() 메서드를 구현한다
public class ReviewWriteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 글 쓰기 작업에 대한 비즈니스 로직 처리를 위한 준비 작업 및 마무리 작업(실제 비즈니스 로직은 Service 클래스와 DAO 클래스에서 수행)
		// Controller -> Action -> Service -> DAO -> Service -> Action -> Controller
		
		
		ActionForward forward = null;
		ReviewBean reviewBean = null;
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		if (id == null) {
			out.println("<script>");
			out.println("alert('잘못된 접근입니다.')");
			out.println("location.href='index.in'");
			out.println("</script>");
			return null;
		} 
		
		// 파일 업로드를 위한 정보 저장
		String realFolder ; // 실제 경로
		String saveFolder = "/itemUpload"; // 톰캣(이클립스) 상의 가상의 경로
		int fileSize = 5 * 1024 * 1024; // 파일 사이즈(5MB)
//		
		ServletContext context = request.getServletContext(); // 현재 서블릿 컨텍스트 객체 얻어오기
		realFolder = context.getRealPath(saveFolder); // 가상의 경로에 해당하는 실제 경로 얻어오기
//		
		Path newDirectory = Paths.get(realFolder);
        
        try {
            Path createDirResult = Files.createDirectories(newDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }
//		// 파일 업로드를 위한 MultipartRequest 객체 생성(cos.jar 필요)
		MultipartRequest multi = new MultipartRequest(request, realFolder, fileSize, "UTF-8", new DefaultFileRenamePolicy());
		Enumeration files = multi.getFileNames();
		
		reviewBean = new ReviewBean(); 
		
		reviewBean.setReview_subject(multi.getParameter("review_subject"));
		reviewBean.setReview_content(multi.getParameter("review_content"));
		reviewBean.setReview_item_num(Integer.parseInt(multi.getParameter("review_item_num")));
		reviewBean.setReview_user_id(multi.getParameter("review_user_id"));
		reviewBean.setReview_img(multi.getOriginalFileName((String) multi.getFileNames().nextElement()));
		
		// 실제 비즈니스 로직 처리를 담당할 Service 클래스(XXXAction => XXXService) 인스턴스를 생성하여
		// 처리 담당 메서드를 호출(매개변수로 BoardBean 객체 전달)
		ReviewWriteProService reviewWriteProService = new ReviewWriteProService();
		boolean isWriteSuccess = reviewWriteProService.registArticle(reviewBean);
		int review_item_num = Integer.parseInt(multi.getParameter("review_item_num"));
		// INSERT 수행 결과가 false 이면 자바 스크립트를 사용하여 "등록 실패" 메세지를 표시(alert())
		if(!isWriteSuccess) {
			out.println("<script>"); // 자바스크립트 시작 태그
			out.println("alert('게시물 등록 실패!')"); // 오류 메세지 다이얼로그 표시
			out.println("history.back()"); // 이전 페이지로 돌아가기
			out.println("</script>"); // 자바스크립트 종료 태그
		} else {
			// true 이면 ActionForward 객체를 사용하여 이동
			forward = new ActionForward();
			forward.setPath("itemSingle.em?item_num="+review_item_num);
			forward.setRedirect(true);
		}
		
		return forward;
	}

}




















