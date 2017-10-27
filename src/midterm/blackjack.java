package midterm;

import java.io.IOException;
import java.net.UnknownHostException;

public class Blackjack
{

	public static void main(String[] args)
			throws UnknownHostException, IOException
	{
		ChatGui cg = new ChatGui();

		try
		{
			@SuppressWarnings("unused")
			Client chatClient = new Client(
					"ec2-54-91-0-253.compute-1.amazonaws.com");
		} catch (UnknownHostException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
