package restaurant.view;

import restaurant.controller.PayButtonController;
import restaurant.model.*;
import restaurant.util.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Payment GUI.
 * @author User
 */
public class Payment extends JFrame {
    
    private final User user;
    private final Order order;
    private JButton payButton;
    private JLabel headerLabel;
    private JLabel amountHintLabel;
    private JLabel amountLabel;
    private JLabel balanceHintLabel;
    private JLabel balanceLabel;
    private JScrollPane orderTableScrollPane;
    private JTable orderTable;
    
    /**
     * Creates new form Payment
     * @param order payment order
     * @param user payer
     */
    public Payment(User user, Order order) {
        this.user = user;
        this.order = order;
        initComponents();
        
        double amount = Utils.getAmount(order);
        
        balanceLabel.setText(Utils.digitsAfterPoint(String.valueOf(user.getMoney()), 2));
        amountLabel.setText(Utils.digitsAfterPoint(String.valueOf(amount), 2));
        
        DefaultTableModel tableModel = (DefaultTableModel) orderTable.getModel();
        
        // Clean up
        for (int i = tableModel.getRowCount() - 1; i >= 0; i--)
            tableModel.removeRow(i);
        
        Object[] rowData = new Object[Dish.VISIBLE_FIELDS_NUMBER];
        
        // Fill up the table
        for (Dish dish : order.getDishes()) {
            rowData[0] = dish.getName();
            rowData[1] = dish.getDescription();
            rowData[2] = dish.getCookingTime();
            rowData[3] = dish.getPrice();
            
            tableModel.addRow(rowData);
        }
    }

    /**
     * This method is called from within the constructor to initialize the frame.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {

        // init components
        headerLabel = new JLabel();
        orderTableScrollPane = new JScrollPane();
        orderTable = new JTable();
        amountHintLabel = new JLabel();
        amountLabel = new JLabel();
        payButton = new JButton();
        balanceHintLabel = new JLabel();
        balanceLabel = new JLabel();

        // setup frame
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Restaurant App | Payment");

        headerLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setText("Payment");

        orderTableScrollPane.setBorder(BorderFactory.createTitledBorder("Your order"));

        // Wrap table into scroll panes and setup table model
        orderTable.setAutoCreateRowSorter(true);
        orderTable.setModel(new MenuTableModel());
        orderTableScrollPane.setViewportView(orderTable);

        // Labels and button setup
        amountHintLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
        amountHintLabel.setText("Amount: ");

        amountLabel.setText(Utils.digitsAfterPoint("0.0", 2));

        payButton.setText("PAY");
        payButton.addActionListener(new PayButtonController(user, order, balanceLabel, this));

        balanceHintLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
        balanceHintLabel.setText("Your money: ");

        balanceLabel.setText(Utils.digitsAfterPoint("0.0", 2));

        // Setup main layout

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        // horizontal alignment on scaling
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(orderTableScrollPane, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(headerLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(amountHintLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(amountLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 178, Short.MAX_VALUE)
                        .addComponent(balanceHintLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(balanceLabel))
                    .addGroup(layout.createSequentialGroup()
                        // center payButton with zero gaps
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(payButton, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        // vertical alignment on scaling
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(headerLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(orderTableScrollPane, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(amountHintLabel)
                    .addComponent(amountLabel)
                    .addComponent(balanceHintLabel)
                    .addComponent(balanceLabel))
                .addGap(13, 13, 13)
                .addComponent(payButton, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null); // center on the display when it appears
    }
}
