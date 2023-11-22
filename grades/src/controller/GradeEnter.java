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
public class GradeEnter extends JFrame implements ActionListener {
	/*
	 * ��ʦ��½�γ���Ϣ
	 */
	String idd; // ��ʦ��
	JPanel contain;
	JLabel id, allCourse;
	JTextField idt, stuIdt, stuGradet, stuNamet;
	JTextArea list;
	JScrollPane listPane;

	String targetFile;

	JButton submit, bn, overview;
	// ArrayList<String> modifiedContent = new ArrayList<String>();

	public GradeEnter(String idd) {
		super("�鿴");
		this.idd = idd;
		setSize(590, 350);
		setLocation(600, 280);
		contain = new JPanel();
		contain.setLayout(null);
		add(contain);
		allCourse = new JLabel("���������ڿεĿγ̣�");
		allCourse.setBounds(10, 10, 300, 20);
		contain.add(allCourse);
		list = new JTextArea();
		list.setBounds(10, 40, 560, 130);
		list.setEditable(false);
		listPane = new JScrollPane();
		listPane.setBounds(10, 40, 560, 130);
		listPane.setViewportView(list);
		listPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contain.add(listPane);
		list.append("�γ̱��\t�γ���\t\tѧ��\tѧʱ\t��ʦ\t�ÿγ�ѧ������\n");

		String courseId, courseName, credit, classHour, teacherName, currentNum, teacherId;
		String coursePath = System.getProperty("user.dir") + "/data/course.txt";
		String s = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(coursePath));
			while ((s = br.readLine()) != null) {
				String[] result = s.split(" ");
				courseId = result[0];
				courseName = result[1];
				credit = result[2];
				classHour = result[3];
				teacherId = result[4];
				teacherName = result[5];
				currentNum = result[7];
				if (teacherId.equals(idd)) {
					list.append("      " + courseId + "\t");
					int stringLength = 0;
					for (int i1 = 0; i1 < courseName.length(); i1++) {
						char temp = courseName.charAt(i1);
						if ((temp + "").getBytes().length == 1) {
							stringLength++;
						} else {
							stringLength += 2;
						}
					}
//					System.out.println(stringLength);
					if (stringLength >= 12) {
						list.append(courseName + " \t");
					} else {
						list.append(courseName + "\t\t");
					}
					list.append(credit + "\t");
					list.append(classHour + "\t");
					list.append(teacherName + "\t");
					list.append("            " + currentNum + "\n");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		id = new JLabel("��Ҫ��¼�ɼ��Ŀγ̺ţ�");
		idt = new JTextField();
		submit = new JButton("�ύ");
		id.setBounds(60, 200, 140, 35);
		idt.setBounds(200, 200, 150, 35);
		submit.setBounds(240, 250, 70, 30);
		contain.add(id);
		contain.add(idt);
		contain.add(submit);
		submit.addActionListener(this);
		setVisible(true);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {
			if (idt.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "����Ϊ�գ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
			} else {
				if (hasThisCourse(idt.getText()) == 1) {
					enter(); // ����ɼ��������
				} else {
					JOptionPane.showMessageDialog(null, "��δ����˿γ̣�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		} else if (e.getSource() == bn) {
			if (hasThisStu() == 1) { // ��½�ɼ�
				try {
					if (Integer.parseInt(stuGradet.getText()) > 100 || Integer.parseInt(stuGradet.getText()) < 0) {
						JOptionPane.showMessageDialog(null, "�ɼ����뷶ΧӦΪ0~100��", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "�ɼ����뷶ΧӦΪ0~100��", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				ArrayList<String> modifiedContent = new ArrayList<String>();
				String path = System.getProperty("user.dir") + "/data/grade";
				// String path = "D://test//grade";

				// �Ҷ�Ӧ�γ̳ɼ��ļ�
				List<String> files = new ArrayList<String>(); // �γ̳ɼ�Ŀ¼�����п�Ŀ�ɼ��ļ�
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
					for (int i = 0; i < files.size(); i++) { // ���������ļ�
						BufferedReader br = new BufferedReader(new FileReader(files.get(i)));
						String s = null;
						String[] result = null;
						while ((s = br.readLine()) != null) {// ʹ��readLine��������һ���ļ�һ�ζ�һ��
							result = s.split(" ");
							if (result[0].equals(idt.getText())) { // ��ʼ��д�ɼ��ļ�
								targetFile = files.get(i);

								// ��ԭ���Ĳ������ѧ���������ȸ���
								if (!(result[4].equals(stuIdt.getText()))) {
									String s1 = "";
									for (int j = 0; j < result.length - 1; j++) {
										s1 = s1 + result[j];
										s1 = s1 + " ";
									}
									s1 = s1 + result[result.length - 1];

									modifiedContent.add(s1);
								}
							}
						} // ����һ���ɼ��ļ�
						if (result != null && result[0].equals(idt.getText())) {
							String gradeInfo = idt.getText();
							gradeInfo = gradeInfo + " ";
							gradeInfo = gradeInfo + result[1];
							gradeInfo = gradeInfo + " ";
							gradeInfo = gradeInfo + result[2];
							gradeInfo = gradeInfo + " ";
							gradeInfo = gradeInfo + result[3];
							gradeInfo = gradeInfo + " ";
							gradeInfo = gradeInfo + stuIdt.getText();
							gradeInfo = gradeInfo + " ";
							gradeInfo = gradeInfo + stuNamet.getText();
							gradeInfo = gradeInfo + " ";
							gradeInfo = gradeInfo + stuGradet.getText();
							modifiedContent.add(gradeInfo);
						}

						br.close();
					}
				} catch (Exception ee) {
					ee.printStackTrace();
				}

				try {
					FileWriter fw = new FileWriter(targetFile);
					BufferedWriter bw = new BufferedWriter(fw);

					for (int i = 0; i < modifiedContent.size(); i++) {
						bw.write(modifiedContent.get(i));
						bw.newLine();
					}

					bw.close();
					fw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				JOptionPane.showMessageDialog(null, "�ɼ���¼�ɹ���", "��ʾ", JOptionPane.INFORMATION_MESSAGE);

			} else if (hasThisStu() == 0) {
				JOptionPane.showMessageDialog(null, "�γ̺�Ϊ" + idt.getText() + "�޴�ѧ��", "��ʾ",
						JOptionPane.INFORMATION_MESSAGE);
			} else if (hasThisStu() == 2) {
				JOptionPane.showMessageDialog(null, "��ǰѧ����������ѧ�Ų�ƥ�䣬���������룡", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (e.getSource() == overview) {
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
				BufferedReader br = new BufferedReader(new FileReader(files.get(i)));
				String s = null;
				while ((s = br.readLine()) != null) {// ʹ��readLine������һ�ζ�һ��
					String[] result = s.split(" ");
					if (result[0].equals(idt.getText()) && result[2].equals(stuIdt.getText())) {
						if (result[3].equals(stuNamet.getText())) {
							return 1;
						} else {
							return 2;
						}
					}
				}
				br.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	void enter() {
		JFrame fm = new JFrame("��¼�ɼ�");
		fm.setSize(300, 340);
		JPanel contain = new JPanel();
		fm.setLocation(600, 400);
		contain.setLayout(null);
		overview = new JButton("�鿴���γ�����ѧ��");
		overview.setBounds(65, 220, 150, 30);
		contain.add(overview);
		overview.addActionListener(this);

		bn = new JButton("�ύ");
		JLabel stuId = new JLabel("ѧ��");
		JLabel stuGrade = new JLabel("�ɼ�");
		JLabel stuName = new JLabel("����");

		stuIdt = new JTextField();
		stuGradet = new JTextField();
		stuNamet = new JTextField();

		stuId.setBounds(38, 50, 75, 35);
		stuIdt.setBounds(80, 50, 150, 35);

		stuGrade.setBounds(38, 110, 75, 35);
		stuGradet.setBounds(80, 110, 150, 35);

		stuName.setBounds(38, 170, 75, 35);
		stuNamet.setBounds(80, 170, 150, 35);

		bn.setBounds(100, 260, 70, 30);
		contain.add(stuId);
		contain.add(stuIdt);
		contain.add(stuGrade);
		contain.add(stuGradet);
		contain.add(stuName);
		contain.add(stuNamet);
		contain.add(bn);
		fm.add(contain);
		bn.addActionListener(this);

		fm.setVisible(true);

	}

	int hasThisCourse(String idd) {

		String file = System.getProperty("user.dir") + "/data/course.txt";
		// String file = "D://test//course.txt";
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String s = null;
			while ((s = br.readLine()) != null) {// ʹ��readLine������һ�ζ�һ��
				String[] result = s.split(" ");
				if (result[0].equals(idd) && result[4].equals(this.idd)) {
					return 1;
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			this.dispose();
			setVisible(false);
		}
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
}
