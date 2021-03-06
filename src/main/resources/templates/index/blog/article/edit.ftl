<!doctype html>
<html>
<head>
    <title>编辑文章 - 守望博客</title>
    <#include "/index/common/meta.ftl">
    <link href="${rc.contextPath}/static/plugins/editor/css/editormd.min.css" rel="stylesheet">
    <link  href="${rc.contextPath}/static/plugins/tagsinput/jquery.tagsinput.min.css" rel="stylesheet"/>
    <script src="${rc.contextPath}/static/plugins/editor/editormd.min.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/static/plugins/tagsinput/jquery.tagsinput.min.js" type="text/javascript"></script>
</head>

<body class="gray-bg">
<#include "/index/common/header.ftl">

<div class="wrapper-content">
    <#include "/index/common/user_top.ftl">

    <div class="container">
        <div class="row">
            <div class="ibox">
                <div class="ibox-content">
                    <div class="col-sm-12">
                        <div class="ibox">
                            <div class="ibox-title">
                                <h5>文章</h5>
                                <a class="btn btn-primary float-right" href="${rc.contextPath}/u/${_SESSION_USER.code}/article" style="margin-top:-10px;"><i class="fa fa-list"></i> 文章列表</a>
                                <div class="clear"></div>
                            </div>

                            <div class="ibox-content">
                                <form class="form-horizontal" action="#" method="post">
                                    <div class="form-group">
                                        <label class="col-sm-1 control-label">专栏：</label>
                                        <div class="col-sm-11">
                                            <select name="groupId" id="groupId" class="form-control">
                                                <option value="">--请选择--</option>
                                                <#list groups as group>
                                                    <#if group.groupId == article.groupId>
                                                        <option value="${group.groupId}" selected>${group.name}</option>
                                                    <#else>
                                                        <option value="${group.groupId}">${group.name}</option>
                                                    </#if>
                                                </#list>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-1 control-label">标题：<span class="tip">*</span></label>
                                        <div class="col-sm-11">
                                            <input id="title" name="title" class="form-control" type="text" value="${article.title}" maxlength="160">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-sm-12" id="article-editormd">
                                            <textarea style="display:none;">${article.content}</textarea>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-1 control-label">类型：</label>
                                        <div class="col-sm-11">
                                            <select name="type" id="type" class="form-control">
                                                <option value="0">--请选择--</option>
                                                <#if article.type == 1>
                                                    <option value="1" selected>原创</option>
                                                <#else>
                                                    <option value="1">原创</option>
                                                </#if>
                                                <#if article.type == 2>
                                                    <option value="2" selected>翻译</option>
                                                <#else>
                                                    <option value="2">翻译</option>
                                                </#if>
                                                <#if article.type == 3>
                                                    <option value="3" selected>转载</option>
                                                <#else>
                                                    <option value="3">转载</option>
                                                </#if>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-1 control-label">置顶：</label>
                                        <div class="col-sm-11">
                                            <#if article.canTop == 0>
                                                <input type="radio" name="canTop" value="0" checked> 不置顶
                                                <input type="radio" name="canTop" value="1"> 置顶
                                            <#else>
                                                <input type="radio" name="canTop" value="0"> 不置顶
                                                <input type="radio" name="canTop" value="1" checked> 置顶
                                            </#if>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-1 control-label">评论：</label>
                                        <div class="col-sm-11">
                                            <#if article.canComment == 1>
                                                <input type="radio" name="canComment" value="1" checked> 开启评论
                                                <input type="radio" name="canComment" value="0"> 关闭评论
                                            <#else>
                                                <input type="radio" name="canComment" value="1"> 开启评论
                                                <input type="radio" name="canComment" value="0" checked> 关闭评论
                                            </#if>

                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-1 control-label">标签：</label>
                                        <div class="col-sm-11">
                                            <input id="tag" name="tag" class="form-control" type="text" value="${tags}" maxlength="160">
                                            <span class="tip">多个标签之间按<b>回车键</b>分开</span>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-sm-8 col-sm-offset-2">
                                            <input type="hidden" value="${article.articleId}" id="articleId">
                                            <input type="button" class="btn btn-primary block full-width" value="编辑文章" onclick="editNote()">
                                        </div>
                                    </div>
                                </form>
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
    var editor;
    $(function () {
        editor = editormd("article-editormd", {
            width: "100%",
            height: 640,
            placeholder: '',
            syncScrolling: "single",
            path: "${rc.contextPath}/static/plugins/editor/lib/",
            saveHTMLToTextarea: true,
            imageUpload: true,
            imageFormats: ["jpg", "jpeg", "gif", "png", "bmp"],
            imageUploadURL: "${rc.contextPath}/upload?_distType=_articleImg",
            imageUploadFileName: "_uploadFile",
            toolbarIcons: "sw"
        });

        $('#tag').tagsInput({
            'height':'36px',
            'width':'100%',
            'defaultText':'',
            'removeWithBackspace': true
        });
    });

    function editNote() {
        var title = $.trim($("#title").val());
        if (title == "") {
            SWDialog.errorTips("请输入文章标题");
            return;
        }

        var content = $.trim(editor.getMarkdown());
        if (content == "") {
            SWDialog.errorTips("请输入文章内容");
            return;
        }

        var groupId = $.trim($("#groupId").val());
        var tag = $.trim($("#tag").val());
        var type = $.trim($("#type").val());
        var canTop = $.trim($("input[name='canTop']:checked").val());
        var canComment = $.trim($("input[name='canComment']:checked").val());
        var htmlContent = $.trim(editor.getHTML()).replace(/<[^>]+(>){0,1}/g, "");
        var description = "";
        if (htmlContent.length > 210) {
            description = htmlContent.substring(0, 200) + "...";
        } else {
            description = htmlContent;
        }

        $.post("${rc.contextPath}/user/article/edit", {articleId:$.trim($("#articleId").val()), groupId:groupId, title:title, content:content, tag:tag, description:description, type:type, canTop:canTop, canComment:canComment}, function(data) {
            if (data.code == 20) {
                SWDialog.successTips(data.msg);
                window.setTimeout("SWUtils.skip('${rc.contextPath}/u/${_SESSION_USER.code}/article')", 1000);
            } else {
                SWDialog.errorTips(data.msg);
            }
        });
    }
</script>
</body>
</html>