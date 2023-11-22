package view;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.CheckLeave;
import controller.ChooseCourse;
import controller.CourseView;
import controller.EditInfo;
import controller.ExamInfo;
import controller.GradeInfo;
import controller.Info;
import controller.EditScholarship;

@SuppressWarnings("serial")
public class StudentsPanel extends JFrame implements ActionListener {
	/*
	 * ѧ����½�����������
	 */
	JPanel contain;
	String id;
	JButton infoButton, gradeButton, courseButton, editButton, courseChooseButton, examButton, checkLeaveButton,
			checkScholarshipButton, applyScholarShipButton;

	public StudentsPanel(String id) {
		super("ѧ��");
		this.id = id;
		setLocation(300, 200);
		setSize(300, 450);
		contain = new JPanel();
		contain.setLayout(null);
		add(contain);
		infoButton = new JButton("��Ϣ��ѯ");
		gradeButton = new JButton("�ɼ���ѯ");
		courseButton = new JButton("�γ̲�ѯ");
		editButton = new JButton("�޸���Ϣ");
		courseChooseButton = new JButton("ѡ/�˿γ�");
		examButton = new JButton("������Ϣ��ѯ");
		checkLeaveButton = new JButton("�����ٲ�ѯ");
		applyScholarShipButton = new JButton("���뽱ѧ��");
		checkScholarshipButton = new JButton("�ҵĽ�ѧ��");

		infoButton.setBounds(70, 40, 140, 30);
		gradeButton.setBounds(70, 80, 140, 30);
		courseButton.setBounds(70, 120, 140, 30);
		editButton.setBounds(70, 160, 140, 30);
		courseChooseButton.setBounds(70, 200, 140, 30);
		examButton.setBounds(70, 240, 140, 30);
		checkLeaveButton.setBounds(70, 280, 140, 30);
		applyScholarShipButton.setBounds(70, 320, 140, 30);
		checkScholarshipButton.setBounds(70, 360, 140, 30);

		contain.add(infoButton);
		infoButton.addActionListener(this);
		contain.add(gradeButton);
		gradeButton.addActionListener(this);
		contain.add(courseButton);
		courseButton.addActionListener(this);
		contain.add(editButton);
		editButton.addActionListener(this);
		contain.add(courseChooseButton);
		courseChooseButton.addActionListener(this);
		contain.add(examButton);
		examButton.addActionListener(this);
		contain.add(checkLeaveButton);
		checkLeaveButton.addActionListener(this);
		contain.add(applyScholarShipButton);
		applyScholarShipButton.addActionListener(this);
		contain.add(checkScholarshipButton);
		checkScholarshipButton.addActionListener(this);

		setVisible(true);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == infoButton) {
			new Info(id, 1);
		}
		if (e.getSource() == gradeButton) {
			new GradeInfo(id);
		}
		if (e.getSource() == courseButton) {
			new CourseView(id, 0);
		}
		if (e.getSource() == editButton) {
			new EditInfo(id, 0);
		}
		if (e.getSource() == courseChooseButton) {
			new ChooseCourse(id);
		}
		if (e.getSource() == examButton) {
			new ExamInfo(id);
		}
		if (e.getSource() == checkLeaveButton) {
			new CheckLeave(id);
		}
		if(e.getSource() == applyScholarShipButton) {
			new EditScholarship(id);
		}
		if (e.getSource() == checkScholarshipButton) {
			new ScholarshipView(id, 1);
		}
	}

	public void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			this.dispose();
			setVisible(false);
			System.exit(0);
		}
	}
}
