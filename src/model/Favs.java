package model;

import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mysql.FavsTableManager;
import server.socket;
import util.mongo.MongoDBConnectMacro;

import com.google.gson.Gson;

import dbmodels.Publication;

/*收藏类进行收藏相关事务*/
public class Favs {
	private boolean NeedCreateTable = false;
	private boolean NeedInsertValues = false;
	private String Msg = "";
	private Connection connect = null;
	private String txt;
	Socket client;
	socket soc;
	private MongoDBConnectMacro mdm;
	private String userName = "";
	private String paperId = "";
	private FavsTableManager ftm = null;

	public Favs(socket soc, Connection connect, Socket client, String txt,
			MongoDBConnectMacro mdm) {
		this.connect = connect;
		this.soc = soc;
		this.client = client;
		this.txt = txt;
		this.mdm = mdm;
	}

	public void start() throws SQLException, IOException {
		if (txt.matches("FAVS(.*?)")) // if里边是执行存放id到数据库的逻辑
		{
			Pattern pattern = Pattern
					.compile("FAVSID(.*)USERNAME(.*)");//
			Pattern pattern1 = Pattern.compile("FAVSUSERNAME(.*)");
			Matcher matcher = pattern.matcher(txt);
			Matcher matcher1 = pattern1.matcher(txt);
			if (matcher.find()) {
				paperId = matcher.group(1);
				userName = matcher.group(2);
				System.out.println(paperId + userName);
				recordin(); // 写入数据库
				System.out.println("返回到收藏信息：" + Msg);
				soc.SendMsg(client, Msg);// 向客户端发送收藏成功和失败消息
			}
			if (matcher1.find()) {
				userName = matcher1.group(1);
				System.out.println("即将获取id，我到了用户名为：" + userName);
				Gson gson = new Gson();
				soc.SendMsg(client,
						gson.toJson(getPapersById()));
				System.out.println("收藏信息发送成功！");
			}
		}
	}

	// 此方法是添加收藏时候执行
	public void recordin() throws SQLException {
		ftm = new FavsTableManager(connect, userName, paperId);
		NeedCreateTable = ftm.isNeedCreateTable();
		NeedInsertValues = ftm.isNeedInsertValues();
		System.out.println("我到了判断创建表这");
		if (NeedCreateTable) { // 本地没有favs表，需要创建表
			ftm.createTable();// 创建favs表 并且向表中插入数据
			// ftm.insertValues();// 插入表
			Msg = "1";
		} else // 本地已经存在favs表 不创建
		{
			if (NeedInsertValues) // 表中是否有之前收藏的记录
			{
				ftm.insertValues(); // 如果不存在这样的记录就插入到表中
				Msg = "1";// 发送1表示加入收藏成功
			} else {
				Msg = "0"; // 发送0表示已经加入收藏
			}
		}

	}

	public List<Publication> getPapersById() {
		ftm = new FavsTableManager(connect, userName, null);
		List<Publication> pubList = new ArrayList<Publication>();
		List<String> idList = ftm.getIdsfromTable();// 从表里获取到id
		long startMil = System.currentTimeMillis();
		System.out.println("用户" + userName + "正在查询收藏id:");
		if (idList.isEmpty()) {
			pubList.clear();
		} else {
			for (int i = 0; i < idList.size(); i++) {
				pubList.addAll(mdm.getPubList_favs(
						"publication_ieee",
						idList.get(i), "ts"));// mongodb查询id的文章
				System.out.println(idList.get(i));
			}
		}

		long endMil = System.currentTimeMillis();
		System.out.println("查询favs使用时间" + (endMil - startMil));
		return pubList;
	}

}
