package fakeMenu;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class FakeMenu extends JFrame implements ActionListener{
	
	public static int width=400;
	public static int height=300;
	JMenuBar bar;
	JMenu _File,_Edit,_Help;
	JMenuItem _New,_Open,_Save,_Copy,_Cut,_Paste,_About;
	
	public void go(){
		setTitle("FakeMenu");
		setBounds(100,50,width,height);
		setLayout(new FlowLayout());
		
		bar=new JMenuBar();
		
		_File=new JMenu("File");
		_Edit=new JMenu("Efit");
		_Help=new JMenu("Help");
		
		_New=new JMenuItem("New");
		_New.addActionListener(this);
		_Open=new JMenuItem("Open");
		_Open.addActionListener(this);
		_Save=new JMenuItem("Save");
		_Save.addActionListener(this);
		_Copy=new JMenuItem("Copy");
		_Copy.addActionListener(this);
		_Cut=new JMenuItem("Cut");
		_Cut.addActionListener(this);
		_Paste=new JMenuItem("Paste");
		_Paste.addActionListener(this);
		_About=new JMenuItem("About");
		_About.addActionListener(this);
		
		_File.add(_New);
		_File.add(_Open);
		_File.add(_Save);
		_Edit.add(_Copy);
		_Edit.add(_Cut);
		_Edit.add(_Paste);
		_Help.add(_About);
		
		
		bar.add(_File);
		bar.add(_Edit);
		bar.add(_Help);
		
		this.setJMenuBar(bar);
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent e){
		Object obj=e.getSource();
		if (obj==(Object)_New)
			JOptionPane.showMessageDialog(null,"New");
		if (obj==(Object)_Open)
			JOptionPane.showMessageDialog(null,"Open");
		if (obj==(Object)_Save)
			JOptionPane.showMessageDialog(null,"Save");
		if (obj==(Object)_Copy)
			JOptionPane.showMessageDialog(null,"Copy");
		if (obj==(Object)_Cut)
			JOptionPane.showMessageDialog(null,"Cut");
		if (obj==(Object)_Paste)
			JOptionPane.showMessageDialog(null,"Paste");
		if (obj==(Object)_About)
			JOptionPane.showMessageDialog(null,"About");
	}
	
}
