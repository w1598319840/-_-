package controller;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class LeaveManagement extends JFrame implements ActionListener {
	/**
	 * ��ʦ¼��ѧ�������Ϣ
	 */
	private static final long serialVersionUID = 1L;
	private JTextField idFieldt, startYeart, startMoutht, startDayt, endYeart, endMoutht, endDayt;
	private JTextArea reasonArea;
	private JButton addButton;
	private JPanel panel;
	private JLabel nameLabel, startDateLabel, startYear, startMouth, startDay, endDateLabel, reasonLabel, endYear,
			endMouth, endDay;

	public LeaveManagement() {
		setTitle("������ϵͳ");
		setLocation(600, 300);
		setSize(400, 300);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);

		panel = new JPanel();
		panel.setLayout(null);

		nameLabel = new JLabel("ѧ��id:");
		nameLabel.setBounds(10, 10, 80, 25);
		panel.add(nameLabel);

		idFieldt = new JTextField(20);
		idFieldt.setBounds(100, 10, 200, 25);
		panel.add(idFieldt);

		startDateLabel = new JLabel("��ʼ����:");
		startDateLabel.setBounds(10, 40, 80, 25);
		panel.add(startDateLabel);

		startYear = new JLabel("��");
		startMouth = new JLabel("��");
		startDay = new JLabel("��");

		startYear.setBounds(150, 40, 30, 25);
		startMouth.setBounds(210, 40, 30, 25);
		startDay.setBounds(270, 40, 30, 25);

		panel.add(startDay);
		panel.add(startMouth);
		panel.add(startYear);

		startYeart = new JTextField();
		startMoutht = new JTextField();
		startDayt = new JTextField();

		startYeart.setBounds(100, 40, 50, 25);
		startMoutht.setBounds(170, 40, 40, 25);
		startDayt.setBounds(230, 40, 40, 25);

		panel.add(startYeart);
		panel.add(startMoutht);
		panel.add(startDayt);

		endDateLabel = new JLabel("��������:");
		endDateLabel.setBounds(10, 70, 80, 25);
		panel.add(endDateLabel);

		endYear = new JLabel("��");
		endMouth = new JLabel("��");
		endDay = new JLabel("��");

		endYear.setBounds(150, 70, 30, 25);
		endMouth.setBounds(210, 70, 30, 25);
		endDay.setBounds(270, 70, 30, 25);

		panel.add(endDay);
		panel.add(endMouth);
		panel.add(endYear);

		endYeart = new JTextField();
		endMoutht = new JTextField();
		endDayt = new JTextField();

		endYeart.setBounds(100, 70, 50, 25);
		endMoutht.setBounds(170, 70, 40, 25);
		endDayt.setBounds(230, 70, 40, 25);

		panel.add(endYeart);
		panel.add(endMoutht);
		panel.add(endDayt);

		reasonLabel = new JLabel("���ԭ��:");
		reasonLabel.setBounds(10, 100, 80, 25);
		panel.add(reasonLabel);

		reasonArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(reasonArea);
		scrollPane.setBounds(100, 100, 200, 100);
		panel.add(scrollPane);

		addButton = new JButton("�����ټ�¼");
		addButton.setBounds(100, 210, 150, 25);
		panel.add(addButton);

		addButton.addActionListener(this);

		setContentPane(panel);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addButton) {
			if (idFieldt.getText().equals("") || startYeart.getText().equals("") || startMoutht.getText().equals("")
					|| startDayt.getText().equals("") || endYeart.getText().equals("") || endMoutht.getText().equals("")
					|| endDayt.getText().equals("") || reasonArea.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "���벻��Ϊ�գ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
			} else {
				if (hasStudent(idFieldt.getText()) == 0) {
					JOptionPane.showMessageDialog(null, "��ѧ�������ڣ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
				} else {
					String id = idFieldt.getText();
					String reason = reasonArea.getText();

					String startDate = null, endDate = null;
					try {
						int startDay, startMouth, startYear, endDay, endMouth, endYear;
						startDay = Integer.parseInt(startDayt.getText());
						startMouth = Integer.parseInt(startMoutht.getText());
						startYear = Integer.parseInt(startYeart.getText());
						endDay = Integer.parseInt(endDayt.getText());
						endMouth = Integer.parseInt(endMoutht.getText());
						endYear = Integer.parseInt(endYeart.getText());

						if (!(startMouth >= 1 && startMouth <= 12 && endMouth >= 1 && endMouth <= 12)) {
							JOptionPane.showMessageDialog(null, "������·ݱ�����1~12֮�䣡", "��ʾ",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						if (!(startDay >= 1 && startDay <= 31 && endDay >= 1 && endDay <= 31)) {
							JOptionPane.showMessageDialog(null, "���������������1~31֮�䣡", "��ʾ",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						if(!(startYear >=2000 && endYear >= 2000)) {
							JOptionPane.showMessageDialog(null, "�������ݱ�����2000���Ժ�", "��ʾ",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						startDate = startYear + "." + startMouth + "." + startDay;
						endDate = endYear + "." + endMouth + "." + endDay;
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "��������ڸ�ʽ����", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
						return;
					}

					String leavePath = System.getProperty("user.dir") + "/data/leave_records/" + id + ".txt";
					BufferedWriter bw = null;
					try {
						bw = new BufferedWriter(new FileWriter(leavePath, true));
						String modifiedContent = id + " " + startDate + " " + endDate + " " + reason;
						bw.write(modifiedContent);
						bw.newLine();
					} catch (IOException e1) {
						e1.printStackTrace();
					} finally {
						try {
							bw.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					JOptionPane.showMessageDialog(null, "�����ټ�¼��ӳɹ���", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}

	int hasStudent(String studentId) {
		String studentPath = System.getProperty("user.dir") + "/data/student.txt";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(studentPath));
			String s = null;
			while ((s = br.readLine()) != null) {
				String[] result = s.split(" ");
				if (result[0].equals(studentId)) {
					return 1;// �ҵ����ѧ�������� 1
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
		return 0;
	}
}
