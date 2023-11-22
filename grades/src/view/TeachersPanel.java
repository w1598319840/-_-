package view;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.AddCourse;
import controller.CourseView;
import controller.DeleteCourse;
import controller.EditInfo;
import controller.ExamEnter;
import controller.GradeEnter;
import controller.Info;
import controller.LeaveManagement;

@SuppressWarnings("serial")
public class TeachersPanel extends JFrame implements ActionListener {
	/*
	 * ��ʦ��½�����������
	 */

	String idd;
	JPanel contain;
	JButton infoButton, gradeButton, courseButton, editButton, addCourseButton, deleteCourseButton, sortGrade,
			examButton, leaveManageButton, scholarshipButton;

	public TeachersPanel(String idd) {
		super("��ʦ");
		this.idd = idd;
		setLocation(300, 200);
		setSize(300, 490);
		contain = new JPanel();
		contain.setLayout(null);
		add(contain);
		infoButton = new JButton("��Ϣ��ѯ");
		gradeButton = new JButton("�ɼ���¼");
		courseButton = new JButton("ȫ���γ�");
		editButton = new JButton("�޸���Ϣ");
		addCourseButton = new JButton("����");
		deleteCourseButton = new JButton("ɾ���γ�");
		sortGrade = new JButton("�ɼ�ͳ��");
		examButton = new JButton("���ÿ�����Ϣ");
		leaveManageButton = new JButton("¼�������Ϣ");
		scholarshipButton = new JButton("�鿴��ѧ������");

		infoButton.setBounds(70, 30, 140, 30);
		editButton.setBounds(70, 70, 140, 30);
		addCourseButton.setBounds(70, 110, 140, 30);
		deleteCourseButton.setBounds(70, 150, 140, 30);
		courseButton.setBounds(70, 190, 140, 30);
		gradeButton.setBounds(70, 230, 140, 30);
		sortGrade.setBounds(70, 270, 140, 30);
		examButton.setBounds(70, 310, 140, 30);
		leaveManageButton.setBounds(70, 350, 140, 30);
		scholarshipButton.setBounds(70, 390, 140, 30);

		contain.add(infoButton);
		infoButton.addActionListener(this);
		contain.add(gradeButton);
		gradeButton.addActionListener(this);
		contain.add(addCourseButton);
		addCourseButton.addActionListener(this);
		contain.add(deleteCourseButton);
		deleteCourseButton.addActionListener(this);
		contain.add(courseButton);
		courseButton.addActionListener(this);
		contain.add(editButton);
		editButton.addActionListener(this);
		contain.add(sortGrade);
		sortGrade.addActionListener(this);
		contain.add(examButton);
		examButton.addActionListener(this);
		contain.add(leaveManageButton);
		leaveManageButton.addActionListener(this);
		contain.add(scholarshipButton);
		scholarshipButton.addActionListener(this);

		setVisible(true);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == infoButton) {
			new Info(idd, 0);
		}
		if (e.getSource() == gradeButton) {
			new GradeEnter(idd);
		}
		if (e.getSource() == courseButton) {
			new CourseView(idd, 1);
		}
		if (e.getSource() == editButton) {
			new EditInfo(idd, 1);
		}
		if (e.getSource() == addCourseButton) {
			new AddCourse();
		}
		if (e.getSource() == sortGrade) {
			new SortGradeFrame();
		}
		if (e.getSource() == examButton) {
			new ExamEnter(idd);
		}
		if (e.getSource() == deleteCourseButton) {
			new DeleteCourse(idd);
		}
		if (e.getSource() == leaveManageButton) {
			new LeaveManagement();
		}
		if(e.getSource() == scholarshipButton) {
			new ScholarshipView(idd, 0);
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
