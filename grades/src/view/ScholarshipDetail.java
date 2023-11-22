package view;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import model.Scholarship;

public class ScholarshipDetail extends JFrame implements ActionListener {
	/**
	 * ѧ�����뽱ѧ�����ϸ����
	 */
	private static final long serialVersionUID = 1L;

	String id, name, inst, major, type, reason;
	JPanel contain;
	JLabel idLabel, nameLabel, instLabel, majorLabel, typeLabel, reasonLabel;
	JTextArea reasonArea;
	JScrollPane scrollPane;
	JButton agree, reject;

	Scholarship stu;

	public ScholarshipDetail(String id,String type) {
		super("��ϸ��Ϣ����");
		this.id = id;
		this.type = type;
		contain = new JPanel();
		contain.setLayout(null);
		add(contain);
		setSize(400, 450);
		setLocation(600, 200);

		String path = System.getProperty("user.dir") + "/data/scholarship/scholarship.txt";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path));
			String s = null;
			while ((s = br.readLine()) != null) {
				String[] result = s.split(" ");
				if (result[0].equals(this.id)&& result[4].equals(this.type)) {
					name = result[1];
					inst = result[2];
					major = result[3];
					type = result[4];
					reason = result[5];
					stu = new Scholarship(this.id, name, inst, major, type, reason);
					idLabel = new JLabel("ѧ�ţ�" + stu.getId());
					nameLabel = new JLabel("������" + stu.getName());
					instLabel = new JLabel("ѧԺ��" + stu.getInst());
					majorLabel = new JLabel("רҵ��" + stu.getMajor());
					typeLabel = new JLabel("���뽱�" + stu.getType());
					reasonLabel = new JLabel("ԭ��");
					reasonArea = new JTextArea(stu.getReason());
					reasonArea.setLineWrap(true);
					reasonArea.setWrapStyleWord(true);
					reasonArea.setEditable(false);
					scrollPane = new JScrollPane();
					scrollPane.setBounds(10, 140, 365, 200);
					scrollPane.setViewportView(reasonArea);
					agree = new JButton("ͬ��");
					reject = new JButton("�ܾ�");

					idLabel.setBounds(10, 10, 200, 30);
					nameLabel.setBounds(10, 30, 200, 30);
					instLabel.setBounds(10, 50, 200, 30);
					majorLabel.setBounds(10, 70, 200, 30);
					typeLabel.setBounds(10, 90, 200, 30);
					reasonLabel.setBounds(10, 110, 200, 30);
					reasonArea.setBounds(10, 140, 365, 200);
					agree.setBounds(70, 360, 100, 30);
					reject.setBounds(200, 360, 100, 30);

					agree.addActionListener(this);
					reject.addActionListener(this);
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
		contain.add(idLabel);
		contain.add(nameLabel);
		contain.add(instLabel);
		contain.add(majorLabel);
		contain.add(typeLabel);
		contain.add(reasonLabel);
		contain.add(scrollPane);
		contain.add(agree);
		contain.add(reject);

		setVisible(true);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	}

	public void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			this.dispose();
			setVisible(false);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int flag = 0;// 1 ��ͬ�� 2 �Ǿܾ�
		if (e.getSource() == agree) {
			flag = 1;
		} else if (e.getSource() == reject) {
			flag = 2;
		}
		String path = System.getProperty("user.dir") + "/data/scholarship/scholarship.txt";
		ArrayList<String> modifiedContent = new ArrayList<String>();
		BufferedReader br = null;// �����ٴζ���γ̵����ݣ������Ϊ���޸����е�
		BufferedWriter bw = null;// ��������������д��
		try {
			br = new BufferedReader(new FileReader(path));
			String s = null;
			while ((s = br.readLine()) != null) {
				String[] result = s.split(" ");// ��ԭ������Ϣ������
				if (result[0].equals(stu.getId())&& result[4].equals(stu.getType())) {
					if (flag == 1) {
						result[6] = "agree";
					} else if(flag == 2){
						result[6] = "reject";
					}
				} // �ҵ������ſΣ��Ͱ����ſε�currentNum + 1
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
			bw = new BufferedWriter(new FileWriter(path));
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
		JOptionPane.showMessageDialog(null, "������", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
	}
}
