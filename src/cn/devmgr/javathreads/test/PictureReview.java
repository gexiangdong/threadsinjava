package cn.devmgr.javathreads.test;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.GridBagLayout;
//import com.jgoodies.forms.layout.FormLayout;
//
//import com.example.swing.bean.Archive;
//import com.example.swing.mapper.ArchiveMapper;
//import com.example.swing.util.MybatisUtil;
//import com.jgoodies.forms.layout.ColumnSpec;
//import com.jgoodies.forms.layout.RowSpec;
//import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import java.awt.SystemColor;

public class PictureReview  extends JFrame{

    private JFrame frame;
    private int rowI = -1;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PictureReview frame = new PictureReview();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public PictureReview() {
        setTitle("图片审核界面");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 375);

        getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setBorder(new BevelBorder(BevelBorder.LOWERED, SystemColor.activeCaption, null, null, null));
        panel.setBounds(0, 0, 312, 213);
        getContentPane().add(panel);
        //从数据库读取数据
		/*SqlSession openSession = MybatisUtil.sqlSessionFactory.openSession();
		 ArchiveMapper mapper = openSession.getMapper(ArchiveMapper.class);
		 List<Archive> selectAll = mapper.selectAll();
		 Object[][] playerInfo = new Object[selectAll.size()][2] ;
		 String str="资料齐全";
		 for(int i=0;i<selectAll.size();i++){
			 playerInfo[i][0]=selectAll.get(i).getReferNumber();
			 if(selectAll.get(i).getStatus()==0){
				str="资料齐全";
			 }else{
				str="资料不全";
			 }
			 playerInfo[i][1]=str;
		 }*/

        //创建表格所需要的列名
        String[] Names = { "受理号", "资料是否齐全" };
        Object[][] playerInfo =
                {
                        new Object[]{"李清照" , 29},
                        new Object[]{"苏格拉底", 56 },
                        new Object[]{"李白", 35 },
                        new Object[]{"弄玉", 18 },
                        new Object[]{"虎头" , 2 }
                };
        panel.setLayout(null);


        // 以Names和playerInfo为参数，创建一个表格
        DefaultTableModel tableModel = new DefaultTableModel(playerInfo, Names);
        JTable table = new JTable(tableModel);

        table.setRowHeight(20);
        table.setShowGrid(true);

        // 设置此表视图的首选大小
        table.setPreferredScrollableViewportSize(new Dimension(800, 800));
        //将表格加入到滚动条组件中
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 292, 200);
        //将滚动组件放入到面板中
        panel.add(scrollPane);

        JButton applyFormBtn = new JButton("申请书");
        applyFormBtn.setBackground(Color.WHITE);
        applyFormBtn.setBounds(322, 0, 93, 23);
        getContentPane().add(applyFormBtn);

        JButton approvBtn = new JButton("审批表");
        approvBtn.setBackground(Color.WHITE);
        approvBtn.setBounds(322, 31, 93, 23);
        getContentPane().add(approvBtn);

        JButton notifyBtn = new JButton("告知书");
        notifyBtn.setBackground(Color.WHITE);
        notifyBtn.setBounds(322, 64, 93, 23);
        getContentPane().add(notifyBtn);

        JButton IdBtn = new JButton("身份证");
        IdBtn.setBackground(Color.WHITE);
        IdBtn.setBounds(322, 92, 93, 23);
        getContentPane().add(IdBtn);

        JButton addBtn = new JButton("添加");
        addBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tableModel.addRow(new Object[]{"new" , 0 });
                //新建一个数据模型
//                DefaultTableModel model = new DefaultTableModel();
//                //用数据模型创建一个JTable
//                JTable table = new JTable(model);
//                //用列表创建可滚动的Panel，把这个Panel添加到窗口中
//                JScrollPane jsp = new JScrollPane(table);
//                DefaultTableModel model = (DefaultTableModel) table.getModel();
//                //数据行向量
//                Vector row = new Vector();
//                //数据行向量集
//                Vector data = new Vector();
//                //数据列名
//                Vector names = new Vector();
//                //设置模型中的元素，它会自动显示在列表中
//                tableModel.setDataVector( data,  names);
            }
        });


        addBtn.setBounds(10, 241, 118, 23);
        getContentPane().add(addBtn);



    }
}