package model;

import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import server.socket;
import util.mongo.MongoDBConnectMacro;

import com.google.gson.Gson;
import com.mysql.jdbc.Statement;

import dbmodels.Publication;

public class Recommendation {

	private String username = null;
	private int Position = 1;
	private List<String> reclist;

	public void start(socket soc, Connection connect, Socket client,
			String txt, MongoDBConnectMacro mdm)
			throws IOException, InterruptedException {
		ArrayList<Publication> publist = new ArrayList<Publication>();
		Pattern p = Pattern.compile("recomendations(.*)position(.*)");
		Matcher m = p.matcher(txt);

		while (m.find()) {
			System.out.println("m.find到了");
			username = m.group(1).replace("[", "").replace("]", "")
					.replace(" ", "");
			Position = Integer.parseInt(m.group(2).replace("[", "")
					.replace("]", "").replace(" ", ""));
			System.out.println("推荐获取到的用户名为：" + username);
			// userinfo=m.group(1).replace(" ","");
		}
		Random rd = new Random();
		Position = rd.nextInt(40);
		System.out.println("随机位置：" + Position);

		try {
			long startMil = System.currentTimeMillis();
			Statement statement = (Statement) connect
					.createStatement();
			// Sql_count="select search_record from recommend where name='"+username+"'";
			String Sql_count = "select search_record from recommend where name='"
					+ username
					+ "' ORDER BY RAND() LIMIT 2";
			ResultSet rs = statement.executeQuery(Sql_count);
			if (!rs.first()) {
				// 如果查询结果不存在，第一条不存在(一般都能查找到)
				System.out.println("推荐表没有找到相关用户的推荐记录！");
				// soc.SendMsg(client, "0");
				Listupdate ld = new Listupdate();
				ld.start(soc, connect, client, txt, mdm);// 如果没有登录或者该用户没有过搜索记录，开启更新列表
			} else {
				reclist = new ArrayList<String>();
				reclist.add(rs.getString("search_record"));// 将第一条数据加到队列
				while (rs.next()) {
					reclist.add(rs.getString("search_record"));
				}// 将第一条以后的查询记录 存到动态数组里面

				System.out.println("即将查询推荐内容");

				for (int loop = 0; loop < reclist.size(); loop++) {
					publist.addAll(mdm
							.getPubList_reccommend(
									"publication_ieee",
									reclist.get(loop),
									"ts",
									Position));
					System.out.println("从数据库中采集到关键字"
							+ reclist.get(loop));
					// System.out.println(num+publist.toString());
					// publist=mdm.getPubList_reccommend("publication_ieee",
					// reclist.get(0), "ts", Position);
				}
				System.out.println("将采集的数据准备好了");
				Gson gson = new Gson();
				/* te是需要序列化的对象 */
				String s1 = gson.toJson(publist); // gson
									// 将publist对象转化为字符串
				// char[] c=new char[1024];
				long endMil = System.currentTimeMillis();
				System.out.println("采集数据耗时："
						+ (endMil - startMil) + "毫秒");
				soc.SendMsg(client, s1);
				System.out.println("推荐内容已成功发送" + s1.length()
						+ "个数据");
				soc.CloseSocket(client);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
