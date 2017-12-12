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
		setTitle("ͨѶ¼��¼");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//����رպ�ֹͣ����
		setSize(310,210);//������
		Toolkit tool = Toolkit.getDefaultToolkit();//����ϵͳ��������߰�
		Dimension d = tool.getScreenSize();//��ȡ��Ļ�ߴ磬����һ����ά�������
		//������������Ļ�м���ʾ
		setLocation(d.width - getWidth() / 2, (d.height - getHeight() ) / 2);
		JPanel contentPane = new JPanel();//�������������
		//�����������ʹ�ÿ�Ⱥͼ�඼Ϊ5���صĿձ߿�
		contentPane.setBorder(new EmptyBorder(5,5,5,5));
		setContentPane(contentPane);//��������������뵽��������ȥ
		contentPane.setLayout(new BorderLayout());//���������ʹ�ñ߽粼��
		//����ʹ��2��1�е����񲼾ֵ��в����
		JPanel centerPanel = new JPanel(new GridLayout(2,1));
		//�в����ŵ������������м�λ��
		contentPane.add(centerPanel,BorderLayout.CENTER);
		//����������
		FlowLayout centerLayout = new FlowLayout();
		centerLayout.setHgap(10);//������������10����
		//����ʹ�õ�һ���������壬��ʹ��������
		JPanel aFloorPanel = new JPanel(centerLayout);
		centerPanel.add(aFloorPanel);
		JLabel usernameLabel = new JLabel("�˺ţ�");//������ǩ
		//���뷽ʽΪ����
		usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		aFloorPanel.add(usernameLabel);
		//�����û������
		final JTextField usernameField = new JTextField();
		usernameField.setColumns(20);//�û��������ĳ���Ϊ20���ַ�
		aFloorPanel.add(usernameField);
		
		//�����ڶ�����������
		JPanel bFloorPanel = new JPanel(centerLayout);
		centerPanel.add(bFloorPanel);
		JLabel pwdLabel = new JLabel("���룺");
		pwdLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bFloorPanel.add(pwdLabel);
		
		//�������������
		final JPasswordField passwordField = new JPasswordField();
		passwordField.setColumns(20);
		bFloorPanel.add(passwordField);
		
		//�����ϲ����
		JPanel southPanel = new JPanel(centerLayout);
		contentPane.add(southPanel,BorderLayout.SOUTH);
		
		final JButton loginBtn = new JButton("��¼");
		southPanel.add(loginBtn);
		
		JButton closeBtn = new JButton("�ر�");
		southPanel.add(closeBtn);
		
		closeBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		
		loginBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Dao dao = DaoFactory.getDao();//�������ݿ�ӿڶ���
				//��ȡ�˺����������ݣ�ȥ�����ߵĿո�
				String account = usernameField.getText().trim();
				//��ȡ��������������
				String password = new String(passwordField.getPassword());
				//���˺����뽻�����ݿ�����ж�
				User user = dao.selectUser(account,password);
				if(null == user){
					JOptionPane.showMessageDialog(null, "����������벻��ȷ");
				}else{
					MainFrame.setUser(user);
					MainFrame frame = new MainFrame();
					dispose();
				}
			}
		});
		
		//���������ӻس��¼�
		passwordField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				loginBtn.doClick();//������¼��ť����¼�
			}
		  });
		
		
		}
}
