package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.mr.contact.dao.Dao;
import com.mr.contact.dao.DaoFactory;
import com.mr.contact.swing.CustomerFrame;
import com.mr.contact.swing.FixedTable;

import pojo.Communication;
import pojo.Customer;

/**
 * @author T-Y-H
 * @Date :2014-9-1
 * @version :1.0
 * @Description:创建展示客户信息窗口
 */
public class ShowInfoFrame  extends CustomerFrame{
	private Dao dao;//数据库接口
	private MainFrame frame;//父窗体
	private Customer cust;//要展示信息的客户
	private FixedTable table;//通信信息表格
	private JTextField nameText;//姓名输入框
	private JTextField workUnitText;//工作单位输入框
	private JTextField roleText;//职位输入框
	private JTextField workAddressText;//工作地点输入框
	private JTextField homeText;//家庭地址输入框
	private JTextField birthText;//生日输入框
	private JTextField sexText;//性别输入框
	private DefaultTableModel tableModel;//通信信息表格数据模型
	
	public ShowInfoFrame(Customer cust,JFrame frame) {
		super(frame,CustomerFrame.SHOW);
		// TODO Auto-generated constructor stub
		this.cust = cust;
		this.frame = (MainFrame) frame;
		setTitle("详细信息");
		dao = DaoFactory.getDao();//实例化数据库接口对象
		
		table = getTable();//获取窗体表格
		table.setCellEditable(false);//表格不可编辑
		tableModel = getTableModel();//获取窗体表格模型
		initTableModel();//初始化窗体表格数据
		
		nameText = getNameText();//获取姓名输入框
		nameText.setText(cust.getName());//姓名输入框展示客户名
		
		sexText = getSexText();
		sexText.setText(cust.getSex());
		
		birthText = getBirthText();
		if(null != cust.getBirth()){
			birthText.setText(cust.getBirth());
		}
		
		workUnitText = getWorkUnitText();
		if(null != cust.getWork_unit()){
			workUnitText.setText(cust.getWork_unit());
		}
		
		roleText = getRoleText();
		if(null != cust.getRole()){
			roleText .setText(cust.getRole());
		}
		
		workAddressText = getWorkAddressText();
		if(null != cust.getWork_addr()){
			workAddressText.setText(cust.getWork_addr());
		}
		
		homeText = getHomeText();
		if(null != cust.getHome_addr()){
			homeText.setText(cust.getHome_addr());
		}
		
		FlowLayout btnPanelLayout = new FlowLayout(FlowLayout.RIGHT);//右对齐流布局
		JPanel btnPanel = new JPanel(btnPanelLayout);//采用流布局的按钮面板
		getContentPane().add(btnPanel, BorderLayout.SOUTH);//按钮面板添加到
		
		JButton btnNewButton = new JButton("关闭");//创建关闭按钮
		btnPanel.add(btnNewButton);//按钮添加到控制面板中
		btnNewButton.addActionListener(new ActionListener(){//关闭按钮添加监听
			public void actionPerformed(ActionEvent e){//当点击时
				dispose();//销毁窗体
			}
		});
		
	}

	private void initTableModel() {
		// TODO Auto-generated method stub
		if(tableModel.getRowCount() > 0 ){ //如果表格中的数据大于0
			tableModel.getDataVector().clear();//清空表数据
			tableModel.fireTableDataChanged();//重新绘制表格数据
		}
	    //获取所有通信信息的字符串数组，用于给表格数据模型赋值
		List<Communication> usableList = dao.selectCustmerCommunicationAll(cust);
		//创建保存通信信息的字符串数组，用于给表格数据模型赋值
		String [] tableValues = new String[5];
		for(Communication com : usableList){ //遍历通信信息集合
			if(com.getAvailable().endsWith("Y")) {
				tableValues[0] = "" + com.getId();//记录ID的字符串
				tableValues[1] = com.getOffice_phone();//记录办公电话
				tableValues[2] = com.getMobile_phone();//记录移动电话
				tableValues[3] = com.getEmail();//记录电子邮箱
				tableValues[4] = com.getQq();//记录QQ
				tableModel.addRow(tableValues);//表格数据模型添加一行记录
			}
		}
	}
	
}
