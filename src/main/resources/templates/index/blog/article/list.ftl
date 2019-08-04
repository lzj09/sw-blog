<!doctype html>
<html>
<head>
    <title><#if (user.realName)?exists>${user.realName}<#else>${user.loginName}</#if>的文章 - 守望博客</title>
    <#include "/index/common/meta.ftl">
</head>

<body class="gray-bg">
<#include "/index/common/header.ftl">

<div class="wrapper-content">
    <#include "/index/common/user_top.ftl">

    <div class="container">
        <div class="row">
            <div class="ibox">
                <div class="ibox-content">
                    <#include "/index/common/user_left.ftl">

                    <div class="col-sm-10">
                        <div class="ibox">
                            <div class="ibox-title">
                                <h5>文章</h5>
                                <#if Session["_SESSION_USER"]?exists>
                                    <a class="btn btn-primary float-right" href="${rc.contextPath}/user/article/add" style="margin-top:-10px;"><i class="fa fa-pencil"></i> 写文章</a>
                                    <div class="clear"></div>
                                </#if>
                            </div>

                            <div class="ibox-content">
                                <#list articles.getRecords() as article>
                                <#if article_index == 0>
                                <div class="note-content note-content-f">
                                    <#else>
                                    <div class="note-content">
                                        </#if>
                                        <div class="col-md-12">
                                                <span class="post-type">
                                                    <#if article.type == 1>
                                                        <label>[原创]</label>
                                                    <#elseif article.type == 2>
                                                        <label>[翻译]</label>
                                                    <#elseif article.type == 3>
                                                        <label>[转载]</label>
                                                    </#if>
                                                </span>
                                            <a href="${rc.contextPath}/p/${article.articleId}" class="title" target="_blank">${article.title}</a>

                                            <div class="abs">${article.description}</div>
                                            <div class="meta">
                                                <#if article.groupId?exists && article.groupId != "">
                                                    <a href="${rc.contextPath}/group/${article.groupId}" target="_blank" class="group">${article.groupName}</a>&nbsp;&nbsp;
                                                </#if>
                                                <a href="${rc.contextPath}/p/${article.articleId}" target="_blank"><i class="fa fa-eye"></i> ${article.viewCount}</a>&nbsp;&nbsp;
                                                <a href="${rc.contextPath}/p/${article.articleId}#comment" target="_blank"><i class="fa fa-comment-o"></i> 0</a>&nbsp;&nbsp;
                                                <span><i class="fa fa-heart-o"></i> ${article.goodNum}</span>&nbsp;&nbsp;
                                                <span><i class="fa fa-clock-o"></i> ${article.createTime?string("yyyy-MM-dd HH:mm")}</span>&nbsp;&nbsp;
                                                <span class="view-all"><i class="fa fa-file-text"></i> <a href="${rc.contextPath}/p/${article.articleId}" target="_blank" class="view-all">阅读全文</a></span>&nbsp;&nbsp;
                                                <#if Session["_SESSION_USER"]?exists && user.userId == _SESSION_USER.userId>
                                                    <a href="${rc.contextPath}/user/article/edit/${article.articleId}" class="operate">编辑</a>&nbsp;&nbsp;
                                                    <a href="javascript:deleteArticle('${article.articleId}');" class="operate">删除</a>
                                                </#if>
                                            </div>
                                        </div>
                                        <div class="clear"></div>
                                    </div>
                                    </#list>
                            </div>
                        </div>
                    </div>
                    <div class="clear"></div>
                </div>
            </div>
        </div>
    </div>
</div>

    <#include "/index/common/footer.ftl">

    <script type="text/javascript">
    </script>
</body>
</html>