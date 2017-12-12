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
public class MainFrame extends ContactFrame { //�̳�ContactFrame��
	
	private Dao dao;//���ݿ�ӿ�
	private FixedTable table;//ͨѶ¼���
	private DefaultTableModel tableModel;//������ģ�Ͷ���ģ��
	static private User user;//��ǰ��¼�û�
	
	public MainFrame(){
		setTitle("ͨѶ¼ϵͳ");//�������
		init();//�����ʼ��
		validate();//���¼������
		addAction();//�����������
	}
	
	/**
	 * ��Ӽ������
	 */
	public void addAction(){
		table.addMouseListener(new MouseAdapter(){ //����������¼�����
			public void mouseClicked(MouseEvent e){ //�������ʱ
				if(e.getClickCount() == 2){ 
					//���ѡ���еĵ�һ�����ݣ���ֵΪid
					String id = (String) table.getValueAt(table.getSelectedRow(), 0);
					//��ȡ��id�ĳ־û��ͻ���Ϣ����
					Customer cust = dao.selectCustomer(Integer.parseInt(id));
					cust.setId(Integer.parseInt(id));//��idֵת��intֵ�������ͻ�����
					//����ϸ��Ϣչʾ����
					ShowInfoFrame info = new ShowInfoFrame(cust,MainFrame.this);
					info.setVisible(true);//����ɼ�
				}
			}
		});
	}
	
	/**
	 * �����ʼ��
	 * @param args
	 */
	protected void  init(){     
		super.init();   //���ø���init��������
		dao = DaoFactory.getDao();				//ʵ�л����ݿ�ӿ�
		table = getTable();//����ָ�����ģ�͵ı��
		table.setCellEditable(false);//�ñ�񲻿ɱ༭
		initTable();//��ʼ�����
		if(user.getStatus().equals(User.ADMIN)){//����ǹ���Ա���
			
		}
	}
	
	//��ʼ���������
	public void initTable(){
		tableModel = getUsableModleSource();//��ȡ������Ч�ͻ���Ϣ
		table.setModel(tableModel);
	}
	
	//��ѯ�����û���������Ϣ
	private DefaultTableModel getUsableModleSource(){
		List<Customer> usableList = dao.selectAllCustomer();//��ȡ������Ч�ͻ�
		return assembledModleSource(usableList);//����������Ч�ͻ��������ģ��
	}
	
	private DefaultTableModel assembledModleSource(List<Customer> usableList){
		int customerCount = usableList.size();//��ȡ���ϵĿͻ�����
		String[] columnNames = {"���","����","�Ա�","��������","������λ","ְλ","�����ص�","��ͥ��ַ"};
		String[][] tableValues = new String[customerCount][8];//���������������
		for(int i = 0;i < customerCount; i++){//����������е���
			Customer cust = usableList.get(i);//��ȡ�пͻ�����
			tableValues[i][0] = "" + cust.getId();//��һ��Ϊ���
			tableValues[i][1] = cust.getName();//��Ϊ����
			tableValues[i][2] = cust.getSex();//��Ϊ�Ա�
			tableValues[i][3] = cust.getBirth();//����Ϊ��������
			tableValues[i][4] = cust.getWork_unit();//��˾����
			tableValues[i][5] = cust.getRole();//ְλ
			tableValues[i][6] = cust.getWork_addr();//��˾��ַ
			tableValues[i][7] = cust.getHome_addr();//��ͥ��ַ
		}
		DefaultTableModel  tmp = new DefaultTableModel(tableValues,columnNames);
		return tmp;
	}
	
	//���õ�ǰ�����û�
	static public User getUser(){
		return user;
	}
	
	static public void setUser(User user){
		MainFrame.user = user;
	}
	
//	public static void main(String[] args){
//		new MainFrame();//��������ʵ�л�
}
