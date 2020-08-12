package gui;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Main extends JFrame implements ActionListener {
   private static final long serialVersionUID = 1L;
   private RoadSystemPanel panel;
   private String[] names = {"Exit","Blue","None","Blue","Magenta","Orange", "Random","Help"};
   private JMenu m1, m2, m3, m4;
   private JMenuItem[] mi;
   private JMenuBar mb;

   
   public static void main(String[]args) {
	   Main fr = new Main();
	   fr.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	   fr.setSize(845,715);
	   fr.setVisible(true);
   }

   
   public Main() {
	    super("Road system");
	    panel = new RoadSystemPanel(this);
	    add(panel);
	    panel.setVisible(true);

		mb = new JMenuBar();
		m1 = new JMenu("File");
		m2 = new JMenu("Background");
		m3 = new JMenu("Vehicles color");
		m4 = new JMenu("Help");
		mi = new JMenuItem[names.length];

		for(int i=0;i<names.length;i++) {
		    mi[i]=new JMenuItem(names[i]);
		    mi[i].addActionListener(this);
		}

		m1.add(mi[0]);

		m2.add(mi[1]);
		m2.addSeparator();
		m2.add(mi[2]);


		m3.add(mi[3]);
		m3.addSeparator();
		m3.add(mi[4]);
		m3.addSeparator();
		m3.add(mi[5]);
		m3.addSeparator();
		m3.add(mi[6]);
		
		m4.add(mi[7]);

		mb.add(m1);
		mb.add(m2);
		mb.add(m3);
		mb.add(m4);
		setJMenuBar(mb);
   }
   

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == mi[0])
			destroy();
		else if(e.getSource() == mi[1])
			panel.setBackgr(1);
		else if(e.getSource() == mi[2])
			panel.setBackgr(0);
		else if(e.getSource() == mi[3])
			panel.setColorIndex(0);
		else if(e.getSource() == mi[4])
			panel.setColorIndex(1);
		else if(e.getSource() == mi[5])
			panel.setColorIndex(2);
		else if(e.getSource() == mi[6])
			panel.setColorIndex(3);
		else if(e.getSource() == mi[7])
			printHelp();
	}
	
	
	public void destroy() {
		System.exit(0);
	}
	
	
	public void printHelp() {
		JOptionPane.showMessageDialog(this, "Home Work 3\nGUI @ Threads");
	}

}
