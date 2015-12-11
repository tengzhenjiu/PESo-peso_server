package model;

import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import server.socket;

public class Login {
	public void start(socket soc, Connection connect, Socket client,
			String txt) {
		// System.out.println(txt);
		txt = txt.replace("[", "").replace("]", "").replace(" ", "");
		String[] data = txt.split(",");
		System.out.println("客户端发来数据:" + "\n" + "用户名:" + data[1] + "\n"
				+ "密码:" + data[2] + "\n");

		try {
			/******************** 检验用户名是否已经被注册 **********************/
			Statement stmt_count = connect.createStatement();
			String Sql_count = "select * from user where name='"
					+ data[1] + "'";
			ResultSet rs = stmt_count.executeQuery(Sql_count);
			/******************** 检验用户名是否已经被注册 **********************/

			if (!rs.first()) {
				// 如果查询结果不存在
				System.out.println("用户名不存在，登陆失败！");
				soc.SendMsg(client, "0");
			} else {
				// 用户名存在
				if (data[2].equals(rs.getString("password"))) {
					// 如果匹配
					System.out.println("用户名密码匹配，登陆成功！");
					soc.SendMsg(client, "1");
				} else {
					System.out.println("密码不匹配，登陆失败！");
					soc.SendMsg(client, "0");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.print("用户登陆失败！");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 中断，关闭此次连接
		try {
			soc.CloseSocket(client);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
