package gui.panel;

import javax.swing.JLabel;

public class SpendPanel {
	public static SpendPanel instance = new SpendPanel();
	
	JLabel lmonthSpend = new JLabel("本月");
	JLabel lTodaySpend = new JLabel("本日");
	JLabel lAvgSpendPerday = new JLabel("日均");
	JLabel lMonthLeft = new JLabel("月剩余");
	JLabel lDayAvgAvailable = new JLabel("日可用");
	JLabel lMonthLeftDay = new JLabel("距离月末");
	
	JLabel vMonthSpend = new JLabel("2300");
	JLabel vTodaySpend = new JLabel("25");
	JLabel vAvgSpendPerDay = new JLabel("123");
	JLabel vMonthAvailable = new JLabel("2044");
	JLabel vDayAvgAvailable = new JLabel("333");
	JLabel vMonthLeftDay = new JLabel("12");

}	
