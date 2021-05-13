package terhelper.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import terhelper.models.Territory;
import terhelper.services.TerritoryHelperApi;
import terhelper.services.repo.TerritoriesRepo;


public class LocationsGetterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LocationsGetterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
            TerritoryHelperApi api = new TerritoryHelperApi.Builder("5dc2c712c9c5472c859392b103064814", "6722084eb68640b4b9a6d8ab540e6f6f").build();
            Territory[] territories = TerritoriesRepo.getInstance().setApi(api).getAllTerritories();
            for (Territory t : territories) {
                System.out.println(t.Address);
            }
        } catch (Exception ex) {
            System.out.println("Ошибка при работе с API TerritoryHelper: " + ex.getMessage());
            ex.printStackTrace();
        }
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
