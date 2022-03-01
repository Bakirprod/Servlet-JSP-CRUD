import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


/**
 * Servlet implementation class Check_User
 */
@WebServlet("/Check_User")
public class Check_User extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Check_User() {
    	super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("text/html");

		PrintWriter pw = response.getWriter();
		
		try
		{
			// Get parameter from Login
			
			String uname = request.getParameter("uname");
			
			String pass = request.getParameter("pass");
			
			// Connection from connectDB
			
			Connection con = ConnectDB.connect();

			// GET Username and Password From users table
			
			String q = "select * from users where username = (?) and password = (?)";
			
			PreparedStatement pst = con.prepareStatement(q);
			
			// Set Parameter
			
			pst.setString(1, uname);
			
			pst.setString(2, pass);
			
			//  Result Set Object
			
			ResultSet rs = pst.executeQuery();
			
			// Create variable for username and password
			
			String U , P;
			
			// Fetch Row
			
			if(rs.next())
			{
				U = rs.getString(1);
				
				P = rs.getString(2);
				
				// Check username and password
				
				if(uname.equalsIgnoreCase(U)  &&  pass.equalsIgnoreCase(P))
				{
					
					// Create Httpsession object
					
					HttpSession session = request.getSession();
					
					session.setAttribute("Username", U);
					
					// Create RequestDispatcher object
					
					RequestDispatcher rd = request.getRequestDispatcher("Profile");
					
					rd.forward(request, response);  //forward()  method use
 				}
			}
			else
			{
				// Otherwise display message and redirect on Login
				
				pw.print("<center><h3 style ='color:red;'>Sorry , Username And Password Does Not Match</h3></center>");
				
				RequestDispatcher rd = request.getRequestDispatcher("Login");
				
				rd.include(request, response);  //include()  method
			}
			
			con.close();
		}
		catch(Exception ex)
		{
			pw.print(ex);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
