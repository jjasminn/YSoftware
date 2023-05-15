import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.sql.*;

public class displayWorkersScreen extends JFrame {

	private JPanel contentPane;
	private JTable table;
	Connection conn;
	Statement s ;
	      

	public displayWorkersScreen() {
		try {
	          conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/YSoft", "jasmin", "postresql");
	          s = conn.createStatement();
	    } catch (Exception e) {
	          System.out.println("Veritabanina Baglanmada Problem "+ e.getMessage());
	    }
		setTitle("YSoft Yazılım Evi");
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 50, 700, 700);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		
		
		ImageIcon backgroundMain = new ImageIcon(this.getClass().getResource("/background.png"));
		JLabel backgroundLabel = new JLabel(backgroundMain);
		backgroundLabel.setBounds(0,0,700,700);
		
		contentPane.add(backgroundLabel);
		
		JLabel txtYsoftYazlmEvi = new JLabel();
		txtYsoftYazlmEvi.setForeground(new Color(0, 0, 0));
		txtYsoftYazlmEvi.setText("YSOFT YAZILIM EVİ");
		txtYsoftYazlmEvi.setHorizontalAlignment(SwingConstants.CENTER);
		txtYsoftYazlmEvi.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 30));
		txtYsoftYazlmEvi.setBounds(50, 100, 293, 35);
		txtYsoftYazlmEvi.setOpaque(false);
		backgroundLabel.add(txtYsoftYazlmEvi);
		
		JButton backButton = new JButton("<");
		backButton.setFont(new Font("Arial", Font.PLAIN, 20));
		backButton.setBounds(0, 0, 74, 47);
		backgroundLabel.add(backButton);
		backButton.setBackground(new Color(248, 248, 255));
		backButton.setFocusPainted(false);
		backButton.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainScreen mainscrn = new mainScreen();
				mainscrn.setVisible(true);
				setVisible(false);
			}
		});
		
		JScrollPane workersScrollPane = new JScrollPane();
		workersScrollPane.setBounds(50, 200, 600, 400);
		backgroundLabel.add(workersScrollPane);
		DefaultTableModel tableModel = new DefaultTableModel() {

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};


		JTable displayWorkersTable = new JTable();
		displayWorkersTable.setModel(tableModel);
		

		displayWorkersTable.setFont(new Font("Arial", Font.PLAIN, 10));
		displayWorkersTable.setRowSelectionAllowed(false);
		workersScrollPane.setViewportView(displayWorkersTable);
		displayWorkersTable.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));

		String queryEmployee= "SELECT employee.name,employee.surname,employee.salary,employee.employee_Type,project.project_name FROM employee , project WHERE project.project_id=employee.project_id ";
		try {
			ResultSet r = s.executeQuery(queryEmployee);
			ResultSetMetaData rsmd = r.getMetaData();
			DefaultTableModel  model =  (DefaultTableModel)displayWorkersTable.getModel();
			int cols = rsmd.getColumnCount();
			String[] colName = new String[cols];
			for(int i=0; i<cols; i++)
				colName[i] = rsmd.getColumnName(i+1);
			model.setColumnIdentifiers(colName);
			String  name, surname,salary,projectName,hireDateTime,empType;
			while(r.next()) {
				name = r.getString(1);
				surname = r.getString(2);
				salary  = r.getString(3);
				empType = r.getString(4);
				projectName  = r.getString(5);

				String[] row = {name,surname,salary,empType,projectName};
				
				model.addRow(row);
				}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
