package com.atguigu.crowd.service.api;

import com.atguigu.crowd.entity.Menu;

import java.util.List;

/**
 * @program: Shangchou
 * @description:  菜单业务类
 * @author: lance
 * @create: 2021-01-28 20:01
 */

public interface MenuService {


    List<Menu> getAll();

    void saveMenu(Menu menu);

    void updateMenu(Menu menu);

    void removeMenuById(Integer id);
}
