import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.Button;
import java.awt.Color;

import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class ApplicantRegistration extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JButton btnAdmin;
    private JButton btnLogIn;
    private JButton btnRegister;
    private JTextField txtName;
    private JTextField txtUsername;
    private JTextField txtEmail;
    private JTextField txtNumber;
    private JButton btnClear;
    private JButton btnAdd;
    private JLabel lblNewLabel;
    private JPasswordField Pass;
    private String urlToDatabase = "jdbc:mysql://localhost:3306/NCVAProjectsSystemDatabase";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApplicantRegistration frame = new ApplicantRegistration();
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
	public ApplicantRegistration() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1300, 800);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
     // TextField =================================================================================

        // NAME
        txtName = new JTextField();
        txtName.setBounds(496, 302, 398, 44);
        contentPane.add(txtName);
        txtName.setColumns(10);

        // Allow only characters for txtName
        txtName.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
                    e.consume();
                }
            }
        });

        // USERNAME
        txtUsername = new JTextField();
        txtUsername.setColumns(10);
        txtUsername.setBounds(496, 367, 398, 44);
        contentPane.add(txtUsername);

        // PASSWORD
        JCheckBox showHideButton = new JCheckBox("Show Password");
        showHideButton.setFont(new Font("Dialog", Font.PLAIN, 11));
        showHideButton.setBounds(900, 439, 114, 23);
        showHideButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (showHideButton.isSelected()) {
                    Pass.setEchoChar((char) 0);
                } else {
                    Pass.setEchoChar('*');
                }
            }
        });
        contentPane.add(showHideButton);

        Pass = new JPasswordField();
        Pass.setBounds(496, 429, 398, 44);
        contentPane.add(Pass);

        // EMAIL
        txtEmail = new JTextField();
        txtEmail.setColumns(10);
        txtEmail.setBounds(496, 494, 398, 44);

        // Verify email format for txtEmail
        txtEmail.setInputVerifier(new InputVerifier() {
            public boolean verify(JComponent input) {
                JTextField textField = (JTextField) input;
                String email = textField.getText().toLowerCase();

                if (email.endsWith("@gmail.com")) {
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid email address. Please use a Gmail address.");
                    return false;
                }
            }
        });

        // Add txtEmail to the content pane
        contentPane.add(txtEmail);

        // Add txtEmail to the content pane
        contentPane.add(txtEmail);

        //PHONE NUMBER
        txtNumber = new JTextField();
        txtNumber.setColumns(10);
        txtNumber.setBounds(496, 559, 398, 44);
        contentPane.add(txtNumber);

        // Set default text to include country code
        txtNumber.setText("+639-");

        // Allow only numeric input for txtNumber
        txtNumber.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                String text = txtNumber.getText();

                // Enforce country code format (e.g., "+639-")
                if (!Character.isDigit(c) || (text.startsWith("+639-") && text.length() == 14)) {
                    e.consume();
                    if (text.length() == 14) {
                        JOptionPane.showMessageDialog(null, "Maximum input length reached");
                    }
                }
            }
        });

        contentPane.add(txtNumber);
        // BUTTONS ====================================================================================

        // REGISTER btn
        btnRegister = new JButton(new ImageIcon("/Users/russeljeromerapi/Desktop/NCVASystemUI/Buttons/ApplicantRegistration/RegisterBtn1.png"));
        btnRegister.setBorder(null);
        btnRegister.setBounds(1015, 86, 104, 36);
        contentPane.add(btnRegister);

        // APPLICANT LOGIN
        btnLogIn = new JButton(new ImageIcon("/Users/russeljeromerapi/Desktop/NCVASystemUI/Buttons/ApplicantRegistration/LoginBtn.png"));
        btnLogIn.setBorder(null);
        btnLogIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                HomePage homePage = new HomePage();
                homePage.setVisible(true);
                dispose();
            }
        });
        btnLogIn.setBounds(920, 86, 73, 29);
        contentPane.add(btnLogIn);

        // ADMIN LOGIN
        btnAdmin = new JButton(new ImageIcon("/Users/russeljeromerapi/Desktop/NCVASystemUI/Buttons/ApplicantRegistration/AdminBtn.png"));
        btnAdmin.setBorder(null);
        btnAdmin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                AdminLogin AdminLog = new AdminLogin();
                AdminLog.setVisible(true);
                dispose();

            }
        });
        btnAdmin.setBounds(1150, 86, 80, 30);
        contentPane.add(btnAdmin);
        setLocationRelativeTo(null);

        // btn ADD, DELETE =============================================================

        // ADD BUTTON
        btnAdd = new JButton(new ImageIcon("/Users/russeljeromerapi/Desktop/NCVASystemUI/Buttons/ApplicantRegistration/btnAdd.png"));
        btnAdd.setBorder(null);
        btnAdd.setBounds(541, 655, 113, 36);
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String name = txtName.getText();
            	String username = txtUsername.getText();
            	String password = new String(Pass.getPassword());
            	String email = txtEmail.getText();
            	String phoneNumber = txtNumber.getText();
            	
            	
            	
            	try {
            		Connection connection = DriverManager.getConnection(urlToDatabase, "root", "RJ@sql48%");
            		
            		String query = "INSERT INTO Applicant (Name, Password, Email, `Mobile Phone`)"
            				+ " VALUES('" + name + "','" + password + "','" + email + "','" + phoneNumber + "')";
            		
            		Statement st = connection.createStatement();
            		int x = st.executeUpdate(query);
            		// Should probably check what this does.
            		if (x == 0) {
            			// Should probably edit this message.
            			JOptionPane.showMessageDialog(btnAdd, "This record already exists.");
            		} else {
            			JOptionPane.showMessageDialog(btnAdd, "Welcome, " + name + ". Your account has been created successfully.");
            		}
            		connection.close();
            	} catch (Exception ex) {
            		ex.printStackTrace();
            	}

            }
        });
        contentPane.add(btnAdd);

        // CLEAR BUTTON
        btnClear = new JButton(new ImageIcon("/Users/russeljeromerapi/Desktop/NCVASystemUI/Buttons/ApplicantRegistration/btnClear.png"));
        btnClear.setBorder(null);
        btnClear.setBounds(680, 655, 113, 36);
        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                txtName.setText("");
                txtUsername.setText("");
                Pass.setText("");
                txtEmail.setText("");
                txtNumber.setText("");
                txtNumber.setText("+639-");

            }
        });
        contentPane.add(btnClear);

        // Background ================================================================================

        JLabel lbl_AdminLogBG = new JLabel(new ImageIcon("/Users/russeljeromerapi/Desktop/NCVASystemUI/Pages/ApplicantRegistration.png"));
        lbl_AdminLogBG.setBounds(0, 0, 1300, 800);
        contentPane.add(lbl_AdminLogBG);

    }
}