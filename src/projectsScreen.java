import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


public class projectsScreen extends JFrame {

	private JPanel contentPane;
	Connection conn;
	Statement s ;

	public projectsScreen() {
		try {
	          conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/YSoft", "jasmin", "postgresql");
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
		JLabel backgroundLabel = new JLabel(/*backgroundMain*/);
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
		
		JLabel chooseTable = new JLabel("Proje İşlemleri :");
		chooseTable.setHorizontalAlignment(SwingConstants.LEFT);
		chooseTable.setFont(new Font("Arial", Font.PLAIN, 20));
		chooseTable.setBounds(55, 150, 160, 60);
		backgroundLabel.add(chooseTable);
		
		JComboBox chooseBox = new JComboBox();
		chooseBox.setBackground(new Color(255, 255, 255));
		chooseBox.setModel(new DefaultComboBoxModel(new String[] {"Seçiniz...", "Aktif Projeleri Göster", "Aktif Olmayan Projeleri Göster", "Yeni Proje Başlat"}));
		chooseBox.setFont(new Font("Arial", Font.PLAIN, 20));
		chooseBox.setBounds(220, 165, 300, 30);
		backgroundLabel.add(chooseBox);
		
		
		DefaultTableModel tableModel = new DefaultTableModel() {

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		
		
		JButton chooseButton = new JButton("Onayla");
		chooseButton.setBackground(new Color(248, 248, 255));
		chooseButton.setFocusPainted(false);
		chooseButton.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
		chooseButton.setFont(new Font("Arial", Font.PLAIN, 20));
		chooseButton.setHorizontalAlignment(SwingConstants.CENTER);
		chooseButton.setBounds(520, 165, 80, 30);
		backgroundLabel.add(chooseButton);
		chooseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chooseBox.getSelectedItem().toString() == "Seçiniz...") {
					JOptionPane.showMessageDialog(null, "Lütfen Seçim Yapınız!!", "Hata", JOptionPane.ERROR_MESSAGE);
				}else if(chooseBox.getSelectedItem().toString() == "Aktif Projeleri Göster") {
					chooseBox.setEnabled(false);
					chooseButton.setVisible(false);
					
					JScrollPane activeProjectsScrollPane = new JScrollPane();
					activeProjectsScrollPane.setBounds(50, 200, 600, 400);
					backgroundLabel.add(activeProjectsScrollPane);
					
					JTable activeProjectsTable = new JTable();
					activeProjectsTable.setFont(new Font("Arial", Font.PLAIN, 10));
					activeProjectsTable.setRowSelectionAllowed(false);
					activeProjectsScrollPane.setViewportView(activeProjectsTable);

					String queryProjects= "SELECT * FROM project WHERE is_project_active = 'True' ";
					try {
						ResultSet r = s.executeQuery(queryProjects);
						ResultSetMetaData rsmd = r.getMetaData();
						DefaultTableModel  model =  (DefaultTableModel)activeProjectsTable.getModel();
						int cols = rsmd.getColumnCount();
						String[] colName = new String[cols];
						for(int i=0; i<cols; i++)
							colName[i] = rsmd.getColumnName(i+1);
						model.setColumnIdentifiers(colName);
						String identificationNumber, name, surname,salary,projectID,hireDateTime;
						while(r.next()) {
							identificationNumber = r.getString(1);
							name = r.getString(2);
							surname  = r.getString(3);
							salary  = r.getString(4);
							projectID  = r.getString(5);
							hireDateTime = r.getString(6);
							String[] row = {identificationNumber,name,surname,salary,projectID, hireDateTime};
							model.addRow(row);
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
						
				}else if(chooseBox.getSelectedItem().toString() == "Aktif Olmayan Projeleri Göster") {
					chooseBox.setEnabled(false);
					chooseButton.setVisible(false);
					
					JScrollPane noActiveProjectsScrollPane = new JScrollPane();
					noActiveProjectsScrollPane.setBounds(50, 200, 600, 400);
					backgroundLabel.add(noActiveProjectsScrollPane);
					
					JTable noActiveProjectsTable = new JTable();
					noActiveProjectsTable.setFont(new Font("Arial", Font.PLAIN, 10));
					noActiveProjectsTable.setRowSelectionAllowed(false);
					noActiveProjectsScrollPane.setViewportView(noActiveProjectsTable);
					noActiveProjectsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					noActiveProjectsTable.setModel(tableModel);

					String queryProjects= "SELECT project_name,employee.name,number_of_analyst,number_of_designer,number_of_programmer FROM project,employee WHERE is_project_active = 'false' AND manager_id=identification_number ";
					try {
						ResultSet r = s.executeQuery(queryProjects);
						ResultSetMetaData rsmd = r.getMetaData();
						DefaultTableModel  model =  (DefaultTableModel)noActiveProjectsTable.getModel();
						int cols = rsmd.getColumnCount();
						String[] colName = new String[cols];
						for(int i=0; i<cols; i++)
							colName[i] = rsmd.getColumnName(i+1);
						model.setColumnIdentifiers(colName);
						String emp_name , name, surname,salary,projectID,hireDateTime;
						while(r.next()) {
							name = r.getString(1);
							emp_name = r.getString(2);
							salary  = r.getString(3);
							projectID  = r.getString(4);
							hireDateTime = r.getString(5);
							String[] row = {name,emp_name,salary,projectID, hireDateTime};
							model.addRow(row);
							JButton activeButton = new JButton("Projeyi Aktif Et");
							activeButton.setVisible(true);
							activeButton.setBackground(new Color(248, 248, 255));
							activeButton.setFocusPainted(false);
							activeButton.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
							activeButton.setFont(new Font("Arial", Font.PLAIN, 20));
							activeButton.setHorizontalAlignment(SwingConstants.CENTER);
							activeButton.setBounds(230, 610, 200, 40);
							backgroundLabel.add(activeButton);
							activeButton.addActionListener( new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent arg0) {
									// TODO Auto-generated method stub
									YSoft ysoft = new YSoft();
									//boolean activated = ysoft.makeProjectActive(name);
									
									
									
								}
								
							});


						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
						
				}
			}
		});
		
	}
	

}