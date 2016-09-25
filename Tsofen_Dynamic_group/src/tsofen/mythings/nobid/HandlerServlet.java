package tsofen.mythings.nobid;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		MyConnection con = null;
		try {
			con = new MyConnection();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		PrintWriter out;
		
		try {
			 
			out = response.getWriter();
			String func = request.getParameter("func");
			
			if(func.equals("getEx"))
				out.print(NoBidMethods.getExchange(con.getCon()));
			if(func.equals("getNoBid")){
				String Exchangeid= request.getParameter("Exchangeid");
			
				out.print(NoBidMethods.getNoBidReason(con.getCon(), Exchangeid));
			}
			else if(func.equals("getAd")){
				String Exchangeid= request.getParameter("exchID");
				String nobid=request.getParameter("noBidID");
			}

			else if(func.equals("getGraph")){
				String Exchangeid= request.getParameter("exchID");
				String nobid=request.getParameter("noBidID");
				
				out.print(NoBidMethods.getExample(con.getCon(), Exchangeid, nobid));
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
