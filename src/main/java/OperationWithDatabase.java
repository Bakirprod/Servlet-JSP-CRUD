import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
 * Servlet implementation class OperationWithDatabase
 */
@WebServlet("/OperationWithDatabase")
public class OperationWithDatabase extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public OperationWithDatabase() {
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
		
		// Create HttpSessions object
		
		HttpSession session = request.getSession(false);
		
		// check session status, already created or not ? if not create then redirect on Login
		
		if(session == null)
		{
			response.sendRedirect("Login");
		}
		
		try
		{
			Connection con = ConnectDB.connect();

			//   Check operationType value
			
			//   this is student delete function
			
			if(request.getParameter("OperationType").equals("Delete"))
			{
				int Id = Integer.parseInt(request.getParameter("Id"));
			
				String delete_query = "delete from Student where id = '"+ Id +"'";
				
				Statement st = con.createStatement();
				
				int check = st.executeUpdate(delete_query);
				
				if(check == 1)
				{
					pw.print("<script>alert('Student Deleted..')</script>");
					
					RequestDispatcher rq = request.getRequestDispatcher("Profile");
					
					rq.include(request, response);
				}
				else
				{
					pw.print("<script>alert('Student Not Deleted Try Again..')</script>");
				}
					return;
			}
			
			//  if operationType not delete then execute this code
			
			// get parameters from operationForm

			String sname , enrollment , gender , phone , status;
			
			sname = request.getParameter("sname");
			
			enrollment = request.getParameter("enroll");
			
			LocalDate dob = LocalDate.parse(request.getParameter("dob"), DateTimeFormatter.ISO_DATE);

			gender = request.getParameter("gender");

			phone = request.getParameter("phone");

			status = request.getParameter("status");

			// this is add Student function
			
			if(request.getParameter("OperationType").equals("Add"))
			{
				String insert_query = "insert into Student (sname , enrollment , dob , gender , phone , status) values ('"+ sname +"','"+ enrollment +"','"+ dob +"','"+ gender +"','"+ phone +"','"+ status +"')";
				
				Statement st = con.createStatement();
				
				int check = st.executeUpdate(insert_query);
				 
				if(check == 1)
				{
					pw.print("<script>alert('Student Inserted..')</script>");
					
					RequestDispatcher rq = request.getRequestDispatcher("Profile");
					
					rq.include(request, response);
				}
				else
				{
					pw.print("<script>alert('Student Not Inserted Try Again..')</script>");
				}
			return;
			}
				//  this is update student function
			
			if(request.getParameter("OperationType").equals("Update"))
			{
				int Id = Integer.parseInt(request.getParameter("Id"));
				
				String update_query = "update Student set sname ='"+ sname +"', enrollment='"+ enrollment +"', dob='"+ dob +"', gender='"+gender+"', phone='"+ phone +"',status='"+ status +"' where id = '"+Id+"'";
				
				Statement st = con.createStatement();
				
				int check = st.executeUpdate(update_query);
				 
				if(check == 1)
				{
					pw.print("<script>alert('Student Updated..')</script>");
					
					RequestDispatcher rq = request.getRequestDispatcher("Profile");
					
					rq.include(request, response);
				}
				else
				{
					pw.print("<script>alert('Student Not Updated Try Again..')</script>");
				}
			
			}
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
