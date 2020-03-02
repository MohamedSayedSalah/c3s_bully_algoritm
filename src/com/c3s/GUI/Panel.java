package com.c3s.GUI;

import com.c3s.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Panel extends JFrame {
    JPanel logsPanel = new JPanel() ;
    JPanel contentPanel = new JPanel() ;

    public  Panel(int count)
    {


        setTitle("Bully");
        getContentPane().setLayout(new BorderLayout());


        logsPanel.setLayout(new GridLayout(500, 1, 1, 1));

//        contentPanel.add( new JScrollPane(logsPanel));
        setSize(Config.width, Config.height);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

//        this.add(contentPanel);
        getContentPane().add(BorderLayout.CENTER, new JScrollPane(logsPanel));
        setVisible(true);
    }

    public void addLogsOutput (String  log){
        JLabel label = new JLabel(log);
        label.setFont(new Font("Helvetica", Font.PLAIN, 15 ));
        logsPanel.add(label);
        logsPanel.getRootPane().revalidate();

    }

}
