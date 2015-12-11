package Test;

import java.util.ArrayList;

import util.mongo.MongoDBConnectMacro;
import dbmodels.Publication;

public class Testmongodb {
	public static void main(String args[]) {
		MongoDBConnectMacro mdm = new MongoDBConnectMacro();
		mdm.init();
		if (mdm.check())
			System.out.println("mongodb连接成功");

		/*
		 * 
		 * System.out.println(mdm.getCollectCount(mdm.getCollection(
		 * "publication_ieee"))); long
		 * startMil=System.currentTimeMillis(); Cursor
		 * cur=mdm.getCollection("publication_ieee").find().sort(new
		 * BasicDBObject("ts",-1)).limit(1); long
		 * endMil=System.currentTimeMillis();
		 * System.out.println((endMil-startMil)+"毫秒");
		 * ArrayList<Publication> pubidlist = new
		 * ArrayList<Publication>(); while(cur.hasNext()) { //
		 * System.out.println(cur.next()); DBObject obj=cur.next(); //反转
		 * //System.out.println(obj.toString()); //Publication
		 * pub=gson.fromJson(obj.toString(), Publication.class);
		 * Publication pub=new Publication();
		 * pub=pub.fromMongdbObject(obj); pubidlist.add(pub);
		 * System.out.println(pub.getPdf_src()); }
		 */

		/*
		 * getpublist
		 */
		ArrayList<Publication> publist = new ArrayList<Publication>();
		long startMil = System.currentTimeMillis();
		publist = mdm.getPubList("publication_ieee", "fun", "ts");
		long endMil = System.currentTimeMillis();
		System.out.println((endMil - startMil) + "毫秒");
		System.out.println(publist.size());
		for (Publication o : publist)
			System.out.println(o.getTitle());
	}
}
