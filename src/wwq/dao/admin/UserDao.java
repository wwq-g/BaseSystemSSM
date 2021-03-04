package wwq.dao.admin;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import wwq.entity.admin.User;

@Repository
public interface UserDao {

    public User findByUsername(String username);
}
