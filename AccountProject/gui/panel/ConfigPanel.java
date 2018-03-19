package gui.panel;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import util.ColorUtil;
import util.GUIUtil;

public class ConfigPanel extends JPanel{
	static{
		GUIUtil.useLNF();
	}
	public static ConfigPanel instance = new ConfigPanel();
	JLabel lBudget = new JLabel("budget");
	public JTextField tfBudget = new JTextField("0");
	JLabel lMysql = new JLabel("Mysql path");
	public JTextField tfMysql = new JTextField("");
	JButton bSubmit = new JButton("update");
	public ConfigPanel() {
		GUIUtil.setColor(ColorUtil.grayColor, lBudget,lMysql);
		GUIUtil.setColor(ColorUtil.blueColor, bSubmit);
		
		JPanel pInput = new JPanel();
		JPanel pSubmit = new JPanel();
		int gap = 40;
		pInput.setLayout(new GridLayout(4,1,gap,gap));
		
		pInput.add(lBudget);
		pInput.add(tfBudget);
		
		pInput.add(lMysql);
		pInput.add(tfMysql);
		
		this.setLayout(new BorderLayout());
		this.add(pInput,BorderLayout.NORTH);
		
		pSubmit.add(bSubmit);
		this.add(pSubmit,BorderLayout.CENTER);
		
		
		
	}
	public static void main(String[] args) {
		GUIUtil.showPanel(ConfigPanel.instance);
	}
	
	
}
