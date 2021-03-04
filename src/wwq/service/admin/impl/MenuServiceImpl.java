package wwq.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wwq.dao.admin.MenuDao;
import wwq.entity.admin.Menu;
import wwq.service.admin.MenuService;

import java.util.List;
import java.util.Map;


@Service
public class MenuServiceImpl implements MenuService
{
    @Autowired
    private MenuDao menuDao;

    @Override
    public int add(Menu menu) {
        return menuDao.add(menu);
    }

    @Override
    public List<Menu> findList(Map<String, Object> queryMap) {
        return menuDao.findList(queryMap);
    }

    @Override
    public List<Menu> findTopList() {
        return menuDao.findTopList();
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return menuDao.getTotal(queryMap);
    }

    @Override
    public int edit(Menu menu) {
        return menuDao.edit(menu);
    }

    @Override
    public int del(Long id) {
        return menuDao.del(id);
    }

    @Override
    public List<Menu> findChildernList(Long id) {
        return menuDao.findChildernList(id);
    }
}
