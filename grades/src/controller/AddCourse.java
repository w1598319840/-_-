package controller;

import java.awt.AWTEvent;
import java.awt.HeadlessException;
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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Course;

@SuppressWarnings("serial")
public class AddCourse extends JFrame implements ActionListener {
	/*
	 * ��ʦ���ӿγ�
	 */
	JPanel contain;
	JButton submit;
	JLabel id, name, credit, classH, teacherId, teacherName, maxNum;
	JTextField idt, namet, creditt, classHt, teacherIdt, teacherNamet, maxNumt;

	public AddCourse() {
		super("���ӿγ�");
		setSize(450, 430);
		setLocation(500, 300);
		contain = new JPanel();
		contain.setLayout(null);
		id = new JLabel("�γ̺�");
		name = new JLabel("�γ���");
		credit = new JLabel("ѧ��");
		classH = new JLabel("ѧʱ");
		teacherId = new JLabel("��ʦ��");
		teacherName = new JLabel("��ʦ");
		maxNum = new JLabel("�������");

		submit = new JButton("�ύ");
		idt = new JTextField();
		namet = new JTextField();
		creditt = new JTextField();
		classHt = new JTextField();
		teacherIdt = new JTextField();
		teacherNamet = new JTextField();
		maxNumt = new JTextField();

		id.setBounds(45, 35, 75, 35);// �γ̺�
		idt.setBounds(90, 35, 150, 35);// �ı���
		name.setBounds(45, 90, 75, 35);// �γ���
		namet.setBounds(90, 90, 150, 35);
		credit.setBounds(45, 135, 75, 35);// ѧ��
		creditt.setBounds(80, 135, 150, 35);
		classH.setBounds(45, 180, 75, 35);// ѧʱ
		classHt.setBounds(80, 180, 150, 35);
		teacherName.setBounds(45, 225, 75, 35);// ��ʦ
		teacherNamet.setBounds(80, 225, 150, 35);
		maxNum.setBounds(45, 270, 75, 35);// �������
		maxNumt.setBounds(110, 270, 150, 35);
		teacherId.setBounds(45, 315, 75, 35);// ��ʦ��
		teacherIdt.setBounds(90, 315, 75, 35);

		submit.setBounds(180, 350, 70, 30);// �ύ

		contain.add(id);
		contain.add(idt);
		contain.add(name);
		contain.add(namet);
		contain.add(credit);
		contain.add(creditt);
		contain.add(classH);
		contain.add(classHt);
		contain.add(teacherId);
		contain.add(teacherIdt);
		contain.add(teacherName);
		contain.add(teacherNamet);
		contain.add(maxNum);
		contain.add(maxNumt);
		contain.add(submit);
		submit.addActionListener(this);
		add(contain);
		setVisible(true);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	}

	public int hasCourse(String id) { // ��ʦ����ǰ���γ��Ƿ��Ѿ�����

		String file = System.getProperty("user.dir") + "/data/course.txt";
		try {
			BufferedReader br = new BufferedReader(new FileReader(file)); // ����һ��BufferedReader������ȡ�ļ�
			String s = null;
			while ((s = br.readLine()) != null) {// ʹ��readLine������һ�ζ�һ��
				String[] result = s.split(" ");
				if (result[0].equals(id)) {
					return 1;

				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {
			if ((idt.getText().equals("")) || (namet.getText().equals("")) || (creditt.getText().equals(""))
					|| (classHt.getText().equals("")) || teacherIdt.getText().equals("")
					|| teacherNamet.getText().equals("") || maxNumt.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "��Ϣ����Ϊ�գ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
			} else {
				if ((hasCourse(idt.getText())) == 1) {
					JOptionPane.showMessageDialog(null, "�˿γ��Ѿ����ڣ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
				} else {
					String file = System.getProperty("user.dir") + "/data/course.txt";

					ArrayList<String> modifiedContent = new ArrayList<String>();
					// StringBuilder result = new StringBuilder();
					try {
						BufferedReader br = new BufferedReader(new FileReader(file));
						String s = null;
						while ((s = br.readLine()) != null) { // �Ƚ�ԭ�����ڵ���Ϣ�洢����
							String[] result = s.split(" ");

							String s1 = "";
							for (int i = 0; i < result.length - 1; i++) {
								s1 = s1 + result[i];
								s1 = s1 + " ";
							}
							s1 = s1 + result[result.length - 1];
							// System.out.println(s1);
							modifiedContent.add(s1);
						}
						br.close();

					} catch (Exception e1) {
						e1.printStackTrace();
					}

					Course course = new Course(idt.getText(), namet.getText(), teacherIdt.getText(),
							teacherNamet.getText(), creditt.getText(), classHt.getText(), maxNumt.getText());

					modifiedContent.add(course.getCourseId() + " " + course.getCourseName() + " " + course.getCredit()
							+ " " + course.getHour() + " " + course.getTeacherId() + " " + course.getTeacherName() + " "
							+ course.getMaxNum() + " " + course.getCurrentNum());

					try {
						FileWriter fw = new FileWriter(file);
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

					// ������Ӷ�Ӧ�γ��ļ��⣬����Ҫ��ӿγ̳ɼ��ļ����γ�ѧ���ļ��Լ���Ӧ�γ̵Ŀ�����Ϣ�ļ�
					File gradeFile = new File(
							System.getProperty("user.dir") + "/data/grade/" + course.getCourseName() + ".txt");
					File studentFile = new File(System.getProperty("user.dir") + "/data/course_student/"
							+ course.getCourseName() + "_student.txt");
					File examFile = new File(
							System.getProperty("user.dir") + "/data/exam/" + course.getCourseName() + ".txt");
					try {
						gradeFile.createNewFile();
						studentFile.createNewFile();
						examFile.createNewFile();
						JOptionPane.showMessageDialog(null, "��ӳɹ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
					} catch (HeadlessException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}

	public void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			this.dispose();
			setVisible(false);
		}
	}
}
