import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.Icon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class HomePage extends JFrame {

	private JPanel HomePage;
	private JTextField ApplicantUser;
	private JPasswordField ApplicantPassword;
	private JButton Admin;
	private JButton btn_Login;
	private String urlToDatabase = "jdbc:mysql://localhost:3306/NCVAProjectsSystemDatabase";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomePage frame = new HomePage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public HomePage() {
		setResizable(false);
        setOpacity(1.0f);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1300, 800);
		HomePage = new JPanel();
		HomePage.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(HomePage);
		setLocationRelativeTo(null);
		HomePage.setLayout(null);
		
		//TEXT FIELDS ====================================================================================

		
		ApplicantUser = new JTextField();
		ApplicantUser.setBounds(874, 391, 246, 41);
		HomePage.add(ApplicantUser);
		ApplicantUser.setColumns(10);
		
		JCheckBox showHidePass = new JCheckBox("Show Password");
		showHidePass.setBounds(1004, 457, 114, 23);
		showHidePass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(showHidePass.isSelected()) {
					
					ApplicantPassword.setEchoChar((char)0);
					
				} else {
					
					ApplicantPassword.setEchoChar('•');
					
				}
			}
		});
		showHidePass.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		HomePage.add(showHidePass);
		
		ApplicantPassword = new JPasswordField();
		ApplicantPassword.setBounds(874, 484, 246, 41);
		HomePage.add(ApplicantPassword);
		
	
		//BUTTONS ====================================================================================
		
		//REGISTER
		JButton Register = new JButton("New button");
		Register.setBorder(null);
		Register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ApplicantRegistration register = new ApplicantRegistration();
                register.setVisible(true);
                dispose();
			}
		});
		Register.setIcon(new ImageIcon("/Users/russeljeromerapi/Desktop/NCVASystemUI/Buttons/HomePage/RegisterBtnn.png"));
		Register.setBounds(1018, 83, 102, 41);
		HomePage.add(Register);
		
		//ADMIN LOGIN
		Admin = new JButton("New button");
		Admin.setBorder(null);
		Admin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				AdminLogin AdminLog = new AdminLogin();
				AdminLog.setVisible(true);
				dispose();
				
			}
		});
		Admin.setIcon(new ImageIcon("/Users/russeljeromerapi/Desktop/NCVASystemUI/Buttons/HomePage/AdminBtn.png"));
		Admin.setBounds(1151, 89, 95, 29);
		HomePage.add(Admin);
		
		//SIGN IN BTN
		btn_Login = new JButton(new ImageIcon("/Users/russeljeromerapi/Desktop/NCVASystemUI/Buttons/HomePage/SIGN IN (ADMIN).png"));
		btn_Login.setBorder(null);
		btn_Login.setBounds(931, 536, 133, 40);
		btn_Login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	// For now, as there is no Username field for the Applicant table, I'll be using the Name column in lieu of the absent field.
            	String username = ApplicantUser.getText();
            	String password = new String(ApplicantPassword.getPassword());
            	
            	
            	
            	try {
            		Connection connection = DriverManager.getConnection(urlToDatabase, "root", "RJ@sql48%");
            		Statement st = connection.createStatement();
            		
            		String query = "SELECT * FROM Applicant WHERE NAME='" + username + "' AND PASSWORD='" + password +"'";
            		ResultSet rs = st.executeQuery(query);
            		if (rs.next()) {
            			JOptionPane.showMessageDialog(null, "Logged in successfully! Welcome, " + username + ".");
            			ApplicantHomeTab applicantHomeTab = new ApplicantHomeTab(rs.getString(1));
            			applicantHomeTab.setVisible(true);
            			dispose();
            		} else {
            			JOptionPane.showMessageDialog(null, "Incorrect credentials entered. Try again.");
            		}
            		
            	
            		// Should probably check what this does.
            		st.close();
            		connection.close();
            	} catch (Exception ex) {
            		ex.printStackTrace();
            	}

            }
        });
		HomePage.add(btn_Login);
		
		
		
		//BACKGROUND====================================================================================
		
		JLabel HomeBG = new JLabel("New label");
		HomeBG.setIcon(new ImageIcon("/Users/russeljeromerapi/Desktop/NCVASystemUI/Pages/HOME PAGEUSER LOGIN__ _ .png"));
		HomeBG.setBounds(0, 0, 1300, 800);
		HomePage.add(HomeBG);
		
		
	
	
		


		
	}
}
