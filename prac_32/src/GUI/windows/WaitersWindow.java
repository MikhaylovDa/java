package GUI.windows;

import GUI.GUI;
import GUI.windows.commonelements.GeneralMenu;
import GUI.windows.commonelements.OrderPane;

import javax.swing.*;
import java.awt.*;

public class WaitersWindow extends JFrame {

    private JTabbedPane wTables;

    public WaitersWindow(){
        super("WaitersWindow");
        setSize(800, 600);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        wTables = new JTabbedPane();
        wTables.setTabPlacement(SwingConstants.RIGHT);
        var newGuest = new JButton("Serve the guest");
        newGuest.addActionListener(e->{
            int[] tn = GUI.getInstance().getFreeTables();
            if (tn.length == 0) {
                JOptionPane.showMessageDialog(this, "No free tables");
            }
            Integer[] tno = new Integer[tn.length];
            for (int i = 0; i < tn.length; i++) {
                tno[i] = tn[i]+1;
            }
            Integer t = (Integer) JOptionPane.showInputDialog(this, "Specify a table: ", "Start order processing", JOptionPane.INFORMATION_MESSAGE, null, tno,  tno[0]);
            try{
                GUI.getInstance().addOrder(t-1, JOptionPane.showConfirmDialog(this, "Is the guest an adult??", "Getting started with a client", JOptionPane.YES_NO_OPTION) == 0);
            }catch (NullPointerException ignored){}

            wTables.addTab("Table no. "+t, new OrderPane(t-1));

        });

        getContentPane().add(newGuest, BorderLayout.NORTH);
        getContentPane().add(wTables, BorderLayout.CENTER);

        setJMenuBar(new GeneralMenu(this, new JMenu[0]));
    }

    public void onRemovedOrder(int tNum){
        for(int i = 0; i < wTables.getTabCount(); i++){
            if(Integer.parseInt(wTables.getTitleAt(i).substring(9)) - 1 == tNum){
                wTables.removeTabAt(i);
                break;
            }
        } }
}
