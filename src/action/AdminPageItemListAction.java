package action;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import svc.ShopMainService;
import vo.ActionForward;
import vo.ItemBean;
import vo.PageInfo;

public class AdminPageItemListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		if (id == null || !id.equals("admin") ) {
			out.println("<script>");
			out.println("alert('잘못된 접근입니다.')");
			out.println("location.href='index.in'");
			out.println("</script>");
			return null;
		} 
		
		ArrayList<ItemBean> itemList = new ArrayList<>(); 
		int page = 1;
		int limit = 8;
		String taste = "all";
		String filter = "newest";
		int degree = 1;
		
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		// 리스트 개수
		ShopMainService shopMainService = new ShopMainService();
		int listCount = shopMainService.getListCount(taste, degree);
//		System.out.println("listCount : " + listCount);

		// 총 리스트 받아옴
		itemList = shopMainService.getItemList(page, limit, taste, filter, degree);
//		System.out.println("page : " + page);
//		System.out.println("limit : " + limit);
//		System.out.println("taste : " + taste);
//		System.out.println("filter : " + filter);
//		System.out.println("degree : " + degree);
//		System.out.println("itemList.size() : " + itemList.size());

		int maxPage = (int)((double)listCount / limit+0.95);
		int startPage = (((int)((double)page / 10 + 0.9)) - 1) * 10 + 1;
		int endPage = startPage + 10 - 1;
		
		if (endPage > maxPage) endPage = maxPage;
		
		PageInfo pageInfo = new PageInfo();
		pageInfo.setEndPage(endPage);
		pageInfo.setListCount(listCount);
		pageInfo.setMaxPage(maxPage);
		pageInfo.setPage(page);
		pageInfo.setStartPage(startPage);
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("itemList", itemList);
		
		forward = new ActionForward();
		forward.setPath("/member/adminPageItemList.jsp");
		
		return forward;
	}

}
