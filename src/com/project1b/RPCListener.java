package com.project1b;
import java.io.IOException;
import java.net.*;

/**
 * Listens on port 5300 for any RPC requests
 * @author rayweng
 *
 */
public class RPCListener implements Runnable {
	public static final int PROJ1B_RPC_PORT = 5300;
	private DatagramSocket rpcSocket = null;
	private RPCServer server = null;
	
	
	@Override
	public void run() {
		if (server == null)
			server = new RPCServer();
		try {
			rpcSocket = new DatagramSocket(PROJ1B_RPC_PORT);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		try {
			listen();
		} catch(IOException e) {
			e.printStackTrace();
			// move on
		}
	}
		
		
	protected void listen() throws IOException {
		byte[] receiveData = new byte[512];
		byte[] sendData = new byte[512];
		
		// keep listening (not busy wait because receive() blocks until data arrives
		while(true) {
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			rpcSocket.receive(receivePacket);
			
			InetAddress returnAddr = receivePacket.getAddress();
			int returnPort = receivePacket.getPort();
			
			// receiveData contains the packet data
			String request = new String(receivePacket.getData());
			
			// TODO: parse operation code, do operation
			
			// sendPacket is data to return to caller
			// byte array can be retrieved from String.getBytes()
			
			DatagramPacket responsePacket = new DatagramPacket(sendData, sendData.length, returnAddr, returnPort);
			rpcSocket.send(responsePacket);
		}
	}

}
