package de.berlin.arzt.net;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class Speedtest {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		int port = 12345;
		long nbytes = 1024;
		String address = "127.0.0.1";
		if (args.length > 0) {
			if ("server".equals(args[0])) {
				if (args.length > 1) {
					try {
						port = Integer.parseInt(args[1]);
					} catch (Exception e) {}
				}
				server(port);
			} else if ("client".equals(args[0])) {
				address = args[1];
				nbytes = Integer.parseInt(args[2]);
				if (args.length > 3) {
					try {
						port = Integer.parseInt(args[3]);
					} catch (Exception e) {}
				}
				client(address, port, nbytes);
			}
		} else {
			System.out.println("Speedtest server <port>");
			System.out.println("Speedtest client <address> <nbytes> <port>");
		}
	}

	public static void server(int port) throws IOException {
		@SuppressWarnings("resource")
		ServerSocket server = new ServerSocket(port);
		byte[] b = new byte[1024*3];
		while (true) {
			System.out.println("Listening on port " + port);
			Socket sock = server.accept();
			System.out.println("Connected.");
			InputStream in = sock.getInputStream();
			OutputStream out = sock.getOutputStream();
			int nbytes = 1;
			int sum = 0;
			try {
				while (nbytes > 0) {
					nbytes = in.read(b);
					if (nbytes > 0) {
						out.write(b, 0, nbytes);
						sum += nbytes; 
						System.out.println("Mirrored " + (sum/1024/1024) + " MB." );
					}
				}
			} catch (IOException e) {}
			sock.shutdownOutput();
		}
	}

	public static void receive(InputStream in, long nbytes) {
	
		long time = System.nanoTime();
		byte[] b = new byte[1024];
		int sum = 0;
		int ibytes = 1;
		try {
			while (ibytes > 0) {
				ibytes = in.read(b);
				if (ibytes > 0) {
					sum += ibytes;
				}
				System.out.println("Received " + sum/1024d/1024 + " MB.");
			}
		} catch (IOException e) {
			System.out.println("Connection closed...");
			e.printStackTrace();
		}
		System.out.println(sum == nbytes);
		long time2 = System.nanoTime() - time;
		double megabytes = nbytes/1024/1024;
		double seconds = time2/1000./1000/1000;
		System.out.println("Sekunden: " + seconds);
		System.out.println("MB: " + megabytes);
		System.out.println("MB/sec: " + megabytes/seconds);
	
	}

	public static void client(String address, int port, final long nbytes) throws IOException, InterruptedException {
		Socket socket = new Socket(address, port);
		long ibytes = 0;
		OutputStream out = socket.getOutputStream();
		final InputStream in = socket.getInputStream();
		byte[] b = new byte[1024*10];
		Runnable target = new Runnable() {
			@Override
			public void run() {
				receive(in, nbytes);
			}
		};
		Thread t = new Thread(target);
		t.start();
		while (ibytes < nbytes) {
			long offset = Math.min(b.length, nbytes - ibytes); 
			out.write(b, 0, (int) offset);
			ibytes += offset;
		}
		socket.shutdownOutput();
		t.join();
		socket.close();
	}

}
