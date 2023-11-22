package controller;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DeleteCourse extends JFrame implements ActionListener {
	/**
	 * ��ʦɾ���γ�
	 */
	private static final long serialVersionUID = 1L;
	String id;
	JPanel contain;
	JLabel allCourse, deleteCourseId;
	JTextArea list;
	JTextField deleteCourseIdt;
	JButton submit;

	public DeleteCourse(String id) {
		super("ɾ���γ�");
		this.id = id;
		setBounds(600, 200, 660, 450);
		contain = new JPanel();
		contain.setLayout(null);
		add(contain);
		allCourse = new JLabel("�������пγ�:");
		allCourse.setBounds(10, 10, 200, 20);
		contain.add(allCourse);
		list = new JTextArea();
		list.setBounds(30, 40, 580, 200);
		list.setEditable(false);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 40, 580, 200);
		scrollPane.setViewportView(list);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contain.add(scrollPane);
		list.append("�γ̱��\t�γ���\t\tѧ��\tѧʱ\t��ʦ\t��ѡ����/�������\n");
		String courseId;
		String courseName;
		String credit = null;
		String classHour = null;
		String maxNum = null;
		String currentNum = null;
		String teacherName = null;
		String teacherId = null;
		String path = System.getProperty("user.dir") + "/data/course.txt";
		String s = null;
		BufferedReader br = null;

		try {

			br = new BufferedReader(new FileReader(path));
			while ((s = br.readLine()) != null) {// ʹ��readLine������һ�ζ�һ��
				String[] result = s.split(" ");
				courseId = result[0];
				courseName = result[1];
				credit = result[2];
				classHour = result[3];
				teacherId = result[4];
				teacherName = result[5];
				maxNum = result[6];
				currentNum = result[7];

				if (teacherId.equals(id)) {
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
					list.append("            " + currentNum + "/");
					list.append(maxNum + "\n");
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

		deleteCourseId = new JLabel("��Ҫɾ���Ŀγ̱�ţ�");
		deleteCourseIdt = new JTextField();

		deleteCourseId.setBounds(80, 280, 160, 35);
		deleteCourseIdt.setBounds(220, 280, 130, 35);

		contain.add(deleteCourseId);
		contain.add(deleteCourseIdt);

		submit = new JButton("�ύ");
		submit.setBounds(250, 350, 100, 30);
		contain.add(submit);
		submit.addActionListener(this);

		setVisible(true);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {
			String courseId = deleteCourseIdt.getText();
			if (courseId.equals("")) {
				JOptionPane.showMessageDialog(null, "ɾ���Ŀγ̵ı�Ų���Ϊ��!", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
			} else if (hasThisCourse(courseId, id) == 0) {
				JOptionPane.showMessageDialog(null, "��δ�������ſγ�!", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
			} else if (hasThisCourse(courseId, id) == 1) {
				// ɾ����Ӧ���ļ�
				BufferedReader br = null;
				String courseName = null;
				String coursePath = System.getProperty("user.dir") + "/data/course.txt";
				try {
					br = new BufferedReader(new FileReader(coursePath));
					String s = null;
					while ((s = br.readLine()) != null) {// ʹ��readLine������һ�ζ�һ��
						String[] result = s.split(" ");
						if (result[0].equals(courseId)) {
							courseName = result[1];
						}
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				} finally {
					try {
						br.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				File course_studentPath = new File(
						System.getProperty("user.dir") + "/data/course_student/" + courseName + "_student.txt");
				File examPath = new File(System.getProperty("user.dir") + "/data/exam/" + courseName + ".txt");
				File gradePath = new File(System.getProperty("user.dir") + "/data/grade/" + courseName + ".txt");
				course_studentPath.delete();
				examPath.delete();
				gradePath.delete();
				// ����Ҫɾ��course.txt�и��ſγ̵���Ϣ
				ArrayList<String> modifiedContent = new ArrayList<String>();
				BufferedWriter bw = null;// ��������������д��
				try {
					br = new BufferedReader(new FileReader(coursePath));
					String s = null;
					while ((s = br.readLine()) != null) {
						String[] result = s.split(" ");// ��ԭ������Ϣ������
						if (result[0].equals(courseId)) {
							continue;
						}
						String s1 = "";
						for (int i = 0; i < result.length - 1; i++) {
							s1 = s1 + result[i];
							s1 = s1 + " ";
						}
						s1 = s1 + result[result.length - 1];
						modifiedContent.add(s1);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				} finally {
					try {
						br.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				try {
					bw = new BufferedWriter(new FileWriter(coursePath));
					for (int i = 0; i < modifiedContent.size(); i++) {
						bw.write(modifiedContent.get(i));
						bw.newLine();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				} finally {
					try {
						bw.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				JOptionPane.showMessageDialog(null, "ɾ���ɹ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	int hasThisCourse(String courseId, String teacherId) {

		String file = System.getProperty("user.dir") + "/data/course.txt";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String s = null;
			while ((s = br.readLine()) != null) {// ʹ��readLine������һ�ζ�һ��
				String[] result = s.split(" ");
				if (result[0].equals(courseId) && result[4].equals(teacherId)) {
					return 1;
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
		return 0;
	}

}
