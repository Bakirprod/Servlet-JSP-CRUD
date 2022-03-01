import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

                                       
/**
 * Servlet implementation class SearchResult
 */
@WebServlet("/SearchResult")
public class SearchResult extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public SearchResult() {
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
		
		// Create HttpSessions object
		
		HttpSession session = request.getSession(false);
		
		// check session status, already created or not ? if not create then redirect on Login
		
		if(session == null)
		{
			response.sendRedirect("Login");
		}
		
		Connection con = ConnectDB.connect();

		try
		{
			// write select Query
			
			String fetch_students = "select * from Student";

			String count_student = "select count(*) from Student";
			
			String active_student = "select count(*) from Student where status = 'Active'";
			
			PreparedStatement pst = con.prepareStatement(fetch_students);
			
			ResultSet rs = pst.executeQuery();
			
			PreparedStatement pst2 = con.prepareStatement(count_student);
			
			ResultSet rs2 = pst2.executeQuery(count_student);
			
			rs2.next();
			
			PreparedStatement pst3 = con.prepareStatement(active_student);
			
			ResultSet rs3 = pst3.executeQuery(active_student);
			
			rs3.next();
			
			String q;
			
			if(request.getParameter("Fetch").equals("Active"))
			{
				q = "select * from Student where status ='Active'";
			}
			else if(request.getParameter("Fetch").equals("Deactive"))
			{
				q = "select * from Student where status ='Deactive'";
			}
			else
			{
				q = "select * from Student where sname like '%"+ request.getParameter("Fetch") +"%'";
			}
			
			PreparedStatement pst1 = con.prepareStatement(q);
			
			ResultSet rs4 = pst1.executeQuery();
					
			pw.print("<html>"
					
					+ "<head><title> Student Result</title>"
					
					+ "<style>"
					
						+ "td,th{padding:14px 30px}"
						+ "body{font-family:arial;}"
						+ "table{border:1px solid black;padding:20px;}"
						+ "a{text-decoration:none;border:1px solid black;padding:10px 10px;}"
						+ "a:hover{color:red;}"
						
				+ "</style>"
				
				+ "</head>"
				
				+ "<body>");
			
		pw.print("<center>"
				
				+ "<h2>Hii ADMIN</h2>"
				
				+ "<br>"	

				// total student
				
				+ "<div style='float:left;color:orange;border:1px solid black;padding:5px 5px;'>Total Student <h3>"+ rs2.getInt(1) +"</h3></div>"
				
				// total active student
				
				+ "<div style='float:left;margine-left:10px;color:blue;border:1px solid black;padding:5px 5px;'>Total Active Student <h3><a href='SearchResult?Fetch=Active' style='border:none;'>"+ rs3.getInt(1) +"</a></h3></div>"

				// total Deactive student
				
				+ "<div style='float:left;margine-left:10px;color:green;border:1px solid black;padding:5px 5px;'>Total Deactive Student <h3><a href='SearchResult?Fetch=Deactive' style='border:none;'>"+ (rs2.getInt(1)-rs3.getInt(1)) +"</a></h3></div>"

				// Add student Link
				
				+ "<div style='clear:both;'></div><div style='float:right;'><a href='Profile'>View Student</a><a href='OperationForm?Id=Add' style='margine-left:10px;'>Add Student</a>"
				
				// Logout Link
				
				+ "<a href='Logout' style='margine-left:10px;'>Log Out</a></div>"
				
				+ "<br><br><br>"
				
				// Search Student
				
				+ "<div style='float:left;'><form action='SearchResult'><input type='text' name ='Fetch' placeholder='Search Student' required><input type='submit' value ='Search' style='margine-left:10px;'></form><br></div>"

				// Generate PDF
				
				+ "<div style='float:right;'><form action='GeneratePDF'><select name='status'><option>Active</option><option>Deactive</option></select><input type='submit' value ='Genarate Raport' style='margine-left:10px;'></form></div>"

				+ "<div style='clear:both;'><h2>Student Details</h2>"
				
				+ "<br><br>"
				
				+ "<h2>Result</h2><br><br>");
			
				pw.print("<table style ='margine-top:-50px;'><tr><th>Id</th>"
						+ "<th>Student</th>"
						+ "<th>Enrollment</th>"
						+ "<th>Date of Birth</th>"
						+ "<th>Gender</th>"
						+ "<th>Phone</th>"
						+ "<th>Update</th>"
						+ "<th>Delete</th></tr>");
				
				while( rs4.next())
				{
				 // Fetch record
					
					pw.print("<tr><td>"+ rs4.getInt(1) + "</td>"
							+ "<td>"+ rs4.getString(2)+ "</td>"
							+ "<td>"+ rs4.getString(3)+ "</td>"
							+ "<td>"+ rs4.getString(4)+ "</td>"
							+ "<td>"+ rs4.getString(5)+ "</td>"
							+ "<td>"+ rs4.getString(6)+ "</td>"
							+ "<td><a href='OperatioForm?Id=" + rs4.getInt(1) +"'>Update</a></td>"                                  // Update link
							+ "<td><a href='OperatioWithDatabase?OperationType=Delete & Id=" + rs4.getInt(1) +"'>Delete</a></td>"      // Delete link
							+ "</tr><br><br>");
				}
				
			pw.print("</table></body></html>");
			
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
