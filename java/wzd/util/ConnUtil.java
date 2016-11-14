package wzd.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnUtil {
	private Connection conn = null;
	private String url = null;
	private String user = null;
	private String passWord = null;
	
	public Connection getMysqlConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
//			url = "jdbc:mysql://localhost:3306/imt?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
			url = "jdbc:mysql://192.168.15.24:3306/pc?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
			user = "root";
			passWord = "362951";
			conn = DriverManager.getConnection(url, user, passWord);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public Connection getMysqlConnectionByName(String dbName) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
//			url = "jdbc:mysql://localhost:3306/imt?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
			url = "jdbc:mysql://192.168.15.24:3306/"+dbName+"?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
			user = "root";
			passWord = "362951";
			conn = DriverManager.getConnection(url, user, passWord);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public Connection getOracleConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			url = "jdbc:oracle:thin:@192.168.15.55:1521:topprod";
			user = "dsbj";
			passWord = "dsbj";
			conn = DriverManager.getConnection(url, user, passWord);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public Connection getOracleConnectionHk() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			url = "jdbc:oracle:thin:@192.168.15.55:1521:topprod";
			user = "dshk";
			passWord = "dshk";
			conn = DriverManager.getConnection(url, user, passWord);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public Connection getOracleConnectionShgy() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			url = "jdbc:oracle:thin:@192.168.15.55:1521:topprod";
			user = "dssh";
			passWord = "dssh";
			conn = DriverManager.getConnection(url, user, passWord);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public Connection getOracleConnectionHfgy() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			url = "jdbc:oracle:thin:@192.168.15.55:1521:topprod";
			user = "dshf";
			passWord = "dshf";
			conn = DriverManager.getConnection(url, user, passWord);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public Connection getOracleConnection(String uname,String ps) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			url = "jdbc:oracle:thin:@192.168.15.55:1521:topprod";
			user = uname;
			passWord = ps;
			conn = DriverManager.getConnection(url, user, passWord);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	public static void close(ResultSet rst,PreparedStatement pst){
		if (pst != null) {
			try {
				pst.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (rst != null) {
			try {
				rst.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void closePst(PreparedStatement pst){
		if (pst != null) {
			try {
				pst.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void closeConn(Connection conn){
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
