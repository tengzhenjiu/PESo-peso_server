package model;

import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import server.socket;

public class Register {
	public void start(socket soc, Connection connect, Socket client,
			String txt) {
		// System.out.println(txt);
		txt = txt.replace("[", "").replace("]", "").replace(" ", "");
		String[] data = txt.split(",");
		System.out.println("客户端发来数据:" + "\n" + "用户名:" + data[1] + "\n"
				+ "邮箱:" + data[2] + "\n" + "密码:" + data[3]
				+ "\n");
		/******************** 将接受到的的注册信息插入数据库 **********************/
		try {
			/******************** 检验用户名是否已经被注册 **********************/
			Statement stmt_count = connect.createStatement();
			String Sql_count = "select * from user where name='"
					+ data[1] + "'";
			ResultSet rs_count = stmt_count.executeQuery(Sql_count);
			/******************** 检验用户名是否已经被注册 **********************/

			if (!rs_count.first()) {
				// 如果查询结果不存在则将用户信息存入数据库
				Statement stmt = connect.createStatement();
				// 与数据库相同
				String Sql = "insert into user (name,email,password) values('"
						+ data[1]
						+ "','"
						+ data[2]
						+ "','" + data[3] + "')";
				int rs = stmt.executeUpdate(Sql);
				if (rs == 1)
					System.out.println("该用户信息已经存入数据库！");
				// 向客户端返回注册成功的消息
				soc.SendMsg(client, "0");
			} else {
				System.out.println("用户名已经存在，注册失败！");
				soc.SendMsg(client, "1");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.print("用户信息存储失败！");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/******************** 将接受到的的注册信息插入数据库 **********************/
		// 中断，关闭此次连接
		try {
			soc.CloseSocket(client);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
