import database.OfficeDataBaseFabric;
import database.OfficeRepository;
import database.Table;
import model.Worker;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.List;
import java.util.Optional;

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
        Table officeWorkerTable = OfficeDataBaseFabric.createOfficeTableWithTestData();
        Table officePhoneTable = OfficeDataBaseFabric.createPhoneNumberTable();
        OfficeRepository repo = new OfficeRepository(officeWorkerTable, officePhoneTable);

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

            List<Worker> allWorkers = repo.getAllWorkers();
            for (Worker worker : allWorkers) {
                JLabel myWorker = new JLabel(worker.getName());
                main.add(myWorker,gbc);
                frame.add(main);
                for (String phone : worker.getPhones()) {
                    JLabel myPhones = new JLabel(phone);
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
                    String concatinate = "";
                    Optional<Worker> worker = repo.getWorkerByName(searchByName.getText());
                    if (worker.isEmpty()){
                        concatinate = "Noting has been found";
                    }
                    else {
                        List<String>phones = worker.get().getPhones();

                        for (String phone : phones) {
                            concatinate += phone + "; ";
                        }

                    }
                    JLabel foundPhone = new JLabel(concatinate);
                    secondFrame.add(foundPhone);

                    secondFrame.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                    secondFrame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    secondFrame.setVisible(true);
                    secondFrame.getPreferredSize();

                }

            });
            main.add(btnSearch);
    }
}
