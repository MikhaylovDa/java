package GUI.windows;

import GUI.GUI;
import GUI.windows.commonelements.GeneralMenu;
import GUI.windows.commonelements.OrderUI;
import app.order.Order;

import javax.swing.*;
import java.awt.*;

public class KitchenWindow extends JFrame {

    private JLabel summaryLabel;
    private JPanel tablesOrders;

    public KitchenWindow(int tablesCount){
        super("KitchenWindow");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(800, 600);
        summaryLabel = new JLabel("Restaurant orders: 0, online orders: 0");
        tablesOrders = new JPanel();
        tablesOrders.setLayout(new BoxLayout(tablesOrders, BoxLayout.Y_AXIS));
        for(int i = 0; i < tablesCount; i++){
            class OrderWindow extends JFrame{
                OrderWindow(OrderUI ui, int i){
                    getContentPane().add(new JScrollPane(ui), BorderLayout.CENTER);
                    setSize(200, 600);
                    JButton b = new JButton("Completed");
                    b.addActionListener(e-> {
                        GUI.getInstance().removeOrder(i);
                        this.dispose();
                    });
                    getContentPane().add(b, BorderLayout.SOUTH);
                }
            }
            JButton b = new JButton("Table no."+(i+1));
            b.addActionListener(e->{
                try{
                    var ow = new OrderWindow(new OrderUI(GUI.getInstance().getOrder(Integer.parseInt(b.getText().substring(9)) - 1)), Integer.parseInt(b.getText().substring(9)) -1);
                    ow.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "There is no order!", JOptionPane.INFORMATION_MESSAGE);
                }
            });
            tablesOrders.add(b);
        }

        var sp = new JScrollPane(tablesOrders);
        getContentPane().add(summaryLabel, BorderLayout.NORTH);
        getContentPane().add(sp, BorderLayout.WEST);
        JPanel p = new JPanel();
        JButton b = new JButton("Execute");
        b.addActionListener(e->GUI.getInstance().removeOrder());
        getContentPane().add(p);
        setIntOrder(null);
        p.add(b);

        JMenu kMenu = new JMenu("Kitchen");
        JMenuItem stat = new JMenuItem("Complete order statistics");
        stat.addActionListener(e->GUI.getInstance().openFullStat());
        kMenu.add(stat);
        setJMenuBar(new GeneralMenu(this, new JMenu[]{kMenu}));
    }

    public void updateDataLabel(int t, int i){
        summaryLabel.setText("Restaurant orders:" + t + ", online orders: "+i);
    }

    public void notifyTableOrderAdded(int num){
        ((JButton)tablesOrders.getComponent(num)).setIcon(new ImageIcon(this.getClass().getResource("img/hasOrder.png")));
    }

    public void setIntOrder(Order o){
        if(((JPanel)getContentPane().getComponent(2)).getComponentCount() > 0)
            ((JPanel)getContentPane().getComponent(2)).remove(0);
        if(o == null) {
            ((JPanel)getContentPane().getComponent(2)).add(new JLabel("No online orders yet!"), 0);
        }else{
            ((JPanel)getContentPane().getComponent(2)).add(new OrderUI(o), 0);
        }
    }

    public void onRemoveOrder(int tNum) {
        ((JButton)tablesOrders.getComponent(tNum)).setIcon(null);
    }
}
