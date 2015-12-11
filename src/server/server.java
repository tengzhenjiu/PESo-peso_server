package server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mysql.MySqlManager;
import util.mongo.MongoDBConnectMacro;

public class server {
	socket soc = new socket();
	String Sql;
	Connection connect = null;
	private MongoDBConnectMacro mdm = new MongoDBConnectMacro();

	public server() throws IOException {
		// 创建Socket服务器
		soc.CreateSocket();
	}

	// 对象创建时开始运行
	public void start() throws SQLException, InterruptedException, IOException {
		MySqlManager manager = new MySqlManager();
		connect = manager.ConnectDB();
		mdm.init();
		mdm.close();
		ExecutorService pool = Executors.newFixedThreadPool(1);
		int i = 10;
		while (i > 0) {
			i--;
			Thread.sleep(100);
			WorkerThread worker = new WorkerThread(soc, connect,
					mdm); // 服务器线程运行入口
			pool.submit(worker);
		}
		pool.shutdown();
	}
}