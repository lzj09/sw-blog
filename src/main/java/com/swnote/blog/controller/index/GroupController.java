package com.swnote.blog.controller.index;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.swnote.auth.domain.User;
import com.swnote.auth.service.IUserService;
import com.swnote.blog.domain.Category;
import com.swnote.blog.domain.Group;
import com.swnote.blog.domain.GroupFans;
import com.swnote.blog.service.IGroupFansService;
import com.swnote.blog.service.IGroupService;
import com.swnote.common.bean.Result;
import com.swnote.common.cache.CategoryCache;
import com.swnote.common.cache.ICache;
import com.swnote.common.domain.Config;
import com.swnote.common.exception.TipException;
import com.swnote.common.util.Const;
import com.swnote.common.util.IdGenarator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 专栏相关信息的前端控制器类
 *
 * @author lzj
 * @since 1.0
 * @date [2019-07-14]
 */
@Slf4j
@Controller
public class GroupController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IGroupService groupService;

    @Autowired
    private IGroupFansService groupFansService;

    @Resource(name = "categoryCache")
    private ICache<List<Category>> categoryCache;

    @Resource(name = "configCache")
    private ICache<Config> configCache;

    /**
     * 加载出用户的专栏列表页面
     *
     * @param code
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/u/{code}/group", method = RequestMethod.GET)
    public String myGroup(@PathVariable("code") String code, Model model, HttpSession session) {
        // 根据code获取用户信息
        User user = userService.getByCode(code);

        // 获取登录信息
        User tempUser = (User) session.getAttribute(Const.SESSION_USER);

        // 判断是否是该用户的笔记集
        boolean flag = false;
        if (tempUser != null && tempUser.getUserId().equals(user.getUserId())) {
            flag = true;
        }

        // 构建专栏的查询条件
        // 构建查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("creator", user.getUserId());
        if (!flag) {
            params.put("status", Group.STATUS_SUCCESS);
        }

        List<Group> groups = groupService.list(new QueryWrapper<Group>().allEq(params).orderByDesc("createTime"));
        // TODO 关于专栏相关的统计信息后续加入

        // 如果用户是登录状态，则获取该用户关注专栏的信息
        if (tempUser != null) {
            List<GroupFans> groupFans = groupFansService.list(new QueryWrapper<GroupFans>().lambda().eq(GroupFans::getUserId, tempUser.getUserId()));
            if (groupFans != null && !groupFans.isEmpty()) {
                Set<String> followGroupIds = new HashSet<String>();
                for (GroupFans item : groupFans) {
                    followGroupIds.add(item.getGroupId());
                }

                model.addAttribute("followGroupIds", followGroupIds);
            }
        }

        model.addAttribute("user", user);
        model.addAttribute("groups", groups);
        return Const.BASE_INDEX_PAGE + "blog/group/myGroup";
    }

    /**
     * 加载出新增专栏页面
     *
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "/user/group/add", method = RequestMethod.GET)
    public String add(HttpSession session, Model model) {
        // session中的信息
        User sessionUser = (User) session.getAttribute(Const.SESSION_USER);

        // 从数据库中获取用户信息
        User user = userService.getById(sessionUser.getUserId());

        // 加载出所有根节点下的分类信息
        List<Category> categorys = categoryCache.get(CategoryCache.ROOT_CATEGORY_ID);

        model.addAttribute("user", user);
        model.addAttribute("categorys", categorys);
        return Const.BASE_INDEX_PAGE + "blog/group/add";
    }

    /**
     * 创建专栏
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/user/group/add", method = RequestMethod.POST)
    @ResponseBody
    public Result add(HttpServletRequest request, HttpSession session) {
        Result result = new Result();
        try {
            // 接收参数
            String categoryId = request.getParameter("categoryId");
            String name = request.getParameter("name");
            String logo = request.getParameter("logo");
            String introduce = request.getParameter("introduce");

            // 校验参数
            if (StringUtils.isEmpty(categoryId) || StringUtils.isEmpty(name) || StringUtils.isEmpty(logo) || StringUtils.isEmpty(introduce)) {
                throw new TipException("缺少必要参数");
            }

            // 获取登录信息
            User tempUser = (User) session.getAttribute(Const.SESSION_USER);
            String userId = tempUser.getUserId();

            // 构建专栏对象
            Group group = new Group();
            group.setGroupId(IdGenarator.longIdStr());
            group.setName(name);
            group.setLogo(logo);
            group.setIntroduce(introduce);
            group.setCategoryId(categoryId);
            group.setCreator(userId);
            group.setCreateTime(new Date());
            // 从系统配置项获取专栏是否审核
            Config config = configCache.get(Config.CONFIG_GROUP_AUDIT);
            if (config != null && "1".equals(config.getConfigValue())) {
                // 需要审核
                group.setStatus(Group.STATUS_NO);
            } else {
                // 不需要审核
                group.setStatus(Group.STATUS_SUCCESS);
            }

            // 保存专栏信息
            boolean flag = groupService.save(group);
            if (!flag) {
                throw new TipException("创建专栏失败");
            }

            result.setCode(Result.CODE_SUCCESS);
            result.setMsg("成功创建专栏");
        } catch (TipException e) {
            result.setCode(Result.CODE_EXCEPTION);
            result.setMsg(e.getMessage());
        } catch (Exception e) {
            log.error("创建专栏失败", e);
            result.setCode(Result.CODE_EXCEPTION);
            result.setMsg("创建专栏失败");
        }
        return result;
    }

    /**
     * 加载出修改专栏页面
     *
     * @param groupId
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/user/group/edit/{groupId}", method = RequestMethod.GET)
    public String edit(@PathVariable("groupId") String groupId, Model model, HttpSession session) throws Exception {
        // 根据id获取专栏信息
        Group group = groupService.getById(groupId);
        if (group == null || StringUtils.isEmpty(group.getGroupId())) {
            log.error("groupId: " + groupId + ": 该专栏不存在");
            throw new Exception("该专栏不存在");
        }

        // 获取用户信息
        User tempUser = (User) session.getAttribute(Const.SESSION_USER);
        String userId = tempUser.getUserId();
        if (!userId.equals(group.getCreator())) {
            log.error("userId: " + userId + "修改别人的groupId: " + groupId);
            throw new Exception("不能修改别人的专栏");
        }

        // 从数据库中获取用户信息
        User user = userService.getById(userId);

        // 加载出所有根节点下的分类信息
        List<Category> categorys = categoryCache.get(CategoryCache.ROOT_CATEGORY_ID);

        model.addAttribute("group", group);
        model.addAttribute("user", user);
        model.addAttribute("categorys", categorys);
        return Const.BASE_INDEX_PAGE + "blog/group/edit";
    }

    /**
     * 修改专栏
     *
     * @param groupId
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/user/group/edit/{groupId}", method = RequestMethod.POST)
    @ResponseBody
    public Result edit(@PathVariable("groupId") String groupId, HttpServletRequest request, HttpSession session) {
        Result result = new Result();
        try {
            // 根据id获取专栏信息
            Group group = groupService.getById(groupId);
            if (group == null || StringUtils.isEmpty(group.getGroupId())) {
                log.error("groupId: " + groupId + ": 该专栏不存在");
                throw new TipException("该专栏不存在");
            }

            // 获取用户信息
            User tempUser = (User) session.getAttribute(Const.SESSION_USER);
            String userId = tempUser.getUserId();
            if (!userId.equals(group.getCreator())) {
                log.error("userId: " + userId + "修改别人的groupId: " + groupId);
                throw new TipException("不能修改别人的专栏");
            }

            // 接收参数
            String categoryId = request.getParameter("categoryId");
            String name = request.getParameter("name");
            String introduce = request.getParameter("introduce");
            String logo = request.getParameter("logo");

            // 校验参数
            if (StringUtils.isEmpty(categoryId) || StringUtils.isEmpty(name) || StringUtils.isEmpty(introduce)) {
                throw new TipException("缺少必要参数");
            }

            group.setCategoryId(categoryId);
            group.setName(name);
            group.setIntroduce(introduce);
            if (!StringUtils.isEmpty(logo)) {
                group.setLogo(logo);
            }
            group.setUpdateTime(new Date());

            // 修改
            boolean flag = groupService.updateById(group);
            if (!flag) {
                throw new TipException("修改专栏失败");
            }

            result.setCode(Result.CODE_SUCCESS);
            result.setMsg("修改专栏成功");
        } catch (TipException e) {
            result.setCode(Result.CODE_EXCEPTION);
            result.setMsg(e.getMessage());
        } catch (Exception e) {
            log.error("修改专栏失败", e);
            result.setCode(Result.CODE_EXCEPTION);
            result.setMsg("修改专栏失败");

        }
        return result;
    }

    /**
     * 根据ID删除专栏
     *
     * @param groupId
     * @param session
     * @return
     */
    @RequestMapping(value = "/user/group/delete/{groupId}", method = RequestMethod.GET)
    @ResponseBody
    public Result delete(@PathVariable("groupId") String groupId, HttpSession session) {
        Result result = new Result();
        try {
            // 根据id获取专栏信息
            Group group = groupService.getById(groupId);
            if (group == null || StringUtils.isEmpty(group.getGroupId())) {
                log.error("groupId: " + groupId + ": 该专栏不存在");
                throw new TipException("该专栏不存在");
            }

            // 获取用户信息
            User tempUser = (User) session.getAttribute(Const.SESSION_USER);
            String userId = tempUser.getUserId();
            if (!userId.equals(group.getCreator())) {
                log.error("userId: " + userId + "删除别人的groupId: " + groupId);
                throw new TipException("不能删除别人的专栏");
            }

            // 删除
            boolean flag = groupService.removeById(groupId);
            if (!flag) {
                throw new TipException("删除专栏失败");
            }

            // TODO 删除专栏后，需要处理其它关联的数据，由于其它模块还没有，此处后续处理

            result.setCode(Result.CODE_SUCCESS);
            result.setMsg("删除专栏成功");
        } catch (TipException e) {
            result.setCode(Result.CODE_EXCEPTION);
            result.setMsg(e.getMessage());
        } catch (Exception e) {
            log.error("删除专栏失败", e);
            result.setCode(Result.CODE_EXCEPTION);
            result.setMsg("删除专栏失败");
        }
        return result;
    }

    /**
     * 关注专栏
     *
     * @param groupId
     * @param session
     * @return
     */
    @RequestMapping(value = "/user/group/follow/{groupId}", method = RequestMethod.GET)
    @ResponseBody
    public Result follow(@PathVariable("groupId") String groupId, HttpSession session) {
        Result result = new Result();
        try {
            Group group = groupService.getById(groupId);
            if (group == null || StringUtils.isEmpty(group.getGroupId())) {
                log.error("groupId: " + groupId + ": 该专栏不存在");
                throw new TipException("该专栏不存在");
            }

            // 获取用户信息
            User tempUser = (User) session.getAttribute(Const.SESSION_USER);
            String userId = tempUser.getUserId();
            if (userId.equals(group.getCreator())) {
                throw new TipException("不能关注自己的专栏");
            }

            // 封装关注专栏信息
            GroupFans groupFans = new GroupFans();
            groupFans.setGroupId(groupId);
            groupFans.setUserId(userId);
            groupFans.setCreateTime(new Date());

            // 保存
            groupFansService.save(groupFans);

            result.setCode(Result.CODE_SUCCESS);
            result.setMsg("关注专栏成功");
        } catch (Exception e) {
            log.error("关注专栏失败", e);
            result.setCode(Result.CODE_EXCEPTION);
            result.setMsg("关注专栏失败");
        }
        return result;
    }
}