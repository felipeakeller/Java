import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
	
	public static void main(String[] args) {
		Connection c = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite://home//felipekeller//Desktop//unisinos//banco de dados//lucas.db");
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    
	    try {
			Statement st = c.createStatement();
			
			StringBuilder sql = new StringBuilder();
			sql.append(" select * from app_infos ");
			ResultSet rs = st.executeQuery(sql.toString());
			
			while ( rs.next() ) {
		         int id = rs.getInt("id");
		         String  name = rs.getString("process_name");
		         String state  = rs.getString("state");
		         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		         Date date = sdf.parse(rs.getString("date"));
		         
		         System.out.print("id = " + id );
		         System.out.print(" name = " + name );
		         System.out.print(" state = " + state );
		         System.out.print(" Data = " + date );
		         System.out.println();
		      }
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
