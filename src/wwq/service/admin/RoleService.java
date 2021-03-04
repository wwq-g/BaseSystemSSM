package wwq.service.admin;

import org.springframework.stereotype.Service;
import wwq.entity.admin.Menu;
import wwq.entity.admin.Role;

import java.util.List;
import java.util.Map;

@Service
public interface RoleService {

    public int add(Role role);

    public int edit(Role role);

    public int delete(Long id);

    public List<Role> findList(Map<String,Object> queryMap);

    public int getTotal(Map<String,Object> queryMap);
}
