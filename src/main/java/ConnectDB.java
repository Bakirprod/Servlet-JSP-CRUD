import java.sql.DriverManager;
import java.sql.*;

public class ConnectDB {

	public static Connection connect ()
	{
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stud","root","root"); // pls entre database name,username and password  of mysql
			return con;
		}
		catch (Exception ex)
		{
			
		}
		return null;
	}
	
	public static void main(String[] args) {
	
		
	}
		
			
}
