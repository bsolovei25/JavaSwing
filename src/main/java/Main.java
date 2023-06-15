import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;

public class Main extends JFrame {
    private JPanel Panel_Main;
    private JButton find_by_name_btn;
    private JLabel firstName;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JLabel searchbyName;
    private JLabel secondName;

    public Main() {



        find_by_name_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "hello world!");
            }
        });


    }

    public static void main(String[] args) {
        String concatinate = "";
        try {
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/office","postgres","postgres");
            PreparedStatement statmentWorkers =  con.prepareStatement("select * from office.office_worker");
            ResultSet officeSetWorkers = statmentWorkers.executeQuery();


            JFrame frame = new JFrame("Main");
            frame.setResizable(false);

            DrawTriangle main = new DrawTriangle();
            JTextPane searchByName = new JTextPane();
            searchByName.setBounds(0, 0, 282, 300);

            main.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.insets = new Insets(10, 0, 0, 0);


            frame.setLayout(new BorderLayout());

            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);

            while (officeSetWorkers.next()) {
                JLabel myWorker = new JLabel(officeSetWorkers.getString("name"));
                main.add(myWorker,gbc);
                frame.add(main);
                PreparedStatement statmentPhones =  con.prepareStatement("select * from office.phones where office.phones.worker_id = "
                        + officeSetWorkers.getString("id"));
                ResultSet officeSetPhones = statmentPhones.executeQuery();
                while (officeSetPhones.next()) {
                    JLabel myPhones = new JLabel(officeSetPhones.getString("phone_number"));
                    main.add(myPhones,gbc);

                }
            }
            frame.setSize(500, 300);
            main.add(searchByName,gbc);
            JButton btnSearch = new JButton("Search by Name");
            btnSearch.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    frame.setVisible(false);
                    frame.dispose();
                    JDialog secondFrame = new JDialog(frame,"search Box");
                    secondFrame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            main.cleanTriangle();
                            frame.setVisible(true);
                        }
                    });
                    secondFrame.setSize(500, 400);
                    try {
                        PreparedStatement searchStatement = con.prepareStatement("select office.phones.phone_number from office.phones " +
                                "inner join office.office_worker " +
                                "on office.office_worker.id = office.phones.worker_id " +
                                "where office.office_worker.name = " + "'"+ searchByName.getText()+ "'"
                        );
                        ResultSet foundPhones = searchStatement.executeQuery();
                        String concatinate = new String();
                        if (!foundPhones.next()){
                            concatinate = "Noting has been found";
                        } else {
                            concatinate += foundPhones.getString("phone_number") + "; ";
                        }
                        while (foundPhones.next()) {
                            concatinate += foundPhones.getString("phone_number") + "; ";
                        }
                        JLabel foundPhone = new JLabel(concatinate);
                        secondFrame.add(foundPhone);

                        secondFrame.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        secondFrame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                        secondFrame.setVisible(true);
                        secondFrame.getPreferredSize();

                    }
                    catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

            });
            main.add(btnSearch);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
