package hellstrike21291.server;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.LinkedList;

public class ServerThread extends Thread{
	private ServerSocket serverSocket;
	private ClientThread clientThread;
	private LinkedList<Connection> clients;
	private Point[] objects;
	
	public ServerThread(int port, Point[] objects) throws IOException {
		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(10);
		clients = new LinkedList<Connection>();
		clientThread = new ClientThread(clients, objects);
	}
	
	@Override
	public void run() {
		clientThread.start();
		
		while(!isInterrupted()) {
			
			try {
				Socket client = serverSocket.accept();
				DataInputStream dos = new DataInputStream(client.getInputStream());
				
				int port = dos.readInt();
				int id = dos.readInt();
				
				DatagramSocket ds = new DatagramSocket();
				
				for(Connection c : clients) {
					if(id > 255 || id < 0 || c.getID() == id) {
						DatagramPacket dp = new DatagramPacket(new byte[1], 1, client.getInetAddress(), port);
						ds.send(dp);
						ds.close();
						client.getInputStream().read();
						client.close();
						throw new IOException();
					}
				}
				
				for(Connection c : clients) {
					ByteBuffer data = ByteBuffer.allocate(Integer.BYTES * 3);
					data.putInt(c.getID());
					data.putInt(objects[c.getID()].x);
					data.putInt(objects[c.getID()].y);
					DatagramPacket pack = new DatagramPacket(data.array(), data.array().length, client.getInetAddress(), port);
					ds.send(pack);
				}
				ByteBuffer data = ByteBuffer.allocate(Integer.BYTES * 3);
				data.putInt(id);
				data.putInt(objects[id].x);
				data.putInt(objects[id].y);
				DatagramPacket pack = new DatagramPacket(data.array(), data.array().length, client.getInetAddress(), port);
				ds.send(pack);
				ds.close();
				
				client.setSoTimeout(5);
				clients.add(new Connection(client, port, id));
				
			} catch (IOException e) {}
		}
		
		/*
		 * Обработать остановку TCP-сервера
		 */
		clientThread.interrupt();
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
