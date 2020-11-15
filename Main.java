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
	public static String prompt="有转换不了的mkv文件算我输。";
	//UI构造函数
	public Main() {
		
			JFrame fcon = new JFrame();
			fcon.setSize(480,360);
			fcon.setTitle("万能MKV转换器");
			fcon.setBackground(Color.blue);
			//位置居中
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			int x = (int)(toolkit.getScreenSize().getWidth()-fcon.getWidth())/2;
			int y = (int)(toolkit.getScreenSize().getHeight()-fcon.getHeight())/2;
			fcon.setLocation(x,y);
			fcon.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JPanel pcon = new JPanel();
			pcon.setLayout(null);
			//创建
			inmsg = new JTextArea();
			outmsg = new JTextArea();
			promsg = new JLabel();
			//设置
			promsg.setText(prompt);
			promsg.setFont(new Font("黑体", Font.BOLD, 16));
			promsg.setBounds(100,145,300,30);
			inmsg.setFont(new Font("黑体", Font.BOLD, 16));
			inmsg.setBounds(100,45,300,30);//文件输入提示框
			inmsg.setEditable(false);
			outmsg.setFont(new Font("黑体", Font.BOLD, 16));
			outmsg.setBounds(100,85,300,30);//文件输出提示框
			inmsg.setText("文件路径:");
			outmsg.setText("输出路径:");
			//添加
			pcon.add(promsg);
			pcon.add(inmsg);
			pcon.add(outmsg);
			fcon.add(pcon);
			//创建按钮
			JButton jb_import =new JButton("导入文件");
			jb_import.setFont(new Font("黑体", Font.BOLD, 16));
			JButton jb_start =new JButton("开始转换");
			jb_start.setFont(new Font("黑体", Font.BOLD, 16));
			jb_import.setBounds(100, 250, 110, 40);//导入按钮
			jb_start.setBounds(280, 250, 110, 40);//开始按钮
			jb_import.setBackground(Color.getHSBColor(57, 61, 100));//更改颜色，但效果不是很满意
			jb_start.setBackground(Color.getHSBColor(57, 61, 100));
			jb_import.addActionListener(new ActionListener() {//加入事件
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
	
	public void outnameset() {//设置文件输出名字
		String[] namelist = filename.split("\\.");
		outname = namelist[0]+"_converted"+".mp4";
	}
	
    public  void show_msg()  //更新显示框
    { 
    	try {
    	inmsg.repaint();
    	outmsg.repaint();
      	msg_out ="文件路径:"+filepath + filename;
        inmsg.setText(msg_out);
    	outmsg.setText("输出路径:"+filepath+outname);    
    	}catch(Exception e) {}
    }
    
    public void start_convert() throws InterruptedException, IOException{
    	Runtime rt = Runtime.getRuntime();
	    try {//下面这个命令行写得有点拖沓（不改了，就这样）
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
	    Runtime.getRuntime().exec("taskkill /f /im cmd.exe");//实现ffmpeg运行结束后直接关闭cmd窗口,但是目前没法做到
    }
}

