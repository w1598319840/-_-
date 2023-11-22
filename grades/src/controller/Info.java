package controller;

import java.awt.AWTEvent;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.*;

public class Info extends JFrame {
	/**
	 * �û���ѯ������Ϣ
	 */
	private static final long serialVersionUID = 1L;
	JLabel idLabel, nameLabel, genderLabel, birLabel, insLabel, majorLabel;
	String id, name, pwd, gender, birthday, institute, major;
	JPanel stuInfoJPanel;

	Student stu;
	Teacher t;
	Administrator ad;

	public Info(String id, int flag) {
		super("��Ϣ");
		this.id = id;
		setSize(300, 340);
		setLocation(600, 400);
		stuInfoJPanel = new JPanel();
		stuInfoJPanel.setLayout(new GridLayout(20, 1));
		add(stuInfoJPanel);
		String file = "";
		if (flag == 1) {
			// file = "D://test//student.txt";
			file = System.getProperty("user.dir") + "/data/student.txt";
		} else if (flag == 0) {
			// file = "D://test//teacher.txt";
			file = System.getProperty("user.dir") + "/data/teacher.txt";
		} else if (flag == 2) {
			file = System.getProperty("user.dir") + "/data/administrator.txt";
		}

		// StringBuilder result = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));// ����һ��BufferedReader������ȡ�ļ�
			String s = null;
			while ((s = br.readLine()) != null) {// ʹ��readLine������һ�ζ�һ��
				String[] result = s.split(" ");
				if (result[0].equals(id)) {
					id = result[0];
					pwd = result[1];
					name = result[2];
					gender = result[3];
					birthday = result[4];
					institute = result[5];
					major = result[6];

					if (flag == 1) {
						stu = new Student(id, pwd, name, gender, birthday, institute, major);
						idLabel = new JLabel("�˺�:" + stu.getId());
						nameLabel = new JLabel("����:" + stu.getName());
						genderLabel = new JLabel("�Ա�:" + stu.getSex());
						birLabel = new JLabel("����:" + stu.getBirthday());
						insLabel = new JLabel("ѧԺ:" + stu.getInstitute());
						majorLabel = new JLabel("רҵ:" + stu.getMajor());
					} else if (flag == 0) {
						t = new Teacher(id, pwd, name, gender, birthday, institute, major);
						idLabel = new JLabel("�˺�:" + t.getId());
						nameLabel = new JLabel("����:" + t.getName());
						genderLabel = new JLabel("�Ա�:" + t.getSex());
						birLabel = new JLabel("����:" + t.getBirthday());
						insLabel = new JLabel("ѧԺ:" + t.getInstitute());
						majorLabel = new JLabel("רҵ:" + t.getMajor());
					}else if(flag == 2) {
						ad = new Administrator(id, pwd, name, gender, birthday, institute, major);
						idLabel = new JLabel("�˺�:" + ad.getId());
						nameLabel = new JLabel("����:" + ad.getName());
						genderLabel = new JLabel("�Ա�:" + ad.getSex());
						birLabel = new JLabel("����:" + ad.getBirthday());
						insLabel = new JLabel("ѧԺ:" + ad.getInstitute());
						majorLabel = new JLabel("רҵ:" + ad.getMajor());
					}

				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		stuInfoJPanel.add(idLabel);
		stuInfoJPanel.add(nameLabel);
		stuInfoJPanel.add(genderLabel);
		stuInfoJPanel.add(birLabel);
		stuInfoJPanel.add(insLabel);
		stuInfoJPanel.add(majorLabel);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		setVisible(true);

	}

	public void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			this.dispose();
			setVisible(false);
		}
	}
}
