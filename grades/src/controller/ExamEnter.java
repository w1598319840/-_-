package controller;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ExamEnter extends JFrame implements ActionListener {
	/*
	 * ��ʦ��½������Ϣ
	 */
	String idd; // ��ʦ��
	JPanel contain;
	JLabel id;
	JTextField idt, stuIdt, stuNamet, examNamet, examTimet, examAreat;

	String targetFile;

	JButton submit, bn, overview;
	ArrayList<String> modifiedContent = new ArrayList<String>();

	public ExamEnter(String idd) {
		super("�鿴");
		this.idd = idd;
		setSize(300, 300);
		setLocation(600, 400);
		contain = new JPanel();
		contain.setLayout(null);
		add(contain);
		id = new JLabel("�γ̺�");
		idt = new JTextField();
		submit = new JButton("�ύ");
		id.setBounds(38, 50, 75, 35);
		idt.setBounds(80, 50, 150, 35);
		submit.setBounds(102, 125, 70, 30);
		contain.add(id);
		contain.add(idt);
		contain.add(submit);
		submit.addActionListener(this);
		setVisible(true);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {
			if (hasThisCourse(idt.getText()) == 1) {
				enter(); // ���뿼����Ϣ�������
			} else {
				JOptionPane.showMessageDialog(null, "��δ����˿γ̣�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (e.getSource() == bn) {
			if (hasThisStu() == 1) { // ���ÿ�������
				String path = System.getProperty("user.dir") + "/data/exam" + "/" + examNamet.getText() + ".txt";
				ArrayList<String> tempContent = new ArrayList<>(); // ������ʱ�����б�
				String path1 = System.getProperty("user.dir") + "/data/teacher.txt";

				// ����ļ��Ƿ�Ϊ��
				boolean isFileEmpty = false;
				try (BufferedReader br = new BufferedReader(new FileReader(path))) {

					if (br.readLine() == null) {
						isFileEmpty = true;
					}
				} catch (IOException ee) {
					ee.printStackTrace();
				}

				// ����ļ�Ϊ�գ�ֱ��д������
				if (isFileEmpty) {
					try (BufferedWriter writer = new BufferedWriter(new FileWriter(path));
							BufferedReader br = new BufferedReader(new FileReader(path1))) {

						String t = null;
						String[] result = null;
						while ((t = br.readLine()) != null) {// ʹ��readLine��������һ���ļ�һ�ζ�һ��
							result = t.split(" ");
						}
						// д���µ�����
						String newExamInfo = idt.getText() + " " + examNamet.getText() + " " + result[0] + " "
								+ result[2] + " " + stuIdt.getText() + " " + stuNamet.getText() + " "
								+ examNamet.getText() + " " + examTimet.getText() + " " + examAreat.getText();
						writer.write(newExamInfo);
						writer.newLine(); // ��ӻ��з�
						JOptionPane.showMessageDialog(null, "������Ϣ��¼�ɹ���", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
					} catch (IOException e1) {
						System.err.println("Error writing to the file: " + e1.getMessage());
					}
					return; // �˳�����
				}

				// ����ļ���Ϊ�գ��޸����ݲ�����
				try (BufferedReader br = new BufferedReader(new FileReader(path))) {
					String s;
					while ((s = br.readLine()) != null) {
						String[] result = s.split(" ");
						if (result[0].equals(idt.getText())) {
							// �޸��ض�������
							String newExamInfo = idt.getText() + " " + result[1] + " " + result[2] + " " + result[3]
									+ " " + stuIdt.getText() + " " + stuNamet.getText() + " " + examNamet.getText()
									+ " " + examTimet.getText() + " " + examAreat.getText();
							// ���޸ĺ�����ݴ洢����ʱ�б���
							tempContent.add(newExamInfo);
						} else {
							tempContent.add(s); // ��δ�޸ĵ�������ӵ���ʱ�б���
						}
					}
				} catch (IOException ee) {
					ee.printStackTrace();
				}

				// �޸����ݺ󣬽�������д���ļ�
				try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
					for (String line : tempContent) {
						writer.write(line);
						writer.newLine(); // ÿ�к���ӻ��з�
					}
					JOptionPane.showMessageDialog(null, "������Ϣ��¼�ɹ���", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException e1) {
					System.err.println("Error writing to the file: " + e1.getMessage());
				}
			} else {
				JOptionPane.showMessageDialog(null, "�γ̺�Ϊ" + idt.getText() + "�޴�ѧ��", "��ʾ",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}else if(e.getSource() == overview) {
			allStudent(idt.getText());
		}
	}

	int hasThisStu() {

		@SuppressWarnings("unused")
		String stuId = stuIdt.getText();

		String path = System.getProperty("user.dir") + "/data/course_student";
		// String path = "D://test//course_student";

		List<String> files = new ArrayList<String>(); // Ŀ¼�������ļ�
		File file = new File(path);
		File[] tempList = file.listFiles();

		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile()) {
				files.add(tempList[i].toString());
				// �ļ�����������·��
				// String fileName = tempList[i].getName();
			}
			if (tempList[i].isDirectory()) {
				// ����Ͳ��ݹ��ˣ�
			}
		}

		try {
			for (int i = 0; i < files.size(); i++) {
				try (BufferedReader br = new BufferedReader(new FileReader(files.get(i)))) {
					String s = null;
					while ((s = br.readLine()) != null) {// ʹ��readLine������һ�ζ�һ��
						String[] result = s.split(" ");
						if (result[0].equals(idt.getText()) && result[2].equals(stuIdt.getText())) {
							return 1;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	void enter() {
		JFrame fm = new JFrame("��¼������Ϣ");
		fm.setSize(300, 460);
		JPanel contain = new JPanel();
		fm.setLocation(600, 400);
		contain.setLayout(null);
		bn = new JButton("�ύ");
		overview = new JButton("�鿴���ſγ�����ѧ��");
		JLabel stuId = new JLabel("ѧ��");
		JLabel examName = new JLabel("��Ŀ");
		JLabel examTime = new JLabel("ʱ��");
		JLabel examArea = new JLabel("�ص�");
		JLabel stuName = new JLabel("����");

		stuIdt = new JTextField();
		examNamet = new JTextField();
		examTimet = new JTextField();
		examAreat = new JTextField();
		stuNamet = new JTextField();

		stuId.setBounds(38, 50, 75, 35);
		stuIdt.setBounds(80, 50, 150, 35);

		examName.setBounds(38, 110, 75, 35);
		examNamet.setBounds(80, 110, 150, 35);

		examTime.setBounds(38, 170, 75, 35);
		examTimet.setBounds(80, 170, 150, 35);

		examArea.setBounds(38, 230, 75, 35);
		examAreat.setBounds(80, 230, 150, 35);

		stuName.setBounds(38, 290, 75, 35);
		stuNamet.setBounds(80, 290, 150, 35);

		bn.setBounds(100, 380, 70, 30);
		overview.setBounds(60, 340, 160, 30);
		contain.add(stuId);
		contain.add(stuIdt);
		contain.add(examName);
		contain.add(examNamet);
		contain.add(examTime);
		contain.add(examTimet);
		contain.add(examArea);
		contain.add(examAreat);
		contain.add(stuName);
		contain.add(stuNamet);
		contain.add(bn);
		contain.add(overview);
		fm.add(contain);
		bn.addActionListener(this);
		overview.addActionListener(this);

		fm.setVisible(true);

	}

	int hasThisCourse(String idd) {

		String file = System.getProperty("user.dir") + "/data/course.txt";
		// String file = "D://test//course.txt";
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String s = null;
			while ((s = br.readLine()) != null) {// ʹ��readLine������һ�ζ�һ��
				String[] result = s.split(" ");
				if (result[0].equals(idd) && result[4].equals(this.idd)) {
					return 1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0; // ���û���ҵ�ƥ�������0
	}

	void allStudent(String courseId) {// ��ȡ���ſγ̵�����ѧ����Ϣ
		JFrame newFrame = new JFrame("���γ�����ѧ��");
		newFrame.setBounds(200, 300, 460, 300);
		JPanel contain = new JPanel();
		contain.setLayout(null);
		newFrame.add(contain);
		JTextArea list = new JTextArea();
		list.setBounds(20, 20, 410, 120);
		list.setEditable(false);
		JScrollPane listPane = new JScrollPane();
		listPane.setBounds(20, 20, 410, 120);
		listPane.setViewportView(list);
		listPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contain.add(listPane);
		list.append("�γ̱��\t�γ�����\t\tѧ��ѧ��\tѧ������\n");

		String path = System.getProperty("user.dir") + "/data/course_student";
		List<String> files = new ArrayList<String>();// ��ȡĿ¼�������ļ�
		File file = new File(path);// ��course_student�ļ���
		File[] tempList = file.listFiles();// ����������и�Ŀ¼�������ļ�
		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile()) {
				files.add(tempList[i].toString());
				// �ļ�����������·��
			}
			if (tempList[i].isDirectory()) {
				// ����Ͳ��ݹ��ˣ�
			}
		} // forѭ����files��̬�����оͰ��������е��ļ�·��
		BufferedReader br = null;
		try {
			for (int i = 0; i < files.size(); i++) {
				br = new BufferedReader(new FileReader(files.get(i)));
				String s = null;
				while ((s = br.readLine()) != null) {
					String[] result = s.split(" ");
					if (result[0].equals(courseId)) {// �ҵ������ſγ�
						// ������ѧ��չʾ����
						list.append("      " + courseId + "\t");
						int stringLength = 0;
						for (int i1 = 0; i1 < result[1].length(); i1++) {
							char temp = result[1].charAt(i1);
							if ((temp + "").getBytes().length == 1) {
								stringLength++;
							} else {
								stringLength += 2;
							}
						}
//						System.out.println(stringLength);
						if (stringLength >= 12) {
							list.append(result[1] + " \t");
						} else {
							list.append(result[1] + "\t\t");
						}
						list.append(result[2] + "\t");
						list.append(result[3] + "\n");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		newFrame.setVisible(true);
	}

	public void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			this.dispose();
			setVisible(false);
		}
	}
}