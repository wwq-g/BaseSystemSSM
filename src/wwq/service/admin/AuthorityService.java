package wwq.service.admin;


import org.springframework.stereotype.Service;
import wwq.entity.admin.Authority;

import java.util.List;

@Service
public interface AuthorityService {
    public int add(Authority authority);

    public int deleteByRoleId(Long roleId);

    public List<Authority> findListByRoleId(Long roleId);

}
