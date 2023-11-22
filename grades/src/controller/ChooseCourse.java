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

public class ChooseCourse extends JFrame implements ActionListener {
    /**
     * ѧ��ѡ��
     */
    private static final long serialVersionUID = 1L;
    JPanel contain;
    JTextArea list;
    JLabel chooseCourseId, allCourse;
    JTextField chooseCourseIdt;
    JButton submit, dropOut;
    String id;

    public ChooseCourse(String id) {
        super("ѡ�ν���");
        this.id = id;
        contain = new JPanel();
        contain.setLayout(null);
        setLocation(600, 200);
        setSize(650, 450);
        add(contain);
        allCourse = new JLabel("���п�ѡ�γ�:");
        allCourse.setBounds(10, 10, 200, 20);
        contain.add(allCourse);
        list = new JTextArea();
        list.setBounds(30, 40, 570, 200);
        list.setEditable(false);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 40, 570, 200);
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
                teacherName = result[5];
                maxNum = result[6];
                currentNum = result[7];

                int stringLength = 0;
                for (int i = 0; i < courseName.length(); i++) {
                    char temp = courseName.charAt(i);
                    if ((temp + "").getBytes().length == 1) {
                        stringLength++;
                    } else {
                        stringLength += 2;
                    }
                }
//				System.out.println(stringLength);
                list.append("      " + courseId + "\t");
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        chooseCourseId = new JLabel("��Ҫѡ��/��ѡ�Ŀγ̱�ţ�");
        chooseCourseIdt = new JTextField();

        chooseCourseId.setBounds(70, 280, 160, 35);
        chooseCourseIdt.setBounds(235, 280, 130, 35);

        contain.add(chooseCourseId);
        contain.add(chooseCourseIdt);

        submit = new JButton("ѡ��");
        dropOut = new JButton("��ѡ");
        submit.setBounds(180, 350, 100, 30);
        submit.addActionListener(this);
        dropOut.setBounds(320, 350, 100, 30);
        dropOut.addActionListener(this);
        contain.add(dropOut);
        contain.add(submit);

        setVisible(true);
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    }

    public boolean hasStudent(String id, String courseId) {
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
                    if (result[0].equals(courseId)) {
                        if (result[2].equals(id)) {
                            return true;// �����ѧ������true
                        }
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
        return false;// �Ҳ������ѧ������false
    }

    public void editCurrentNum(String courseId, boolean condition) {
        // condtion�����ж��ǵ�ǰ���� +1(true) ���ǵ�ǰ���� -1(false)
        String coursePath = System.getProperty("user.dir") + "\\data\\course.txt";
        ArrayList<String> modifiedContent = new ArrayList<String>();
        BufferedReader br = null;// �����ٴζ���γ̵����ݣ������Ϊ���޸����е�currentNum
        BufferedWriter bw = null;// ��������������д�룬�ڶ�������Ĺ���֮�У�currentNum�õ����޸�
        try {
            br = new BufferedReader(new FileReader(coursePath));
            String s = null;
            while ((s = br.readLine()) != null) {
                String[] result = s.split(" ");// ��ԭ������Ϣ������
                if (result[0].equals(courseId)) {
                    if (condition) {
                        result[7] = "" + (Integer.parseInt(result[7]) + 1);
                    } else {
                        result[7] = "" + (Integer.parseInt(result[7]) - 1);
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            bw = new BufferedWriter(new FileWriter(coursePath));
            for (int i = 0; i < modifiedContent.size(); i++) {
                bw.write(modifiedContent.get(i));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void editGrade(String courseId, String studentId, boolean flag) {
        String coursePath = System.getProperty("user.dir") + "\\data\\course.txt";
        String studentPath = System.getProperty("user.dir") + "\\data\\student.txt";
        BufferedReader br1 = null;// ������ȡ�γ���Ϣ
        BufferedReader br2 = null;// ������ȡѧ����Ϣ
        BufferedReader br3 = null;
        BufferedWriter bw = null;
        String s = null;
        String courseName = null;// ��Ӧ�γ̱�ŵĿγ���
        String teacherId = null;// ��Ӧ����ʦ���
        String teacherName = null;// ��Ӧ����ʦ����
        String studentName = null;
        ArrayList<String> modifiedContent = new ArrayList<String>();
        try {
            br1 = new BufferedReader(new FileReader(coursePath));
            while ((s = br1.readLine()) != null) {
                String[] result = s.split(" ");
                if (result[0].equals(courseId)) {
                    courseName = result[1];
                    teacherId = result[4];
                    teacherName = result[5];
                }
            }
            br2 = new BufferedReader(new FileReader(studentPath));
            while ((s = br2.readLine()) != null) {
                String result[] = s.split(" ");
                if (result[0].equals(studentId)) {
                    studentName = result[2];
                }
            }
            String gradePath = System.getProperty("user.dir") + "\\data\\grade\\" + courseName + ".txt";// �ҵ���Ӧ�γ̵ĳɼ��ļ�

            if (flag) {
                bw = new BufferedWriter(new FileWriter(gradePath, true));
                // �γ̱�� �γ����� ��ʦ��� ��ʦ���� ѧ��ѧ�� ѧ������ �ɼ�
                // courseId courseName teacherId teacherName studentId studentName ��δ�޿��Լ�¼
                bw.write(courseId + " " + courseName + " " + teacherId + " " + teacherName + " " + studentId + " "
                        + studentName + " " + "���޿��Լ�¼");
                bw.newLine();
            }
            else {
                try {
                    br3 = new BufferedReader(new FileReader(gradePath));
                    String s2 = null;
                    while ((s2 = br3.readLine()) != null) {
                        String[] result2 = s2.split(" ");
                        if (result2[4].equals(id)) {
                            continue;
                        }
                        s = "";
                        for (int i = 0; i < result2.length - 1; i++) {
                            s = s + result2[i];
                            s = s + " ";
                        }
                        s = s + result2[result2.length - 1];
                        modifiedContent.add(s);
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                } finally {
                    try {
                        br3.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                bw = new BufferedWriter(new FileWriter(gradePath));
                for (int i = 0; i < modifiedContent.size(); i++) {
                    bw.write(modifiedContent.get(i));
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br1.close();
                br2.close();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {// �ж��ύ��ť�Ƿ���
            if (chooseCourseIdt.getText().equals("")) {// �ж�ѡ�ο������Ƿ�Ϊ��
                JOptionPane.showMessageDialog(null, "ѡ�εı�Ų���Ϊ�գ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
            } else {
                if (hasStudent(id, chooseCourseIdt.getText())) {// �ж����ѧ���Ƿ�ѡ�����ſ�
                    JOptionPane.showMessageDialog(null, "���Ѽ�����˿γ̣������ظ�ѡ�Σ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
                    chooseCourseIdt.setText("");
                } else {
                    String coursePath = System.getProperty("user.dir") + "/data/course.txt";
                    String studentPath = System.getProperty("user.dir") + "/data/student.txt";
                    BufferedReader br1 = null;// ��������γ̵�����
                    BufferedReader br2 = null;// ��������ѧ��������
                    BufferedWriter bw = null;// ����дcourse_student
                    String[] result2 = null;
                    boolean flag = false;// ����������ſδ治����
                    try {
                        br1 = new BufferedReader(new FileReader(coursePath));
                        br2 = new BufferedReader(new FileReader(studentPath));
                        String s1 = null;// ������¼�γ���Ϣ
                        String s2 = null;// ������¼ѧ����Ϣ
                        while ((s2 = br2.readLine()) != null) {
                            result2 = s2.split(" ");// ������¼ѧ����Ϣ
                            if (result2[0].equals(id)) {
                                break;
                            }
                        }
                        while ((s1 = br1.readLine()) != null) {
                            String[] result1 = s1.split(" ");// ������¼�γ���Ϣ
                            if (result1[0].equals(chooseCourseIdt.getText())) {// �ҵ���Ҫ��������ſ�
                                flag = true;
                                String course_studentPath = System.getProperty("user.dir") + "/data/course_student/"
                                        + result1[1] + "_student.txt";// ����Ҫ����γ̵�ѧ��������·��
                                bw = new BufferedWriter(new FileWriter(course_studentPath, true));
                                if (Integer.parseInt(result1[7]) >= Integer.parseInt(result1[6])) {// �жϿγ������Ƿ�����
                                    JOptionPane.showMessageDialog(null, "��Ǹ���ÿγ������Ѵ����ޣ�", "��ʾ",
                                            JOptionPane.INFORMATION_MESSAGE);
                                    break;
                                } else {
                                    bw.write(result1[0] + " " + result1[1] + " " + result2[0] + " " + result2[2] + " "
                                            + result2[3] + " " + result2[4] + " " + result2[5] + " " + result2[6]);
                                    bw.newLine();
                                    editCurrentNum(chooseCourseIdt.getText(), true);
                                    editGrade(chooseCourseIdt.getText(), id, true);// ѡ����Ժ���Ҫ�����Ӧ�ĳɼ��ļ�����
                                    JOptionPane.showMessageDialog(null, "ѡ�� " + result1[1] + " �ɹ�!", "��ʾ",
                                            JOptionPane.INFORMATION_MESSAGE);
                                    chooseCourseIdt.setText("");
                                    bw.close();
                                }
                            }
                        }
                        if (!(flag)) {
                            JOptionPane.showMessageDialog(null, "��Ǹ���ÿγ̲����ڣ�������ѡ��", "��ʾ",
                                    JOptionPane.INFORMATION_MESSAGE);
                            chooseCourseIdt.setText("");
                            return;
                        }

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } finally {
                        try {
                            br1.close();
                            br2.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        }
        if (e.getSource() == dropOut) {
            if (chooseCourseIdt.getText().equals("")) {// �ж�ѡ�ο������Ƿ�Ϊ��
                JOptionPane.showMessageDialog(null, "�˿εı�Ų���Ϊ�գ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
            } else {
                if (!(hasStudent(id, chooseCourseIdt.getText()))) {// �ж����ſ�����û�����ѧ��
                    JOptionPane.showMessageDialog(null, "����δ�������ſγ�/���ſγ̲����ڣ��޷��˿Σ�", "��ʾ",
                            JOptionPane.INFORMATION_MESSAGE);
                    chooseCourseIdt.setText("");
                } else {
                    String coursePath = System.getProperty("user.dir") + "/data/course.txt";
                    BufferedReader br1 = null;// br1������ȡcourse.txt�ļ���·��
                    BufferedReader br2 = null;// br2������ȡ��Ӧ��course_student�ļ��е�����
                    BufferedWriter bw = null;// bw������ɾ�������������д��
                    ArrayList<String> modifiedContent = new ArrayList<String>();
                    String course_studentPath = null;
                    String[] result1 = null;
                    try {
                        br1 = new BufferedReader(new FileReader(coursePath));
                        String s1 = null;
                        while ((s1 = br1.readLine()) != null) {
                            result1 = s1.split(" ");
                            if (result1[0].equals(chooseCourseIdt.getText())) {
                                course_studentPath = System.getProperty("user.dir") + "/data/course_student/"
                                        + result1[1] + "_student.txt";// ����Ҫ����γ̵�ѧ��������·��
                                break;
                            }
                        }
                        br2 = new BufferedReader(new FileReader(course_studentPath));
                        String s2 = null;
                        while ((s2 = br2.readLine()) != null) {
                            String[] result2 = s2.split(" ");
                            if (result2[2].equals(id)) {
                                continue;
                            }
                            String s = "";
                            for (int i = 0; i < result2.length - 1; i++) {
                                s = s + result2[i];
                                s = s + " ";
                            }
                            s = s + result2[result2.length - 1];
                            modifiedContent.add(s);
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } finally {
                        try {
                            br1.close();
                            br2.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    try {
                        bw = new BufferedWriter(new FileWriter(course_studentPath));
                        for (int i = 0; i < modifiedContent.size(); i++) {
                            bw.write(modifiedContent.get(i));
                            bw.newLine();
                        }
                        JOptionPane.showMessageDialog(null, "�˿� " + result1[1] + " �ɹ�!", "��ʾ",
                                JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } finally {
                        try {
                            bw.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    editGrade(chooseCourseIdt.getText(),this.id,false);
                    editCurrentNum(chooseCourseIdt.getText(), false);
                    chooseCourseIdt.setText("");
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
