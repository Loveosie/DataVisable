package itheima;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.editor.DefaultChartEditorFactory;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @program: user_management
 * @description: JFreeChart-->饼图
 * @author: lance
 * @create: 2021-04-20 20:51
 */
public class JFreeChartDemo1 {
	public static void main(String[] args) throws IOException {
		DefaultChartEditorFactory chartEditorFactory = new DefaultChartEditorFactory();
		DefaultPieDataset dataSet = new DefaultPieDataset();
		dataSet.setValue("技术部",10);
		dataSet.setValue("运营部",20);
		dataSet.setValue("策划部",30);
		dataSet.setValue("销售部",40);

		StandardChartTheme theme = new StandardChartTheme("CN");
		// 大标题  图例 内容的字体设置
		theme.setExtraLargeFont(new Font("华文宋体",Font.BOLD,25));
		theme.setRegularFont(new Font("华文宋体",Font.BOLD,20));
		theme.setLargeFont(new Font("华文宋体",Font.BOLD,15));
		ChartFactory.setChartTheme(theme);
		// JFreeChart pieChart = ChartFactory.createPieChart("部门人数", dataSet, true, false, false);
		JFreeChart pieChart = ChartFactory.createPieChart3D("部门人数", dataSet, true, false, false);
		ChartUtils.saveChartAsPNG(new File("d:\\jfreechart.png"),pieChart,400,300);
	}
}
