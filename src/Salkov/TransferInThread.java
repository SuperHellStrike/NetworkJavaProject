package Salkov;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;

public class TransferInThread extends Thread {
    private  DatagramSocket datagramSocket;
    private DatagramPacket datagramPacket;
    private Socket socket;
    private Vector<Rectangle> rectangleVector;

    public TransferInThread(DatagramSocket datagramSocket, Socket socket, Vector<Rectangle> rectangleVector) throws SocketException {
        this.datagramSocket = datagramSocket;
        this.socket = socket;
        this.rectangleVector = rectangleVector;
        datagramPacket = new DatagramPacket(new byte[12], 12);
    }

    public int getUDP(){
        return datagramSocket.getPort();
    }

    @Override
    public void run() {
        while(!isInterrupted()){
            try {
                datagramSocket.receive(datagramPacket);
                if(datagramPacket.getLength() != 12){
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    dos.writeByte(0);
                    interrupt();
                }
                else{
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(datagramPacket.getData());
                    int id = byteArrayInputStream.read();
                    int x = byteArrayInputStream.read();
                    int y = byteArrayInputStream.read();
                    boolean isIdFound = false;
                    for(int i = 0; i < rectangleVector.size(); i++){
                        if(rectangleVector.get(i).getId() == id){
                            rectangleVector.get(i).setCenterX(x);
                            rectangleVector.get(i).setCenterY(y);
                            isIdFound = true;
                            break;
                        }
                    }
                    if(!isIdFound){
                        rectangleVector.add(new Rectangle(id,x,y));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        datagramSocket.close();
        System.exit(0);
    }
}
