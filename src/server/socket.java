package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class socket {
	private ServerSocket server = null;
	private BufferedWriter writer;
	private BufferedReader reader;
	private static final int PORT = 6000;// 设置端口号
	ObjectInputStream objInputStream = null;
	ObjectOutputStream objOutputStream = null;

	// 创建一个ServerSocket实例，绑定到一个特定的端口，一个服务器端口最多可以支持50个链接，超出了则拒绝链接
	// 所以，最多只能有50个客户端同时使用这个指定的端口向服务器发送消息
	void CreateSocket() throws IOException {
		server = new ServerSocket(PORT);
		System.out.println("服务器已经启动...");
	}

	/*
	 * ServerSocket：这个类是实现了一个服务器端的Socket，利用这个类可以监听来自网络的请求。 　　
	 * 1、创建ServerSocket的方法： 　　 ServerSocket(Int localPort) 　　
	 * ServerSocket(int localport,int queueLimit) 　　 ServerSocket(int
	 * localport,int queueLimit,InetAddress localAddr) 　　
	 * 创建一个ServerSocket必须指定一个端口，以便客户端能够向该端口号发送连接请求。端口的有效范围是0-65535
	 * 　　2、ServerSocket操作 　　 Socket accept() 　　 void close 　　
	 * accept()方法为下一个传入的连接请求创建Socket实例，并将已成功连接的Socket实例返回给服务器套接字，如果没有连接请求，
	 * accept()方法将阻塞等待； 　　 close方法用于关闭套接字
	 */

	public Socket ResponseSocket() throws IOException {
		Socket client = server.accept();
		System.out.println("与客户端连接已建立..." + "客户端IP："
				+ client.getInetAddress().getHostAddress());
		return client;
	}

	// 关闭socket

	public void CloseSocket(Socket socket) throws IOException {
		reader.close();
		writer.close();
		socket.close();
		System.out.println("服务器已关闭..");
	}

	// 向客户端发送消息

	public void SendMsg(Socket socket, String Msg) throws IOException {
		writer = new BufferedWriter(new OutputStreamWriter(
				socket.getOutputStream())); // 获取输出流并且加缓冲
		writer.write(Msg.replace("\n", " ") + "\n"); // 调用write方法向客户端发送数据
		writer.flush();
	}

	// 接收来自客户端的消息，服务器通过server.accept();接收来自客户端的套接字，采用I/O方式
	// 将套接字的消息取出来

	public String ReceiveMsg(Socket socket) throws IOException {
		reader = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		System.out.println("获得从客户端发送消息..");
		System.out.println(Thread.currentThread().getName() + "收到消息");
		String line = null;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
			return line;
		}
		return line;
	}
}
