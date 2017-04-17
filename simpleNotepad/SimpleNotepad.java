package simpleNotepad;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;


public class SimpleNotepad extends JFrame implements ActionListener{
	JPanel content;
	JMenuBar menuBar;
	JScrollPane scrollPane;
	JTextArea text;
	JFileChooser fileChooser=new JFileChooser();
	File file;
	
	SimpleNotepad(String fileName)throws FileNotFoundException{
		setBounds(200,50,600,600);
		String[] menuStrings={"新建","打开","保存"};
		JMenu menu=new JMenu("文件");
		for (int i=0;i<menuStrings.length;i++){
			JMenuItem item=new JMenuItem(menuStrings[i]);
			menu.add(item);
			item.addActionListener(this);
		}
		menuBar=new JMenuBar();
		menuBar.add(menu);
		content=new JPanel();
		content.setLayout(new BorderLayout());
		content.add(menuBar,BorderLayout.NORTH);
		text=new JTextArea();
		text.setFont(new Font("宋体", Font.PLAIN, 20));
		scrollPane=new JScrollPane(text);
		content.add(scrollPane,BorderLayout.CENTER);
		this.getContentPane().add(content);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		file=new File(fileName);
		if (file.exists())
			text.setText(readFromFile());
		else file=new File("新建文档.txt");
		this.setTitle(file.getName());
	}
	
	String readFromFile()throws FileNotFoundException{
		Scanner fin=new Scanner(file);
		String ans="";
		while (fin.hasNextLine())
			ans+=fin.nextLine()+"\n";
		return ans;
	}
	
	void writeToFile(String s)throws FileNotFoundException{
		PrintStream fout=new PrintStream(file);
		fout.println(s);
	}
	
	public void actionPerformed(ActionEvent e){
		if (e.getActionCommand().equals("新建")){
			String fileName = JOptionPane.showInputDialog("请输入文件名:","新建文档.txt");
			file=new File(fileName);
			try {
				file.createNewFile();
			} catch (IOException e1){}
			this.setTitle(file.getAbsolutePath());
		}
		else if (e.getActionCommand().equals("打开")){
			if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(null)) {
				file = fileChooser.getSelectedFile();
				try {
					text.setText(readFromFile());
				} catch (FileNotFoundException e1){}
				this.setTitle(file.getAbsolutePath());
			}
		}
		else if (e.getActionCommand().equals("保存")){
			try {
				writeToFile(text.getText());
			} catch (FileNotFoundException e1){}
		}
	}
}