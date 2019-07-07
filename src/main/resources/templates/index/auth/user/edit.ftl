<!doctype html>
<html>
<head>
    <title>修改个人信息 - 守望博客</title>
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
                                <h5>修改资料</h5>
                            </div>

                            <div class="ibox-content">
                                <div class="tabs-container">
                                    <ul class="nav nav-tabs">
                                        <li class="active"><a data-toggle="tab" href="#" onclick="SWUtils.skip('${rc.contextPath}/user/edit')">个人信息</a></li>
                                        <li class=""><a data-toggle="tab" href="#" onclick="SWUtils.skip('${rc.contextPath}/user/avatar')">修改头像</a></li>
                                        <li class=""><a data-toggle="tab" href="#" onclick="SWUtils.skip('${rc.contextPath}/user/password')">修改密码</a></li>
                                    </ul>
                                    <div class="tab-content">
                                        <div id="tab-1" class="tab-pane active">
                                            <div class="panel-body">
                                                <form class="form-horizontal" action="#" method="post">
                                                    <div class="form-group">
                                                        <label class="col-sm-2 control-label">用户名：</label>
                                                        <div class="col-sm-8" style="padding-top:7px;">${user.loginName}</div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-sm-2 control-label">邮箱：</label>
                                                        <div class="col-sm-8" style="padding-top:7px;">${user.email}</div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-sm-2 control-label">主页标识：</label>
                                                        <div class="col-sm-8" style="padding-top:7px;">${user.code}</div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-sm-2 control-label">真实姓名：<span class="tip">*</span></label>
                                                        <div class="col-sm-8">
                                                            <input id="realName" name="realName" class="form-control" type="text" value="${user.realName!}" maxlength="100">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-sm-2 control-label">手机号码：<span class="tip">*</span></label>
                                                        <div class="col-sm-8">
                                                            <input id="cellphone" name="cellphone" class="form-control" type="text" value="${user.cellphone!}" maxlength="20">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-sm-2 control-label">性别：<span class="tip">*</span></label>
                                                        <div class="col-sm-8" style="padding-top:7px;">
                                                            <#if user.sex?exists && user.sex == 0>
                                                                <input type="radio" name="sex" value="0" checked>女
                                                            <#else>
                                                                <input type="radio" name="sex" value="0">女
                                                            </#if>
                                                            <#if user.sex?exists && user.sex == 1>
                                                                <input type="radio" name="sex" value="1" checked>男
                                                            <#else>
                                                                <input type="radio" name="sex" value="1">男
                                                            </#if>
                                                            <#if user.sex?exists && user.sex == -1>
                                                                <input type="radio" name="sex" value="-1" checked >保密
                                                            <#else>
                                                                <input type="radio" name="sex" value="-1" >保密
                                                            </#if>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-sm-2 control-label">个人介绍：<span class="tip">*</span></label>
                                                        <div class="col-sm-8">
                                                            <textarea id="introduce" name="introduce" class="form-control" cols="5" maxlength="200">${user.introduce!}</textarea>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <div class="col-sm-8 col-sm-offset-2">
                                                            <input type="button" class="btn btn-primary block full-width" value="修改" onclick="editInfo()">
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
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
    function editInfo() {
        var realName = $.trim($("#realName").val());
        var cellphone = $.trim($("#cellphone").val());
        var sex = $.trim($("input[name='sex']:checked").val());
        var introduce = $.trim($("#introduce").val());

        if (SWUtils.isEmpty(realName)) {
            SWDialog.errorTips("真实姓名不能为空");
            return;
        }

        if (SWUtils.isEmpty(cellphone)) {
            SWDialog.errorTips("手机号码不能为空");
            return;
        }

        if (SWUtils.isEmpty(sex)) {
            SWDialog.errorTips("请选择性别");
            return;
        }

        if (SWUtils.isEmpty(introduce)) {
            SWDialog.errorTips("个人介绍不能为空");
            return;
        }

        $.post("${rc.contextPath}/user/edit", {realName:realName, cellphone:cellphone, sex:sex, introduce:introduce}, function(data) {
            if (data.code == 20) {
                SWDialog.successTips(data.msg);

                window.setTimeout("SWUtils.skip('${rc.contextPath}/user/edit')", 1000);
            } else {
                SWDialog.errorTips(data.msg);
            }
        });
    }
</script>
</body>
</html>