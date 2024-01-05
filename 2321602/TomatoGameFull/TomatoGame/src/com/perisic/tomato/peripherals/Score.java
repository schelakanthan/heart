package com.perisic.tomato.peripherals;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class Score implements ActionListener{
JFrame frame, frame1;
JTextField textbox;
JLabel label;
JButton button;
JPanel panel;
static JTable table;

String driverName = "com.mysql.cj.jdbc.Driver";
String url = "jdbc:mysql://localhost:3306/employee_management_system";
String userName = "root";
String password = "Zujanshan83@";
String[] columnNames = {"id", "user_name", "score"};

public void createUI()
{
frame = new JFrame("Database Search Result");
frame.getContentPane().setBackground(new Color(0, 0, 128));
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.getContentPane().setLayout(null);
//textbox = new JTextField();
//textbox.setBounds(120,30,150,20); 
//label = new JLabel("Enter your id");
//label.setBounds(10, 30, 100, 20);
button = new JButton("search");
button.setForeground(new Color(64, 0, 64));
button.setBackground(new Color(128, 0, 0));
button.setBounds(120,89,150,20);
button.addActionListener(this);

//frame.getContentPane().add(textbox);
//frame.getContentPane().add(label);
frame.getContentPane().add(button);
frame.setVisible(true);
frame.setSize(500, 400); 
} 

public void actionPerformed(ActionEvent ae)
{
button = (JButton)ae.getSource();
System.out.println("Showing Table Data.......");
showTableData(); 
} 

public void showTableData()
{

frame1 = new JFrame("Database Search Result");
frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame1.getContentPane().setLayout(new BorderLayout()); 
//TableModel tm = new TableModel();
DefaultTableModel model = new DefaultTableModel();
model.setColumnIdentifiers(columnNames);
//DefaultTableModel model = new DefaultTableModel(tm.getData1(), tm.getColumnNames()); 
//table = new JTable(model);
table = new JTable();
table.setModel(model); 
table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
table.setFillsViewportHeight(true);
JScrollPane scroll = new JScrollPane(table);
scroll.setHorizontalScrollBarPolicy(
JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
scroll.setVerticalScrollBarPolicy(
JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
//String textvalue = textbox.getText();
String id= "";
String username= "";
String score = "";

try
{ 
Class.forName(driverName); 
Connection con = DriverManager.getConnection(url, userName, password);
String sql = "select * from score1";
PreparedStatement ps = con.prepareStatement(sql);
ResultSet rs = ps.executeQuery();

while(rs.next())
{
id = rs.getString("id");
username = rs.getString("user_name");
score = rs.getString("score");

model.addRow(new Object[]
		{id, username, score});

}
con.close();
//if(i <0)
//{
//JOptionPane.showMessageDialog(null, "No Record Found","Error",
//JOptionPane.ERROR_MESSAGE);
//}
//if(i ==1)
//{
//System.out.println(i+" Record Found");
//}
//else
//{
//System.out.println(i+" Records Found");
//}
}
catch(Exception ex)
{
JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",
JOptionPane.ERROR_MESSAGE);
}
frame1.getContentPane().add(scroll);
frame1.setVisible(true);
frame1.setSize(400,300);
}

public static void main(String args[])
{
	Score sr = new Score();
sr.createUI(); 
}
}