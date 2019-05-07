<!doctype html>
<html>
    <head>
        <title>主页标识 - 守望博客</title>
        <#include "/index/common/meta.ftl">
    </head>

    <body class="gray-bg">
        <#include "/index/common/header.ftl">

        <div class="animated fadeInDown">
            <div class="row login-panel">
                <div class="col-md-4"></div>
                <div class="col-md-4">
                    <div class="ibox ibox-content">
                        <h2 class="font-bold">主页标识</h2>
                        <form method="post">
                            <div class="form-group">
                                <span class="tip">只能修改一次，确定后则不能修改</span>
                                <input type="text" class="form-control" name="code" placeholder="只能是字母、数字和下划线组成" maxlength="64" id="code">
                            </div>
                            <button type="button" class="btn btn-primary block full-width" onclick="saveCode()">确定</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <#include "/index/common/footer.ftl">

        <script type="text/javascript">
            function saveCode() {
                var code = $.trim($("#code").val());

                if (SWUtils.isEmpty(code)) {
                    SWDialog.errorTips("主页标识不能为空");
                    return;
                }

                if (!SWUtils.isId(code)) {
                    SWDialog.errorTips("主页标识只能包含字母、数字和下划线");
                    return;
                }

                $.post("${rc.contextPath}/user/code", {code:code}, function(data) {
                    if (data.code == 20) {
                        SWUtils.skip("${rc.contextPath}/u/" + data.content);
                    } else {
                        SWDialog.errorTips(data.msg);
                    }
                });
            }
        </script>
    </body>
</html>