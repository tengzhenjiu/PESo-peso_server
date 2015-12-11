package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.DatabaseMetaData;
import com.mysql.jdbc.Statement;

public class FavsTableManager {
	private Connection connect = null;
	private String username = "";
	private String paperid = "";

	public FavsTableManager(Connection connect, String username,
			String paperid) {
		this.connect = connect;
		this.username = username;
		this.paperid = paperid;
	}

	private String ctFavsTableSql = "CREATE TABLE favs"
			+ "(name VARCHAR(255) , " + "favsid VARCHAR(255))";

	public boolean isNeedCreateTable() throws SQLException {
		// TODO Auto-generated method stub
		boolean bl = false;
		DatabaseMetaData dMetaData = (DatabaseMetaData) connect
				.getMetaData();
		ResultSet rS = dMetaData.getTables(null, null, "favs", null);
		if (!rS.first()) {
			bl = true;
			System.out.println("我到了");
		}
		return bl;
	}

	public void createTable() {
		try {
			Statement stmt = (Statement) connect.createStatement();
			stmt.executeUpdate(ctFavsTableSql);
			insertValues();// 创建完成紧接着插入数据
			System.out.println("创建favs表成功");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 向favs表中插入一条用户添加新收藏记录
	public void insertValues() {
		String insertString = "insert into favs (name,favsid) values(?,?)";
		try {
			PreparedStatement psmt = connect
					.prepareStatement(insertString); // 预编译sql语句
			psmt.setString(1, username);
			psmt.setString(2, paperid);
			psmt.executeUpdate();
			System.out.println("用户" + username + "成功的向收藏中添加了一篇文章！"
					+ paperid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 查询是否需要添加收藏记录
	public boolean isNeedInsertValues() {
		boolean bl = false;

		String queryis = "SELECT * FROM favs WHERE name='" + username
				+ "' AND favsid='" + paperid + "'";
		try {
			Statement stquery = (Statement) connect
					.createStatement();
			ResultSet rSet = stquery.executeQuery(queryis);
			if (!rSet.first()) {
				bl = true;
				System.out.println("可以添加收藏");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bl; // 返回true 表明已经存在记录不需要进行添加 false没有查询到需要添加
	}

	// 查询favs表中的id
	public List<String> getIdsfromTable() {
		List<String> idList = new ArrayList<String>();
		String queryId = "SELECT favsid FROM favs WHERE name='"
				+ username + "'";
		try {
			Statement st = (Statement) connect.createStatement();
			ResultSet rSet = st.executeQuery(queryId);
			if (!rSet.first()) {
				System.out.println("没有查询到记录");
				idList.clear();// 把数据清空
			} else {
				idList.add(rSet.getString("favsid"));
				while (rSet.next()) {
					idList.add(rSet.getString("favsid"));
					// System.out.println("-----"+rSet.getString("favsid"));
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return idList;
	}

}
