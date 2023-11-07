import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Frame extends JFrame {

	Panel panel;
	
	Frame(){
		panel = new Panel();
		this.add(panel);
		this.setTitle("Pong Game");
		this.setResizable(false);
		this.setBackground(Color.black);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack(); // frame will adjust to panel size 
		this.setVisible(true);
		this.setLocationRelativeTo(null); // to appear at center
	}
	
}
