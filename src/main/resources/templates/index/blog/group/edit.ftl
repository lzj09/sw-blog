<!doctype html>
<html>
<head>
    <title>修改专栏 - 守望博客</title>
    <#include "/index/common/meta.ftl">
    <link rel="stylesheet" type="text/css" href="${rc.contextPath}/static/plugins/webuploader/webuploader.css">
    <script type="text/javascript" src="${rc.contextPath}/static/plugins/webuploader/webuploader.min.js"></script>
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
                                <a class="btn btn-primary float-right" href="${rc.contextPath}/user/group/add" style="margin-top:-10px;"><i class="fa fa-book"></i> 创建专栏</a>
                                <div class="clear"></div>
                            </div>

                            <div class="ibox-content">
                                <div class="tabs-container">
                                    <ul class="nav nav-tabs">
                                        <li class="active"><a data-toggle="tab" href="#">修改专栏</a></li>
                                    </ul>
                                    <div class="tab-content">
                                        <div id="tab-1" class="tab-pane active">
                                            <div class="panel-body">
                                                <form class="form-horizontal" action="#" method="post">
                                                    <div class="form-group">
                                                        <label class="col-sm-2 control-label">分类：<span class="tip">*</span></label>
                                                        <div class="col-sm-8">
                                                            <select class="form-control" id="categoryId" name="categoryId">
                                                                <option value="0">--请选择--</option>
                                                                <#list categorys as category>
                                                                    <#if category.categoryId == group.categoryId>
                                                                        <option value="${category.categoryId}" selected>${category.name}</option>
                                                                    <#else>
                                                                        <option value="${category.categoryId}">${category.name}</option>
                                                                    </#if>
                                                                </#list>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-sm-2 control-label">名称：<span class="tip">*</span></label>
                                                        <div class="col-sm-8">
                                                            <input id="name" name="name" class="form-control" type="text" value="${group.name}" maxlength="100">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-sm-2 control-label">Logo图片：</label>
                                                        <div class="col-sm-8">
                                                            <div id="uploader">
                                                                <!--用来存放item-->
                                                                <div id="fileList" class="uploader-list"></div>
                                                                <div id="filePicker">选择图片</div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-sm-2 control-label">简介：<span class="tip">*</span></label>
                                                        <div class="col-sm-8">
                                                            <textarea id="introduce" name="introduce" class="form-control" cols="5" maxlength="200">${group.introduce}</textarea>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <div class="col-sm-8 col-sm-offset-2">
                                                            <input type="hidden" name="logo" id="logo">
                                                            <input type="button" class="btn btn-primary block full-width" value="修改" onclick="editCategory()">
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
    $(function() {
        var _list = $('#fileList');
        var ratio = window.devicePixelRatio || 1;
        var thumbnailWidth = 100 * ratio;
        var thumbnailHeight = 100 * ratio;

        // 初始化Web Uploader
        var uploader = WebUploader.create({

            // 选完文件后，是否自动上传。
            auto: true,

            // swf文件路径
            swf: '${rc.contextPath}/static/plugins/webuploader/Uploader.swf',

            // 文件接收服务端。
            server: '${rc.contextPath}/upload',

            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: '#filePicker',

            fileVal: "_uploadFile",

            formData: {
                _distType:'_groupLogo',
            },

            // 只允许选择图片文件。
            accept: {
                title: 'Images',
                extensions: 'gif,jpg,jpeg,png',
                mimeTypes: 'image/*'
            },

            fileNumLimit: 1,
            fileSizeLimit: 2 * 1024 * 1024,    // 2 M
            fileSingleSizeLimit: 2 * 1024 * 1024    // 2 M
        });

        // 当有文件添加进来的时候
        uploader.on( 'fileQueued', function( file ) {
            var li = $(
                '<div id="' + file.id + '" class="file-item thumbnail text-center">' +
                '<img>' +
                // '<div class="info">' + file.name + '</div>' +
                '</div>'
                ),
                img = li.find('img');


            // _list为容器jQuery实例
            _list.append( li );

            // 创建缩略图
            // 如果为非图片文件，可以不用调用此方法。
            // thumbnailWidth x thumbnailHeight 为 100 x 100
            uploader.makeThumb( file, function( error, src ) {
                if ( error ) {
                    img.replaceWith('<span>不能预览</span>');
                    return;
                }

                img.attr( 'src', src );
            }, thumbnailWidth, thumbnailHeight );
        });

        // 文件上传过程中创建进度条实时显示。
        uploader.on( 'uploadProgress', function( file, percentage ) {
        });

        // 文件上传成功，给item添加成功class, 用样式标记上传成功。
        uploader.on( 'uploadSuccess', function(file, response) {
            $( '#'+file.id ).addClass('upload-state-done');
            $( '#'+file.id ).append('<a class="del" href="javascript:void(0);">删除</a>' )
            $("#logo").val(response.url);
        });

        // 文件上传失败，显示上传出错。
        uploader.on( 'uploadError', function( file ) {
            var li = $( '#'+file.id ),
                error = li.find('div.error');

            // 避免重复创建
            if ( !error.length ) {
                error = $('<div class="error"></div>').appendTo( li );
            }

            error.text('上传失败');
        });

        // 完成上传完了，成功或者失败，先删除进度条。
        uploader.on( 'uploadComplete', function( file ) {
        });

        // 执行删除方法
        _list.on('click', '.del', function () {
            var Id = $(this).parent().attr('id');
            //删除该图片
            uploader.removeFile(uploader.getFile(Id, true));
            $(this).parent().remove();
            $("#logo").val("");
        });
    });

    function editCategory() {
        var categoryId = $.trim($("#categoryId").val());
        var name = $.trim($("#name").val());
        var introduce = $.trim($("#introduce").val());
        var logo = $.trim($("#logo").val());

        if (categoryId == "0") {
            SWDialog.errorTips("请选择分类");
            return;
        }

        if (SWUtils.isEmpty(name)) {
            SWDialog.errorTips("名称不能为空");
            return;
        }

        if (SWUtils.isEmpty(introduce)) {
            SWDialog.errorTips("简介不能为空");
            return;
        }

        $.post("${rc.contextPath}/user/group/edit/${group.groupId}", {categoryId:categoryId, name:name, introduce:introduce, logo:logo}, function(data) {
            if (data.code == 20) {
                SWDialog.successTips(data.msg);
                window.setTimeout("SWUtils.skip('${rc.contextPath}/u/${_SESSION_USER.code}/group')", 1000);
            } else {
                SWDialog.errorTips(data.msg);
            }
        });
    }
</script>
</body>
</html>