package model;

/*负责处理客户端系统设置发来的请求*/
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;

import mysql.MySqlManager;
import server.socket;
import util.mongo.MongoDBConnectMacro;

public class Systemsetting {
	Socket client;
	private String sendMsg;

	public void start(socket soc, Connection connect, Socket client,
			String txt, MongoDBConnectMacro mdm)
			throws SQLException, IOException {
		// 匹配客户端传入信号，在此分支各信号的作用并将其从此返回客户端请求
		if (txt.matches("SYSSETTINGUPDATECHECK")) {
			System.out.println("匹配成功！客户端请求最新版本号码");
			MySqlManager manager = new MySqlManager(connect);
			sendMsg = manager.getVersion("VERSION");
			try {
				soc.SendMsg(client, sendMsg); // 将更新后的信息发送给客户端
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("服务器已发送版本号码");
		}

	}

}
