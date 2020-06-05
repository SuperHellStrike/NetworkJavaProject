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
		frame = new JFrame("Сервер. АВТ-818 Жигулин Сальков Ягубцев Чекалова");
		portLabel = new JLabel("Порт:");
		portField = new JTextField();
		logArea = new JTextArea();
		exitButton = new JButton("Выйти");
		startButton = new JButton("Запустить");
		stopButton = new JButton("Остановить");
		saveButton = new JButton("Сохранить лог");
		kickLabel = new JLabel("Исключить по ID:");
		kickField = new JTextField();
		kickButton = new JButton("Исключить");
		
		isStart = false;
		
		startButton.setBounds(600, 70, 170, 20);
		frame.add(startButton);
		
		stopButton.setBounds(600, 90, 170, 20);
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
