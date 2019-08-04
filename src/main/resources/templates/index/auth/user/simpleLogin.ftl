<!doctype html>
<html>
<head>
    <title>用户登录</title>
    <#include "/index/common/meta.ftl">
</head>
<body class="gray-bg" style="padding-top:0px;">
<div>
    <div class="col-md-2"></div>
    <div class="col-md-8">
        <h2 class="font-bold">登录</h2>
        <form method="post">
            <div class="form-group">
                用户名/邮箱
                <input type="text" class="form-control" name="name" placeholder="用户名/邮箱" maxlength="100" id="name">
            </div>
            <div class="form-group">
                密码
                <input type="password" class="form-control" name="password" placeholder="密码" maxlength="100" id="pwd">
            </div>
            <button type="button" class="btn btn-primary block full-width" onclick="login()">登录</button>
            <div style="text-align: center;margin-top: 8px;">
                <a href="${rc.contextPath}/auth/signup" target="_blank">免费注册</a>
            </div>
        </form>
    </div>
    <div class="col-md-2"></div>
</div>

<script type="text/javascript">
    function login() {
        var name = $.trim($("#name").val());
        var pwd = $.trim($("#pwd").val());

        if (SWUtils.isEmpty(name)) {
            SWDialog.errorTips("用户名不能为空");
            return;
        }

        if (SWUtils.isEmpty(pwd)) {
            SWDialog.errorTips("密码不能为空");
            return;
        }

        $.post("${rc.contextPath}/auth/login", {name:name, password:pwd}, function(data) {
            if (data.code == 20) {
                parent.location.reload();
            } else {
                SWDialog.errorTips(data.msg);
            }
        });
    }
</script>
</body>
</html>