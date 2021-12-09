package GUI;

import GUI.windows.InternetUserWindow;
import GUI.windows.KitchenWindow;
import GUI.windows.WaitersWindow;
import app.InternetOrdersManager;
import app.TableOrdersManager;
import app.exception.OrderAlreadyAddedException;
import app.order.InternetOrder;
import app.order.Order;
import app.order.TableOrder;
import app.order.customer.Address;
import app.order.customer.Customer;
import app.order.menu.Dish;
import app.order.menu.Drink;
import app.order.menu.MenuItem;
import app.order.menu.DrinkTypeEnum;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.*;

public class GUI implements Runnable{
    private KitchenWindow kw;
    private InternetUserWindow iw;
    private WaitersWindow ww;

    private InternetOrdersManager iom;
    private TableOrdersManager tom;

    private FStat f;

    private static GUI instance;
    private GUI(){
        tom = new TableOrdersManager();
        iom = new InternetOrdersManager();

        kw = new KitchenWindow(25);
        kw.setVisible(true);
        iw = new InternetUserWindow();
        iw.setVisible(true);
        ww = new WaitersWindow();
        ww.setVisible(true);
        new Thread(this).start();
    }

    public int[] getFreeTables()  {return tom.freeTableNumbers();}

    private class FStat extends JFrame implements Runnable{

        FStat(){
            super("Order statistics");
            setLayout(new GridLayout(0, 2));
            getContentPane().add(new JLabel("Online orders: "));
            getContentPane().add(new JLabel(""));
            getContentPane().add(new JLabel("Total orders:"));
            getContentPane().add(new JLabel(Integer.toString(iom.ordersQuantity())));
            getContentPane().add(new JLabel("Planned revenue:"));
            getContentPane().add(new JLabel(Double.toString(iom.ordersCostSummary())));
            getContentPane().add(new JLabel("Restaurant orders:"));
            getContentPane().add(new JLabel(""));
            getContentPane().add(new JLabel("Tables occupied:"));
            getContentPane().add(new JLabel(Integer.toString(tom.ordersQuantity())));
            getContentPane().add(new JLabel("Planned revenue:"));
            getContentPane().add(new JLabel(Double.toString(tom.ordersCostSummary())));
            getContentPane().add(new JLabel("Total"));
            getContentPane().add(new JLabel(""));
            getContentPane().add(new JLabel("Planned revenue:"));
            getContentPane().add(new JLabel(Double.toString(tom.ordersCostSummary() + iom.ordersCostSummary())));
            setSize(320, 320);
            setDefaultCloseOperation(HIDE_ON_CLOSE);
            new Thread(this).start();
        }

        @Override
        public void run() {
            while (true){
                ((JLabel)getContentPane().getComponent(3)).setText(Integer.toString(iom.ordersQuantity()));
                ((JLabel)getContentPane().getComponent(5)).setText(Double.toString(iom.ordersCostSummary()));
                ((JLabel)getContentPane().getComponent(9)).setText(Integer.toString(tom.ordersQuantity()));
                ((JLabel)getContentPane().getComponent(11)).setText(Double.toString(tom.ordersCostSummary()));
                ((JLabel)getContentPane().getComponent(15)).setText(Double.toString(tom.ordersCostSummary() + iom.ordersCostSummary()));
            }
        }
    }

    public void openFullStat(){
        if(f == null) f = new FStat();
        f.setVisible(true);

    }
    public void showAll(){
        kw.setVisible(true);
        iw.setVisible(true);
        ww.setVisible(true);
    }

    public void addOrder(int tNum, boolean mature){
        var to = new TableOrder();
        to.setCustomer(mature ? Customer.MATURE_UNKNOWN_CUSTOMER : Customer.NOT_MATURE_UNKNOWN_CUSTOMER);
        try{
            tom.add(to, tNum);
        }catch (Exception e){
            JOptionPane.showMessageDialog(ww, e.getMessage(), "Table number error!", JOptionPane.WARNING_MESSAGE);
        }
        kw.notifyTableOrderAdded(tNum);
    }

    public void addToOrder(String[] arr, int tNum){
        MenuItem mi;
        try{
            if(arr.length == 3) {
                mi = new Dish(Double.parseDouble(arr[0]), arr[1], arr[2]);
            }
            else{
                mi = new Drink(Double.parseDouble(arr[0]), arr[1], arr[2], DrinkTypeEnum.valueOf(arr[4]), Double.parseDouble(arr[3]));
                if (((Drink) mi).isAlcoholicDrink() && tom.getOrder(tNum).getCustomer().getAge() < 18) {
                    JOptionPane.showMessageDialog(ww, "You cannot order alcohol", "Alcohol is prohibited!", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(ww, "Number input error:\n"+e.getMessage(), "Input Error!", JOptionPane.WARNING_MESSAGE);
            return;
        }
    }

    public void addOrder(String[][] order){
        String [] c = iw.askCustomer();
        Customer customer;
        try{
            customer = new Customer(c[0], c[1], Integer.parseInt(c[2]), new Address(c[3], Integer.parseInt(c[4]), c[5], Integer.parseInt(c[6]), c[7].charAt(0), Integer.parseInt(c[8])));
        }catch (IllegalArgumentException e){
            JOptionPane.showMessageDialog(iw, "Invalid age");
            return;
        }
        MenuItem[] items = new MenuItem[order.length];
        for(int i = 0; i< order.length; i++){
            try{
                if(order[i].length == 3) {
                    items[i] = new Dish(Double.parseDouble(order[i][0]), order[i][1], order[i][2]);
                } else {
                    items[i] = new Drink(Double.parseDouble(order[i][0]), order[i][1], order[i][2], DrinkTypeEnum.valueOf(order[i][4]), Double.parseDouble(order[i][3]));
                    if (((Drink) items[i]).isAlcoholicDrink() && customer.getAge() < 18) {
                        JOptionPane.showMessageDialog(ww, "You cannot order alcohol", "Alcohol is prohibited!", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(iw, "In order item number "+ (i+1)+ " incorrect number entered.");
                return;
            }
        }
        InternetOrder io;
        io = new InternetOrder(items, customer);
        try{
            iom.add(io);
        } catch (OrderAlreadyAddedException e) {
            JOptionPane.showMessageDialog(iw, e.getMessage());
        }
        if(iom.ordersQuantity() == 1) kw.setIntOrder(iom.order());
        JOptionPane.showMessageDialog(iw, "The order is in the processing queue!");
    }

    public Order getOrder(int tableNum){
        return tom.getOrder(tableNum);
    }
    public void removeOrder(int tNum){
        tom.remove(tNum);
        ww.onRemovedOrder(tNum);
        kw.onRemoveOrder(tNum);
    }
    public void removeOrder(){
        if (iom.ordersQuantity() > 0) {
            iom.remove();
        } else {
            JOptionPane.showMessageDialog(kw, "There are no orders in the queue!");
        }
        try{
            kw.setIntOrder(iom.order());
        }catch (Exception e){
            kw.setIntOrder(null);
        }
    }

    public static GUI getInstance(){
        if(instance == null) instance = new GUI();
        return instance;
    }

    @Override
    public void run() {
        while (true) {
            if (!kw.isVisible() && !iw.isVisible() && !ww.isVisible())
                System.exit(0);
            kw.updateDataLabel(tom.ordersQuantity(), iom.ordersQuantity());
        }

    }


    public static void main(String[] args) {
        GUI.getInstance();
    }
}