package gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class CreateRoadSystemlDialog extends JDialog  implements  ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel p1,p2;
    private JButton ok, cancel;
    private JLabel lbl_sz, lbl_hor;
    private JSlider sl_sz, sl_hor;
    private RoadSystemPanel rs;
 
    public CreateRoadSystemlDialog(Main parent, RoadSystemPanel pan, String title) {
    	super((Main)parent,title,true);
    	rs = pan;

    	setSize(600,300);
	
		setBackground(new Color(100,230,255));
		p1 = new JPanel();
		p2 = new JPanel();
	
		p1.setLayout(new GridLayout(4,1,10,5));
		lbl_sz = new JLabel("Number of junctions",JLabel.CENTER);
		p1.add(lbl_sz);
		lbl_hor = new JLabel("Number of vehicles",JLabel.CENTER);
		
		sl_sz = new JSlider(3,20);
		sl_sz.setMajorTickSpacing(1);
		sl_sz.setMinorTickSpacing(1);
		sl_sz.setPaintTicks(true);
		sl_sz.setPaintLabels(true);
		p1.add(sl_sz);
		
		p1.add(lbl_hor);
		sl_hor = new JSlider(0,50);
		sl_hor.setMajorTickSpacing(5);
		sl_hor.setMinorTickSpacing(1);
		sl_hor.setPaintTicks(true);
		sl_hor.setPaintLabels(true);
		p1.add(sl_hor);
		
		p2.setLayout(new GridLayout(1,2,5,5));
		ok=new JButton("OK");
		ok.addActionListener(this);
		ok.setBackground(Color.lightGray);
		p2.add(ok);		
		cancel=new JButton("Cancel");
		cancel.addActionListener(this);
		cancel.setBackground(Color.lightGray);
		p2.add(cancel);
		
		setLayout(new BorderLayout());
		add("North" , p1);
		add("South" , p2);
    }
    
    

    public void actionPerformed(ActionEvent e) {
 		if(e.getSource() == ok){
		    rs.createNewRoadSystem(sl_sz.getValue(),sl_hor.getValue());
		    setVisible(false);
		}
		else 
		    setVisible(false);
    }
}
