package terhelper.servlets;

import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import terhelper.services.datamart.NamesCheck;
import terhelper.services.storages.Redis;

/**
 * Servlet implementation class IndexServlet
 */
//@WebServlet(name = "IndexServlet", urlPatterns = {"/index"})
public class CheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public CheckServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		NamesCheck dataMart = new NamesCheck();
		response.setContentType("text/html;charset=UTF-8");
		request.setAttribute("data", dataMart.getResult());
		request.setAttribute("dateUpdate", dataMart.getDateUpdate());
		request.getRequestDispatcher("/check.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
