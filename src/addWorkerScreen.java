import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class addWorkerScreen extends JFrame {

	private JPanel contentPane;

	public addWorkerScreen() {
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
		
		JLabel nameLabel = new JLabel("İsim :");
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
		nameLabel.setBounds(50, 200, 140, 60);
		backgroundLabel.add(nameLabel);
		
		
		JLabel surnameLabel = new JLabel("Soy İsim :");
		surnameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		surnameLabel.setFont(new Font("Arial", Font.BOLD, 20));
		surnameLabel.setBounds(50, 250, 140, 60);
		backgroundLabel.add(surnameLabel);
		
		JLabel respLabel = new JLabel("Görev :");
		respLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		respLabel.setFont(new Font("Arial", Font.BOLD, 20));
		respLabel.setBounds(50, 300, 140, 60);
		backgroundLabel.add(respLabel);
		
		JLabel timeLabel = new JLabel("Çalışma Türü :");
		timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		timeLabel.setFont(new Font("Arial", Font.BOLD, 20));
		timeLabel.setBounds(50, 350, 140, 60);
		backgroundLabel.add(timeLabel);
		
		JTextField nameField = new JTextField();
		backgroundLabel.add(nameField);
		nameField.setFont(new Font("Arial", Font.BOLD, 20));
		nameField.setBounds(220, 215, 250, 30);
		nameField.setColumns(10);
		
		JTextField surnameField = new JTextField();
		backgroundLabel.add(surnameField);
		surnameField.setFont(new Font("Arial", Font.BOLD, 20));
		surnameField.setBounds(220, 265, 250, 30);
		surnameField.setColumns(10);
		
		JComboBox respBox = new JComboBox();
		respBox.setBackground(new Color(255, 255, 255));
		respBox.setModel(new DefaultComboBoxModel(new String[] {"Seçiniz...", "Designer", "Programmer", "Analyst", "Manager"}));
		respBox.setFont(new Font("Arial", Font.BOLD, 20));
		respBox.setBounds(220, 315, 250, 30);
		backgroundLabel.add(respBox);
		
		JComboBox timeBox = new JComboBox();
		timeBox.setBackground(new Color(255, 255, 255));
		timeBox.setModel(new DefaultComboBoxModel(new String[] {"Seçiniz...", "HalfTime", "FullTime"}));
		timeBox.setFont(new Font("Arial", Font.BOLD, 20));
		timeBox.setBounds(220, 365, 250, 30);
		backgroundLabel.add(timeBox);
		
		JButton addWorkerButton = new JButton("Yeni Çalışan Ekle");
		addWorkerButton.setBackground(new Color(248, 248, 255));
		addWorkerButton.setFocusPainted(false);
		addWorkerButton.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
		addWorkerButton.setFont(new Font("Arial", Font.PLAIN, 20));
		addWorkerButton.setHorizontalAlignment(SwingConstants.CENTER);
		addWorkerButton.setBounds(200, 430, 200, 70);
		backgroundLabel.add(addWorkerButton);
		addWorkerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (nameField.getText().isEmpty() || surnameField.getText().isEmpty()  || respBox.getSelectedItem() == "Seçiniz..." ||  timeBox.getSelectedItem() == "Seçiniz..." ) {
					JOptionPane.showMessageDialog(null, "Lütfen Tüm Bilgileri Doldurunuz!!", "Hata", JOptionPane.ERROR_MESSAGE);
				}else {
					YSoft ysoft= new YSoft();
					
					try {
						ysoft.addNewEmployee(nameField.getText(), surnameField.getText(),respBox.getSelectedItem().toString(), timeBox.getSelectedItem().toString());
					
						JOptionPane.showMessageDialog(null, "Çalışan Eklendi!!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
	                    mainScreen mainscrn = new mainScreen();
	                    mainscrn.setVisible(true);
	                    setVisible(false);
					}
					catch (Exception exc){
						JOptionPane.showMessageDialog(null, "Boyle Bir Calisan Var!!", "Hata", JOptionPane.ERROR_MESSAGE);
						
					}
					
							
					
				}
			}
		});
		
		
	
		
		
		
		
		
	}
}
