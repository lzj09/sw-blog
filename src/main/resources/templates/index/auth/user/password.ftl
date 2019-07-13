<!doctype html>
<html>
<head>
    <title>修改密码 - 守望博客</title>
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
                                        <li class=""><a data-toggle="tab" href="#" onclick="SWUtils.skip('${rc.contextPath}/user/edit')">个人信息</a></li>
                                        <li class=""><a data-toggle="tab" href="#" onclick="SWUtils.skip('${rc.contextPath}/user/avatar')">修改头像</a></li>
                                        <li class="active"><a data-toggle="tab" href="#" onclick="SWUtils.skip('${rc.contextPath}/user/password')">修改密码</a></li>
                                    </ul>
                                    <div class="tab-content">
                                        <div id="tab-1" class="tab-pane active">
                                            <div class="panel-body">
                                                <form class="form-horizontal" action="#" method="post">
                                                    <div class="form-group">
                                                        <label class="col-sm-2 control-label">旧密码：<span class="tip">*</span></label>
                                                        <div class="col-sm-8">
                                                            <input id="password" name="password" class="form-control" type="password" maxlength="100">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-sm-2 control-label">新密码：<span class="tip">*</span></label>
                                                        <div class="col-sm-8">
                                                            <input id="newPwd" name="newPwd" class="form-control" type="password" maxlength="100">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-sm-2 control-label">确认新密码：<span class="tip">*</span></label>
                                                        <div class="col-sm-8">
                                                            <input id="confirmNewPwd" name="confirmNewPwd" class="form-control" type="password" maxlength="100">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <div class="col-sm-8 col-sm-offset-2">
                                                            <input type="button" class="btn btn-primary block full-width" value="修改" onclick="editPwd()">
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
    function editPwd() {
        var password = $.trim($("#password").val());
        var newPwd = $.trim($("#newPwd").val());
        var confirmNewPwd = $.trim($("#confirmNewPwd").val());

        if (SWUtils.isEmpty(password)) {
            SWDialog.errorTips("旧密码不能为空");
            return;
        }

        if (SWUtils.isEmpty(newPwd)) {
            SWDialog.errorTips("新密码不能为空");
            return;
        }

        if (newPwd != confirmNewPwd) {
            SWDialog.errorTips("两次输入的新密码不相等");
            return;
        }

        $.post("${rc.contextPath}/user/password", {password:password, newPwd:newPwd, confirmNewPwd:confirmNewPwd}, function(data) {
            if (data.code == 20) {
                SWDialog.successTips(data.msg);

                window.setTimeout("SWUtils.skip('${rc.contextPath}/user/password')", 1000);
            } else {
                SWDialog.errorTips(data.msg);
            }
        });
    }
</script>
</body>
</html>