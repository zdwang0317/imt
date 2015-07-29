package wzd.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class TableOfMysqlUtil {
	
	public static int createTableToCpWipHistory(Connection conn,String tableName){
		PreparedStatement pst = null;
		int fd = -1;
		if (conn != null) {
			try {
				pst = conn
						.prepareStatement("CREATE TABLE "+tableName+"("
								+ "Id int(11) NOT NULL AUTO_INCREMENT,"
								+ "pn varchar(50) DEFAULT NULL,"
								+ "cpn varchar(50) DEFAULT NULL,"
								+ "ipn varchar(50) DEFAULT NULL,"
								+ "lid varchar(50) DEFAULT NULL,"
								+ "qty int(11) DEFAULT NULL,"
								+ "wid varchar(255) DEFAULT NULL,"
								+ "startDate date DEFAULT NULL,"
								+ "stage varchar(100) DEFAULT NULL,"
								+ "status varchar(100) DEFAULT NULL,"
								+ "foTime date DEFAULT NULL,"
								+ "remLayer varchar(200) DEFAULT NULL,"
								+ "holdDate date DEFAULT NULL,"
								+ "holdRemark mediumtext,"
								+ "location varchar(100) DEFAULT NULL,"
								+ "sendDate datetime DEFAULT NULL,"
								+ "firm varchar(100) DEFAULT NULL,"
								+ "fileName varchar(100) DEFAULT NULL,"
								+ "erpDate varchar(50) DEFAULT NULL,"
								+ "tpnFlow varchar(100) DEFAULT NULL,"
								+ "productNo varchar(100) DEFAULT NULL,"
								+ "PRIMARY KEY (Id)"
								+ ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;");
				fd = pst.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				ConnUtil.closePst(pst);
			}
		}
		return fd;
	}
	
	public static boolean checkTableExist(Connection conn,String tableName){
		boolean hasTable = false;
		DatabaseMetaData meta;
		try {
			meta = (DatabaseMetaData) conn.getMetaData();
			ResultSet hasRst = meta.getTables (null, null,tableName, null);
			while (hasRst.next()) {
				hasTable =true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return hasTable;
	}
}
