package wwq.controller.admin;


import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import wwq.entity.admin.Menu;
import wwq.page.admin.Page;
import wwq.service.admin.MenuService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/menu")
public class MenuController {


    @Autowired
    private MenuService menuService;


    /**
     *菜单管理列表页
     * @param model
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView model){
        model.addObject("topList",menuService.findTopList());
        model.setViewName("menu/list");
        return model;
    }


    /**
     * 获取菜单管理页
     * @param page
     * @param name
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getMenuList(Page page,
                                          @RequestParam(name = "name",required = false,defaultValue = "") String name){

        Map<String,Object> ret = new HashMap<String,Object>();
        Map<String,Object> queryMap = new HashMap<String,Object>();
        queryMap.put("offset",page.getOffset());
        queryMap.put("pageSize",page.getRows());
        queryMap.put("name",name);
        List<Menu> findList = menuService.findList(queryMap);
        ret.put("rows",findList);
        ret.put("total",menuService.getTotal(queryMap));
        return ret;
    }


    /**
     * 添加菜单
     * @param menu
     * @return
     */
     @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(Menu menu){
        Map<String,String> ret = new HashMap<String, String>();
        if (menu == null){
            ret.put("type","error");
            ret.put("msg","请填写正确的菜单信息！");
            return ret;
        }
        if (StringUtil.isEmpty(menu.getName())){
            ret.put("type","error");
            ret.put("msg","请填写菜单名称！");
            return ret;
        }
        if (StringUtil.isEmpty(menu.getIcon())){
            ret.put("type","error");
            ret.put("msg","请填写菜单图标！");
            return ret;
        }
        if (menu.getParentId() == null){
            menu.setParentId(0l);
        }
        if (menuService.add(menu) <= 0){
            ret.put("type","error");
            ret.put("msg","添加失败，请联系管理员");
            return ret;
        }
        ret.put("type","success");
        ret.put("msg","添加成功！");
        return ret;
    }


    /**
     * 获取图标
     * @param request
     * @return
     */
    @RequestMapping(value = "/get_icons",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getIconList(HttpServletRequest request){
        Map<String,Object> ret = new HashMap<String,Object>();
        List<String> icons = new ArrayList<String>();
        String realPath = request.getServletContext().getRealPath("/");
        File file = new File(realPath+"\\resources\\admin\\easyui\\css\\icons");
        if (!file.exists()){
            ret.put("type","error");
            ret.put("msg","文件目录不存在");
            return ret;
        }
        File[] listFiles = file.listFiles();
        for (File f : listFiles){
            if (f!=null && f.getName().contains("png")){
                icons.add("icon-"+f.getName().substring(0,f.getName().indexOf(".")).replace("_", "-"));
            }
        }
//        System.out.println(icons);
        ret.put("type","success");
        ret.put("content",icons);
        return ret;
    }


    /**
     * 修改菜单
     * @param menu
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> edit(Menu menu){
        Map<String,String> ret = new HashMap<String, String>();
        if (menu == null){
            ret.put("type","error");
            ret.put("msg","请选择正确的菜单信息！");
            return ret;
        }
        if (StringUtil.isEmpty(menu.getName())){
            ret.put("type","error");
            ret.put("msg","请修改正确的菜单名称！");
            return ret;
        }
        if (StringUtil.isEmpty(menu.getIcon())){
            ret.put("type","error");
            ret.put("msg","请修改正确的菜单图标！");
            return ret;
        }
        if (menu.getParentId() == null){
            menu.setParentId(0l);
        }
        if (menuService.edit(menu) <= 0){
            ret.put("type","error");
            ret.put("msg","修改失败，请联系管理员");
            return ret;
        }
        ret.put("type","success");
        ret.put("msg","修改成功！");
        return ret;
    }


    /**
     * 删除菜单
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
            ret.put("msg", "请选择要删除的菜单信息!");
            return ret;
        }
        List<Menu> findChildernList = menuService.findChildernList(id);
        if(findChildernList != null && findChildernList.size() > 0){
            //表示该分类下存在子分类，不能删除
            ret.put("type", "error");
            ret.put("msg", "该分类下存在子分类，不能删除!");
            return ret;
        }
        if(menuService.del(id) <= 0){
            ret.put("type", "error");
            ret.put("msg", "删除失败，请联系管理员!");
            return ret;
        }
        ret.put("type", "success");
        ret.put("msg", "删除成功!");
        return ret;
    }


}
