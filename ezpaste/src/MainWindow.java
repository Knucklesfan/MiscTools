import com.tulskiy.keymaster.common.HotKey;
import com.tulskiy.keymaster.common.HotKeyListener;
import com.tulskiy.keymaster.common.Provider;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MainWindow extends JFrame {
    JFrame frame;
    int time = 5;
    MainWindow() {
        JPanel listPane = new JPanel();
        listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
        JLabel l = new JLabel("Knuxfan's dirt simple paste program");
        l.setAlignmentX(Component.CENTER_ALIGNMENT);

        listPane.add(l);
        JLabel a = new JLabel("Time to paste: " + time);
        a.setAlignmentX(Component.CENTER_ALIGNMENT);

        listPane.add(a);
        JSpinner spin = new JSpinner();
        spin.setValue(5);
        spin.setAlignmentX(Component.CENTER_ALIGNMENT);

        spin.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                        time = (int)spin.getValue();
                a.setText("Time to paste: " + time);

            }
        });



        listPane.add(spin);

        JButton b = new JButton("Paste from Clipboard");
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.addActionListener(new ActionListener() {
            @Override
                    public void actionPerformed(ActionEvent e) {
                Thread t1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(int x = (int)spin.getValue(); x >= 0; x--) {
                            try {
                                TimeUnit.SECONDS.sleep(1);
                                time = x;
                                a.setText("Time to paste: " + time);

                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                        System.out.println("it's been 5 secs");
                        time = (int)spin.getValue();
                        a.setText("Time to paste: " + time);
                        try {
                            String s = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                            main.typeString(s);
                        } catch (Exception ex) {
                            a.setText("Error! Do you have special characters in your clipboard?-");
                        }
                    }
                });
                t1.start();
            }
        });
        listPane.add(b);
        JTextPane v = new JTextPane();
        v.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        v.setSize(120,64);
        v.setAlignmentX(Component.CENTER_ALIGNMENT);
        listPane.add(v);
        Provider provider = Provider.getCurrentProvider(true);
        provider.register(KeyStroke.getKeyStroke("F7"), new HotKeyListener() {
            @Override
            public void onHotKey(HotKey hotKey) {
                try {
                    String s = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                    main.typeString(s);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error pasting, do you have special characters?");
                }
            }
        });
        provider.register(KeyStroke.getKeyStroke("F8"), new HotKeyListener() {
            @Override
            public void onHotKey(HotKey hotKey) {
                try {
                    main.typeString(v.getText());
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error pasting, do you have special characters?");
                }
            }
        });

        JButton d = new JButton("Paste above text");
        d.setAlignmentX(Component.CENTER_ALIGNMENT);
        d.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread t1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(int x = (int)spin.getValue(); x >= 0; x--) {
                            try {
                                TimeUnit.SECONDS.sleep(1);
                                time = x;
                                a.setText("Time to paste: " + time);

                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                        System.out.println("it's been 5 secs");
                        time = (int)spin.getValue();
                        a.setText("Time to paste: " + time);
                        try {
                            String s = v.getText();
                            main.typeString(s);
                        } catch (Exception ex) {
                            a.setText("Error! Do you have special characters in your clipboard?-");
                        }
                    }
                });
                t1.start();
            }
        });

        listPane.add(d);

        JLabel e = new JLabel("You can also paste in the background with F7 and F8 respectively.");
        e.setAlignmentX(Component.CENTER_ALIGNMENT);
        listPane.add(e);

        a.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(listPane);
        setSize(500,300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
