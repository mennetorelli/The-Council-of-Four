package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import client.controller.ClientController;
import client.view.socket.CLIsocket;
import modelDTO.GameDTO;

public class ClientSocket {

	private final static int PORT = 29999;
	private final static String IP = "127.0.0.1";
	
	public void startClient()
				throws UnknownHostException, IOException {
		Socket socket=new Socket(IP, PORT);
		System.out.println("Connection created");
		
		

		GameDTO clientGame=new GameDTO();
		ClientController clientController=new ClientController(clientGame);
		CLIsocket view=new CLIsocket(clientGame.getParser(), 
				new ObjectOutputStream(socket.getOutputStream()),
				new ObjectInputStream(socket.getInputStream()));
		clientGame.registerObserver(view);				
		view.registerObserver(clientController);
		ExecutorService executor=Executors.newSingleThreadExecutor();
		executor.submit(view);
		view.welcome();
		view.input();
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException {

		ClientSocket client=new ClientSocket();
		client.startClient();
				
	}
}