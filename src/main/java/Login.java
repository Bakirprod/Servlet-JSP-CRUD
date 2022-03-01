import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Login() {
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
		
		response.setContentType("text/html");
		
		PrintWriter pw= response.getWriter();
		
		// Include Logout
		
		request.getRequestDispatcher("Logout").include(request, response);
		
		// Login form Design 
		
		
		pw.print( "<html>"
				
				+ "<head><title> Login </title>"
				
				+ "<style>"
				
					+ "body{font-family:arial;}"
					
				+ "</style>"
				
				+ "</head>"
				
				+ "<body>"
				
				+ "<center>"
				
				+ "<h1>Login</h1>"	
				
				+ " <form action='Check_User' method='post'>"
				
				+ "		<input type='text' name='uname' placeholder='Username' required>" //Entre Username
				
				+ "		<input type='password' name='pass' placeholder='Password' required>" //Entre Username	

				+ "		<input type='submit' value='Go'>"
				
				+ " </form>"
				
				+ "</center>"
				
				+ "</body></html>");
		
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
