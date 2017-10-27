package midterm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import blackjack.message.MessageFactory;

public class Client
{
	ChatGui chat;
	ObjectOutputStream writer;

	public Client(String ip, ChatGui cg)
			throws UnknownHostException, IOException
	{
		chat = cg;

		Socket socket = new Socket(ip, 8090);

		new Thread(new Handler(socket)).start();
	}

	private class Handler implements Runnable
	{
		Socket sock;
		BufferedReader buffReader;

		public Handler(Socket socket) throws IOException
		{
			sock = socket;

			buffReader = new BufferedReader(
					new InputStreamReader(sock.getInputStream()));
			writer = new ObjectOutputStream(sock.getOutputStream());

		}

		@Override
		public void run()
		{
			try
			{
				writer.writeObject(
						MessageFactory.getLoginMessage("Nick Goldberg"));
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}

			while (sock.isConnected())
			{
				try
				{
					addToChat(buffReader.readLine());
					writer.flush();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	public void addToChat(String in)
	{
		chat.addText(in);
	}

	public void writeToServer(Object o)
	{
		try
		{
			writer.writeObject(o);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
