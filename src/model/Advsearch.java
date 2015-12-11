package model;

import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;

import server.socket;
import util.mongo.MongoDBConnectMacro;

import com.google.gson.Gson;

import dbmodels.Publication;

public class Advsearch {

	public void start(socket soc, Connection connect, Socket client,
			String txt, MongoDBConnectMacro mdm)
			throws IOException, InterruptedException {
		ArrayList<Publication> publist = new ArrayList<Publication>();
		txt = txt.replace("[", "").replace("]", "");
		String[] data = txt.split(",");
		System.out.println("客户端发来数据:" + "\n" + "模糊搜索:" + data[1] + "\n"
				+ "精确搜索:" + data[2] + "\n" + "文章位置:" + data[3]
				+ "\n" + "作者名:" + data[4] + "\n" + "月份:"
				+ data[5] + "\n" + "年份:" + data[6] + "\n");
		int i = 0;
		for (String dat : data) {
			data[i++] = dat.replaceFirst(" ", "");
		}

		// 记录时间
		long startMil = System.currentTimeMillis();
		publist = mdm.getPubList_adv("publication_ieee", data, "ts");
		// System.out.println(num+publist.toString());
		long endMil = System.currentTimeMillis();
		System.out.println("采集数据耗时：" + (endMil - startMil) + "毫秒");
		Gson gson = new Gson();
		/* te是需要序列化的对象 */
		String s1 = gson.toJson(publist);
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
