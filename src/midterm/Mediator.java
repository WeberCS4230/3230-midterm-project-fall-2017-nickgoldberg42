package midterm;

public class Mediator
{
	Client c;
	ChatGui g;

	public Mediator(Client client, ChatGui gui)
	{
		c = client;
		g = gui;
	}

	public void guiToClient(Object o)
	{
		c.writeToServer(o);
	}

	public void clientToGUI(String in)
	{
		g.addText(in);
	}

}
