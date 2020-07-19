package sec04.ex02;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ServletContext context = null;
	List user_list = new ArrayList();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		context = getServletContext();
		PrintWriter out = response.getWriter();
		String user_id = request.getParameter("user_id");
		String user_pw = request.getParameter("user_pw");
		//LoginImpl loginUser = new LoginImpl(user_id, user_pw);
		Integer cnt = 0;
		
		HttpSession session = request.getSession();

		if(session.getAttribute("loginUser") == null) { //세션이 없는경우
			if(context.getAttribute(user_id)==null) { //context에 없는경우
				session.setAttribute("loginUser", user_id);
				user_list.add(user_id);
				context.setAttribute("user_list", user_list);
				context.setAttribute(user_id, ++cnt);
				
				out.println("<html><body>");
				out.println("아이디는 " + session.getAttribute("loginUser") + "<br />");
				out.println("총 접속자수는 " + ((ArrayList)context.getAttribute("user_list")).size() + "<br /><br />");
				out.println("접속 아이디 : <br />");
				List list = (ArrayList)context.getAttribute("user_list");
				for(int i=0; i<list.size();i++) {
					out.println(list.get(i) + "<br />");
				}
				out.println("<a href='logout?user_id=" + user_id + "'>로그아웃</a>");
				out.println("</body></html>");
			} else if((Integer)context.getAttribute(user_id) < 2) {
				session.setAttribute("loginUser", user_id);
				cnt = (Integer)context.getAttribute(user_id);
				context.removeAttribute(user_id);
				context.setAttribute(user_id, ++cnt);
				
				out.println("<html><body>");
				out.println("아이디는 " + session.getAttribute("loginUser") + "<br />");
				out.println("총 접속자수는 " + ((ArrayList)context.getAttribute("user_list")).size() + "<br /><br />");
				out.println("접속 아이디 : <br />");
				List list = (ArrayList)context.getAttribute("user_list");
				for(int i=0; i<list.size();i++) {
					out.println(list.get(i) + "<br />");
				}
				out.println("<a href='logout?user_id=" + user_id + "'>로그아웃</a>");
				out.println("</body></html>");
			} else { //context에 존재하는경우 => 다중접속
				out.println("<html><body>");
				out.println("3개 이상 접속할 수 없습니다.");
				out.println("</html></body>");
			}
		} else { //세션이 있는경우?
			out.println("<html><body>");
			out.println("아이디는 " + session.getAttribute("loginUser") + "<br />");
			out.println("총 접속자수는 " + ((ArrayList)context.getAttribute("user_list")).size() + "<br /><br />");
			out.println("접속 아이디 : <br />");
			List list = (ArrayList)context.getAttribute("user_list");
			for(int i=0; i<list.size();i++) {
				out.println(list.get(i) + "<br />");
			}
			out.println("<a href='logout?user_id=" + user_id + "'>로그아웃</a>");
			out.println("</body></html>");
		}
	}

}
