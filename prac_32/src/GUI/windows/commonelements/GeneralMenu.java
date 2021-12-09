package GUI.windows.commonelements;

import GUI.GUI;

import javax.swing.*;
import java.awt.*;

public class GeneralMenu extends JMenuBar {

    public GeneralMenu(Component parent, JMenu[] others){
        JMenu fileMenu = new JMenu("File");

        JMenuItem showAll = new JMenuItem("Show all windows");
        showAll.addActionListener(e-> GUI.getInstance().showAll());
        fileMenu.add(showAll);

        var exit = new JMenuItem("Exit");
        exit.addActionListener(e->System.exit(0));
        fileMenu.add(exit);

        this.add(fileMenu);

        for(var m: others){
            this.add(m);
        }
    }
}
