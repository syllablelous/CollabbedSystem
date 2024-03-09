import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ApplicantHomeTab extends JFrame {

    private JPanel contentPane;
    private JTextField searchField;
    public static String applicantID;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	ApplicantHomeTab frame = new ApplicantHomeTab(applicantID);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ApplicantHomeTab(String applicantID) {
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
        btn_LogOut.setBounds(1174, 68, 96, 40);
        btn_LogOut.setOpaque(false);
        btn_LogOut.setContentAreaFilled(false);
        btn_LogOut.setBorderPainted(false);
        contentPane.add(btn_LogOut);

        // HOME
        JButton HomeBtn = new JButton("");
        HomeBtn.setBorder(null);
        HomeBtn.setContentAreaFilled(false);
        HomeBtn.setFont(new Font("SansSerif", Font.BOLD, 20));
        HomeBtn.setBounds(262, 96, 85, 21);
        HomeBtn.setMargin(new Insets(0, 0, 0, 0));
        HomeBtn.setOpaque(false);
        HomeBtn.setContentAreaFilled(false);
        HomeBtn.setBorderPainted(false);
        contentPane.add(HomeBtn);

        // POSITIONS
        JButton PositionsBtn = new JButton("");
        PositionsBtn.setBorder(null);
        PositionsBtn.setContentAreaFilled(false);
        PositionsBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        PositionsBtn.setBounds(367, 96, 96, 21);
        PositionsBtn.setMargin(new Insets(0, 0, 0, 0));
        PositionsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	dispose();
                ApplicantPositionTab positionTab = new ApplicantPositionTab(applicantID);
                positionTab.setVisible(true);
            	
            }
        });
        PositionsBtn.setOpaque(false);
        PositionsBtn.setContentAreaFilled(false);
        PositionsBtn.setBorderPainted(false);
        contentPane.add(PositionsBtn);

        // APPLY
        JButton ApplyBtn = new JButton("");
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
        ApplyBtn.setBounds(478, 96, 85, 21);
        ApplyBtn.setMargin(new Insets(0, 0, 0, 0));
        ApplyBtn.setOpaque(false);
        ApplyBtn.setContentAreaFilled(false);
        ApplyBtn.setBorderPainted(false);
        contentPane.add(ApplyBtn);

        // BLUELINE FOR APPLY BUTTON
//        JLabel blueLine = new JLabel("");
//        blueLine.setIcon(new ImageIcon("/Users/russeljeromerapi/Desktop/NCVASystemUI/Pages/blueline.png"));
//        blueLine.setBounds(272, 82, 59, 40);
//        contentPane.add(blueLine);

        // STATUS
        JButton StatusBtn = new JButton("");
        StatusBtn.setBorder(null);
        StatusBtn.setContentAreaFilled(false);
        StatusBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        StatusBtn.setBounds(579, 96, 85, 21);
        StatusBtn.setMargin(new Insets(0, 0, 0, 0));
        StatusBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                ApplicantStatusTab statusTab = new ApplicantStatusTab(applicantID);
                statusTab.setVisible(true);
                
            }
        });
        StatusBtn.setOpaque(false);
        StatusBtn.setContentAreaFilled(false);
        StatusBtn.setBorderPainted(false);
        contentPane.add(StatusBtn);

        // TABLE
        


        // BACKGROUND
        JLabel lbl_ApplyTabBG = new JLabel(new ImageIcon("/Users/russeljeromerapi/Desktop/NCVASystemUI/Pages/APPLICANT_HOMETAB.png"));
        lbl_ApplyTabBG.setBounds(0, 0, 1300, 800);
        contentPane.add(lbl_ApplyTabBG);
        
        
    }

    // Logout Action Method
    private void logoutAction() {
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?", "Logout", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            dispose();
            HomePage homepage = new HomePage();
            homepage.setVisible(true);
        }
    }
}
