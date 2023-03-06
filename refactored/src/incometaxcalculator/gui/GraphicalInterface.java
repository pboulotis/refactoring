package incometaxcalculator.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import incometaxcalculator.data.management.FileManager;
import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.WrongFileEndingException;
import incometaxcalculator.exceptions.WrongFileFormatException;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.WrongReceiptKindException;
import incometaxcalculator.exceptions.WrongTaxpayerStatusException;
import javax.swing.JRadioButton;
import javax.swing.BoxLayout;
import javax.swing.JTextArea;

public class GraphicalInterface extends JFrame {

  private JPanel contentPane;
  private TaxpayerManager taxpayerManager = new TaxpayerManager();
  private FileManager fileManager = new FileManager();

  private String taxpayersTRN = new String();
  private JTextField txtTaxRegistrationNumber;
  private int selectedTaxpayerRegistrationNumber = -1;

  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          GraphicalInterface frame = new GraphicalInterface();
          frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  public GraphicalInterface() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 450, 500);
    contentPane = new JPanel();
    contentPane.setBackground(new Color(204, 204, 204));
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);

    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
        | UnsupportedLookAndFeelException e2) {
      e2.printStackTrace();
    }

    JTextPane textPane = new JTextPane();
    textPane.setEditable(false);
    textPane.setBackground(new Color(153, 204, 204));
    textPane.setBounds(0, 21, 433, 441);

    JPanel fileLoaderPanel = new JPanel(new BorderLayout());
    fileLoaderPanel.setName("Load Taxpayer");
    JPanel boxPanel = new JPanel();
    JPanel taxRegistrationNumberPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
    
    JLabel taxRegistrationNumberLabel = new JLabel("Tax Registration Number: ");
    taxRegistrationNumberPanel.add(taxRegistrationNumberLabel);
    JTextField taxRegistrationNumberField = new JTextField(20);
    taxRegistrationNumberPanel.add(taxRegistrationNumberField);
    JPanel loadPanel = new JPanel(new GridLayout(1, 2));
    loadPanel.add(taxRegistrationNumberPanel);
    
    JTextArea fileChooserLog = new JTextArea();
    taxRegistrationNumberPanel.add(fileChooserLog);
    fileLoaderPanel.add(boxPanel, BorderLayout.NORTH);
    fileLoaderPanel.add(loadPanel, BorderLayout.CENTER);
    boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.X_AXIS));
    
    JLabel fileSelectLabel = new JLabel("Select your file:");
    boxPanel.add(fileSelectLabel);
    
    JRadioButton txtOption = new JRadioButton(".txt");
    boxPanel.add(txtOption);
    
    JRadioButton xmlOption = new JRadioButton(".xml");
    xmlOption.setSelected(true);
    boxPanel.add(xmlOption);
    
    txtOption.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        xmlOption.setSelected(false);
      }
    });

    xmlOption.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        txtOption.setSelected(false);
      }
    });

    JButton fileChooserButton = new JButton("File explorer");
    fileChooserButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        final JFileChooser fileChooser = new JFileChooser();
        if(txtOption.isSelected()) {
          FileNameExtensionFilter fileTypeFilter = new FileNameExtensionFilter("*txt", "txt");
          fileChooser.setFileFilter(fileTypeFilter);
        }else{
          FileNameExtensionFilter fileTypeFilter = new FileNameExtensionFilter("*xml", "xml");
          fileChooser.setFileFilter(fileTypeFilter);          
        }
        
        if (e.getSource() == fileChooserButton) {
          int returnVal = fileChooser.showOpenDialog(GraphicalInterface.this);

          if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            fileChooserLog.append("Opening: " + file.getName() + "\n");
            taxpayersTRN = file.getName().substring(0,9);
            taxRegistrationNumberField.setText(taxpayersTRN);
          } else {
            fileChooserLog.append("Open command cancelled by user." + "\n");
          }
          
        } 
      }
    });
    boxPanel.add(fileChooserButton);

    DefaultListModel<String> taxRegisterNumberModel = new DefaultListModel<String>();

    JList<String> taxRegisterNumberList = new JList<String>(taxRegisterNumberModel);
    taxRegisterNumberList.setBackground(new Color(153, 204, 204));
    taxRegisterNumberList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    taxRegisterNumberList.setSelectedIndex(0);
    taxRegisterNumberList.setVisibleRowCount(3);
    taxRegisterNumberList.setToolTipText("Double-click a loaded taxpayer to select them");
    taxRegisterNumberList.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent event) {
          JList list = (JList)event.getSource();
          try {
            if (event.getClickCount() == 1) {
               selectedTaxpayerRegistrationNumber = Integer.valueOf(list.getSelectedValue().toString());
               
            }else if (event.getClickCount() == 2) {
              selectedTaxpayerRegistrationNumber = Integer.valueOf(list.getSelectedValue().toString());              
              TaxpayerData taxpayerData = new TaxpayerData(selectedTaxpayerRegistrationNumber,
                    taxpayerManager, fileManager);
              taxpayerData.setVisible(true);
            }
          }catch (NullPointerException e1){
            JOptionPane.showMessageDialog(null, "Please load a taxpayer to select them");            
          }
      }
    });

    JScrollPane taxRegisterNumberListScrollPane = new JScrollPane(taxRegisterNumberList);
    taxRegisterNumberListScrollPane.setSize(300, 300);
    taxRegisterNumberListScrollPane.setLocation(70, 100);
    contentPane.add(taxRegisterNumberListScrollPane);

    JButton btnLoadTaxpayer = new JButton("Load Taxpayer");
    btnLoadTaxpayer.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int answer = JOptionPane.showConfirmDialog(null, fileLoaderPanel, "",
            JOptionPane.OK_CANCEL_OPTION);
        if (answer == 0) {
          String taxRegistrationNumber = taxRegistrationNumberField.getText();
          while (taxRegistrationNumber.length() != 9 && answer == 0) {
            JOptionPane.showMessageDialog(null,
                "The tax  registration number must have 9 digit.\n" + " Try again.");
            answer = JOptionPane.showConfirmDialog(null, fileLoaderPanel, "",
                JOptionPane.OK_CANCEL_OPTION);
            taxRegistrationNumber = taxRegistrationNumberField.getText();
          }
          if (answer == 0) {
            int trn = 0;
            String taxRegistrationNumberFile;
            try {
              trn = Integer.parseInt(taxRegistrationNumber);
              if (txtOption.isSelected()) {
                taxRegistrationNumberFile = taxRegistrationNumber + "_INFO.txt";
              } else {
                taxRegistrationNumberFile = taxRegistrationNumber + "_INFO.xml";
              }
              if (taxpayerManager.containsTaxpayer(trn)) {
                JOptionPane.showMessageDialog(null, "This taxpayer is already loaded.");
              } else {
                fileManager.loadTaxpayer(taxRegistrationNumberFile);
                taxRegisterNumberModel.addElement(taxRegistrationNumber);
              }

            } catch (NumberFormatException e1) {
              JOptionPane.showMessageDialog(null,
                  "The tax registration number must have only digits.");
            } catch (IOException e1) {
              JOptionPane.showMessageDialog(null, "The file doesn't exists.");
            } catch (WrongFileFormatException e1) {
              JOptionPane.showMessageDialog(null, "Please check your file format and try again.");
            } catch (WrongFileEndingException e1) {
              JOptionPane.showMessageDialog(null, "Please check your file ending and try again.");
            } catch (WrongTaxpayerStatusException e1) {
              JOptionPane.showMessageDialog(null, "Please check taxpayer's status and try again.");
            } catch (WrongReceiptKindException e1) {
              JOptionPane.showMessageDialog(null, "Please check receipts kind and try again.");
            } catch (WrongReceiptDateException e1) {
              JOptionPane.showMessageDialog(null,
                  "Please make sure your date is " + "DD/MM/YYYY and try again.");
            }
          }
        }
      }
    });
    btnLoadTaxpayer.setBounds(0, 0, 224, 23);
    contentPane.add(btnLoadTaxpayer);
    
    JButton btnDeleteTaxpayer = new JButton("Delete Taxpayer");
    btnDeleteTaxpayer.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        if (taxpayerManager.containsTaxpayer()) {
          if (selectedTaxpayerRegistrationNumber != -1) {
              taxpayerManager.removeTaxpayer(selectedTaxpayerRegistrationNumber);
              taxRegisterNumberModel.removeElement(Integer.toString(selectedTaxpayerRegistrationNumber));
          }
        }
      }
    });
    btnDeleteTaxpayer.setBounds(225, 0, 210, 23);
    contentPane.add(btnDeleteTaxpayer);

    txtTaxRegistrationNumber = new JTextField();
    txtTaxRegistrationNumber.setEditable(false);
    txtTaxRegistrationNumber.setBackground(new Color(153, 204, 204));
    txtTaxRegistrationNumber.setFont(new Font("Tahoma", Font.BOLD, 14));
    txtTaxRegistrationNumber.setText("Tax Registration Number:");
    txtTaxRegistrationNumber.setBounds(70, 80, 300, 20);
    contentPane.add(txtTaxRegistrationNumber);
    txtTaxRegistrationNumber.setColumns(10);

  }
  
}