package hellstrike21291.server;

import java.awt.Point;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.LinkedList;

public class ClientThread extends Thread {
	private LinkedList<Connection> clients;
	private Point[] objects;
	private DatagramSocket udpSocket;
	private DatagramPacket udpPacket;
	
	public ClientThread(LinkedList<Connection> clients, Point[] objects) {
		this.clients = clients;
		this.objects = objects;
		try {
			this.udpSocket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		
		while(!isInterrupted()) {
			
			for(int i = 0; i < clients.size(); i++) {
				try {
					int command = clients.get(i).getSocket().getInputStream().read();
					
					switch(command) {
					case 0:
						Connection c = clients.remove(i);
						udpPacket = new DatagramPacket(new byte[1], 1, c.getSocket().getInetAddress(), c.getPort());
						udpSocket.send(udpPacket);
						c.getSocket().close();
						break;
					case 1:
						objects[clients.get(i).getID()].y -= 10;
						break;
					case 2:
						objects[clients.get(i).getID()].y += 10;
						break;
					case 3:
						objects[clients.get(i).getID()].x -= 10;
						break;
					case 4:
						objects[clients.get(i).getID()].x += 10;
						break;
					}
					
					ByteBuffer data = ByteBuffer.allocate(Integer.BYTES * 3);
					data.putInt(clients.get(i).getID());
					data.putInt(objects[clients.get(i).getID()].x);
					data.putInt(objects[clients.get(i).getID()].y);
					udpPacket = new DatagramPacket(data.array(), data.array().length);
					
					for(int j = 0; j < clients.size(); j++) {
						udpPacket.setAddress(clients.get(j).getSocket().getInetAddress());
						udpPacket.setPort(clients.get(j).getPort());
						udpSocket.send(udpPacket);
					}
				} catch (IOException e) {}
			}
		}
		
		udpPacket = new DatagramPacket(new byte[1], 1);
		for(int i = 0; i < clients.size(); i++) {
			udpPacket.setAddress(clients.get(i).getSocket().getInetAddress());
			udpPacket.setPort(clients.get(i).getPort());
			try {
				udpSocket.send(udpPacket);
				clients.get(i).getSocket().getInputStream().read();
				clients.get(i).getSocket().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		while(clients.size() != 0)
			clients.remove();
	}
}
