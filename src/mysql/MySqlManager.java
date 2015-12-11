package mysql;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.mysql.jdbc.DatabaseMetaData;

public class MySqlManager {
	Connection connect = null;
	DatabaseMetaData dMetaData;
	@SuppressWarnings("rawtypes")
	Map mapcolumn;
	Statement stat;

	private String MYSQL_CONN = "jdbc:mysql://localhost:3306";
	private String MYSQL_USERNAME = "root";
	private String MYSQL_PASSWORD = "root";
	private String MYSQL_DBNAME = "peso";
	private String MYSQL_PESO_SYSCONFIG_SETTINGNAME = "Version";
	private String MYSQL_PESO_SYSCONFIG_VALUE = "2";

	public MySqlManager() {
	}

	public MySqlManager(Connection connect) throws SQLException {
		this.connect = connect;
		this.stat = this.connect.createStatement();
	}

	public void JDBC() {
		/************************* 加载MYSQL JDBC驱动程序 ********************************/
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("系统成功加载Mysql数据库！");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.print("系统连接数据库失败!");
			e.printStackTrace();
		}
		/************************* 加载MYSQL JDBC驱动程序 ********************************/
	}

	public void mysqlLoadConfig() throws IOException {
		String fileName = "myConfig.properties";
		Properties properties = new Properties();
		FileInputStream fileInpustStream = null;
		fileInpustStream = new FileInputStream(fileName);
		properties.load(fileInpustStream);
		fileInpustStream.close();
		if (properties.containsKey("mysql.conn")) {
			MYSQL_CONN = properties.getProperty("mysql.conn");
		}
		if (properties.containsKey("mysql.username")) {
			MYSQL_USERNAME = properties
					.getProperty("mysql.username");
		}
		if (properties.containsKey("mysql.password")) {
			MYSQL_PASSWORD = properties
					.getProperty("mysql.password");
		}
		if (properties.containsKey("mysql.dbname")) {
			MYSQL_DBNAME = properties.getProperty("mysql.dbname");
		}
		if (properties.containsKey("mysql.peso.sysconfig.settingname")) {
			MYSQL_PESO_SYSCONFIG_SETTINGNAME = properties
					.getProperty("mysql.peso.sysconfig.settingname");
		}
		if (properties.containsKey("mysql.peso.sysconfig.value")) {
			MYSQL_PESO_SYSCONFIG_VALUE = properties
					.getProperty("mysql.peso.sysconfig.value");
		}
	}

	/**
	 * 创建连接并且检查数据库表名和字段名
	 * 
	 * @throws SQLException
	 * @throws IOException
	 */
	public Connection ConnectDB() throws SQLException, IOException {
		mysqlLoadConfig();
		connect = DriverManager.getConnection(MYSQL_CONN,
				MYSQL_USERNAME, MYSQL_PASSWORD);
		stat = connect.createStatement();
		stat.executeUpdate("create database if not exists peso3");
		connect = DriverManager.getConnection(MYSQL_CONN + "/"
				+ MYSQL_DBNAME, MYSQL_USERNAME, MYSQL_PASSWORD);
		System.out.println("成功连接到Mysql数据库！");
		setTableName();
		insertVersion();
		System.out.println("本地MySql数据库初始化完毕！");
		return connect;
	}

	/**
	 * 数据库字段名在这里声明
	 * 
	 * @throws SQLException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setTableName() throws SQLException {
		mapcolumn = new HashMap();

		/* user表检查与创建 */
		mapcolumn.clear();
		mapcolumn.put("name", "varchar(255)");
		mapcolumn.put("password", "varchar(255)");
		mapcolumn.put("email", "varchar(255)");
		CheckTable("user", mapcolumn);// 第一个变量是表名

		/* recommend表检查与创建 */
		mapcolumn.clear();
		mapcolumn.put("search_record", "INTEGER(255)");
		mapcolumn.put("name", "varchar(255)");
		mapcolumn.put("count", "INTEGER(255)");
		CheckTable("recommend", mapcolumn);

		/* updateCheck表检查与创建 */
		mapcolumn.clear();
		mapcolumn.put("settingname", "varchar(255)");
		mapcolumn.put("value", "varchar(255)");
		CheckTable("sysconfig", mapcolumn);
	}

	/**
	 * 检查完自动就创建表和字段
	 * 
	 * @param tabName
	 *                表名
	 * @param colName
	 *                字段名
	 * @param Type
	 *                字段类型
	 */
	public void CheckTable(String tabName, String colName, String Type)
			throws SQLException {

		ResultSet rs = connect.getMetaData().getColumns(null, null,
				tabName, colName);
		if (rs.first()) {
			// System.out.println("数据库中表名="+tabName+"\t\t存在");
		} else {
			System.out.println("\t数据库中表名=" + tabName + "中的"
					+ colName + "\t不存在！！！");
			CreatTable(tabName, colName, Type);
		}
	}

	/**
	 * 检查完就创建表和字段
	 * 
	 * @param tabName表名
	 * @param mapCol字段名和类型的集合
	 * @throws SQLException
	 */
	public void CheckTable(String tabName, Map<String, String> mapCol)
			throws SQLException {
		stat = connect.createStatement();
		stat.executeUpdate("create table if not exists " + tabName
				+ "(id int)");
		Iterator<Map.Entry<String, String>> it = mapCol.entrySet()
				.iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			ResultSet rs = connect.getMetaData().getColumns(null,
					null, tabName, entry.getKey());
			if (rs.first()) {
				// System.out.println("数据库中表名="+tabName+"\t\t存在");
			} else {
				System.out.println("\t数据库中表名=" + tabName
						+ "中的\t" + entry.getKey()
						+ "\t不存在！！！");
				CreatTable(tabName, entry.getKey(),
						entry.getValue());
			}
		}
	}

	private void CreatTable(String tabName, String key, String value)
			throws SQLException {
		// TODO Auto-generated method stub
		// stat.executeUpdate("create table %s(%s %s))",tabName,key,value);
		// stat.executeUpdate(MessageFormat.format("create table {0}({1} {2}))",tabName,key,value));
		stat.executeUpdate("ALTER table " + tabName + " ADD " + key
				+ " " + value);
	}

	public String getVersion(String VERSION) throws SQLException {
		String version = "0";
		String sql = "select value from sysconfig where settingname='"
				+ VERSION + "'";
		ResultSet rs = stat.executeQuery(sql);
		System.out.println("数据库到了");
		// System.out.println("txttxt"+txt);
		if (!rs.first()) {
			System.out.println("用户查询版本出错！");
		} else {
			version = rs.getString("value");
			System.out.println("获取到了版本号码为" + version);
		}
		return version;
	}

	public void insertVersion() throws SQLException {
		String insertVersionInfo = "insert into sysconfig (settingname,value) values(?,?)";
		PreparedStatement pst = connect
				.prepareStatement(insertVersionInfo);
		pst.setString(1, MYSQL_PESO_SYSCONFIG_SETTINGNAME);
		pst.setString(2, MYSQL_PESO_SYSCONFIG_VALUE);
		pst.executeUpdate();// 插入了一条记录(加入version 版本号为2 的记录)
		System.out.println("当前版本号为：" + MYSQL_PESO_SYSCONFIG_SETTINGNAME
				+ "=" + MYSQL_PESO_SYSCONFIG_VALUE);
	}
}
