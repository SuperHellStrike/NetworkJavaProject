package hellstrike21291.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainWindow {
	
	private JFrame frame;
	private JLabel portLabel;
	private JTextField portField;
	private JTextArea logArea;
	private JButton exitButton;
	private JButton startButton;
	private JButton stopButton;
	private JButton saveButton;
	private JLabel kickLabel;
	private JTextField kickField;
	private JButton kickButton;
	
	private boolean isStart;
	private int port;
	
	public static void main(String[] args) {
		new MainWindow();
	}
	
	public MainWindow() {
		frame = new JFrame("������. ���-818 ������� ������� ������� ��������");
		portLabel = new JLabel("����:");
		portField = new JTextField();
		logArea = new JTextArea();
		exitButton = new JButton("�����");
		startButton = new JButton("���������");
		stopButton = new JButton("����������");
		saveButton = new JButton("��������� ���");
		kickLabel = new JLabel("��������� �� ID:");
		kickField = new JTextField();
		kickButton = new JButton("���������");
		
		isStart = false;
		port = 0;
		
		startButton.setBounds(600, 70, 170, 20);
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isStart == true) {
					logArea.append("������ ��� �������\n");
				}
				else {
					/*
					 * ����������� ������ �������
					 */
					try {
						port = Integer.parseInt(portField.getText());
						if(port > 65535 || port < 0)
							throw new NumberFormatException();
					}
					catch(NumberFormatException ex) {
						logArea.append("������� ������ ����\n");
						port = 0;
						return;
					}
					
					isStart = true;
					logArea.append("������ �������\n");
				}
			}
		});
		frame.add(startButton);
		
		stopButton.setBounds(600, 90, 170, 20);
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isStart == false) {
					logArea.append("������ ��� ����������\n");
				}
				else {
					/*
					 * ����������� ��������� �������
					 */
					isStart = false;
					logArea.append("������ ����������\n");
				}
			}
		});
		frame.add(stopButton);
		
		saveButton.setBounds(600, 110, 170, 20);
		frame.add(saveButton);
		
		kickButton.setBounds(600, 190, 170, 20);
		frame.add(kickButton);	
		
		exitButton.setBounds(600, 520, 170, 20);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isStart) {
					stopButton.doClick();
					saveButton.doClick();
				}
				System.exit(0);
			}
		});
		frame.add(exitButton);
		
		
		portLabel.setBounds(600, 30, 200, 20);
		frame.add(portLabel);
		
		kickLabel.setBounds(600, 150, 170, 20);
		frame.add(kickLabel);
		
		portField.setBounds(600, 50, 170, 20);
		frame.add(portField);
		
		kickField.setBounds(600, 170, 170, 20);
		frame.add(kickField);
		
		logArea.setBounds(30, 30, 540, 510);
		logArea.setEditable(false);
		frame.add(logArea);
		
		frame.setSize(800, 600);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setVisible(true);
	}
}
