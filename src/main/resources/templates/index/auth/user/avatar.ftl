<!doctype html>
<html>
    <head>
        <title>修改头像 - 守望博客</title>
        <#include "/index/common/meta.ftl">
        <script src="${rc.contextPath}/static/plugins/fullAvatarEditor/scripts/fullswfobject.js"></script>
        <script src="${rc.contextPath}/static/plugins/fullAvatarEditor/scripts/fullAvatar.js"></script>
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
                                            <li class="active"><a data-toggle="tab" href="#" onclick="SWUtils.skip('${rc.contextPath}/user/avatar')">修改头像</a></li>
                                            <li class=""><a data-toggle="tab" href="#" onclick="SWUtils.skip('${rc.contextPath}/user/password')">修改密码</a></li>
                                        </ul>
                                        <div class="tab-content">
                                            <div id="tab-1" class="tab-pane active">
                                                <div class="panel-body">
                                                    <div style="width:632px;text-align:center">
                                                        <div>
                                                            <p id="swfContainer">本组件需要安装Flash Player后才可使用，请从<a href="http://www.adobe.com/go/getflashplayer">这里</a>下载安装。</p>
                                                        </div>
                                                        <button type="button" id="upload" style="display:none;margin-top:8px;">swf外定义的上传按钮，点击可执行上传保存操作</button>
                                                    </div>
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
        swfobject.addDomLoadEvent(function () {
            var swf = new fullAvatarEditor("${rc.contextPath}/static/plugins/fullAvatarEditor/fullAvatarEditor.swf", "${rc.contextPath}/resources/plugins/fullAvatarEditor/expressInstall.swf", "swfContainer", {
                    id: 'swf',
                    upload_url: '${rc.contextPath}/upload?_distType=_avatar',	//上传接口
                    method: 'post',	//传递到上传接口中的查询参数的提交方式。更改该值时，请注意更改上传接口中的查询参数的接收方式
                    src_upload: 0,		//是否上传原图片的选项，有以下值：0-不上传；1-上传；2-显示复选框由用户选择
                    avatar_box_border_width: 0,
                    avatar_sizes: '150*150',
                    avatar_sizes_desc: '150*150像素',
                    avatar_field_names: '_uploadFile'
                }, function (msg) {
                    console.log(msg);
                    switch (msg.code) {
                        case 1 :
                            break;
                        case 2 :
                            document.getElementById("upload").style.display = "inline";
                            break;
                        case 3 :
                            if (msg.type == 0) {

                            }
                            else if (msg.type == 1) {
                                alert("摄像头已准备就绪但用户未允许使用！");
                            }
                            else {
                                alert("摄像头被占用！");
                            }
                            break;
                        case 5 :
                            setTimeout(function () {
                                window.location.href = window.location.href;
                            }, 1000);
                            break;
                    }
                }
            );
            document.getElementById("upload").onclick = function () {
                swf.call("upload");
            };
        });
    </script>
    </body>
</html>