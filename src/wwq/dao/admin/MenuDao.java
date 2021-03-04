package wwq.dao.admin;

import org.springframework.stereotype.Repository;
import wwq.entity.admin.Menu;

import java.util.List;
import java.util.Map;

@Repository
public interface MenuDao {
    public int add(Menu menu);

    public List<Menu> findList(Map<String, Object> queryMap);

    public List<Menu> findTopList();

    public int getTotal(Map<String, Object> queryMap);

    public int edit(Menu menu);

    public int del(Long id);

    public List<Menu> findChildernList(Long id);
}
