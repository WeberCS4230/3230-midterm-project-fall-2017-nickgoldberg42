package midterm;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

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

				JScrollBar vertical = scroll.getVerticalScrollBar();
				vertical.setValue(vertical.getMaximum());

				textEntry.setText("");
			}

		});

		frame.getContentPane().add(panel);
		panel.add(submitButton);

		JButton btnNewButton = new JButton("HIT");
		btnNewButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
			}
		});
		panel.add(btnNewButton);

		JButton btnStay = new JButton("STAY");
		panel.add(btnStay);
		frame.setVisible(true);

	}

	public void addText(String text)
	{
		textArea.append(text + "\n");

	}
}
