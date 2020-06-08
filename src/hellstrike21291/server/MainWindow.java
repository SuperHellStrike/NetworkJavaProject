package hellstrike21291.server;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	private ServerThread server;
	
	private Date currentDate;
	private SimpleDateFormat formatForDate;
	private Font logFont;
	
	private Point[] objects;
	
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
		port = 0;
		
		formatForDate = new SimpleDateFormat("[HH:mm:ss] ");
		logFont = new Font("Monospaced", 0, 16);
		
		objects = new Point[256];
		for(int i = 0; i < objects.length; i++)
			objects[i] = new Point();
		
		try(FileInputStream fis = new FileInputStream("./objects.data")) {
			DataInputStream dis = new DataInputStream(fis);
			for(int i = 0; i < objects.length; i++) 
				objects[i] = new Point(dis.readInt(), dis.readInt());
			dis.close();
		} catch (FileNotFoundException e2) {
			System.out.println("Файл с объектанми не найден");
			try(FileOutputStream fos = new FileOutputStream("./objects.data")) {
				fos.write(new byte[256*Integer.BYTES*2], 0, 256*Integer.BYTES*2);
			} catch (FileNotFoundException e1) {} catch (IOException e1) {}
		} catch (IOException e2) {
			System.out.println("Какое-то говно при чтении файла\n");
		}
		
		
		
		startButton.setBounds(600, 70, 170, 20);
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isStart == true) {
					currentDate = new Date();
					logArea.append(formatForDate.format(currentDate) + "Сервер уже запущен\n");
				}
				else {
					/*
					 * Реализовать запуск сервера
					 */
					try {
						port = Integer.parseInt(portField.getText());
						if(port > 65535 || port < 0)
							throw new NumberFormatException();
					}
					catch(NumberFormatException ex) {
						currentDate = new Date();
						logArea.append(formatForDate.format(currentDate) + "Неверно указан порт\n");
						port = 0;
						return;
					}
					
					try {
						server = new ServerThread(port, objects);
						server.start();
					} catch (IOException e1) {
						currentDate = new Date();
						logArea.append(formatForDate.format(currentDate) + "Ошибка при запуске потока сервера\n");
						return;
					}
					
					isStart = true;
					currentDate = new Date();
					logArea.append(formatForDate.format(currentDate) + "Сервер запущен\n");
				}
			}
		});
		frame.add(startButton);
		
		stopButton.setBounds(600, 90, 170, 20);
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isStart == false) {
					currentDate = new Date();
					logArea.append(formatForDate.format(currentDate) + "Сервер уже остановлен\n");
				}
				else {
					/*
					 * Реализовать остановку сервера
					 */
					server.interrupt();
					
					isStart = false;
					currentDate = new Date();
					logArea.append(formatForDate.format(currentDate) + "Сервер остановлен\n");
				}
			}
		});
		frame.add(stopButton);
		
		saveButton.setBounds(600, 110, 170, 20);
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try(FileOutputStream logFile = new FileOutputStream("./server.log")) {
					logFile.write(logArea.getText().getBytes());
				} catch (FileNotFoundException e) {
					currentDate = new Date();
					logArea.append(formatForDate.format(currentDate) + "Ошибка при открытии лога сервера\n");
				} catch (IOException e) {
					currentDate = new Date();
					logArea.append(formatForDate.format(currentDate) + "Ошибка при записи лога сервера\n");
				}
			}
		});
		frame.add(saveButton);
		
		kickButton.setBounds(600, 190, 170, 20);
		kickButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isStart == false) {
					currentDate = new Date();
					logArea.append(formatForDate.format(currentDate) + "Нельзя кикнуть игрока: сервер выключен\n");
					return;
				}
				int kickID = 0;
				try {
					kickID = Integer.parseInt(kickField.getText());
					if(kickID < 0)
						throw new NumberFormatException();
				}
				catch(NumberFormatException ex) {
					currentDate = new Date();
					logArea.append(formatForDate.format(currentDate) + "Неверно указан ID объекта\n");
					return;
				}
				
			}
		});
		frame.add(kickButton);	
		
		exitButton.setBounds(600, 520, 170, 20);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isStart) {
					stopButton.doClick();
					saveButton.doClick();
				}
				try(FileOutputStream fos = new FileOutputStream("./objects.data")) {
					DataOutputStream dos = new DataOutputStream(fos);
					for(int i = 0; i < objects.length; i ++) {
						dos.writeInt(objects[i].x);
						dos.writeInt(objects[i].y);
					}
				} catch (FileNotFoundException e1) {} catch (IOException e1) {}
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
		logArea.setFont(logFont);
		frame.add(logArea);
		
		frame.setBounds(550, 150, 800, 600);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setVisible(true);
	}
}
