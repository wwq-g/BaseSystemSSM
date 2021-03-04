package wwq.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wwq.dao.admin.AuthorityDao;
import wwq.entity.admin.Authority;
import wwq.service.admin.AuthorityService;

import java.util.List;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityDao authorityDao;


    @Override
    public int add(Authority authority) {
        return authorityDao.add(authority);
    }

    @Override
    public int deleteByRoleId(Long roleId) {
        return authorityDao.deleteByRoleId(roleId);
    }

    @Override
    public List<Authority> findListByRoleId(Long roleId) {
        return authorityDao.findListByRoleId(roleId);
    }
}
