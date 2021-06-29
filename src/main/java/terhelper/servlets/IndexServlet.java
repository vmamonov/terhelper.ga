package terhelper.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public IndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String envName = request.getParameter("env");
		System.out.println("env "+envName+":" + System.getenv(envName));
		request.getRequestDispatcher("/check.jsp").forward(request, response);
		//request.getRequestDispatcher("/check").forward(request, response);
		//request.getRequestDispatcher("/check").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
