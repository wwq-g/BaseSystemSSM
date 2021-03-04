package wwq.dao.admin;

import org.springframework.stereotype.Repository;
import wwq.entity.admin.Role;

import java.util.List;
import java.util.Map;

@Repository
public interface RoleDao {

    public int add(Role role);

    public int edit(Role role);

    public int delete(Long id);

    public List<Role> findList(Map<String,Object> queryMap);

    public int getTotal(Map<String,Object> queryMap);

}
