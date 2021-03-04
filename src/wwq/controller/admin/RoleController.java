package wwq.controller.admin;


import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import wwq.entity.admin.Authority;
import wwq.entity.admin.Menu;
import wwq.entity.admin.Role;
import wwq.page.admin.Page;
import wwq.service.admin.AuthorityService;
import wwq.service.admin.MenuService;
import wwq.service.admin.RoleService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/role")
public class RoleController {


    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private MenuService menuService;


    /**
     *角色管理列表页
     * @param model
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView model){
        model.setViewName("role/list");
        return model;
    }


    /**
     * 获取角色管理页
     * @param page
     * @param name
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getroleList(Page page,
                                          @RequestParam(name = "name",required = false,defaultValue = "") String name){

        Map<String,Object> ret = new HashMap<String,Object>();
        Map<String,Object> queryMap = new HashMap<String,Object>();
        queryMap.put("offset",page.getOffset());
        queryMap.put("pageSize",page.getRows());
        queryMap.put("name",name);

        ret.put("rows",roleService.findList(queryMap));
        ret.put("total",roleService.getTotal(queryMap));
        return ret;
    }


    /**
     * 添加角色
     * @param role
     * @return
     */
     @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(Role role){
        Map<String,String> ret = new HashMap<String, String>();
        if (role == null){
            ret.put("type","error");
            ret.put("msg","请填写正确的角色信息！");
            return ret;
        }
        if (StringUtil.isEmpty(role.getName())){
            ret.put("type","error");
            ret.put("msg","请填写角色名称！");
            return ret;
        }


        if (roleService.add(role) <= 0){
            ret.put("type","error");
            ret.put("msg","添加失败，请联系管理员");
            return ret;
        }
        ret.put("type","success");
        ret.put("msg","添加成功！");
        return ret;
    }

    /**
     * 修改角色
     * @param role
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> edit(Role role){
        Map<String,String> ret = new HashMap<String, String>();
        if (role == null){
            ret.put("type","error");
            ret.put("msg","请选择正确的角色信息！");
            return ret;
        }
        if (StringUtil.isEmpty(role.getName())){
            ret.put("type","error");
            ret.put("msg","请修改正确的角色名称！");
            return ret;
        }


        if (roleService.edit(role) <= 0){
            ret.put("type","error");
            ret.put("msg","修改失败，请联系管理员");
            return ret;
        }
        ret.put("type","success");
        ret.put("msg","修改成功！");
        return ret;
    }


    /**
     * 删除角色信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> delete(
            @RequestParam(name="id",required=true) Long id
    ){
        Map<String, String> ret = new HashMap<String, String>();
        if(id == null){
            ret.put("type", "error");
            ret.put("msg", "请选择要删除的角色信息!");
            return ret;
        }

        try {
            if(roleService.del(id) <= 0){
                ret.put("type", "error");
                ret.put("msg", "删除失败，请联系管理员!");
                return ret;
            }
        } catch (Exception e) {
           ret.put("type","error");
           ret.put("msg","该角色下存在权限或者用户信息，不能删除！");
           return ret;
        }
        ret.put("type", "success");
        ret.put("msg", "删除成功!");
        return ret;
    }


    /**
     * 获取菜单列表
     * @return
     */
    @RequestMapping(value = "/get_all_menu",method = RequestMethod.POST)
    @ResponseBody
    public List<Menu> getAllMenu(){
        Map<String ,Object> queryMap = new HashMap<String,Object>();
        queryMap.put("offset",0);
        queryMap.put("pageSize",99999);
        return menuService.findList(queryMap);
    }


}
