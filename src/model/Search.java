package model;

import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import server.socket;
import util.mongo.MongoDBConnectMacro;

import com.google.gson.Gson;
import com.mysql.jdbc.Statement;

import dbmodels.Publication;

public class Search {
	private Statement statement;

	public void start(socket soc, Connection connect, Socket client,
			String txt, MongoDBConnectMacro mdm)
			throws IOException, InterruptedException {
		ArrayList<Publication> publist = new ArrayList<Publication>();
		Pattern p = Pattern.compile("search(.*)username(.*)");
		Matcher m = p.matcher(txt);
		String SearchInfo = null;
		String SearchUserName = null;
		while (m.find()) {
			SearchInfo = m.group(1);
			// if(m.group(2).isEmpty()||m.group(2)==null)
			// {SearchUserName="passager_null_name";
			// System.out.println("我到了");}
			SearchUserName = m.group(2);
			System.out.println("用户：" + SearchUserName + "正在搜素："
					+ SearchInfo);
		}

		try {
			statement = (Statement) connect.createStatement();
			String SQL_Query = "select * from recommend where name='"
					+ SearchUserName
					+ "' AND search_record='"
					+ SearchInfo
					+ "'";
			String SQL_Insert = "insert into recommend (search_record,name,count) values(?,?,?)";
			String sql_countchange = "update recommend set count=count+1 where name='"
					+ SearchUserName
					+ "' AND search_record='"
					+ SearchInfo
					+ "'";
			ResultSet rs_query = statement.executeQuery(SQL_Query);
			int count = 0;
			PreparedStatement pstmt;
			pstmt = connect.prepareStatement(SQL_Insert);
			if (!rs_query.first()) // 关键字之前没有创建过
			{
				pstmt.setString(1, SearchInfo);
				pstmt.setString(2, SearchUserName);
				pstmt.setInt(3, 1);// count初始化为1
				pstmt.executeUpdate();
				System.out.println("查询不到搜素的关键字，新创建搜素记录完毕！");
				pstmt.close();
				// connect.close();
			} else // 否则数据库之中存在之前创建过的关键字
			{
				if (rs_query.next())

				{
					count = rs_query.getInt("count");
					System.out.println("获取到count：" + count);
				}
				// pstmt.setString(1, SearchInfo);
				// pstmt.setString(2, SearchUserName);
				// pstmt.setInt(3,++count);
				pstmt.executeUpdate(sql_countchange);
				System.out.println("查询到了，并且count增加！");
				pstmt.close();
				// connect.close();

			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		long startMil = System.currentTimeMillis();
		System.out.println("搜索的关键字为：" + SearchInfo);
		publist = mdm.getPubList("publication_ieee", SearchInfo, "ts");
		// System.out.println(num+publist.toString());
		long endMil = System.currentTimeMillis();
		System.out.println("采集数据耗时：" + (endMil - startMil) + "毫秒");
		Gson gson = new Gson();
		/* te是需要序列化的对象 */
		String s1 = gson.toJson(publist); // gson 将publist对象转化为字符串
		// char[] c=new char[1024];

		try {
			/*
			 * int a=s.length()/1024;//将s分成a段 int b=s.length()%1024;
			 * int a1=a; while(a1>0) { s.getChars(a1*1024-1024+1,
			 * a1*1024, c, 0); String s1 = new String(c);
			 * soc.SendMsg(client, s1); a1--; } if(b!=0) {
			 * s.getChars(a*1024+1, s.length(), c, 0); String s1 =
			 * new String(c); soc.SendMsg(client, s1); }
			 */
			// soc.SendMsg(client, String.valueOf(s.length()));
			soc.SendMsg(client, s1);
			System.out.println("已成功发送" + s1.length() + "个数据");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// try {
		// soc.CloseSocket(client);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}
}
