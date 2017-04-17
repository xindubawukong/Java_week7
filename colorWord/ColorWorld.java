package colorWord;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class ColorWorld extends JFrame implements ActionListener{
	
	public static int width=400;
	public static int height=300;
	JLabel label;
	JButton button1,button2;
	
	public void go(){
		setTitle("ColorWorld");
		setBounds(100,50,width,height);
		setLayout(new FlowLayout());
		label=new JLabel("Java语言程序设计");
		label.setFont(new Font("宋体",Font.BOLD,15));
		button1=new JButton("Set Blue");
		button2=new JButton("Set Red");
		add(label);
		add(button1);
		add(button2);
		button1.addActionListener(this);
		button2.addActionListener(this);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent e){
		Object tmp=e.getSource();
		if (tmp==(Object)button1){
			label.setForeground(Color.BLUE);
		}
		else if (tmp==(Object)button2){
			label.setForeground(Color.RED);
		}
	}
	
}
