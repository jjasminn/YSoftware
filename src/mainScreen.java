import javax.swing.*;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.BevelBorder;

public class mainScreen extends JFrame {

	private JPanel contentPane;

	public mainScreen() {
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
		
		JButton dispWorkerButton = new JButton("  Çalışanları Görüntüle");
		dispWorkerButton.setBackground(new Color(248, 248, 255));
		dispWorkerButton.setFocusPainted(false);
		dispWorkerButton.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
		dispWorkerButton.setFont(new Font("Arial", Font.PLAIN, 24));
		dispWorkerButton.setHorizontalAlignment(SwingConstants.LEFT);
		dispWorkerButton.setBounds(50, 280, 350, 70);
		backgroundLabel.add(dispWorkerButton);
		dispWorkerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayWorkersScreen displayWorkers = new displayWorkersScreen();
				displayWorkers.setVisible(true);
				setVisible(false);
			}
		});
		
		JButton projectsButton = new JButton("  Projeler");
		projectsButton.setBackground(new Color(248, 248, 255));
		projectsButton.setFocusPainted(false);
		projectsButton.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
		projectsButton.setFont(new Font("Arial", Font.PLAIN, 24));
		projectsButton.setHorizontalAlignment(SwingConstants.LEFT);
		projectsButton.setBounds(50, 180, 350, 70);
		backgroundLabel.add(projectsButton);
		projectsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				projectsScreen projects = new projectsScreen();
				projects.setVisible(true);
				setVisible(false);
			}
		});
		
		JButton newWorkerButton = new JButton("  Yeni Çalışan Ekle");
		newWorkerButton.setBackground(new Color(248, 248, 255));
		newWorkerButton.setFocusPainted(false);
		newWorkerButton.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
		newWorkerButton.setFont(new Font("Arial", Font.PLAIN, 24));
		newWorkerButton.setHorizontalAlignment(SwingConstants.LEFT);
		newWorkerButton.setBounds(50, 380, 350, 70);
		backgroundLabel.add(newWorkerButton);
		newWorkerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addWorkerScreen addWorker = new addWorkerScreen();
				addWorker.setVisible(true);
				setVisible(false);
			}
		});
		
	}
	
	
	
}
