package controller;

import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ExamInfo extends JFrame { 
	/**
	 * ѧ������ѧ�Ų�ѯ������Ϣ
	 */
	private static final long serialVersionUID = 1L;
	JPanel contain;
	JTextArea list;
	String id;

	String courseid;
	String coursename;
	String teacherid;
	String teachername;
	String studentid;
	String studentname;
	String Examname;
	String Examtime;
	String Examarea;
	
	

	public ExamInfo(String id) {
		super("����");
		this.id = id;
		setSize(760, 400);
		contain = new JPanel();
		setLocation(600, 400);
		list = new JTextArea();
		list.setEditable(false);
		contain.add(list);
		
		list.append("�γ̺�" + "\t");
		list.append("�γ�����" + "\t\t");
		list.append("��ʦ��" + "\t");
		list.append("��ʦ����" + "\t");
		list.append("ѧ����" + "\t");
		list.append("ѧ������" + "\t");
		list.append("���Կ�Ŀ" + "\t");
		list.append("����ʱ��" + "\t");
		list.append("���Եص�" + "\n");

		// String path = "D://test//grade";
		String path = System.getProperty("user.dir")+"/data/exam";

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
				BufferedReader br = new BufferedReader(new FileReader(
						files.get(i)));
				String s = null;
				while ((s = br.readLine()) != null) {// ʹ��readLine������һ�ζ�һ��
					String[] result = s.split(" ");
					if (result[4].equals(id)) { // ѧ��ѧ�����ʱ
						courseid = result[0];
						coursename = result[1];
						teacherid = result[2];
						teachername = result[3];
						studentid = result[4];
						studentname = result[5];
						Examname = result[6];
						Examtime = result[7];
						Examarea = result[8];

						list.append("    " + courseid + "\t");
						int stringLength = 0;
						for (int i1 = 0; i1 < coursename.length(); i1++) {
							char temp = coursename.charAt(i1);
							if ((temp + "").getBytes().length == 1) {
								stringLength++;
							} else {
								stringLength += 2;
							}
						}
//						System.out.println(stringLength);
						if (stringLength >= 12) {
							list.append(coursename + " \t");
						} else {
							list.append(coursename + "\t\t");
						}
						list.append(teacherid + "\t");
						list.append(teachername + "\t");
						list.append(studentid + "\t");
						list.append(studentname + "\t");
						list.append(Examname + "\t");
						list.append(Examtime + "\t");
						list.append(Examarea + "\n");
					}
				}
				br.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		add(contain);
		setVisible(true);

	}
}
