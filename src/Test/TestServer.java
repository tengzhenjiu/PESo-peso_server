package Test;

import java.io.IOException;
import java.sql.SQLException;

import server.server;

public class TestServer {
	public static void main(String args[]) throws SQLException,
			IOException, InterruptedException {
		server comm = new server();
		if (comm != null) {
			comm.start();
		}
	}
}
