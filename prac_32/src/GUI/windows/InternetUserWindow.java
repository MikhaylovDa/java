package GUI.windows;

import GUI.GUI;
import GUI.windows.commonelements.GeneralMenu;
import GUI.windows.commonelements.OrderPane;

import javax.swing.*;
import java.awt.*;

public class InternetUserWindow extends JFrame {

    public InternetUserWindow(){
        super("InternetUserWindow");
        setSize(800, 600);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        var op = new OrderPane(-1);
        getContentPane().add(op, BorderLayout.CENTER);
        var save = new JButton("Save");
        save.addActionListener(e->{
            String [][] ord = new String[op.getTabCount()-1][];
            for(int i = 0; i < op.getTabCount()-1; i++){
                ord[i] = ((OrderPane.MenuItemPanel)op.getComponentAt(i)).getData();
            }
            GUI.getInstance().addOrder(ord);
        });
        getContentPane().add(save, BorderLayout.SOUTH);

        setJMenuBar(new GeneralMenu(this, new JMenu[0]));
    }

    public String[] askCustomer(){
        String [] res = new String[9];
        res[0] = JOptionPane.showInputDialog("FirstName: ");
        res[1] = JOptionPane.showInputDialog("SecondName: ");
        res[2] = JOptionPane.showInputDialog("Age: ");
        res[3] = JOptionPane.showInputDialog("City: ");
        res[4] = JOptionPane.showInputDialog("ZipCode: ");
        res[5] = JOptionPane.showInputDialog("Street: ");
        res[6] = JOptionPane.showInputDialog("Building number: ");
        res[7] = JOptionPane.showInputDialog("Building letter: ");
        res[8] = JOptionPane.showInputDialog("Apartment: ");
        return res;
    }
}
