package midterm;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import blackjack.message.MessageFactory;

public class ChatGui
{
	JTextArea textArea = new JTextArea();
	JTextArea textEntry = new JTextArea();
	private JFrame frame;

	public ChatGui() throws UnknownHostException, IOException
	{
		initialize();
	}

	public void initialize() throws UnknownHostException, IOException
	{
		final Socket socket = new Socket(
				"ec2-54-172-123-164.compute-1.amazonaws.com", 8989);
		Client client = new Client(socket);

		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();

		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		textArea.setLineWrap(true);
		textArea.setEditable(true);
		panel.add(textArea);

		final JScrollPane scroll = new JScrollPane(textArea);
		panel.add(scroll);
		scroll.setBackground(Color.blue);

		textEntry.setFocusable(true);
		textEntry.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{

				if (e.getKeyCode() == KeyEvent.VK_ENTER && e.isControlDown())
				{
					String entry = textEntry.getText() + "\n";
					textArea.append(entry);
					try
					{
						send(entry, socket);
					} catch (IOException e2)
					{
						e2.printStackTrace();
					}

					JScrollBar vertical = scroll.getVerticalScrollBar();
					vertical.setValue(vertical.getMaximum());

					textEntry.setText("");
				}
			}
		});
		panel.add(textEntry);

		JButton submitButton = new JButton("Submit");
		submitButton.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				String entry = textEntry.getText();
				textArea.append(entry + "\n");
				try
				{
					send(entry, socket);
				} catch (IOException e2)
				{
					e2.printStackTrace();
				}

				JScrollBar vertical = scroll.getVerticalScrollBar();
				vertical.setValue(vertical.getMaximum());

				textEntry.setText("");
			}

		});
		panel.add(submitButton);

		frame.getContentPane().add(panel);

		JButton newGame = new JButton("NEW GAME");
		newGame.addMouseListener(new MouseAdapter()
		{

			@Override
			public void mouseClicked(MouseEvent e)
			{
				try
				{
					send(MessageFactory.getStartMessage(), socket);
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		panel.add(newGame);

		JButton hit = new JButton("HIT");
		hit.addMouseListener(new MouseAdapter()
		{

			@Override
			public void mouseClicked(MouseEvent e)
			{
				try
				{
					send(MessageFactory.getHitMessage(), socket);
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}

		});
		panel.add(hit);

		JButton btnStay = new JButton("STAY");
		panel.add(btnStay);
		btnStay.addMouseListener(new MouseAdapter()
		{

			@Override
			public void mouseClicked(MouseEvent e)
			{
				try
				{
					send(MessageFactory.getStayMessage(), socket);
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		frame.setVisible(true);

	}

	public void addText(String text)
	{
		textArea.append(text + "\n");

	}

	public void send(Object o, Socket socket) throws IOException
	{
		ObjectOutputStream writer = new ObjectOutputStream(
				socket.getOutputStream());
		writer.writeObject(o);
		writer.flush();
	}

	public class Client
	{
		ObjectOutputStream writer;

		public Client(Socket socket) throws UnknownHostException, IOException
		{
			new Thread(new Handler(socket)).start();
		}

		private class Handler implements Runnable
		{
			Socket sock;
			BufferedReader buffReader;
			ObjectInputStream input;

			public Handler(Socket socket) throws IOException
			{
				sock = socket;

				buffReader = new BufferedReader(
						new InputStreamReader(sock.getInputStream()));
				writer = new ObjectOutputStream(sock.getOutputStream());
				input = new ObjectInputStream(sock.getInputStream());

			}

			@Override
			public void run()
			{
				try
				{
					writer.writeObject(
							MessageFactory.getLoginMessage("Nick Goldberg"));
					writer.flush();
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}

				while (sock.isConnected())
				{
					try
					{
						textArea.append(buffReader.readLine() + "\n");
						Object x = input.readObject();
						if (x.equals(MessageFactory.getJoinMessage()))
						{
							writer.writeObject(MessageFactory.getJoinMessage());
							writer.flush();
						}
					} catch (IOException e)
					{
						e.printStackTrace();
					} catch (ClassNotFoundException e)
					{
						e.printStackTrace();
					}
				}
			}
		}

	}
}
