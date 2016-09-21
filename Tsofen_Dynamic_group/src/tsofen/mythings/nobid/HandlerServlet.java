package mythings.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mythings.classes.DBManager;
import tsofen.course.db.MyConnection;

/**
 * Servlet implementation class HandlerServlet
 */
@WebServlet("/getdata")
public class HandlerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public HandlerServlet() { super(); }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 MyConnection con;
		PrintWriter out;
		
		try {
			
			out = response.getWriter();
			NoBidMethods nobid=NoBidMethods.getInstance();
			
			String func = request.getParameter("func");
			
			if(func.equals("getEx"))
				out.print(nobid.getExchange(con.getCon()));
//			else if(func.equals("getAdv"))
//				out.print(um.getAdv());
//			else if(func.equals("getAdvForEx"))
//				out.print(um.getAdvForEx(Integer.parseInt(request.getParameter("exID"))));
//			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
