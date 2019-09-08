<!doctype html>
<html>
    <head>
        <title>${article.title} - 守望博客</title>
        <#include "/index/common/meta.ftl">
        <link href="${rc.contextPath}/static/plugins/editor/css/editormd.preview.css" rel="stylesheet">

        <meta name="description" content="${article.description}">

    </head>

    <body class="gray-bg">
        <#include "/index/common/header.ftl">
        <div class="wrapper-content">
            <div class="container">
                <div class="row">
                    <div class="ibox">
                        <div class="ibox-content view-content">
                            <div class="col-sm-3">
                                <div class="user-info-fw">
                                    <div class="avatar">
                                        <a href="${rc.contextPath}/u/${user.code}">
                                            <#if (user.picture)?exists>
                                                <img src="${rc.contextPath}/img/avatar/${user.picture}" class="img-circle" width="65px" height="65px"/>
                                            <#else>
                                                <img src="${rc.contextPath}/static/img/default-avatar.png" class="img-circle" width="65px" height="65px"/>
                                            </#if>
                                        </a>
                                    </div>

                                    <div class="meta">
                                        <h3><a href="${rc.contextPath}/u/${user.code}"><#if (user.realName)?exists> ${user.realName} <#else> ${user.loginName}</#if></a></h3>
                                        <div>
                                            <#if Session["_SESSION_USER"]?exists && user.userId == _SESSION_USER.userId>
                                                <a class="btn btn-primary btn-sm" href="${rc.contextPath}/user/edit">
                                                    <i class="fa fa-edit"></i> 编辑个人资料
                                                </a>
                                            <#else>
                                                <#if Session["_SESSION_USER"]?exists>

                                                <#else>
                                                    <a class="btn btn-primary btn-sm follows" href="javascript:followUserLogin('${rc.contextPath}');">
                                                        <i class="fa fa-heart-o"></i> 关注
                                                    </a>

                                                    <a class="btn btn-primary  btn-sm comments" href="javascript:followUserLogin('${rc.contextPath}');">
                                                        <i class="fa fa-comments"></i> 私信
                                                    </a>
                                                </#if>
                                            </#if>
                                        </div>
                                    </div>
                                    <div class="clear"></div>

                                    <div class="statis">
                                        <ul>
                                            <li>
                                                <div class="meta-block">
                                                    <a href="${rc.contextPath}/u/${user.code}/following">
                                                        <div>关注</div>
                                                        <b>0</b>
                                                    </a>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="meta-block">
                                                    <a href="${rc.contextPath}/u/${user.code}/followers">
                                                        <div>粉丝</div>
                                                        <b>0</b>
                                                    </a>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="meta-block">
                                                    <a href="${rc.contextPath}/u/${user.code}/group">
                                                        <div>专栏</div>
                                                        <b>0</b>
                                                    </a>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="meta-block">
                                                    <a href="${rc.contextPath}/u/${user.code}/article">
                                                        <div>文章</div>
                                                        <b>0</b>
                                                    </a>
                                                </div>
                                            </li>
                                        </ul>
                                    </div>

                                    <div class="more-note">
                                        <#list moreArticles as moreArticle>
                                            <div class="more-note-item">
                                                <a href="${rc.contextPath}/p/${moreArticle.articleId}">${moreArticle.title}</a>
                                            </div>
                                        </#list>
                                        <div class="m-note"><a href="${rc.contextPath}/u/${user.code}/article">更多文章</a></div>
                                    </div>
                                </div>

                                <div class="like-note-fw">
                                    <div class="like-top">猜你喜欢</div>
                                    <#list likeArticles as likeArticle>
                                        <div class="like-note-item">
                                            <a href="${rc.contextPath}/p/${likeArticle.articleId}">${likeArticle.title}</a>
                                        </div>
                                    </#list>
                                </div>
                            </div>

                            <div class="col-sm-9">
                                <article>
                                    <h1 class="note-title">${article.title}</h1>

                                    <div class="meta">
                                        <#if article.groupId?exists && article.groupId != "">
                                            <a href="${rc.contextPath}/group/${article.groupId}" class="group">${article.groupName}</a>&nbsp;&nbsp;
                                        </#if>

                                        <a href="${rc.contextPath}/p/${article.articleId}" target="_blank"><i class="fa fa-eye"></i> ${article.viewCount}</a>&nbsp;&nbsp;
                                        <a href="#comment"><i class="fa fa-comment-o"></i> <span id="commentCountNum">0</span></a>&nbsp;&nbsp;
                                        <span><i class="fa fa-heart-o"></i> <span id="goodNum1">${article.goodNum}</span></span>&nbsp;&nbsp;
                                        <span><i class="fa fa-clock-o"></i> ${article.publishTime?string("yyyy-MM-dd HH:mm")}</span>&nbsp;&nbsp;

                                        <#if Session["_SESSION_USER"]?exists && user.userId == _SESSION_USER.userId>
                                            <a href="${rc.contextPath}/user/article/edit/${article.articleId}" class="operate"><i class="fa fa-pencil-square-o" aria-hidden="true"></i> 编辑</a>&nbsp;&nbsp;
                                            <a href="javascript:deleteArticle('${article.articleId}');" class="operate"><i class="fa fa-trash-o" aria-hidden="true"></i> 删除</a>
                                        <#else>
                                            <#if collectArticleFlag?exists && collectArticleFlag == true>
                                                <span class="collected"><i class="fa fa-bookmark"></i> 已收藏</span>
                                            <#else>
                                                <a href="javascript:void(0);" class="collect" onclick="collectArticle('${article.articleId}')"><i class="fa fa-bookmark"></i> 收藏</a>
                                            </#if>
                                        </#if>
                                    </div>

                                    <div class="note-cont">
                                        <div id="article-content">
                                            <textarea id="article-content-textarea" style="display:none;">${article.content}</textarea>
                                        </div>
                                    </div>
                                </article>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <#include "/index/common/footer.ftl">

        <script src="${rc.contextPath}/static/plugins/editor/lib/marked.min.js" type="text/javascript"></script>
        <script src="${rc.contextPath}/static/plugins/editor/lib/prettify.min.js" type="text/javascript"></script>
        <script src="${rc.contextPath}/static/plugins/editor/lib/raphael.min.js" type="text/javascript"></script>
        <script src="${rc.contextPath}/static/plugins/editor/lib/underscore.min.js" type="text/javascript"></script>
        <script src="${rc.contextPath}/static/plugins/editor/lib/sequence-diagram.min.js" type="text/javascript"></script>
        <script src="${rc.contextPath}/static/plugins/editor/lib/flowchart.min.js" type="text/javascript"></script>
        <script src="${rc.contextPath}/static/plugins/editor/lib/jquery.flowchart.min.js" type="text/javascript"></script>
        <script src="${rc.contextPath}/static/plugins/editor/editormd.min.js" type="text/javascript"></script>

        <script type="text/javascript">
            $(function() {
                editormd.markdownToHTML("article-content", {
                    htmlDecode      : "style,script,iframe",  // you can filter tags decode
                    emoji           : true,
                    taskList        : true,
                    tex             : true,  // 默认不解析
                    flowChart       : true,  // 默认不解析
                    sequenceDiagram : true,  // 默认不解析
                });
            });
        </script>
    </body>
</html>