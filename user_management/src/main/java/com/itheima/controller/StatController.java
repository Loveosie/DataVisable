package com.itheima.controller;

import com.itheima.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @program: user_management
 * @description: highCharts状态调整
 * @author: lance
 * @create: 2021-04-20 22:09
 */
@RestController
@RequestMapping("/stat")
public class StatController {
	@Autowired
	private StatService statService;
	// columnCharts()  统计各部门人数
	@GetMapping(value = "/columnCharts",name = "统计各部门人数")
	public List<Map> columnCharts(){
		return statService.columnCharts();
	}


	//lineCharts() 月份入职人数统计
	@GetMapping(value = "/lineCharts",name = "月份入职人数统计")
	public List<Map> lineCharts(){
		return statService.lineCharts();
	}


	//pieCharts() 员工地方来源统计
	@RequestMapping(value = "/pieCharts",name = "员工地方来源统计")
	public List<Map<String, Object>> pieCharts(){
		return statService.pieCharts();
	}

	//pieCharts() 员工地方来源统计
	@RequestMapping(value = "/pieECharts",name = "员工地方来源统计ECharts")
	public Map pieECharts(){
		//{"province":[{},{},{}],"city":[{},{},{}]}
		return statService.pieECharts();
	}
}
