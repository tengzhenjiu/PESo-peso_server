package model;

import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;

import server.socket;
import util.mongo.MongoDBConnectMacro;

import com.google.gson.Gson;

import dbmodels.Publication;

public class Listupdate {

	public void start(socket soc, Connection connect, Socket client,
			String txt, MongoDBConnectMacro mdm)
			throws IOException, InterruptedException {
		ArrayList<Publication> publist = new ArrayList<Publication>();
		// Pattern p=Pattern.compile("i need (.*?) papers");
		// Matcher m=p.matcher(txt); // 此处都是我注销掉的
		int num = 5;
		// while(m.find()){
		// num=Integer.valueOf(m.group(1));
		// }
		long startMil = System.currentTimeMillis();
		System.out.println("将要想客户端发送" + num + "个数据.......");
		publist = mdm.getPubList("publication_ieee", num, "ts");
		// System.out.println(num+publist.toString());
		long endMil = System.currentTimeMillis();
		System.out.println("采集数据耗时：" + (endMil - startMil) + "毫秒");
		Gson gson = new Gson();
		/* te是需要序列化的对象 */
		String s = gson.toJson(publist);

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
			soc.SendMsg(client, s);
			System.out.println("已成功发送" + s.length() + "个数据");
			soc.CloseSocket(client);
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
