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
                    <div class="ibox-content">
                        <h3>欢迎加入守望博客</h3>
                        <form method="post">
                            <div class="form-group">
                                用户名<span class="tip">*</span>
                                <input type="text" name="name" class="form-control" placeholder="用户名" maxlength="100" id="name">
                            </div>
                            <div class="form-group">
                                邮箱<span class="tip">*</span>
                                <input type="email" name="email" class="form-control" placeholder="邮箱" maxlength="100" id="email">
                            </div>
                            <div class="form-group">
                                密码<span class="tip">*</span>
                                <input type="password" name="password" class="form-control" placeholder="密码" maxlength="100" id="password">
                            </div>
                            <div class="form-group">
                                确认密码<span class="tip">*</span>
                                <input type="password" name="repassword" class="form-control" placeholder="确认密码" id="repassword">
                            </div>
                            <button type="button" class="btn btn-primary block full-width" onclick="signup()">注册</button>
                            <p></p>
                            <p class="text-muted text-center">点击"注册"即表示您同意并愿意遵守望博客的<a href="${rc.contextPath}/info/protocol" target="_blank">用户协议</a>。</p>
                            <p class="text-muted text-center">
                            <!-- <a href="forgetpwd">忘记密码?</a> | -->
                            <a href="${rc.contextPath}/auth/login">我要登录</a>
                            </p>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        
        <#include "/index/common/footer.ftl">
        
        <script type="text/javascript">
            function signup() {
                var name = $.trim($("#name").val());
                var email = $.trim($("#email").val());
                var password = $.trim($("#password").val());
                var repassword = $.trim($("#repassword").val());

                if (SWUtils.isEmpty(name)) {
                    SWDialog.errorTips("用户名不能为空");
                    return;
                }
                
                if (!SWUtils.isEmail(email)) {
                    SWDialog.errorTips("邮箱不符全规范");
                    return;
                }
                
                if (!SWUtils.isId(password)) {
                    SWDialog.errorTips("密码只能包含字母、数字和下划线");
                    return;
                }
                
                if (password != repassword) {
                    SWDialog.errorTips("两次输入的密码不相等");
                    return;
                }
                
                $.post("${rc.contextPath}/auth/signup", {name:name, email:email, password:password}, function(data) {
                    if (data.code == 20) {
                        SWDialog.successTips("注册成功，马上跳转到登录页面...");
                        
                        window.setTimeout("SWUtils.skip('${rc.contextPath}/auth/login')", 2000);
                    } else {
                        SWDialog.errorTips(data.msg);
                    }
                });
            }
        </script>
    </body>
</html>