package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.mr.contact.dao.Dao;
import com.mr.contact.dao.DaoFactory;

import pojo.User;

public class LoginFrame extends JFrame{
	
	
	public LoginFrame(){
		setTitle("通讯录登录");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//窗体关闭后停止程序
		setSize(310,210);//窗体宽高
		Toolkit tool = Toolkit.getDefaultToolkit();//创建系统该组件工具包
		Dimension d = tool.getScreenSize();//获取屏幕尺寸，赋给一个二维坐标对象
		//让主窗体在屏幕中间显示
		setLocation(d.width - getWidth() / 2, (d.height - getHeight() ) / 2);
		JPanel contentPane = new JPanel();//创建主容器面板
		//主容器的面板使用宽度和间距都为5像素的空边框
		contentPane.setBorder(new EmptyBorder(5,5,5,5));
		setContentPane(contentPane);//将主容器面板载入到主容器中去
		contentPane.setLayout(new BorderLayout());//主容器面板使用边界布局
		//创建使用2行1列的网格布局的中部面板
		JPanel centerPanel = new JPanel(new GridLayout(2,1));
		//中部面板放到主容器面板的中间位置
		contentPane.add(centerPanel,BorderLayout.CENTER);
		//创建流布局
		FlowLayout centerLayout = new FlowLayout();
		centerLayout.setHgap(10);//布局中组件间隔10像素
		//创建使用第一行组件的面板，并使用流布局
		JPanel aFloorPanel = new JPanel(centerLayout);
		centerPanel.add(aFloorPanel);
		JLabel usernameLabel = new JLabel("账号：");//创建标签
		//对齐方式为居中
		usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		aFloorPanel.add(usernameLabel);
		//创建用户输入框
		final JTextField usernameField = new JTextField();
		usernameField.setColumns(20);//用户名输入框的长度为20个字符
		aFloorPanel.add(usernameField);
		
		//创建第二行组件的面板
		JPanel bFloorPanel = new JPanel(centerLayout);
		centerPanel.add(bFloorPanel);
		JLabel pwdLabel = new JLabel("密码：");
		pwdLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bFloorPanel.add(pwdLabel);
		
		//创建密码输入框
		final JPasswordField passwordField = new JPasswordField();
		passwordField.setColumns(20);
		bFloorPanel.add(passwordField);
		
		//创建南部面板
		JPanel southPanel = new JPanel(centerLayout);
		contentPane.add(southPanel,BorderLayout.SOUTH);
		
		final JButton loginBtn = new JButton("登录");
		southPanel.add(loginBtn);
		
		JButton closeBtn = new JButton("关闭");
		southPanel.add(closeBtn);
		
		closeBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		
		loginBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Dao dao = DaoFactory.getDao();//创建数据库接口对象
				//获取账号输入框的内容，去掉两边的空格
				String account = usernameField.getText().trim();
				//获取密码输入框的内容
				String password = new String(passwordField.getPassword());
				//将账号密码交给数据库进行判断
				User user = dao.selectUser(account,password);
				if(null == user){
					JOptionPane.showMessageDialog(null, "你输入的密码不正确");
				}else{
					MainFrame.setUser(user);
					MainFrame frame = new MainFrame();
					dispose();
				}
			}
		});
		
		//密码面板添加回车事件
		passwordField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				loginBtn.doClick();//触发登录按钮点击事件
			}
		  });
		
		
		}
}
