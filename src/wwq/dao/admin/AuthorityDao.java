package wwq.dao.admin;

import org.springframework.stereotype.Repository;
import wwq.entity.admin.Authority;

import java.util.List;

@Repository
public interface AuthorityDao {

    public int add(Authority authority);

    public int deleteByRoleId(Long roleId);

    public List<Authority> findListByRoleId(Long roleId);
}
