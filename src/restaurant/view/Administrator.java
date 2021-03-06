package restaurant.view;

import restaurant.controller.AcceptOrdersButtonController;
import restaurant.controller.CancelBillButtonController;
import restaurant.controller.DeclineOrdersButtonController;
import restaurant.controller.RequestPaymentButtonController;
import restaurant.model.Bill;
import restaurant.model.Dish;
import restaurant.model.FilesDAO;
import restaurant.model.Order;
import restaurant.util.Constants;
import restaurant.util.Utils;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Administrator GUI.
 * @author User
 */
public class Administrator extends JFrame {

    private JButton requestPaymentButton;
    private JButton acceptOrderButton;
    private JButton declineOrderButton;
    private JButton cancelBillButton;
    private JLabel headerLabel;
    private JScrollPane ordersScrollPane;
    private JScrollPane billsScrollPane;
    private JTree ordersTree;
    private JTree billsTree;
    private final Timer ordersTimer;
    private final Timer billsTimer;
    private List<Order> lastOrders = new ArrayList<>();
    private List<Bill> lastBills = new ArrayList<>();

    /**
     * Creates new Administrator GUI
     */
    public Administrator() {
        // setup timers
        ordersTimer = new Timer(0, (ActionEvent e) -> {
            List<Order> orders = FilesDAO.getInstance().getOrders();
            
            if (orders.isEmpty())
                fillOrdersTree(orders); // wipe default data
            if (!orders.equals(lastOrders)) {
                lastOrders = orders;
                fillOrdersTree(orders.stream()
                    .filter(x -> x.isAccepted() == false)
                    .collect(Collectors.toList()));
            }
        });
        ordersTimer.setDelay(Constants.UPDATE_DELAY);
        ordersTimer.start();
        
        billsTimer = new Timer(0, (ActionEvent e) -> {
            List<Bill> bills = FilesDAO.getInstance().getBills();
            
            if (bills.isEmpty())
                fillBillsTree(bills); // wipe default data
            if (!bills.equals(lastBills)) {
                lastBills = bills;
                fillBillsTree(bills.stream()
                    .filter(x -> x.isBilled() == false)
                    .collect(Collectors.toList()));
            }
        });
        
        billsTimer.setDelay(Constants.UPDATE_DELAY);
        billsTimer.start();

        initComponents();

        // hide root node from trees
        ordersTree.setRootVisible(false);
        billsTree.setRootVisible(false);
    }

    /**
     * Refresh bills tree
     * @param bills updated bills
     */
    private void fillBillsTree(List<Bill> bills) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Bills");
        
        for (Bill b : bills) {
            DefaultMutableTreeNode billNode = new DefaultMutableTreeNode("Bill " + b.getId() +
                    " = " + Utils.digitsAfterPoint(String.valueOf(b.getAmount()), 2) + " UAH");
            
            billNode.add(new DefaultMutableTreeNode("id: " + b.getId()));
            billNode.add(new DefaultMutableTreeNode("order id: " + b.getId_order()));
            billNode.add(new DefaultMutableTreeNode("amount: " + b.getAmount()));
            billNode.add(new DefaultMutableTreeNode("billed: " + b.isBilled()));
            billNode.add(new DefaultMutableTreeNode("requested for payment: " + b.isRequestedForPayment()));
            
            root.add(billNode);
        }
        
        DefaultTreeModel dtm = (DefaultTreeModel) billsTree.getModel();
        
        dtm.setRoot(root);
    }

    /**
     * Refresh orders tree
     * @param orders updated orders
     */
    private void fillOrdersTree(List<Order> orders) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Orders");

        for (Order o : orders) {
            DefaultMutableTreeNode order  = new DefaultMutableTreeNode("Order " + o.getId());
            
            for (Dish d : o.getDishes()) {
                DefaultMutableTreeNode dishNode = new DefaultMutableTreeNode(d.getName());
                
                dishNode.add(new DefaultMutableTreeNode("id: " + d.getId()));
                dishNode.add(new DefaultMutableTreeNode("name: " + d.getName()));
                dishNode.add(new DefaultMutableTreeNode("description: " + d.getDescription()));
                dishNode.add(new DefaultMutableTreeNode("cooking time: " + d.getCookingTime()));
                dishNode.add(new DefaultMutableTreeNode("price: " + d.getPrice()));
                dishNode.add(new DefaultMutableTreeNode("type: " + d.getType()));
                
                order.add(dishNode);
            }
            
            root.add(order);
        }
        
        DefaultTreeModel dtm = (DefaultTreeModel) ordersTree.getModel();
        
        dtm.setRoot(root);
    }
    
    /**
     * This method is called from within the constructor to initialize the frame
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {

        // init components
        headerLabel = new JLabel();
        ordersScrollPane = new JScrollPane();
        ordersTree = new JTree();
        acceptOrderButton = new JButton();
        billsScrollPane = new JScrollPane();
        billsTree = new JTree();
        declineOrderButton = new JButton();
        cancelBillButton = new JButton();
        requestPaymentButton = new JButton();

        // setup frame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Restaurant App | Administrator");
        setPreferredSize(new Dimension(Constants.ADMINISTRATOR_FRAME_WIDTH, Constants.ADMINISTRATOR_FRAME_HEIGHT));

        // Setup every component

        headerLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setText("Administrator control panel");

        ordersScrollPane.setBorder(BorderFactory.createTitledBorder("Orders"));
        ordersScrollPane.setViewportView(ordersTree);

        acceptOrderButton.setText("Accept");
        acceptOrderButton.addActionListener(new AcceptOrdersButtonController(ordersTree, ordersTimer,
                billsTimer, this));

        billsScrollPane.setBorder(BorderFactory.createTitledBorder("Active bills"));
        billsScrollPane.setViewportView(billsTree);

        declineOrderButton.setText("Decline");
        declineOrderButton.addActionListener(new DeclineOrdersButtonController(ordersTree, ordersTimer, this));

        cancelBillButton.setText("Cancel bill");
        cancelBillButton.addActionListener(new CancelBillButtonController(billsTree, billsTimer, this));

        requestPaymentButton.setText("Request payment");
        requestPaymentButton.addActionListener(new RequestPaymentButtonController(billsTree, billsTimer, this));

        // Create and setup main layout

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        // setup horizontal positioning
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(headerLabel,
                            GroupLayout.DEFAULT_SIZE, Constants.ADMINISTRATOR_TREE_WIDTH, Short.MAX_VALUE)
                    .addComponent(ordersScrollPane)
                    .addComponent(billsScrollPane)
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(acceptOrderButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(declineOrderButton))
                            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(requestPaymentButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cancelBillButton)))))
                .addContainerGap())
        );
        // setup vertical positioning
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(headerLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ordersScrollPane,
                        GroupLayout.DEFAULT_SIZE, Constants.ADMINISTRATOR_TREE_HEIGHT, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(acceptOrderButton)
                    .addComponent(declineOrderButton))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(billsScrollPane,
                        GroupLayout.DEFAULT_SIZE, Constants.ADMINISTRATOR_TREE_HEIGHT, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(requestPaymentButton)
                    .addComponent(cancelBillButton))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null); // center on the display when it appears
    }
}
