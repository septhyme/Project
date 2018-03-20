package gui.panel;

import static util.GUIUtil.setColor;
import static util.GUIUtil.showPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.page.SpendPage;
import service.SpendService;
import util.CircleProgressBar;
import util.ColorUtil;

public class SpendPanel extends WorkingPanel {
	public static SpendPanel instance = new SpendPanel();

	JLabel lMonthSpend = new JLabel("month cost");
	JLabel lTodaySpend = new JLabel("today cost");
	JLabel lAvgSpendPerDay = new JLabel("daily cost");
	JLabel lMonthLeft = new JLabel("month remain");
	JLabel lDayAvgAvailable = new JLabel("daily remain");
	JLabel lMonthLeftDay = new JLabel("day to next month");

	JLabel vMonthSpend = new JLabel("2300");
	JLabel vTodaySpend = new JLabel("25");
	JLabel vAvgSpendPerDay = new JLabel("120");
	JLabel vMonthAvailable = new JLabel("2084");
	JLabel vDayAvgAvailable = new JLabel("389");
	JLabel vMonthLeftDay = new JLabel("15day");

	CircleProgressBar bar;

	public SpendPanel() {
		this.setLayout(new BorderLayout());
		bar = new CircleProgressBar();
		bar.setBackgroundColor(ColorUtil.blueColor);

		setColor(ColorUtil.grayColor, lMonthSpend, lTodaySpend, lAvgSpendPerDay, lMonthLeft, lDayAvgAvailable,
				lMonthLeftDay, vAvgSpendPerDay, vMonthAvailable, vDayAvgAvailable, vMonthLeftDay);
		setColor(ColorUtil.blueColor, vMonthSpend, vTodaySpend);


		this.add(center(), BorderLayout.CENTER);
		this.add(south(), BorderLayout.SOUTH);

	}

	private JPanel center() {
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(west(), BorderLayout.WEST);
		p.add(east());

		return p;
	}

	private Component east() {

		return bar;
	}

	private Component west() {
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(4, 1));
		p.add(lMonthSpend);
		p.add(vMonthSpend);
		p.add(lTodaySpend);
		p.add(vTodaySpend);
		return p;
	}

	private JPanel south() {
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(2, 4));

		p.add(lAvgSpendPerDay);
		p.add(lMonthLeft);
		p.add(lDayAvgAvailable);
		p.add(lMonthLeftDay);
		p.add(vAvgSpendPerDay);
		p.add(vMonthAvailable);
		p.add(vDayAvgAvailable);
		p.add(vMonthLeftDay);

		return p;
	}

	public static void main(String[] args) {
		showPanel(SpendPanel.instance);
	}

	@Override
	public void updateData() {
		SpendPage spend = new SpendService().getSpendPage();
		vMonthSpend.setText(spend.monthSpend);
		vTodaySpend.setText(spend.todaySpend);
		vAvgSpendPerDay.setText(spend.avgSpendPerDay);
		vMonthAvailable.setText(spend.monthAvailable);
		vDayAvgAvailable.setText(spend.dayAvgAvailable);
		vMonthLeftDay.setText(spend.monthLeftDay);

		bar.setProgress(spend.usagePercentage);
		if (spend.isOverSpend) {
			vMonthAvailable.setForeground(ColorUtil.warningColor);
			vMonthSpend.setForeground(ColorUtil.warningColor);
			vTodaySpend.setForeground(ColorUtil.warningColor);

		} else {
			vMonthAvailable.setForeground(ColorUtil.grayColor);
			vMonthSpend.setForeground(ColorUtil.blueColor);
			vTodaySpend.setForeground(ColorUtil.blueColor);
		}
		bar.setForegroundColor(ColorUtil.getByPercentage(spend.usagePercentage));
		addListener();

	}

	@Override
	public void addListener() {
		// TODO Auto-generated method stub

	}
}
