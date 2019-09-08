package com.swnote.blog.controller.index;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swnote.auth.domain.User;
import com.swnote.auth.service.IUserService;
import com.swnote.blog.domain.Article;
import com.swnote.blog.domain.Group;
import com.swnote.blog.domain.Tag;
import com.swnote.blog.service.IArticleService;
import com.swnote.blog.service.IGroupService;
import com.swnote.blog.service.ITagService;
import com.swnote.common.bean.Result;
import com.swnote.common.exception.TipException;
import com.swnote.common.util.Const;
import com.swnote.common.util.HttpUtil;
import com.swnote.common.util.IdGenarator;
import com.swnote.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 文章相关信息的前端控制器类
 *
 * @author lzj
 * @since 1.0
 * @date [2019-07-31]
 */
@Slf4j
@Controller
public class ArticleController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IGroupService groupService;

    @Autowired
    private IArticleService articleService;

    @Autowired
    private ITagService tagService;

    /**
     * 加载出新增文章页面
     *
     * @param model
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/user/article/add", method = RequestMethod.GET)
    public String add(Model model, HttpServletRequest request, HttpSession session) {
        // 获取登录信息
        User tempUser = (User) session.getAttribute(Const.SESSION_USER);
        String userId = tempUser.getUserId();

        // 获取用户信息
        User user = userService.getById(userId);

        // 构建专栏的查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("creator", user.getUserId());
        params.put("status", Group.STATUS_SUCCESS);

        List<Group> groups = groupService.list(new QueryWrapper<Group>().allEq(params).orderByDesc("createTime"));

        model.addAttribute("user", user);
        model.addAttribute("groups", groups);
        return Const.BASE_INDEX_PAGE + "blog/article/add";
    }

    /**
     * 新增文章
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/user/article/add", method = RequestMethod.POST)
    @ResponseBody
    public Result add(HttpServletRequest request, HttpSession session) {
        Result result = new Result();
        try {
            // 接收参数
            String groupId = request.getParameter("groupId");
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            String tag = request.getParameter("tag");
            String description = request.getParameter("description");
            String typeStr = request.getParameter("type");
            String canTopStr = request.getParameter("canTop");
            String canCommentStr = request.getParameter("canComment");

            // 校验参数
            if (StringUtils.isEmpty(title) || StringUtils.isEmpty(content) || StringUtils.isEmpty(description)) {
                throw new TipException("缺少必要参数");
            }

            int type = 0;
            int canTop = 0;
            int canComment = 1;
            try {
                type = Integer.parseInt(typeStr);
                canTop = Integer.parseInt(canTopStr);
                canComment = Integer.parseInt(canCommentStr);
            } catch (Exception e) {
                throw new TipException("参数类型错误");
            }

            // 去html相关标签
            description = StringUtil.replaceHtmlTags(description);

            // 客户端ip
            String ip = HttpUtil.getIpAddr(request);

            // 获取session中的用户信息
            User tempUser = (User) session.getAttribute(Const.SESSION_USER);
            String userId = tempUser.getUserId();

            // 封装文章信息
            Article article = new Article();
            article.setArticleId(IdGenarator.guid());
            article.setGroupId(groupId);
            article.setTitle(title);
            article.setContent(content);
            article.setDescription(description);
            article.setType(type);
            article.setCanTop(canTop);
            article.setCanComment(canComment);
            article.setViewCount(0L);
            article.setGoodNum(0L);
            article.setBadNum(0L);
            article.setCreateTime(new Date());
            article.setCreateIp(ip);
            article.setUserId(userId);

            // 保存文章
            articleService.create(article, tag);

            result.setCode(Result.CODE_SUCCESS);
            result.setMsg("发布文章成功");
        } catch (TipException e) {
            result.setCode(Result.CODE_EXCEPTION);
            result.setMsg(e.getMessage());
        } catch (Exception e) {
            log.error("新增文章失败", e);
            result.setCode(Result.CODE_EXCEPTION);
            result.setMsg("新增文章失败");
        }
        return result;
    }

    /**
     * 加载出编辑文章页面
     *
     * @param articleId
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/user/article/edit/{articleId}", method = RequestMethod.GET)
    public String edit(@PathVariable("articleId") String articleId, Model model, HttpSession session) throws Exception {
        // 根据文章ID,获取文章信息
        Article article = articleService.getById(articleId);

        // 获取登录信息
        User tempUser = (User) session.getAttribute(Const.SESSION_USER);
        String userId = tempUser.getUserId();
        if (article == null || !userId.equals(article.getUserId())) {
            log.error("userId: " + userId + ", 修改articleId: " + articleId);
            throw new Exception("文章不存在或不能修改别人的文章");
        }

        // 获取用户信息
        User user = userService.getById(userId);

        // 构建专栏的查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("creator", user.getUserId());
        params.put("status", Group.STATUS_SUCCESS);
        List<Group> groups = groupService.list(new QueryWrapper<Group>().allEq(params).orderByDesc("createTime"));

        // 根据文章ID获取文章的标签信息
        List<Tag> tags = tagService.queryByArticleId(articleId);
        String tag = "";
        if (tags != null && !tags.isEmpty()) {
            for (Tag item : tags) {
                if ("".equals(tag)) {
                    tag = item.getTag();
                } else {
                    tag += "," + item.getTag();
                }
            }
        }

        model.addAttribute("article", article);
        model.addAttribute("user", user);
        model.addAttribute("groups", groups);
        model.addAttribute("tags", tag);
        return Const.BASE_INDEX_PAGE + "blog/article/edit";
    }

    /**
     * 编辑文章
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/user/article/edit", method = RequestMethod.POST)
    @ResponseBody
    public Result edit(HttpServletRequest request, HttpSession session) {
        Result result = new Result();
        try {
            // 接收参数
            String articleId = request.getParameter("articleId");
            String groupId = request.getParameter("groupId");
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            String tag = request.getParameter("tag");
            String description = request.getParameter("description");
            String typeStr = request.getParameter("type");
            String canTopStr = request.getParameter("canTop");
            String canCommentStr = request.getParameter("canComment");

            // 根据文章ID，获取文章信息
            Article article = articleService.getById(articleId);
            if (article == null || StringUtils.isEmpty(article.getArticleId())) {
                log.error("articleId: " + articleId + "文章不存在");
                throw new TipException("文章不存在");
            }

            // 校验参数
            if (StringUtils.isEmpty(title) || StringUtils.isEmpty(content) || StringUtils.isEmpty(description)) {
                throw new TipException("缺少必要参数");
            }

            int type = 0;
            int canTop = 0;
            int canComment = 1;
            try {
                type = Integer.parseInt(typeStr);
                canTop = Integer.parseInt(canTopStr);
                canComment = Integer.parseInt(canCommentStr);
            } catch (Exception e) {
                throw new TipException("参数类型错误");
            }

            // 去html相关标签
            description = StringUtil.replaceHtmlTags(description);

            // 获取session中的用户信息
            User tempUser = (User) session.getAttribute(Const.SESSION_USER);
            String userId = tempUser.getUserId();
            if (!userId.equals(article.getUserId())) {
                log.error("userId: " + userId + ", 修改articleId: " + articleId);
                throw new Exception("不能修改别人的文章");
            }

            // 修改文章信息
            article.setTitle(title);
            article.setGroupId(groupId);
            article.setContent(content);
            article.setType(type);
            article.setCanTop(canTop);
            article.setCanComment(canComment);
            article.setDescription(description);
            article.setUpdateTime(new Date());

            // 更新
            articleService.update(article, tag);

            result.setCode(Result.CODE_SUCCESS);
            result.setMsg("编辑文章成功");
        } catch (TipException e) {
            result.setCode(Result.CODE_EXCEPTION);
            result.setMsg(e.getMessage());
        } catch (Exception e) {
            log.error("编辑文章失败", e);
            result.setCode(Result.CODE_EXCEPTION);
            result.setMsg("编辑文章失败");
        }
        return result;
    }

    /**
     * 加载出第1页文章列表
     *
     * @param code
     * @param model
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/u/{code}/article", method = RequestMethod.GET)
    public String list(@PathVariable("code") String code, Model model, HttpSession session) throws Exception {
        return list(code, 1, model, session);
    }

    /**
     * 分页加载出文章列表页面
     *
     * @param code
     * @param pageIndex
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/u/{code}/article/{pageIndex}", method = RequestMethod.GET)
    public String list(@PathVariable("code") String code, @PathVariable("pageIndex") int pageIndex, Model model, HttpSession session) throws Exception {
        // 根据code获取用户信息
        User user = userService.getByCode(code);
        if (user == null) {
            log.error("code：" + code + "，不存在的用户");
            throw new Exception("不存在的用户");
        }
        String userId = user.getUserId();

        // 获取登录信息
        User tempUser = (User) session.getAttribute(Const.SESSION_USER);

        // 判断是否是该用户的笔记集
        boolean flag = false;
        if (tempUser != null && userId.equals(tempUser.getUserId())) {
            flag = true;
        }

        // 构建查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        if (!flag) {
            params.put("status", Article.STATUS_SUCCESS);
        }

        // 分页查询
        IPage<Article> articles = articleService.page(new Page<Article>(pageIndex, 10), new QueryWrapper<Article>().allEq(params).orderByDesc("publishTime"));

        // 获取文章相关的信息
        List<Article> list = articles.getRecords();
        if (list != null && !list.isEmpty()) {
            list.stream().forEach(article -> {
                // 作者用户ID，获取用户信息
                User articleUser = userService.getById(article.getUserId());
                if (StringUtils.isEmpty(articleUser.getRealName())) {
                    article.setUserName(articleUser.getLoginName());
                } else {
                    article.setUserName(articleUser.getRealName());
                }

                // 根据专栏ID，获取专栏信息
                if (!StringUtils.isEmpty(article.getGroupId())) {
                    Group articleGroup = groupService.getById(article.getGroupId());
                    article.setGroupName(articleGroup.getName());
                }
            });
        }

        model.addAttribute("user", user);
        model.addAttribute("articles", articles);
        return Const.BASE_INDEX_PAGE + "blog/article/list";
    }

    /**
     * 删除文章
     *
     * @param articleId
     * @param session
     * @return
     */
    @RequestMapping(value = "/user/article/delete/{articleId}", method = RequestMethod.GET)
    @ResponseBody
    public Result delete(@PathVariable("articleId") String articleId, HttpSession session) {
        Result result = new Result();
        try {
            // 根据文章ID,获取文章信息
            Article article = articleService.getById(articleId);

            // 获取登录信息
            User tempUser = (User) session.getAttribute(Const.SESSION_USER);

            if (article != null && tempUser.getUserId().equals(article.getUserId())) {
                // 删除文章
                articleService.removeById(articleId);
            } else {
                throw new TipException("删除文章失败");
            }

            result.setCode(Result.CODE_SUCCESS);
            result.setMsg("删除文章成功");
        } catch (Exception e) {
            log.error("删除文章失败", e);
            result.setCode(Result.CODE_EXCEPTION);
            result.setMsg("删除文章失败");
        }
        return result;
    }

    /**
     * 加载出文章详情页面
     *
     * @param articleId
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/p/{articleId}", method = RequestMethod.GET)
    public String view(@PathVariable("articleId") String articleId, Model model, HttpSession session) {
        // 根据ID获取文章信息
        Article article = articleService.getById(articleId);

        // 获取用户信息
        User user = userService.getById(article.getUserId());

        if (!StringUtils.isEmpty(article.getGroupId())) {
            // 获取专栏信息
            Group group = groupService.getById(article.getGroupId());
            article.setGroupName(group.getName());
        }

        // 获取文章标签信息
        List<Tag> tags = tagService.queryByArticleId(articleId);

        // 获取该用户更多的文章信息
        Wrapper<Article> queryWrapper = new QueryWrapper<Article>().lambda().eq(Article::getUserId, user.getUserId()).eq(Article::getStatus, Article.STATUS_SUCCESS).ne(Article::getArticleId, articleId).orderByDesc(Article::getPublishTime);
        List<Article> moreArticles = articleService.queryForLimit(queryWrapper, 6);

        // 获取推荐的文章信息
        List<Article> likeArticles = null;
        if (tags != null && !tags.isEmpty()) {
            // 根据标签来获取类似的文章
            List<String> tagStrs = new ArrayList<String>();
            tags.stream().forEach(tag -> tagStrs.add(tag.getTag()));
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("status", Article.STATUS_SUCCESS);
            params.put("articleId", articleId);
            likeArticles = articleService.queryForLimitByTags(params, tagStrs, 10);
        } else {
            // 获取最新的文章信息
            Wrapper<Article> likeWrapper = new QueryWrapper<Article>().lambda().eq(Article::getStatus, Article.STATUS_SUCCESS).ne(Article::getArticleId, articleId).orderByDesc(Article::getPublishTime);
            likeArticles = articleService.queryForLimit(queryWrapper, 10);
        }

        model.addAttribute("article", article);
        model.addAttribute("user", user);
        model.addAttribute("tags", tags);
        model.addAttribute("moreArticles", moreArticles);
        model.addAttribute("likeArticles", likeArticles);
        return Const.BASE_INDEX_PAGE + "blog/article/view";
    }
}