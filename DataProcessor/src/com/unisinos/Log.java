package com.unisinos;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Log {
	
	static JFrame frame;
	static JPanel panel;
	static JTextArea tfLog;
	
	public static void show(String msg) { 
		if(frame == null) {
			frame = new JFrame("DataProcessor - Felipe Keller v1.0");
			
			JPanel panel = new JPanel(new BorderLayout());
			tfLog = new JTextArea();
			panel.add(new JScrollPane(tfLog), BorderLayout.CENTER);
			
			frame.add(panel);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setPreferredSize(new Dimension(500, 400));
			frame.pack();
			frame.setVisible(true);
		}
		tfLog.setText(tfLog.getText() + "\r\n" + msg);
	}

}
