<!doctype html>
<html>
<head>
    <title><#if (user.realName)?exists>${user.realName}<#else>${user.loginName}</#if>的专栏 - 守望博客</title>
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
                                <h5>专栏</h5>
                                <#if Session["_SESSION_USER"]?exists>
                                    <a class="btn btn-primary float-right" href="${rc.contextPath}/user/group/add" style="margin-top:-10px;"><i class="fa fa-book"></i> 创建专栏</a>
                                    <div class="clear"></div>
                                </#if>
                            </div>

                            <div class="ibox-content">
                                <#list groups as group>
                                    <#if group_index gt 2>
                                        <div class="col-md-4" style="margin-top:15px;">
                                    <#else>
                                        <div class="col-md-4">
                                    </#if>
                                            <div class="group-item">
                                                <div class="group-top">
                                                    <div class="banner-color"></div>
                                                    <a href="${rc.contextPath}/group/${group.groupId}" class="group-logo" target="_blank">
                                                        <img alt="" src="${rc.contextPath}/img/group/logo/${group.logo}">
                                                    </a>
                                                </div>

                                                <div class="group-bottom">
                                                    <a href="${rc.contextPath}/group/${group.groupId}" class="title" target="_blank">${group.name}</a>

                                                    <p class="pext"><em>0</em>&nbsp;&nbsp;收录资源&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;<em>0</em>&nbsp;&nbsp;关注者</p>
                                                    <p class="pabtn">
                                                        <a href="${rc.contextPath}/group/${group.groupId}" target="_blank">立即进入</a>
                                                        <#if Session["_SESSION_USER"]?exists>
                                                            <#if user.userId == _SESSION_USER.userId>
                                                                &nbsp;&nbsp;<a href="${rc.contextPath}/user/group/edit/${group.groupId}">编辑</a>&nbsp;&nbsp;<a href="javascript:void(0);" onclick="deleteGroup('${group.groupId}')">删除</a>
                                                            <#else>
                                                                <#if followGroupIds?exists && followGroupIds?seq_contains(group.groupId)>
                                                                    &nbsp;&nbsp;<a href="javascript:void(0);" class="followed">已关注</a>
                                                                <#else>
                                                                    &nbsp;&nbsp;<a href="javascript:followGroup('${group.groupId}');" target="_blank">关注</a>
                                                                </#if>
                                                            </#if>
                                                        <#else>
                                                            &nbsp;&nbsp;<a href="javascript:followUserLogin('${rc.contextPath}');">关注</a>
                                                        </#if>
                                                    </p>
                                                </div>
                                            </div>
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
    function deleteGroup(id) {
        SWDialog.confirm("你确定要删除专栏吗？", function () {
            $.get("${rc.contextPath}/user/group/delete/" + id, {}, function(data) {
                if (data.code == 20) {
                    SWDialog.successTips(data.msg);

                    window.setTimeout("SWUtils.skip('${rc.contextPath}/u/${_SESSION_USER.code}/group')", 1000);
                } else {
                    SWDialog.errorTips(data.msg);
                }
            });
        });
    }

    function followGroup(id) {
        $.get("${rc.contextPath}/user/group/follow/" + id, {}, function(data) {
            if (data.code == 20) {
                SWDialog.successTips(data.msg);

                window.setTimeout("window.location.reload()", 1000);
            } else {
                SWDialog.errorTips(data.msg);
            }
        });
    }
</script>
</body>
</html>