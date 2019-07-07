<!doctype html>
<html>
    <head>
        <title><#if (user.realName)?exists> ${user.realName} <#else> ${user.loginName}</#if> - 守望博客</title>
        <#include "/index/common/meta.ftl">
    </head>

    <body class="gray-bg">
        <#include "/index/common/header.ftl">

        <div class="wrapper-content">
            <#include "/index/common/user_top.ftl">

            <div class="container">
            </div>
        </div>

        <#include "/index/common/footer.ftl">
    </body>
</html>