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
 * Servlet implementation class OperationForm
 */
@WebServlet("/OperationForm")
public class OperationForm extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public OperationForm() {
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
			
			pw.print("<html>"
					
					+ "<head><title> Operation Form</title>"
					
					+ "<style>"
					
						+ "td{padding:10px 10px}"
						+ "body{font-family:arial;}"
						+ "table{border:1px solid black;padding:20px;}"
						+ "a{text-decotation:none;border:1px solid black;padding:10px 10px;}"
						+ "a:hover{color:red;}"
						+ ".btn{padding:10px 20px;}"
						
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

				// view and Add student Link
				
				+ "<div style='clear:both;'></div><div style='float:right;'><a href='Profile'>View Student</a><a href='OperationForm?Id=Add' style='margine-left:10px;'>Add Student</a>"
				
				// Logout Link
				
				+ "<a href='Logout' style='margine-left:10px;'>Log Out</a></div>"
				
				+ "<br><br><br>"
				
				// Search Student
				
				+ "<div style='float:left;'><form action='SearchResult'><input type='text' name ='Fetch' placeholder='Search Student' required><input type='submit' value ='Search' style='margine-left:10px;'></form><br></div>"

				// Generate PDF
				
				+ "<div style='float:right;'><form action='GeneratePDF'><select name='status'><option>Active</option><option>Deactive</option></select><input type='submit' value ='Genarate Raport' style='margine-left:10px;'></form></div>");

			
			String Id = request.getParameter("Id");

			String sname , enrollment , gender , phone;
			
			ResultSet rs4 = null;
			
			// Add Student Code
			
			if(Id.equals("Add"))
			{
				pw.print("<h1>Add New Student</h1>");
			
				pw.print("<form action='OperationWithDatabase' method='post'>"   // Redirect operation with database
								+ "<table>"
								+ "			<tr>"
								+ "					<td>Student Name</td>"
								+ "					<td><input type='text' name='sname' placeholder='Student Name'></td>"
								+ "			</tr>"
								
								+ "			<tr>"
								+ "					<td>Enrellment</td>"
								+ "					<td><input type='text' name='enroll' placeholder='Enrollment'></td>"
								+ "			</tr>"
								
								+ "			<tr>"
								+ "					<td>Date Of Birth</td>"
								+ "					<td><input type='data' name='dob'></td>"
								+ "			</tr>"
								
								+ "			<tr>"
								+ "					<td>Gender</td>"
								+ "					<td><input type='radio' name='gender' value='Male'> Male"
								+ "					    <input type='radio' name='gender' value='Female'>Female</td>"
								+ "			</tr>"
								
								+ "			<tr>"
								+ "					<td>Phone</td>"
								+ "					<td><input type='text' name='phone' placeholder='Phone No'></td>"
								+ "			</tr>"
								
								+ "			<tr>"
								+ "					<td>Status</td>"
								+ "					<td><select name='status'><option>Active</option><option>Deactive</option></select></td>"
								+ "			</tr>"
								
								+ "			<tr>"
								+ "					<td><input type='hidden' value='Add' name='OperationType'>"  // hidden field for send operation type (add student)
								+ "					<td><input type='submit' value='Add' class='btn'></td>"
								+ "			</tr>"
								
								+ "         <tr></table></form>" );
			}
					// Update student code
			else	
			{
				pw.print("<h1>Update Student Id = "+ Id +"</h1>");
				
				//  Write select Query
				
				String q = "select * from Student where id = "+ Integer.parseInt(Id) +"";
				
				PreparedStatement pst1 = con.prepareStatement(q);
				
				rs4 = pst1.executeQuery();
				
				rs4.next();
				
				// set controls value according student id
				
				pw.print("<form action='OperationWithDatabase' method='post'>"
						+ "<table>"
						+ "			<tr>"
						+ "					<td>Student Name</td>"
						+ "					<td><input type='text' name='sname' placeholder='Student Name' value='"+rs4.getString(2)+"'></td>"
						+ "			</tr>"
						
						+ "			<tr>"
						+ "					<td>Enrellment</td>"
						+ "					<td><input type='text' name='enroll' placeholder='Enrollment' value='"+rs4.getString(3)+"'></td>"
						+ "			</tr>"
						
						+ "			<tr>"
						+ "					<td>Date Of Birth</td>"
						+ "					<td><input type='date' name='dob' value='"+rs4.getString(4)+"'></td>"
						+ "			</tr>"
						
						+ "			<tr>"
						+ "					<td>Gender</td>");
						
						if(rs4.getString(5).equals("Male"))
						{
							pw.print("<td><input type='radio' name='gender' value='Male' checked> Male"
								+ "      <input type='radio' name='gender' value='Female'>Female");
						}
						else
						{
							pw.print("<td><input type='radio' name='gender' value='Male' > Male"
									+ "      <input type='radio' name='gender' value='Female' checked>Female");	
						}
						
						pw.print("</tr>"
						
						+ "			<tr>"
						+ "					<td>Phone</td>"
						+ "					<td><input type='text' name='phone' placeholder='Phone No' value='" + rs4.getString(6) +"'></td>"
						+ "			</tr>");
						
						if(rs4.getString(7).equals("Active"))
						{
							pw.print("   </tr>"
									+ "         <td>Status</td>"
									+ "			<td><select name='status'><option selected>Active</option><option>Deactive</option></select></td>"
									+ "			</tr>");
						}
						else
						{
							pw.print("   </tr>"
									+ "         <td>Status</td>"
									+ "			<td><select name='status'><option>Active</option><option selected>Deactive</option></select></td>"
									+ "			</tr>");
						}
						
						pw.print(	  "   </tr>"
									+ "					<input type='hidden' value='Update' name='OperationType'>"                 // hidden field for send operation type (update student)
									+ "					<input type='hidden' value='"+rs4.getString("Id")+"' name='Id'>"        // pass student id
									+ "					<td><input type='submit' value='Update' class='btn'></td>"              // update button
									+ "			</tr>"															  
						
						+ "         </table></form>" );       }

			pw.print("</center></body></html>");
			
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
