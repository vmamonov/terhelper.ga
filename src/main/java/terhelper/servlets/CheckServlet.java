package terhelper.servlets;

import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import terhelper.AppConfig;
import terhelper.services.datamart.NamesCheck;
import terhelper.services.storages.Redis;

public class CheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CheckServlet() {
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//@TODO
		String envName = request.getParameter("env");
		if (null != envName) {
			String envVal = System.getenv(envName);
			System.out.println("env "+envName+": " + envVal);
			if (null != envVal) {
				System.out.println("length:  "+System.getenv(envName).length());
				System.out.println("getClass().getName():  "+System.getenv(envName).getClass().getName());
			} else {
				System.out.println("envVal is null");
			}
		}
		
		
		NamesCheck dataMart = new NamesCheck();
		response.setContentType("text/html;charset=UTF-8");
		request.setAttribute("data", dataMart.getResult());
		request.setAttribute("dateUpdate", dataMart.getDateUpdate());
		request.setAttribute("env", AppConfig.getInstance().getEnv());
		request.getRequestDispatcher("/check.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
