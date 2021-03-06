package restaurant.view;

import restaurant.controller.AddDishButtonController;
import restaurant.controller.ConfirmOrderButtonController;
import restaurant.controller.DeleteDishButtonController;
import restaurant.model.Dish;
import restaurant.model.FilesDAO;
import restaurant.model.MenuTableModel;
import restaurant.model.User;
import restaurant.util.Constants;
import restaurant.util.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Client GUI.
 * @author User
 */
public class Client extends JFrame {
    private User user;
    private List<Dish> lastDishes = new ArrayList<>();
    private JButton addFirstDishButton;
    private JButton addSecondDishButton;
    private JButton addDrinkButton;
    private JButton deleteFromOrderButton;
    private JButton confirmOrderButton;
    private JLabel headerLabel;
    private JLabel userLoginLabel;
    private JLabel hintUserLoginLabel;
    private JLabel userBalanceLabel;
    private JLabel hintUserBalanceLabel;
    private JPanel footerPanel;
    private JScrollPane firstDishesScrollPane;
    private JScrollPane secondDishesScrollPane;
    private JScrollPane drinksScrollPane;
    private JScrollPane orderScrollPane;
    private JTable firstDishesTable;
    private JTable secondDishesTable;
    private JTable drinksTable;
    private JTable ordersTable;

    /**
     * Create new User GUI
     * @param user client
     */
    public Client(User user) {
        this.user = user;
        initComponents();

        userLoginLabel.setText(user.getLogin());
        userBalanceLabel.setText(Utils.digitsAfterPoint(String.valueOf(user.getMoney()),2));

        Timer tablesTimer = new Timer(0, (ActionEvent e) -> {
            List<Dish> dishes = FilesDAO.getInstance().getDishes();

            if (!dishes.equals(lastDishes)) {
                lastDishes = dishes;
                fillTables(dishes);
            }
        });

        tablesTimer.setDelay(Constants.UPDATE_DELAY);
        tablesTimer.start();

        Timer balanceTimer = new Timer(0, (ActionEvent e) -> {
            FilesDAO.getInstance().getUsers()
                    .stream()
                    .filter(x -> x.getId() == user.getId())
                    .findFirst()
                    .ifPresent(x -> {
                        user.setMoney(x.getMoney());
                        userBalanceLabel.setText(Utils.digitsAfterPoint(String.valueOf(user.getMoney()), 2));
                    });
        });

        balanceTimer.setDelay(Constants.UPDATE_DELAY);
        balanceTimer.start();
    }

    /**
     * Fill tables based on the dish type
     * @param dishes new dishes
     */
    private void fillTables(List<Dish> dishes) {
        DefaultTableModel ftm = (DefaultTableModel) firstDishesTable.getModel(),
                stm = (DefaultTableModel) secondDishesTable.getModel(),
                dtm = (DefaultTableModel) drinksTable.getModel();

        for (int i = ftm.getRowCount() - 1; i >= 0; i--)
            ftm.removeRow(i);
        for (int i = stm.getRowCount() - 1; i >= 0; i--)
            stm.removeRow(i);
        for (int i = dtm.getRowCount() - 1; i >= 0; i--)
            dtm.removeRow(i);

        Object[] rowData = new Object[Dish.VISIBLE_FIELDS_NUMBER];

        for (Dish dish : dishes) {
            rowData[0] = dish.getName();
            rowData[1] = dish.getDescription();
            rowData[2] = dish.getCookingTime();
            rowData[3] = dish.getPrice();

            if (dish.getType().equals(Dish.TYPE_FIRST)) {
                ftm.addRow(rowData);
            } else if (dish.getType().equals(Dish.TYPE_SECOND)) {
                stm.addRow(rowData);
            } else if (dish.getType().equals(Dish.TYPE_DRINK)) {
                dtm.addRow(rowData);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize components.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {

        // init components
        headerLabel = new JLabel();
        firstDishesScrollPane = new JScrollPane();
        firstDishesTable = new JTable();
        secondDishesScrollPane = new JScrollPane();
        secondDishesTable = new JTable();
        drinksScrollPane = new JScrollPane();
        drinksTable = new JTable();
        orderScrollPane = new JScrollPane();
        ordersTable = new JTable();
        footerPanel = new JPanel();
        confirmOrderButton = new JButton();
        addFirstDishButton = new JButton();
        addSecondDishButton = new JButton();
        addDrinkButton = new JButton();
        deleteFromOrderButton = new JButton();
        userLoginLabel = new JLabel();
        hintUserLoginLabel = new JLabel();
        userBalanceLabel = new JLabel();
        hintUserBalanceLabel = new JLabel();

        // setup frame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Restaurant App | Client");
        setPreferredSize(new Dimension(Constants.CLIENT_FRAME_WIDTH, Constants.CLIENT_FRAME_HEIGHT));

        headerLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setText("Client order panel");

        // Wrap tables into scroll panes and setup tables models

        firstDishesScrollPane.setBorder(BorderFactory.createTitledBorder("First dishes"));
        firstDishesScrollPane.setPreferredSize(new Dimension(Constants.CLIENT_TABLE_WIDTH,
                Constants.CLIENT_TABLE_HEIGHT));

        firstDishesTable.setAutoCreateRowSorter(true);
        firstDishesTable.setModel(new MenuTableModel());
        firstDishesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        firstDishesScrollPane.setViewportView(firstDishesTable);
        firstDishesTable.getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        secondDishesScrollPane.setBorder(BorderFactory.createTitledBorder("Second dishes"));
        secondDishesScrollPane.setPreferredSize(new Dimension(Constants.CLIENT_TABLE_WIDTH,
                Constants.CLIENT_TABLE_HEIGHT));

        secondDishesTable.setAutoCreateRowSorter(true);
        secondDishesTable.setModel(new MenuTableModel());
        secondDishesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        secondDishesScrollPane.setViewportView(secondDishesTable);
        secondDishesTable.getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        drinksScrollPane.setBorder(BorderFactory.createTitledBorder("Drinks"));
        drinksScrollPane.setPreferredSize(new Dimension(Constants.CLIENT_TABLE_WIDTH, Constants.CLIENT_TABLE_HEIGHT));

        drinksTable.setAutoCreateRowSorter(true);
        drinksTable.setModel(new MenuTableModel());
        drinksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        drinksScrollPane.setViewportView(drinksTable);
        drinksTable.getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        orderScrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(
                        new Color(0, 51, 255)), "Your order"));
        orderScrollPane.setPreferredSize(new Dimension(Constants.CLIENT_TABLE_WIDTH, Constants.CLIENT_TABLE_HEIGHT));

        ordersTable.setAutoCreateRowSorter(true);
        ordersTable.setModel(new MenuTableModel());
        ordersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        orderScrollPane.setViewportView(ordersTable);
        ordersTable.getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // footer panel for centering confirmation button
        footerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        footerPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        confirmOrderButton.setText("Confirm order");
        confirmOrderButton.addActionListener(new ConfirmOrderButtonController(user, ordersTable, this));

        GroupLayout footerPanelLayout = new GroupLayout(footerPanel);
        footerPanel.setLayout(footerPanelLayout);
        // horizontal alignment on scaling
        footerPanelLayout.setHorizontalGroup(
            footerPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, footerPanelLayout.createSequentialGroup()
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(confirmOrderButton, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        // vertical alignment on scaling
        footerPanelLayout.setVerticalGroup(
            footerPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(footerPanelLayout.createSequentialGroup()
                .addComponent(confirmOrderButton, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
        );

        // Setup buttons for every tables(appearance + event handling)

        addFirstDishButton.setText("Add first dish");
        addFirstDishButton.addActionListener(new AddDishButtonController(firstDishesTable,
                (DefaultTableModel) ordersTable.getModel(), this));

        addSecondDishButton.setText("Add second dish");
        addSecondDishButton.addActionListener(new AddDishButtonController(secondDishesTable,
                (DefaultTableModel) ordersTable.getModel(), this));

        addDrinkButton.setText("Add drink");
        addDrinkButton.addActionListener(new AddDishButtonController(drinksTable,
                (DefaultTableModel) ordersTable.getModel(), this));

        deleteFromOrderButton.setText("Delete from order");
        deleteFromOrderButton.addActionListener(new DeleteDishButtonController(ordersTable, this));

        // Setup user profile info

        userLoginLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        userLoginLabel.setText("userLogin");

        hintUserLoginLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
        hintUserLoginLabel.setText("Login:");

        userBalanceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        userBalanceLabel.setText(Utils.digitsAfterPoint("0.0", 2));

        hintUserBalanceLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
        hintUserBalanceLabel.setText("Balance:");

        // Setup main layout for proper scaling and alignment

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        // horizontal positioning of all elements
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(headerLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(footerPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        // first dishes table + second dishes table
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(firstDishesScrollPane, GroupLayout.DEFAULT_SIZE,
                                        Constants.CLIENT_TABLE_WIDTH, Short.MAX_VALUE)
                                .addGroup(layout.createSequentialGroup()
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                                            GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(addFirstDishButton)
                                    .addGap(0, 0, Short.MAX_VALUE))) // centering addFirstDishButton
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(secondDishesScrollPane, GroupLayout.DEFAULT_SIZE,
                                        Constants.CLIENT_TABLE_WIDTH, Short.MAX_VALUE)
                                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGap(0, 0, Short.MAX_VALUE) // centering addSecondDishButton
                                    .addComponent(addSecondDishButton)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                                            GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        // drinks table + orders table
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(drinksScrollPane, GroupLayout.DEFAULT_SIZE,
                                        Constants.CLIENT_TABLE_WIDTH, Short.MAX_VALUE)
                                .addGroup(layout.createSequentialGroup()
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                                            GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(addDrinkButton)
                                    .addGap(0, 0, Short.MAX_VALUE))) // centering addDrinkButton
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(orderScrollPane, GroupLayout.DEFAULT_SIZE,
                                        Constants.CLIENT_TABLE_WIDTH, Short.MAX_VALUE)
                                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGap(0, 0, Short.MAX_VALUE) // centering deleteFromOrderButton
                                    .addComponent(deleteFromOrderButton)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                                            GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGap(12, 12, 12))
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    // user info
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(hintUserBalanceLabel,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(hintUserLoginLabel,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(userLoginLabel, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                        .addComponent(userBalanceLabel,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(15, 15, 15))
        );
        // vertical positioning of all elements
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(headerLabel)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    // user info
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(userLoginLabel)
                        .addComponent(hintUserLoginLabel))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(userBalanceLabel)
                        .addComponent(hintUserBalanceLabel))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    // tables and buttons
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(secondDishesScrollPane,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(firstDishesScrollPane,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(1, 1, 1)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(addFirstDishButton)
                        .addComponent(addSecondDishButton))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(drinksScrollPane,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(orderScrollPane,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(addDrinkButton)
                        .addComponent(deleteFromOrderButton))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(footerPanel,
                            GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null); // center on the display when it appears
    }
}
