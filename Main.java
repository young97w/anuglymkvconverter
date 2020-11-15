package mkvconverter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Main extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String filename = "66";
	public static String filepath = "";
	public static String msg_out = "";
	public static JTextArea inmsg;
	public static JTextArea outmsg;
	public static JLabel promsg;
	public static String outname ="";
	public static String prompt="��ת�����˵�mkv�ļ������䡣";
	//UI���캯��
	public Main() {
		
			JFrame fcon = new JFrame();
			fcon.setSize(480,360);
			fcon.setTitle("����MKVת����");
			fcon.setBackground(Color.blue);
			//λ�þ���
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			int x = (int)(toolkit.getScreenSize().getWidth()-fcon.getWidth())/2;
			int y = (int)(toolkit.getScreenSize().getHeight()-fcon.getHeight())/2;
			fcon.setLocation(x,y);
			fcon.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JPanel pcon = new JPanel();
			pcon.setLayout(null);
			//����
			inmsg = new JTextArea();
			outmsg = new JTextArea();
			promsg = new JLabel();
			//����
			promsg.setText(prompt);
			promsg.setFont(new Font("����", Font.BOLD, 16));
			promsg.setBounds(100,145,300,30);
			inmsg.setFont(new Font("����", Font.BOLD, 16));
			inmsg.setBounds(100,45,300,30);//�ļ�������ʾ��
			inmsg.setEditable(false);
			outmsg.setFont(new Font("����", Font.BOLD, 16));
			outmsg.setBounds(100,85,300,30);//�ļ������ʾ��
			inmsg.setText("�ļ�·��:");
			outmsg.setText("���·��:");
			//���
			pcon.add(promsg);
			pcon.add(inmsg);
			pcon.add(outmsg);
			fcon.add(pcon);
			//������ť
			JButton jb_import =new JButton("�����ļ�");
			jb_import.setFont(new Font("����", Font.BOLD, 16));
			JButton jb_start =new JButton("��ʼת��");
			jb_start.setFont(new Font("����", Font.BOLD, 16));
			jb_import.setBounds(100, 250, 110, 40);//���밴ť
			jb_start.setBounds(280, 250, 110, 40);//��ʼ��ť
			jb_import.setBackground(Color.getHSBColor(57, 61, 100));//������ɫ����Ч�����Ǻ�����
			jb_start.setBackground(Color.getHSBColor(57, 61, 100));
			jb_import.addActionListener(new ActionListener() {//�����¼�
				public void actionPerformed(ActionEvent e) {
					JFrame yourJFrame = new JFrame();
			        FileDialog fd = new FileDialog(yourJFrame, "Choose a file", FileDialog.LOAD);
					fd.setDirectory("");
					fd.setFile("*.mkv");
					fd.setVisible(true);
					filename = fd.getFile();
					filepath = fd.getDirectory();
					outnameset();
					show_msg();
				}
			});
			jb_start.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						start_convert();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			});		
			pcon.add(jb_start);
			pcon.add(jb_import);	    
			fcon.setVisible(true);
	}                                

	public static void main(String[] args) {	
		Main f=new Main();	
	}
	
	public void refre_msg(String n,String p) {
		filename=n;filepath=p;		
	}
	
	public void outnameset() {//�����ļ��������
		String[] namelist = filename.split("\\.");
		outname = namelist[0]+"_converted"+".mp4";
	}
	
    public  void show_msg()  //������ʾ��
    { 
    	try {
    	inmsg.repaint();
    	outmsg.repaint();
      	msg_out ="�ļ�·��:"+filepath + filename;
        inmsg.setText(msg_out);
    	outmsg.setText("���·��:"+filepath+outname);    
    	}catch(Exception e) {}
    }
    
    public void start_convert() throws InterruptedException, IOException{
    	Runtime rt = Runtime.getRuntime();
	    try {//�������������д���е����������ˣ���������
	    	//Process p1=rt.exec("cmd /C start cmd.exe /K"+" C:");
	    	Process p=rt.exec("cmd /C start cmd.exe /K"+" \"" +System.getProperty("user.dir")+"\\ffmpeg\\bin\\ffmpeg -i "+filepath+filename+ " -vcodec copy -acodec aac -b:a 384k "+filepath+outname+"\"");
	    	InputStream is=p.getInputStream();
	    	InputStreamReader ir1=new InputStreamReader(is);
	    	BufferedReader br1=new BufferedReader(ir1);
	    	String str=null;
	    	while((str=br1.readLine())!=null){
	    		System.out.println(str);
	    	}
	        int ret=p.waitFor();
	        int exit_v=p.exitValue();
	        System.out.println("return value:"+ret);
	        System.out.println("exit value:"+exit_v);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    Runtime.getRuntime().exec("taskkill /f /im cmd.exe");//ʵ��ffmpeg���н�����ֱ�ӹر�cmd����,����Ŀǰû������
    }
}

