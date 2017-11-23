package c.jacob;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;


public class Main extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.getWriter().print("Squad Bongers!");
	}

}
