package com.c3s.GUI;

import com.c3s.Config;
import com.c3s.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;


public class Panel extends JFrame {
    JPanel logsPanel = new JPanel() ;
    JPanel contentPanel = new JPanel() ;


    JButton submit = new JButton("Submit") ;
    JFormattedTextField pCount = new JFormattedTextField();
    JLabel processesCount = new JLabel() ;
    JPanel input = new JPanel() ;

    public  Panel()
    {
        setTitle("Bully");
        setSize(Config.width, Config.height);


        logsPanel.setLayout(new BoxLayout(logsPanel,BoxLayout.Y_AXIS));
        input.setLayout(new FlowLayout());

        pCount.setColumns(5);
        pCount.setPreferredSize(new Dimension(50 , 30));
        submit.setPreferredSize(new Dimension(80 , 30));

        processesCount.setText("Enter Process Count");
        processesCount.setFont(new Font("Helvetica", Font.PLAIN, 15 ));


        input.setSize(new Dimension(100,100));

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.out.println("Exit");
                Main.kill_all();
                System.exit(0);
            }
        });


        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(pCount.getText());

                try {
                    Main.init(Integer.parseInt(pCount.getText()));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } );

        input.add(processesCount);
        input.add(pCount) ;
        input.add(submit) ;

        add(input) ;
        contentPanel.add(logsPanel,BorderLayout.EAST);
        getContentPane().add(new JScrollPane(contentPanel),BorderLayout.EAST);
        setVisible(true);
        addLogsOutput("Waiting For logs Messages it takes around 10 seconds to start all the processes.....................");
    }

    public void addLogsOutput (String  log){
        JLabel label = new JLabel(log);
        if (log.contains("down")){
            label.setForeground(Color.RED);
        }else if (log.contains("Coordinator")){
            label.setForeground(new Color(0,100,0));
        }
        label.setFont(new Font("Helvetica", Font.PLAIN, 15 ));
        logsPanel.add(label);
        logsPanel.getRootPane().revalidate();

    }




}
