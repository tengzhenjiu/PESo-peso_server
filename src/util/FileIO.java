package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileIO {
	public enum RW {
		READ, WRITE
	};

	private RW rw = RW.READ;
	private boolean isConnected = false;
	private String fileName = null;
	private BufferedReader br = null;
	private BufferedWriter bw = null;

	private StringBuffer writeBuffer = null;

	public FileIO() {
	}

	public FileIO(String fileName) {
		connect(fileName);
	}

	public FileIO(String fileName, RW rw) {
		this.rw = rw;
		connect(fileName);
	}

	public boolean connect(String fileName) {
		if (rw == RW.WRITE) {
			writeBuffer = new StringBuffer();
		}
		this.fileName = fileName;
		File f = new File(fileName);
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				return false;
			}
		}
		try {
			if (rw == RW.READ)
				br = new BufferedReader(new FileReader(f));
			else
				bw = new BufferedWriter(new FileWriter(f));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		isConnected = true;
		return true;
	}

	public boolean flush() {
		if (isConnected && rw == RW.WRITE) {
			try {
				if (writeBuffer != null
						&& writeBuffer.length() > 0) {
					bw.write(writeBuffer.toString());
					writeBuffer = new StringBuffer();
				}
				if (bw != null) {
					bw.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	public boolean commit() {
		return this.flush();
	}

	public boolean close() {
		if (!isConnected)
			return true;
		isConnected = false;
		try {
			if (br != null)
				br.close();
		} catch (Exception e) {
		}
		try {
			if (bw != null) {
				flush();
				bw.close();
			}
		} catch (Exception e) {
		}
		return true;
	}

	public String readLine() {
		if (!isConnected)
			return null;
		if (rw == RW.WRITE) {
			rw = RW.READ;
			close();
			connect(fileName);
		}
		try {
			return br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public synchronized FileIO append(String str) {
		if (writeBuffer != null)
			writeBuffer.append(str);
		return this;
	}

	public synchronized FileIO append(Integer intData) {
		return append(String.valueOf(intData));
	}

	public synchronized FileIO append(Long longData) {
		return append(String.valueOf(longData));
	}

	public synchronized boolean write(String str) {
		if (!isConnected)
			return false;
		if (rw == RW.READ) {
			rw = RW.WRITE;
			close();
			connect(fileName);
		}
		try {
			if (writeBuffer != null && writeBuffer.length() > 0) {
				bw.write(writeBuffer.toString());
				writeBuffer = new StringBuffer();
			}
			bw.write(str);
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public RW getIOMode() {
		return rw;
	}

	public boolean isConnected() {
		return isConnected;
	}

	public String getFileName() {
		return fileName;
	}

	public BufferedReader getBufferedReader() {
		return br;
	}

	public BufferedWriter getBufferedWriter() {
		return bw;
	}

	// public static void main(String[] args) {
	// System.out.println();
	// FileIO fio = new FileIO("try.txt",RW.WRITE);
	// fio.append("abc").append("cde").append("fg");
	// fio.flush();
	// fio.close();
	// }
}
