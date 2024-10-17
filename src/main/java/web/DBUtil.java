package web;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBUtil {
	protected static BasicDataSource dataSource;
	protected static final Logger logger = LoggerFactory.getLogger(DBUtil.class);
	
	static {
		dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:mysql://localhost:3306/ruoyi?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
		dataSource.setUsername("root");
		dataSource.setPassword("123456");
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		
		// Pool Configuration
        dataSource.setInitialSize(5);
        dataSource.setMaxTotal(10);
        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(10);
        dataSource.setMaxWaitMillis(10000);
	}

	public static Connection getConnection() throws SQLException {

		return dataSource.getConnection();
	}

}
