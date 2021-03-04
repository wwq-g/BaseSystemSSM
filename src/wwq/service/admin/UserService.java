package wwq.service.admin;


import org.springframework.stereotype.Service;
import wwq.entity.admin.User;


@Service
public interface UserService {

    public  User findByUsername(String username);

}
