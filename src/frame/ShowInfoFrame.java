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
 * @Description:����չʾ�ͻ���Ϣ����
 */
public class ShowInfoFrame  extends CustomerFrame{
	private Dao dao;//���ݿ�ӿ�
	private MainFrame frame;//������
	private Customer cust;//Ҫչʾ��Ϣ�Ŀͻ�
	private FixedTable table;//ͨ����Ϣ���
	private JTextField nameText;//���������
	private JTextField workUnitText;//������λ�����
	private JTextField roleText;//ְλ�����
	private JTextField workAddressText;//�����ص������
	private JTextField homeText;//��ͥ��ַ�����
	private JTextField birthText;//���������
	private JTextField sexText;//�Ա������
	private DefaultTableModel tableModel;//ͨ����Ϣ�������ģ��
	
	public ShowInfoFrame(Customer cust,JFrame frame) {
		super(frame,CustomerFrame.SHOW);
		// TODO Auto-generated constructor stub
		this.cust = cust;
		this.frame = (MainFrame) frame;
		setTitle("��ϸ��Ϣ");
		dao = DaoFactory.getDao();//ʵ�������ݿ�ӿڶ���
		
		table = getTable();//��ȡ������
		table.setCellEditable(false);//��񲻿ɱ༭
		tableModel = getTableModel();//��ȡ������ģ��
		initTableModel();//��ʼ������������
		
		nameText = getNameText();//��ȡ���������
		nameText.setText(cust.getName());//���������չʾ�ͻ���
		
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
		
		FlowLayout btnPanelLayout = new FlowLayout(FlowLayout.RIGHT);//�Ҷ���������
		JPanel btnPanel = new JPanel(btnPanelLayout);//���������ֵİ�ť���
		getContentPane().add(btnPanel, BorderLayout.SOUTH);//��ť�����ӵ�
		
		JButton btnNewButton = new JButton("�ر�");//�����رհ�ť
		btnPanel.add(btnNewButton);//��ť��ӵ����������
		btnNewButton.addActionListener(new ActionListener(){//�رհ�ť��Ӽ���
			public void actionPerformed(ActionEvent e){//�����ʱ
				dispose();//���ٴ���
			}
		});
		
	}

	private void initTableModel() {
		// TODO Auto-generated method stub
		if(tableModel.getRowCount() > 0 ){ //�������е����ݴ���0
			tableModel.getDataVector().clear();//��ձ�����
			tableModel.fireTableDataChanged();//���»��Ʊ������
		}
	    //��ȡ����ͨ����Ϣ���ַ������飬���ڸ��������ģ�͸�ֵ
		List<Communication> usableList = dao.selectCustmerCommunicationAll(cust);
		//��������ͨ����Ϣ���ַ������飬���ڸ��������ģ�͸�ֵ
		String [] tableValues = new String[5];
		for(Communication com : usableList){ //����ͨ����Ϣ����
			if(com.getAvailable().endsWith("Y")) {
				tableValues[0] = "" + com.getId();//��¼ID���ַ���
				tableValues[1] = com.getOffice_phone();//��¼�칫�绰
				tableValues[2] = com.getMobile_phone();//��¼�ƶ��绰
				tableValues[3] = com.getEmail();//��¼��������
				tableValues[4] = com.getQq();//��¼QQ
				tableModel.addRow(tableValues);//�������ģ�����һ�м�¼
			}
		}
	}
	
}
