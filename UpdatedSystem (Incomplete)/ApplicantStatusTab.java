import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

public class ApplicantStatusTab extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private JTextField searchField;
    private JTextField textFieldName;
    private JTextField textFieldEmail;
    private JTextField textFieldPhone;
    public static String applicantID;
    private String urlToDatabase = "jdbc:mysql://localhost:3306/NCVAProjectsSystemDatabase";
    private JTable appliedPositionsTable;
    private DefaultTableModel apTableModel;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	ApplicantStatusTab frame = new ApplicantStatusTab(applicantID);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ApplicantStatusTab(String applicantID) {
    	setResizable(false);
        setOpacity(1.0f);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1300, 800);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setLocationRelativeTo(null);

        // LOG OUT Button
        JButton btn_LogOut = new JButton("");
        btn_LogOut.setBorder(null);
        btn_LogOut.setContentAreaFilled(false);
        btn_LogOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logoutAction();
            }
        });
        btn_LogOut.setIcon(new ImageIcon("/Users/russeljeromerapi/Desktop/NCVASystemUI/Buttons/ApplicantProcess/logoutbtnicon.png"));
        btn_LogOut.setBounds(1139, 96, 96, 40);
        contentPane.add(btn_LogOut);

        // HOME
        JButton HomeBtn = new JButton("Home");
        HomeBtn.setBorder(null);
        HomeBtn.setContentAreaFilled(false);
        HomeBtn.setFont(new Font("SansSerif", Font.BOLD, 20));
        HomeBtn.setBounds(275, 104, 85, 21);
        HomeBtn.setMargin(new Insets(0, 0, 0, 0));
        HomeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                ApplicantHomeTab HomeTab = new ApplicantHomeTab(applicantID);
                HomeTab.setVisible(true);
                
            }
        });
        contentPane.add(HomeBtn);

        // POSITIONS
        JButton PositionsBtn = new JButton("Positions");
        PositionsBtn.setBorder(null);
        PositionsBtn.setContentAreaFilled(false);
        PositionsBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        PositionsBtn.setBounds(375, 104, 85, 21);
        PositionsBtn.setMargin(new Insets(0, 0, 0, 0));
        PositionsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	dispose();
                ApplicantPositionTab positionTab = new ApplicantPositionTab(applicantID);
                positionTab.setVisible(true);
            	
            }
        });
        contentPane.add(PositionsBtn);

        // APPLY
        JButton ApplyBtn = new JButton("Apply");
        ApplyBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	dispose();
                ApplicantProcessApplyTab applyTab = new ApplicantProcessApplyTab(applicantID);
                applyTab.setVisible(true);
            	
            }
        });
        ApplyBtn.setBorder(null);
        ApplyBtn.setContentAreaFilled(false);
        ApplyBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        ApplyBtn.setBounds(475, 104, 85, 21);
        ApplyBtn.setMargin(new Insets(0, 0, 0, 0));
        contentPane.add(ApplyBtn);

        // BLUELINE FOR APPLY BUTTON
        JLabel blueLine = new JLabel("");
        blueLine.setIcon(new ImageIcon("/Users/russeljeromerapi/Desktop/NCVASystemUI/Pages/blueline.png"));
        blueLine.setBounds(585, 90, 59, 40);
        contentPane.add(blueLine);

        // STATUS
        JButton StatusBtn = new JButton("Status");
        StatusBtn.setBorder(null);
        StatusBtn.setContentAreaFilled(false);
        StatusBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        StatusBtn.setBounds(575, 104, 85, 21);
        StatusBtn.setMargin(new Insets(0, 0, 0, 0));
        contentPane.add(StatusBtn);
        
        // maybe if instead of showing applicant information in this panel, show a table of all the positions that the applicant applied to 
        // (operating under the assumption that a single applicant can apply to many positions.) (ask to confirm today)
        JPanel panel = new JPanel();
        panel.setBounds(146, 211, 922, 537);
        contentPane.add(panel);
        panel.setLayout(null);
        
        JLabel lblName = new JLabel("Name");
        lblName.setFont(new Font("Dialog", Font.PLAIN, 18));
        lblName.setBounds(32, 136, 59, 16);
        panel.add(lblName);
        
        JLabel lblEmail = new JLabel("Email Address");
        lblEmail.setFont(new Font("Dialog", Font.PLAIN, 18));
        lblEmail.setBounds(32, 254, 134, 16);
        panel.add(lblEmail);
        
        JLabel lblPhoneNumber = new JLabel("Phone Number");
        lblPhoneNumber.setFont(new Font("Dialog", Font.PLAIN, 18));
        lblPhoneNumber.setBounds(32, 376, 134, 16);
        panel.add(lblPhoneNumber);
        
        JLabel lblAppliedPositions = new JLabel("APPLIED POSITION(S):");
        lblAppliedPositions.setFont(new Font("Dialog", Font.PLAIN, 18));
        lblAppliedPositions.setBounds(447, 100, 196, 16);
        panel.add(lblAppliedPositions);
        
        JLabel lblApplicantsInfo = new JLabel("APPLICANT'S INFORMATION PANEL");
        lblApplicantsInfo.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
        lblApplicantsInfo.setBounds(298, 31, 416, 30);
        panel.add(lblApplicantsInfo);
        
        textFieldName = new JTextField();
        textFieldName.setBounds(32, 164, 360, 30);
        panel.add(textFieldName);
        textFieldName.setColumns(10);
        
        textFieldEmail = new JTextField();
        textFieldEmail.setColumns(10);
        textFieldEmail.setBounds(32, 282, 359, 30);
        panel.add(textFieldEmail);
        
        textFieldPhone = new JTextField();
        textFieldPhone.setColumns(10);
        textFieldPhone.setBounds(32, 404, 359, 30);
        panel.add(textFieldPhone);
        
        appliedPositionsTable = new JTable();
        appliedPositionsTable.setBounds(447, 139, 447, 376);
        
        panel.add(appliedPositionsTable);
        
        apTableModel = (DefaultTableModel) appliedPositionsTable.getModel();
        
        try {
    		Connection connection = DriverManager.getConnection(urlToDatabase, "root", "RJ@sql48%");
    		
    		String jobReferenceQuery = "SELECT Title FROM JobPosition WHERE JobID = ?";
    		String fromApplicationQuery = "SELECT JobID, Status FROM Application WHERE ApplicantID = ?";
    		String applicantInfoQuery = "SELECT Name, Email, `Mobile Phone` FROM Applicant WHERE ApplicantID = ?";

    		PreparedStatement p1 = connection.prepareStatement(applicantInfoQuery);
    		p1.setString(1, applicantID);
    		
    		String[] columns = {"Position", "Status"};
    		apTableModel.setColumnIdentifiers(columns);
    		
    		ResultSet applicantInfo = p1.executeQuery();
    		if (applicantInfo.next()) {
    			textFieldName.setText(applicantInfo.getString("Name"));
    			textFieldEmail.setText(applicantInfo.getString("Email"));
    			textFieldPhone.setText(applicantInfo.getString("Mobile Phone"));
    		}
    		
    		PreparedStatement p2 = connection.prepareStatement(fromApplicationQuery);
    		p2.setString(1, applicantID);
    		
    		PreparedStatement p3 = connection.prepareStatement(jobReferenceQuery);
    		
    		String status = "", title = "", referenceJobID = "";
    		
    		
    		ArrayList<String[]> rows = new ArrayList<>();
    		
    		ResultSet appliedJob = p2.executeQuery();
    		while (appliedJob.next()) {
    			status = appliedJob.getString("Status");
    			referenceJobID = appliedJob.getString("JobID");
    			
    			
    		}
    		
    		p3.setString(1, referenceJobID);
			ResultSet nameOfJob = p3.executeQuery();
			
			if(nameOfJob.next()) {
    			nameOfJob.getString("Title");
    			
    			rows.add(new String[] {title, status});
    		}
    		
    	
    		p1.close();
    		p2.close();
    		p3.close();
    		connection.close();
    		
    		for (String[] row : rows) {
    			apTableModel.addRow(row);
    		}
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}

        appliedPositionsTable.setModel(apTableModel);

        // BACKGROUND
        JLabel lbl_ApplyTabBG = new JLabel(new ImageIcon("/Users/russeljeromerapi/Desktop/NCVASystemUI/Pages/STATUS_TAB.png"));
        lbl_ApplyTabBG.setBounds(0, 0, 1300, 800);
        contentPane.add(lbl_ApplyTabBG);
    }

    // Logout Action Method
    private void logoutAction() {
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to Logout?", "Logout", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            dispose();
            HomePage homepage = new HomePage();
            homepage.setVisible(true);
        }
    }
}
