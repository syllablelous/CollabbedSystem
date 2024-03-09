import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class ApplicantProcessApplyTab extends JFrame {

    private JPanel contentPane;
    private JTable ApplicationTable; // Changed variable name here
    private JTextField searchField;
    private JTextField textFieldName;
    private JTextField textFieldPosition;
    DefaultTableModel tableModel;
    private String urlToDatabase = "jdbc:mysql://localhost:3306/NCVAProjectsSystemDatabase";
    public static String applicantID;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ApplicantProcessApplyTab frame = new ApplicantProcessApplyTab(applicantID);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ApplicantProcessApplyTab(String applicantID) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(false);
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
        blueLine.setIcon(new ImageIcon("Pages\\blueline.png"));
        blueLine.setBounds(485, 90, 59, 40);
        contentPane.add(blueLine);

        // STATUS
        JButton StatusBtn = new JButton("Status");
        StatusBtn.setBorder(null);
        StatusBtn.setContentAreaFilled(false);
        StatusBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        StatusBtn.setBounds(575, 104, 85, 21);
        StatusBtn.setMargin(new Insets(0, 0, 0, 0));
        StatusBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                ApplicantStatusTab statusTab = new ApplicantStatusTab(applicantID);
                statusTab.setVisible(true);

            }
        });
        contentPane.add(StatusBtn);
        
        
        // APPLICATION FORM PANEL
        JPanel formPanel = new JPanel();
        formPanel.setBounds(73, 242, 587, 486);
        contentPane.add(formPanel);
        formPanel.setLayout(null);
        
        JButton btnApplyButton = new JButton("Apply");
        btnApplyButton.setBounds(185, 412, 172, 56);
        btnApplyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String nameReference = textFieldName.getText();
            	String positionReference = textFieldPosition.getText();
            	
            	String applicantQuery = "SELECT ApplicantID FROM Applicant WHERE Name = ?";
        		
        		String jobQuery = "SELECT JobID FROM JobPosition WHERE Title = ?";
        		
        		String applyQuery = "INSERT INTO Application (ApplicantID, JobID, Status) VALUES (?, ?, 'Pending')";
        		int applicantsID = 0, jobID = 0;
        		
            	
                try {
            		Connection applyConnection = DriverManager.getConnection(urlToDatabase, "root", "RJ@sql48%");
            		
            		
            		
            		PreparedStatement p1 = applyConnection.prepareStatement(applicantQuery);
            		
            		p1.setString(1, nameReference);
            		
            		PreparedStatement p2 = applyConnection.prepareStatement(jobQuery);
            		
            		p2.setString(1, positionReference);
            		
            		ResultSet rsApplicant = p1.executeQuery();
            		if (rsApplicant.next()) {
            			applicantsID = rsApplicant.getInt("ApplicantID");
            		}
            		
            		
            		ResultSet rsJob = p2.executeQuery();
            		if (rsJob.next()) {
            			jobID = rsJob.getInt("JobID");
            		}
            		
            		PreparedStatement p3 = applyConnection.prepareStatement(applyQuery);
            		p3.setString(1, Integer.toString(applicantsID));
            		p3.setString(2, Integer.toString(jobID));
            		
            		int x = p3.executeUpdate();
            		// Should probably check what this does.
            		if (x == 0) {
            			// Should probably edit this message.
            			JOptionPane.showMessageDialog(btnApplyButton, "An error has occured. Failed to apply for position.");
            		} else {
            			JOptionPane.showMessageDialog(btnApplyButton, "Congratulations, " + nameReference + 
            					"! You have successfully applied for the position of " + positionReference + ". Check your status page to keep track of your applied position.");
            		}
            		
            		p1.close();
            		p2.close();
            		p3.close();

            		applyConnection.close();
                	
                } catch (Exception applyBtnException) {
                	applyBtnException.printStackTrace();
                }
            }
        });
        formPanel.add(btnApplyButton);
        
        textFieldName = new JTextField();
        textFieldName.setBounds(165, 121, 390, 53);
        formPanel.add(textFieldName);
        textFieldName.setColumns(10);
        textFieldName.setEditable(false);
        
        textFieldPosition = new JTextField();
        textFieldPosition.setColumns(10);
        textFieldPosition.setBounds(165, 234, 390, 53);
        textFieldPosition.setEditable(false);
        formPanel.add(textFieldPosition);
        
        JLabel lblName = new JLabel("APPLICANT NAME");
        lblName.setBounds(41, 130, 112, 35);
        formPanel.add(lblName);
        
        JLabel lblPosition = new JLabel("JOB POSITION\n");
        lblPosition.setBounds(41, 243, 112, 35);
        formPanel.add(lblPosition);
        
        JLabel lblNewLabel = new JLabel("APPLICATION PANEL");
        lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
        lblNewLabel.setBounds(148, 14, 315, 95);
        formPanel.add(lblNewLabel);
        
        JTextArea textAboutPosition = new JTextArea();
        textAboutPosition.setBounds(41, 299, 514, 86);
        textAboutPosition.setLineWrap(true);
        formPanel.add(textAboutPosition);
        
        
        
        
        // TABLE
        ApplicationTable = new JTable(); // Changed variable name here
        ApplicationTable.setPreferredScrollableViewportSize(new Dimension(1170, 489));
        ApplicationTable.setFillsViewportHeight(true);
        ApplicationTable.setBackground(new Color(184, 182, 182));
        ApplicationTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int i = ApplicationTable.getSelectedRow();
				textFieldPosition.setText(tableModel.getValueAt(i,0).toString());
				textAboutPosition.setText("For the position of " + tableModel.getValueAt(i,0).toString() + ", "
						+ "you will be handling the task of: " + tableModel.getValueAt(i,1).toString() + "\n" 
						+ "For applying in this position, you will receive a salary of " + tableModel.getValueAt(i,2).toString() + ".");
			}
		});

        JTableHeader header = ApplicationTable.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 18));

        JScrollPane scrollPane = new JScrollPane(ApplicationTable); // Changed variable name here
        scrollPane.setBounds(662, 240, 582, 489);
        contentPane.add(scrollPane);
        
        tableModel = (DefaultTableModel) ApplicationTable.getModel();
        
        try {
    		Connection connection = DriverManager.getConnection(urlToDatabase, "root", "RJ@sql48%");
    		
    		String selectQuery = "SELECT Title, Task, Salary FROM JobPosition;";
    		
    		Statement st = connection.createStatement();
    		ResultSet rs = st.executeQuery(selectQuery);
    		ResultSetMetaData rsmd = rs.getMetaData();
    		
    		String applicantQuery = "SELECT * FROM Applicant WHERE ApplicantID = " + applicantID + "";
    		Statement stApp = connection.createStatement();
    		ResultSet rsApplicant = stApp.executeQuery(applicantQuery);
    		
    		// maybe set applicant's name text field to not editable
    		String loggedApplicant = "";
    		while(rsApplicant.next()) {
    			loggedApplicant = rsApplicant.getString(2);
    		}
    		
    		textFieldName.setText(loggedApplicant);
    		
    		// TABLE COLUMNS - POSITIONS TABLE
    		int columns = rsmd.getColumnCount();
    		String[] columnNames = new String[columns];
    		
    		for (int i = 0; i < columns; i++) {
    			columnNames[i] = rsmd.getColumnName(i + 1);
    		}
    		
    		tableModel.setColumnIdentifiers(columnNames);
    		
    		
    		// TABLE ROWS - POSITIONS TABLE
    		String title, task, salary;
    		
    		while (rs.next()) {
    			title = rs.getString(1);
    			task = rs.getString(2);
    			salary = rs.getString(3);
    			String[] row = {title, task, salary};
    			tableModel.addRow(row);
    		}
    		
    		
    		st.close();
    		stApp.close();
    		connection.close();
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
        
        

        // SEARCH
        searchField = new JTextField();
        searchField.setFont(new Font("SansSerif", Font.BOLD, 22));
        searchField.setBounds(146, 162, 828, 57);
        searchField.setBorder(null);
        searchField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String query = searchField.getText();
				filter(query);
			}

		});
        searchField.setColumns(10);
        searchField.setBackground(new Color(228, 228, 228));
        contentPane.add(searchField);
        

        JButton searchBtn = new JButton("");
        searchBtn.setOpaque(false);
        searchBtn.setContentAreaFilled(false);
        searchBtn.setBorderPainted(false);
        searchBtn.setBorder(null);
        searchBtn.setBounds(975, 161, 180, 56);
        contentPane.add(searchBtn);

        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "You have clicked the search button!");
            }
        });
        
        

        // BACKGROUND
        JLabel lbl_ApplyTabBG = new JLabel(new ImageIcon("/Users/russeljeromerapi/Desktop/NCVASystemUI/Pages/APPLY_TAB.png"));
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
    
    private void filter(String query) {
		DefaultTableModel model = (DefaultTableModel)ApplicationTable.getModel();
		TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
		ApplicationTable.setRowSorter(tr);
		
		tr.setRowFilter(RowFilter.regexFilter("(?i)" + query));
	}
}
