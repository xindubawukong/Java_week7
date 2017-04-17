package simpleNotepad;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class Others extends JFrame {
	
	JPanel content;
	JMenuBar menuBar;
	JMenuItem lineWrap;
	JScrollPane scrollPane;
	JTextArea text;
	JPopupMenu popupMenu;
	static Others notepad;
	final static String author = "Notepad-Rainplus";
	boolean isNew = false;
	File file;
	JFileChooser fileChooser = new JFileChooser();

	public static void main(String[] args) {
		if (0 == args.length) {
			notepad = new Others("新建文档.txt");
		} else {
			notepad = new Others(args[0]);
		}
	}
	
	public Others() {
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		getContentPane().add(content = new JPanel());
		
		/* 在内容面板中添加:菜单和文本编辑区 */
		content.setLayout(new BorderLayout(2, 2));
		content.add(menuBar = new JMenuBar(), BorderLayout.NORTH);
		content.add(scrollPane = new JScrollPane(text = new JTextArea()),BorderLayout.CENTER);
		
		/* 向菜单栏添加菜单和菜单项 */
		String[] menusStrings = { "文件(F)", "编辑(E)", "格式(O)", "查看(V)", "帮助(H)" };
		int[] menukeys = { 'F', 'E', 'O', 'V', 'H' };
		String[][] itemStrings = {
				{ "新建(N)", "打开(O)", "-", "保存(S)", "另存为(A)", "-", "打印(P)",
						"页面设置(U)", "-", "退出(X)" },
				{ "撤销(U)", "-", "剪贴(T)", "复制(C)", "粘贴(P)", "删除(D)", "全选(A)",
						"-", "日期(D)" }, { "字体(F)", "自动换行(W)" },
				{ "查找(F)", "查找下一个(N)", "转到(G)", "替换(R)" },
				{ "查看帮助(H)", "有关Notepad(A)" } };
		int[][] itemkeys = {
				{ 'N', 'O', ' ', 'S', 'A', ' ', 'P', 'U', ' ', 'X' },
				{ 'U', ' ', 'T', 'C', 'P', 'D', 'A', ' ', 'D' }, { 'F', 'W' },
				{ 'F', 'N', 'G', 'R' }, { 'H', 'A' } };
		for (int i = 0; i < menusStrings.length; i++) {
			JMenu menu = new JMenu(menusStrings[i]);
			menu.setMnemonic(menukeys[i]);
			menuBar.add(menu);
			for (int j = 0; j < itemStrings[i].length; j++) {
				if ("-" == itemStrings[i][j]) {
					menu.addSeparator();
				}
				else {
					JMenuItem item = new JMenuItem(itemStrings[i][j]);
					item.setAccelerator(KeyStroke.getKeyStroke((char) itemkeys[i][j], InputEvent.CTRL_MASK));
					item.addActionListener(new MenuAction());
					menu.add(item);
					if ("自动换行(W)" == itemStrings[i][j]) {
						lineWrap = item;
					}
				}
			}
		}
		
		/* 向text文本去内设置右键弹出菜单 */
		String[] popUpStrings = { "撤销(U)", "-", "剪贴(T)", "复制(C)", "粘贴(P)", "删除(D)", "-", "全选(A)"};
		int[] popUpKeys = { 'U', ' ', 'T', 'C', 'P', 'D', ' ', 'A' };
		popupMenu = new JPopupMenu();
		for (int i = 0; i < popUpStrings.length; i++) {
			if ("-" == popUpStrings[i]) {
				popupMenu.addSeparator();
			}
			else {
				JMenuItem menuItem = new JMenuItem(popUpStrings[i]);
				menuItem.addActionListener(new pupUpAciton());
				menuItem.setAccelerator(KeyStroke.getKeyStroke((char) popUpKeys[i]));
				popupMenu.add(menuItem);
			}
		}
		
		/* 给文本编辑区添加右键点击事件响应 */
		text.addMouseListener(new TextMouse());
		text.setFont(new Font("SansSerif", Font.PLAIN, 16));
		setVisible(true);
		setLocationRelativeTo(null);
	}

	public Others(String fileName) {
		this();
		file = new File(fileName);
		if (file.exists()) {
			text.setText(readFromFile());
			setTitle(author + "--" + file.getName());
		} else {
			file = new File("新建文档.txt");
			setTitle(author + "" + "-----" + "新建文档.txt");
			isNew = true;
		}
	}

	public String readFromFile() { //返回file2里的字符串
		char[] ch = null;
		try {
			FileReader fin = new FileReader(file);
			ch = new char[(int) file.length()];
			fin.read(ch);
			fin.close();
		} catch (FileNotFoundException fe) {
			JOptionPane.showMessageDialog(notepad, "\"+找不到" + file.getName());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(notepad, "\"IO出错,读取" + file.getName()
					+ "\"");
		}
		return new String(ch);
	}

	public void writeToFile() {
		String textContent = text.getText();

		try {
			FileWriter fout = new FileWriter(file);
			fout.write(textContent);
			fout.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(notepad, "\"IO出错,写入" + file.getName()
					+ "不成功\"");
		}
	}

	public boolean isSave() {
		char[] ch = null;
		try {
			FileReader fin = new FileReader(file);
			ch = new char[(int) file.length()];
			fin.read(ch);
			fin.close();
		}
		catch (FileNotFoundException fe) {}
		catch (IOException e) {}
		return new String(ch).equals(text.getText());
	}

class TextMouse implements MouseListener {
	@Override
	public void mouseClicked(MouseEvent e) {
		if (MouseEvent.BUTTON3 == e.getButton()) {
			popupMenu.show(text, e.getX(), e.getY());
		}
	}
		@Override
	public void mouseReleased(MouseEvent e) {
	}
		@Override
	public void mousePressed(MouseEvent e) {
	}
		@Override
	public void mouseExited(MouseEvent e) {
	}
		@Override
	public void mouseEntered(MouseEvent e) {
	}
}

class MenuAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if ("新建(N)" == e.getActionCommand()) {
				String fileName = JOptionPane.showInputDialog("输入文件的名字:","新建文档.txt");
				file = new File(fileName);
				try {
					file.createNewFile();
				} catch (IOException e1) {}
				notepad.setTitle(author + "-----" + file.getAbsolutePath());
			}
			if ("打开(O)" == e.getActionCommand()) {
				if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(notepad)) {
					file = fileChooser.getSelectedFile();
					text.setText(readFromFile());
					notepad.setTitle(author + "-----" + file.getAbsolutePath());
					isNew = false;
				}
			}
			if ("保存(S)" == e.getActionCommand()) {
				writeToFile();
				isNew = false;
			}
			if ("另存为(A)" == e.getActionCommand()) {
				if (JFileChooser.APPROVE_OPTION == fileChooser
						.showSaveDialog(notepad)) {
					file = fileChooser.getSelectedFile();
					writeToFile();
					notepad.setTitle(author + "-----" + file.getAbsolutePath());
					isNew = false;
				}
			}
			if ("退出(X)" == e.getActionCommand()) {
				int rec;
				if (isNew) {
					rec = JOptionPane.showConfirmDialog(null, "是否需要保存并退出");
					if (0 == rec) {
						writeToFile();
						System.exit(0);

					} else if (1 == rec) {
						System.exit(0);
					}
					return;
				}
				if (isSave()) {
					System.exit(0);
				}
				else {
					rec = JOptionPane.showConfirmDialog(null, "是否需要保存并退出");
					if (0 == rec) {
						writeToFile();
						System.exit(0);
					} else if (1 == rec) {
						System.exit(0);
					}
				}
			}
			if ("剪贴(T)" == e.getActionCommand()) {
				text.copy();
			}
			if ("复制(C)" == e.getActionCommand()) {
				text.copy();
			}
			if ("粘贴(P)" == e.getActionCommand()) {
				text.paste();
			}
			if ("删除(D)" == e.getActionCommand()) {
				text.replaceSelection("");
			}
			if ("全选(A)" == e.getActionCommand()) {
				text.selectAll();
			}
			if ("自动换行(W)" == e.getActionCommand()) {
				text.setLineWrap(true);
				lineWrap.setText("√自动换行(W)");
			}
			if ("√自动换行(W)" == e.getActionCommand()) {
				text.setLineWrap(false);
				lineWrap.setText("自动换行(W)");

			}
			if ("查看帮助(H)" == e.getActionCommand()) {
				JOptionPane.showMessageDialog(null, "详情参考windowsnotepad的用法");
			}
			if ("有关Notepad(A)" == e.getActionCommand()) {
				JOptionPane.showMessageDialog(null,
						"仿制windows Notepad\n       基本实现全部功能\n"+ "                          作者:Rainplus");
			}
		}
	}

	class pupUpAciton implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if ("撤销(U)" == e.getActionCommand()) {
				// TODO
			}
			if ("剪贴(T)" == e.getActionCommand()) {
				text.cut();
			}
			if ("复制(C)" == e.getActionCommand()) {
				text.copy();
			}
			if ("粘贴(P)" == e.getActionCommand()) {
				text.paste();
			}
			if ("删除(D)" == e.getActionCommand()) {
				text.replaceSelection("");

			}
			if ("全选(A)" == e.getActionCommand()) {
				text.selectAll();
			}
		}
	}
}
