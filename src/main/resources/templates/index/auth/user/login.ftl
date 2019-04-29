<!doctype html>
<html>
    <head>
        <title>用户注册 - 守望博客</title>
        <#include "/index/common/meta.ftl">
    </head>
    <body class="gray-bg">
        <#include "/index/common/header.ftl">

        <div class="animated fadeInDown">
            <div class="row login-panel">
                <div class="col-md-4"></div>
                <div class="col-md-4">
                    <div class="ibox ibox-content">
                        <h2 class="font-bold">登录</h2>
                        <form method="post">
                            <div class="form-group">
                                用户名/邮箱<span class="tip">*</span>
                                <input type="text" class="form-control" name="name" placeholder="用户名/邮箱" maxlength="100" id="name">
                            </div>
                            <div class="form-group">
                                密码<span class="tip">*</span>
                                <input type="password" class="form-control" name="password" placeholder="密码" maxlength="100" id="pwd">
                            </div>
                            <button type="button" class="btn btn-primary block full-width" onclick="login()">登录</button>
                        </form>
                        <p></p>
                        <p class="text-muted text-center">
                            <!-- <a href="forgetpwd">忘记密码?</a> | -->
                            <a href="${rc.contextPath}/auth/signup">免费注册</a>
                        </p>
                    </div>
                </div>
            </div>
        </div>

        <#include "/index/common/footer.ftl">
        
        <script>
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
                        SWUtils.skip('${rc.contextPath}/')
                    } else {
                        SWDialog.errorTips(data.msg);
                    }
                });
            }
        </script>
    </body>
</html>