package wwq.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wwq.dao.admin.RoleDao;
import wwq.entity.admin.Role;
import wwq.service.admin.RoleService;

import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;


    @Override
    public int add(Role role) {
        return roleDao.add(role);
    }

    @Override
    public int edit(Role role) {
        return roleDao.edit(role);
    }

    @Override
    public int del(Long id) {
        return roleDao.del(id);
    }

    @Override
    public List<Role> findList(Map<String, Object> queryMap) {
        return roleDao.findList(queryMap);
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return roleDao.getTotal(queryMap);
    }
}
