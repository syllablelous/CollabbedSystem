/*
 * In regards to the program, to implement CRUD SQL-style in our program, we need to make sure that we tackle its four facets: 
 * CREATE (done by INSERT INTO commands), READ (done by SELECT commands), UPDATE (done by UPDATE commands), and DELETE (done by DELETE commands)
 * System's implementation of CRUD is best demonstrated in JobPosting.java, as it involves all CRUD operations
 */

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class JobPosting {

	public JFrame JobPosting;
	private JTextField PosCode;
	public JTextField JobTitle;
	private JTextField Responsibilities;
	private JTextField Salary;
	private JTable table_1;
	private JScrollPane scrollPane;
    private JTable dash_table;
	private JTable App_table;
	DefaultTableModel model;
	private String urlToDatabase = "jdbc:mysql://localhost:3306/NCVAProjectsSystemDatabase";

    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JobPosting window = new JobPosting();
					window.JobPosting.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//METHODS ====================================================================================


    
   // ====================================================================================

	/**
	 * Create the application.
	 */
	public JobPosting() {
        this.dash_table = dash_table;
        this.App_table = App_table;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		JobPosting = new JFrame();
		JobPosting.setBounds(100, 100, 1300, 800);
		JobPosting.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JobPosting.setResizable(false);
		JobPosting.setLocationRelativeTo(null);
		JobPosting.getContentPane().setLayout(null);
		
		//TEXT FIELDS ====================================================================================
		
		//Position Code
		PosCode = new JTextField();
		PosCode.setBounds(1019, 261, 233, 60);
		JobPosting.getContentPane().add(PosCode);
		PosCode.setColumns(10);
		
		//Job Title
		JobTitle = new JTextField();
		JobTitle.setBounds(1019, 345, 233, 60);
		JobPosting.getContentPane().add(JobTitle);
		JobTitle.setColumns(10);
		
		//Responsibilites
		Responsibilities = new JTextField();
		Responsibilities.setBounds(1019, 424, 233, 60);
		JobPosting.getContentPane().add(Responsibilities);
		Responsibilities.setColumns(10);
		
		//Salary
		Salary = new JTextField();
		Salary.setBounds(1019, 496, 233, 60);
		Salary.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				String SalaryNum = Salary.getText();

				//get length of string
				int length = SalaryNum.length();
				
				char c = e.getKeyChar();
				
				//check for numbers 0-9
				if (e.getKeyChar() >= '0' && e.getKeyChar() <= '9') {
					
					if(length<6) {
						//Editable 
						Salary.setEditable(true);
					
					} else {
						//Not editable if lenghth is more than 6
						Salary.setEditable(false);
					}
					
				} else if (e.getExtendedKeyCode()== KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode()== KeyEvent.VK_DELETE) {
					
					Salary.setEditable(true);
					
				} else {
					
					Salary.setEditable(false);
					JOptionPane.showMessageDialog(null,"Only Input Numbers from 0 to 9");

				}						
			}

		});
		JobPosting.getContentPane().add(Salary);
		Salary.setColumns(10);
		
		
		//Scroll Panel ====================================================================================

		//Add JScrollPane to show values in table
		scrollPane = new JScrollPane();
		scrollPane.setBounds(49, 213, 782, 504);
		scrollPane.addMouseListener(new MouseAdapter() {	
		public void mouseClicked(MouseEvent e) {
				
				int i = table_1.getSelectedRow();
				PosCode.setText(model.getValueAt(i, 0).toString());
				JobTitle.setText(model.getValueAt(i, 1).toString());
				Responsibilities.setText(model.getValueAt(i, 2).toString());
				Salary.setText(model.getValueAt(i, 3).toString());
			}
		});
		JobPosting.getContentPane().add(scrollPane);
		
		//TABLE ====================================================================================
		
		table_1 = new JTable();
		table_1.setShowGrid(true);
		table_1.setShowHorizontalLines(true);
		table_1.setGridColor(Color.black);  
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int i = table_1.getSelectedRow();
				PosCode.setText(model.getValueAt(i,0).toString());
				JobTitle.setText(model.getValueAt(i,1).toString());
				Responsibilities.setText(model.getValueAt(i,2).toString());
				Salary.setText(model.getValueAt(i,3).toString());

			}
		});
		model = new DefaultTableModel();
		Object[] column = {"       Position Code","          Job Title", "    Responsibilities", "            Salary"};
		final Object[] row = new Object[4];
		model.setColumnIdentifiers(column);
		table_1.setModel(model);
		scrollPane.setViewportView(table_1);
		
		//BUTTONS ====================================================================================
						
		
		//ADD BUTTON
		JButton Add = new JButton("");
		Add.setBorder(null);
		Add.setIcon(new ImageIcon("/Users/russeljeromerapi/Desktop/NCVASystemUI/Buttons/Job Posting/POSTING (ADD)_ .png"));
		Add.setBounds(910, 603, 102, 40);
		Add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(table_1, "Review your added inputs first. Are you sure that the data that you've entered is correct?", 
		    			"Adding Confirmation Window", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					int posCode = Integer.parseInt(PosCode.getText());
					String title = JobTitle.getText();
					String tasks = Responsibilities.getText();
					float salary = Float.parseFloat(Salary.getText());
					
					try {
	            		Connection connection = DriverManager.getConnection(urlToDatabase, "root", "RJ@sql48%");
	            		
	            		String insertQuery = "INSERT INTO JobPosition (JobID, Title, Task, Salary)"
	            				+ " VALUES('" + posCode + "','" + title + "','" + tasks + "','" + salary + "')";
	            		
	            		Statement st = connection.createStatement();
	            		// Should add check-case to see if inputted record already exists.
	            		int x = st.executeUpdate(insertQuery);
	            		// Should probably check what this does.
	            		if (x == 0) {
	            			JOptionPane.showMessageDialog(Add, "An error has occured: Failed to add record.");
	            		} else {
	            			JOptionPane.showMessageDialog(Add, "Inputted job position has been successfully added!");
	            		}
	            		connection.close();
	            	} catch (Exception ex) {
	            		ex.printStackTrace();
	            	}
				} else {
					JOptionPane.showMessageDialog(Add, "Adding of input canceled.");
				}
			} 

		});
		JobPosting.getContentPane().add(Add);
		
		//UPDATE BUTTON
		JButton Update = new JButton("");
		Update.setBorder(null);
		Update.setIcon(new ImageIcon("/Users/russeljeromerapi/Desktop/NCVASystemUI/Buttons/Job Posting/POSTING (UPDATE)  _ .png"));
		Update.setBounds(1033, 603, 102, 40);
		Update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Check first if there's a selected row on the table
				// Check for inputs in the textfields
				// Verify with the user if the information inputted on the fields are what they would want.
				// If yes, proceed to update the table
				int selectedRow = table_1.getSelectedRow();
				
				if (selectedRow >= 0) {
					if (!(PosCode.getText().isBlank()) && !(JobTitle.getText().isBlank()) && !(Responsibilities.getText().isBlank()) 
							&& !(Salary.getText().isBlank())) {
						int result = JOptionPane.showConfirmDialog(table_1, "Review your updated inputs first. Are you sure that the data that you've entered is correct?", 
		    			"Update Confirmation Window", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (result == JOptionPane.YES_OPTION) {
							int posCode = Integer.parseInt(PosCode.getText());
							String title = JobTitle.getText();
							String tasks = Responsibilities.getText();
							float salary = Float.parseFloat(Salary.getText());
							
							String originalCode = (String) table_1.getValueAt(selectedRow, 0);
							
							try {
			            		Connection connection = DriverManager.getConnection(urlToDatabase, "root", "RJ@sql48%");
			            		
			            		
			            		// There might be a possibility that Position Code would not or cannot be changed.
			            		String updateQuery = "UPDATE JobPosition SET JobID = '" + posCode + "', Title = '" + title + "', "
			            				+ "Task = '" + tasks + "', Salary = '" + salary + "' WHERE JobID = " + originalCode + ";";
			            		
			            		Statement st = connection.createStatement();
			            		// Should add check-case to see if inputted record already exists.
			            		int x = st.executeUpdate(updateQuery);
			            		// Should probably check what this does.
			            		if (x == 0) {
			            			JOptionPane.showMessageDialog(Update, "An error has occured: Failed to update database.");
			            		} else {
			            			JOptionPane.showMessageDialog(Update, "Inputted information has been successfully added and updated!");
			            		}
			            		connection.close();
			            	} catch (Exception ex) {
			            		ex.printStackTrace();
			            	}
						} else {
							JOptionPane.showMessageDialog(Update, "Updating of selected row canceled.");
						}
					} else {
						JOptionPane.showMessageDialog(Update, "Make sure that there are no empty values. If there are, indicate it with N/A");
					}
			    } else {
					JOptionPane.showMessageDialog(Update, "Please select a line in the table first");
			    }
						
			}
		});
		JobPosting.getContentPane().add(Update);
		
		//DELETE BUTTON
		JButton Delete = new JButton("");
		Delete.setBorder(null);
		Delete.setIcon(new ImageIcon("/Users/russeljeromerapi/Desktop/NCVASystemUI/Buttons/Job Posting/POSTING (DELETE)  _ .png"));
		Delete.setBounds(1018, 677, 117, 40);
		Delete.setBackground(Color.RED);
		Delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Modify this one to be delete-functional
				int selectedRow = table_1.getSelectedRow();
				
				if (selectedRow >= 0) {
					if (!(PosCode.getText().isBlank()) && !(JobTitle.getText().isBlank()) && !(Responsibilities.getText().isBlank()) 
							&& !(Salary.getText().isBlank())) {
						int result = JOptionPane.showConfirmDialog(table_1, "Review your selections first. Are you sure that you want to delete this data/row from the database?", 
		    			"Delete Confirmation Window", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (result == JOptionPane.YES_OPTION) {
							int posCode = Integer.parseInt(PosCode.getText());
							String title = JobTitle.getText();
							String tasks = Responsibilities.getText();
							float salary = Float.parseFloat(Salary.getText());
							
							String originalCode = (String) table_1.getValueAt(selectedRow, 0);
							
							try {
			            		Connection connection = DriverManager.getConnection(urlToDatabase, "root", "RJ@sql48%");
			            		
			            		
			            		// There might be a possibility that Position Code would not or cannot be changed.
			            		// For efficiency, maybe just set the condition to reference the JobID instead
//			            		String deleteQuery = "DELETE FROM JobPosition WHERE JobID = '" + posCode + "', Title = '" + title + "', "
//			            				+ "Task = '" + tasks + "', Salary = '" + salary + "' WHERE JobID = " + originalCode + ";";
			            		String deleteQuery = "DELETE FROM JobPosition WHERE JobID = '" + posCode + "';";
			            		
			            		Statement st = connection.createStatement();
			            		// Should add check-case to see if inputted record already exists.
			            		int x = st.executeUpdate(deleteQuery);
			            		// Should probably check what this does.
			            		if (x == 0) {
			            			JOptionPane.showMessageDialog(Delete, "An error has occured: Failed to delete data from the database.");
			            		} else {
			            			JOptionPane.showMessageDialog(Delete, "Selected information has been successfully removed!");
			            		}
			            		connection.close();
			            	} catch (Exception ex) {
			            		ex.printStackTrace();
			            	}
						} else {
							JOptionPane.showMessageDialog(Delete, "Deleting of selected row canceled.");
						}
					} else {
						//JOptionPane.showMessageDialog(Delete, "Make sure that there are no empty values. If there are, indicate it with N/A");
					}
			    } else {
					JOptionPane.showMessageDialog(Delete, "Please select a line in the table first");
			    }

			}
		});
		JobPosting.getContentPane().add(Delete);
		
		//CLEAR BUTTON
		JButton Clear = new JButton("");
		Clear.setBorder(null);
		Clear.setIcon(new ImageIcon("/Users/russeljeromerapi/Desktop/NCVASystemUI/Buttons/Job Posting/POSTING (CLEAR)  _ .png"));
		Clear.setBounds(1158, 603, 108, 40);
		Clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				PosCode.setText("");
				JobTitle.setText("");
				Responsibilities.setText("");
				Salary.setText("");
			}
		});
		JobPosting.getContentPane().add(Clear);
		
		//RETURN BUTTON
		JButton btn_Return = new JButton("");
		btn_Return.setBorder(null);
		btn_Return.setBounds(1179, 94, 100, 42);
		btn_Return.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ExecutiveDashboard ExecutiveDash = new ExecutiveDashboard();
				ExecutiveDash.ExecutiveDashboard.setVisible(true);
				JobPosting.dispose();
                			
			}
		});
		btn_Return.setIcon(new ImageIcon("/Users/russeljeromerapi/Desktop/NCVASystemUI/Buttons/Job Posting/Return (ADMIN)_ .png"));
		JobPosting.getContentPane().add(btn_Return);
		
		DefaultTableModel tableModel = (DefaultTableModel)table_1.getModel();
		
		JButton btnViewTable = new JButton("View / Refresh");
		btnViewTable.setBounds(30, 152, 127, 29);
		btnViewTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Clear table first
				tableModel.setRowCount(0);
				
				try {
					Connection connection = DriverManager.getConnection(urlToDatabase, "root", "RJ@sql48%");
					
					Statement st = connection.createStatement();
					
					String sqlQuery = "SELECT * FROM JobPosition";
					ResultSet rs = st.executeQuery(sqlQuery);
					
					while (rs.next()) {
						// Ask Marc if JobID should be auto increment or have a set value from the user
						// If has a set value from the user, should probably remove option to add Position code
						// Data from the database will continue to be added until it reaches the end
						String jobID = String.valueOf(rs.getInt("JobID")); // Conversion due to int value
						String jobTitle = rs.getString("Title");
						String jobTask = rs.getString("Task"); 
						String jobSalary = String.valueOf(rs.getFloat("Salary"));
						
						// String array to store data into the JTable
						String tableData[] = {jobID, jobTitle, jobTask, jobSalary};
						
						
						// Add string array data into jtable
						tableModel.addRow(tableData);
					} // Add code to clear everything off the table in case the user presses the button multiple times
					
					connection.close();
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		JobPosting.getContentPane().add(btnViewTable);
		
		
	
		//BACKGROUND====================================================================================

		JLabel lblJobPosting_BG = new JLabel("");
		lblJobPosting_BG.setBounds(0, 0, 1300, 800);
		lblJobPosting_BG.setIcon(new ImageIcon("/Users/russeljeromerapi/Desktop/NCVASystemUI/Pages/JOB POSTING (ADMIN).png"));
		JobPosting.getContentPane().add(lblJobPosting_BG);
					

	}
}
