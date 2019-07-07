<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed"
                data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">切换导航</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand logo" href="${rc.contextPath}/">守望博客</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li><a href="${rc.contextPath}/">首页</a></li>
                <li><a href="${rc.contextPath}/article">文章</a></li>
                <li><a href="${rc.contextPath}/group">专栏</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="${rc.contextPath}/user/note/add" style="font-size:18px;">写文章</a></li>
                <#if Session["_SESSION_USER"]?exists>
                    <li><a href="${rc.contextPath}/u/${_SESSION_USER.userId}"><#if (_SESSION_USER.realName)?exists> ${_SESSION_USER.realName} <#else> ${_SESSION_USER.loginName}</#if></a></li>
                    <li><a href="javascript:logout('${rc.contextPath}');">退出</a></li>
                <#else>
                    <li><a href="${rc.contextPath}/auth/login">登录</a></li>
                    <li><a href="${rc.contextPath}/auth/signup">注册</a></li>
                </#if>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container-fluid -->
</nav>