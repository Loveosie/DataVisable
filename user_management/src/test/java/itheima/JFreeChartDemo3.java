package itheima;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @program: user_management
 * @description: JFreeChart-->柱状图
 * @author: lance
 * @create: 2021-04-20 20:51
 */
public class JFreeChartDemo3 {
	public static void main(String[] args) throws IOException {
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		dataSet.addValue(200,"销售部","2011年");
		dataSet.addValue(210,"销售部","2012年");
		dataSet.addValue(220,"销售部","2013年");
		dataSet.addValue(230,"销售部","2014年");

		dataSet.addValue(300,"技术部","2011年");
		dataSet.addValue(310,"技术部","2012年");
		dataSet.addValue(320,"技术部","2013年");
		dataSet.addValue(330,"技术部","2014年");

		dataSet.addValue(410,"广告部","2011年");
		dataSet.addValue(420,"广告部","2012年");
		dataSet.addValue(430,"广告部","2013年");
		dataSet.addValue(440,"广告部","2014年");

		StandardChartTheme theme = new StandardChartTheme("CN");
		// 大标题  图例 内容的字体设置
		theme.setExtraLargeFont(new Font("华文宋体",Font.BOLD,25));
		theme.setRegularFont(new Font("华文宋体",Font.BOLD,20));
		theme.setLargeFont(new Font("华文宋体",Font.BOLD,15));
		ChartFactory.setChartTheme(theme);

		JFreeChart lineChart = ChartFactory.createBarChart("部门招聘人数图","部门类别","人数",dataSet);
		ChartUtils.saveChartAsPNG(new File("d:\\barChar.png"),lineChart,1000,500);
	}
}
