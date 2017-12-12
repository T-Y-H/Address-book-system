package frame;

import com.mr.contact.swing.ContactFrame;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import com.mr.contact.dao.Dao;
import com.mr.contact.dao.DaoFactory;
import com.mr.contact.swing.FixedTable;
import pojo.Customer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import pojo.User;


@SuppressWarnings("unused")
public class MainFrame extends ContactFrame { //继承ContactFrame类
	
	private Dao dao;//数据库接口
	private FixedTable table;//通讯录表格
	private DefaultTableModel tableModel;//定义表格模型对象模型
	static private User user;//当前登录用户
	
	public MainFrame(){
		setTitle("通讯录系统");//窗体标题
		init();//组件初始化
		validate();//重新加载组件
		addAction();//开启组件监听
	}
	
	/**
	 * 添加监听组件
	 */
	public void addAction(){
		table.addMouseListener(new MouseAdapter(){ //表格添加鼠标事件监听
			public void mouseClicked(MouseEvent e){ //当鼠标点击时
				if(e.getClickCount() == 2){ 
					//获得选中行的第一列数据，赋值为id
					String id = (String) table.getValueAt(table.getSelectedRow(), 0);
					//获取次id的持久化客户信息对象
					Customer cust = dao.selectCustomer(Integer.parseInt(id));
					cust.setId(Integer.parseInt(id));//将id值转成int值并赋给客户对象
					//打开详细信息展示窗体
					ShowInfoFrame info = new ShowInfoFrame(cust,MainFrame.this);
					info.setVisible(true);//窗体可见
				}
			}
		});
	}
	
	/**
	 * 组件初始化
	 * @param args
	 */
	protected void  init(){     
		super.init();   //调用父类init（）方法
		dao = DaoFactory.getDao();				//实列化数据库接口
		table = getTable();//创建指定表格模型的表格
		table.setCellEditable(false);//让表格不可编辑
		initTable();//初始化表格
		if(user.getStatus().equals(User.ADMIN)){//如果是管理员身份
			
		}
	}
	
	//初始化表格数据
	public void initTable(){
		tableModel = getUsableModleSource();//获取所以有效客户信息
		table.setModel(tableModel);
	}
	
	//查询所以用户的所有信息
	private DefaultTableModel getUsableModleSource(){
		List<Customer> usableList = dao.selectAllCustomer();//获取所有有效客户
		return assembledModleSource(usableList);//返回所有有效客户表格数据模型
	}
	
	private DefaultTableModel assembledModleSource(List<Customer> usableList){
		int customerCount = usableList.size();//获取集合的客户数量
		String[] columnNames = {"编号","姓名","性别","出生日期","工作单位","职位","工作地点","家庭地址"};
		String[][] tableValues = new String[customerCount][8];//创建表格数据数组
		for(int i = 0;i < customerCount; i++){//遍历表格所有的行
			Customer cust = usableList.get(i);//获取行客户对象
			tableValues[i][0] = "" + cust.getId();//第一列为标号
			tableValues[i][1] = cust.getName();//列为姓名
			tableValues[i][2] = cust.getSex();//列为性别
			tableValues[i][3] = cust.getBirth();//四列为出生日期
			tableValues[i][4] = cust.getWork_unit();//公司名称
			tableValues[i][5] = cust.getRole();//职位
			tableValues[i][6] = cust.getWork_addr();//公司地址
			tableValues[i][7] = cust.getHome_addr();//家庭地址
		}
		DefaultTableModel  tmp = new DefaultTableModel(tableValues,columnNames);
		return tmp;
	}
	
	//设置当前操作用户
	static public User getUser(){
		return user;
	}
	
	static public void setUser(User user){
		MainFrame.user = user;
	}
	
//	public static void main(String[] args){
//		new MainFrame();//将主窗体实列化
}
