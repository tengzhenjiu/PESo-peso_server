package server;

import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;

import model.Advsearch;
import model.Favs;
import model.Listupdate;
import model.Login;
import model.Recommendation;
import model.Register;
import model.Search;
import model.Systemsetting;
import util.mongo.MongoDBConnectMacro;

public class WorkerThread implements Runnable {
	socket soc;
	Connection connect = null;
	MongoDBConnectMacro mdm = new MongoDBConnectMacro();
	Socket client = null;
	String txt;

	public WorkerThread(socket soc, Connection connect,
			MongoDBConnectMacro mdm) {
		this.soc = soc;
		this.connect = connect;
		this.mdm = mdm;
	}

	@Override
	public void run() {
		Register register = new Register();
		Login login = new Login();
		Search search = new Search();
		Advsearch advsearch = new Advsearch();
		Recommendation recommendation = new Recommendation();
		Systemsetting syssetting = new Systemsetting();

		// 线程无限循环，实时监听socket端口
		// 响应客户端链接请求。

		while (true) {
			// mongo
			System.out.println(Thread.currentThread().getName()
					+ "正在工作");

			// 连接客户端
			try {
				client = soc.ResponseSocket();
				txt = soc.ReceiveMsg(client);
				mdm.init();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 客户端连接结束
			if (txt.matches("i need(.*?)papers")) {
				// 发送更新数据
				try {
					Listupdate update = new Listupdate();
					update.start(soc, connect, client, txt,
							mdm);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (txt.matches("\\[login(.*?)\\]")) {
				login.start(soc, connect, client, txt);
			}
			if (txt.matches("\\[register(.*?)\\]")) {
				register.start(soc, connect, client, txt);
			}
			if (txt.matches("\\[高级搜索(.*?)\\]")) {
				try {
					advsearch.start(soc, connect, client,
							txt, mdm);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (txt.matches("search(.*?)")) {
				try {
					search.start(soc, connect, client, txt,
							mdm);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (txt.matches("recomendations(.*?)")) {
				try {
					System.out.println("我到了");
					recommendation.start(soc, connect,
							client, txt, mdm);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (txt.matches("FAVS(.*?)")) // 匹配通用接受客户端发送的请求
			{
				try {
					Favs favs = new Favs(soc, connect,
							client, txt, mdm);
					favs.start();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (txt.matches("SYSSETTING(.*?)")) // 匹配通用接受客户端发送的请求
			{
				try {
					syssetting.start(soc, connect, client,
							txt, mdm);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (txt.isEmpty()) {
				System.out.println("数据接收错误！");
			}
			try {
				soc.CloseSocket(client);
				mdm.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}