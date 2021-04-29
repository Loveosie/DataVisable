package com.itheima.service;

import com.itheima.mapper.UserMapper;
import com.itheima.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: user_management
 * @description:
 * @author: lance
 * @create: 2021-04-20 22:12
 */
@Service
public class StatService {

	@Autowired
	private UserMapper userMapper;
	public List<Map> columnCharts() {
		return userMapper.columnCharts();
	}

	public List<Map> lineCharts() {
		return userMapper.lineCharts();
	}

	/*{
		  name: '山东省',
	      y: 7,
          id: '山东省',
          drilldown: '山东省'
          data: [
				{name:'济南市',y:2},
				{name:'威海市',y:2},
				{name:'青岛市',y:3},
		  ]
	}*/
	public List<Map<String, Object>> pieCharts() {
		//省份信息
		List<User> users = userMapper.selectAll();
		List<Map<String, Object>> resultMapList = new ArrayList<>();
		Map<String, List<User>> provinceMap = users.stream().collect(Collectors.groupingBy(User::getProvince));
		Map<String, Object> resultMap = null;
		for (String provinceName : provinceMap.keySet()) {
			resultMap = new HashMap<>();
			resultMap.put("name",provinceName);
			List<User> userList = provinceMap.get(provinceName);
			resultMap.put("y",userList.size());
			resultMap.put("drilldown",provinceName);
			resultMap.put("id",provinceName);

			// 下钻城市信息
			Map<String, List<User>> userCityMap = userList.stream().collect(Collectors.groupingBy(User::getCity));
			List<Map<String, Object>> userCityList = new ArrayList<>();
			for (String cityName : userCityMap.keySet()) {
				Map<String, Object> cityMap = new HashMap<>();
				cityMap.put("name",cityName);
				cityMap.put("y",userCityMap.get(cityName).size());
				userCityList.add(cityMap);
			}
			resultMap.put("data",userCityList);
			resultMapList.add(resultMap);
		}
		return resultMapList;
	}
	//{"province":[{'name':'河北省','value':9},{},{}],"city":[{},{},{}]}
	public Map pieECharts() {
		Example  example = new Example(User.class);
		example.setOrderByClause("province,city");
		List<User> users = userMapper.selectByExample(example);

		Map resultMap = new HashMap();
		List<Map> provinceMapList = new ArrayList<>();
		List<Map> cityMapList = new ArrayList<>();

		// 生成有序集合
		Map<String, List<User>> sortedMapByProvince = users.stream().collect(Collectors.groupingBy(User::getProvince, LinkedHashMap::new, Collectors.toList()));
		for (String province : sortedMapByProvince.keySet()) {
			Map<String, Object> provinceMap = new HashMap();
			provinceMap.put("name",province);
			provinceMap.put("value",sortedMapByProvince.get(province).size());
			provinceMapList.add(provinceMap);
			Map<String, List<User>>  sortedMapByCity = users.stream().collect(Collectors.groupingBy(User::getCity, LinkedHashMap::new, Collectors.toList()));
			for (String city : sortedMapByCity.keySet()) {
				Map<String, Object> cityMap = new HashMap();
				cityMap.put("name",city);
				cityMap.put("value",sortedMapByCity.get(city).size());
				cityMapList.add(cityMap);
			}
		}
		resultMap.put("province",provinceMapList);
		resultMap.put("city",cityMapList);
		return resultMap;
	}
}
