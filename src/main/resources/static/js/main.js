var SWDialog = {
    loading: function () {
        //加载层
        return parent.layer.load(0);
    },

    close: function (index) {
        parent.layer.close(index);
    },

    closeAll: function () {
        parent.layer.closeAll();
    },

    alert: function(msg) {
        parent.layer.alert(msg);
    },

    confirm: function(msg,confirmFun) {
        parent.layer.confirm(msg, function(){
            confirmFun();
        }, function(){

        });
    },

    tips: function(msg){
        parent.layer.msg(msg);
    },

    tips: function(msg,type){
        if (type == "error") {
            parent.layer.msg(msg, {icon: 5});
        } else if (type == "success") {
            parent.layer.msg(msg, {icon: 6});
        } else {
            parent.layer.msg(msg);
        }
    },

    errorTips: function(msg) {
    	SWDialog.tips(msg,"error");
    },

    successTips: function(msg) {
    	SWDialog.tips(msg, "success");
    },

    open: function (url, title, width, height) {
        layer.open({
            title: title,
            type: 2,
            area: [width, height],
            fix: true,
            maxmin: false,
            content: url,
            scrollbar: false
        });
    }
};

var SWUtils = {
	// 判断是否为空
    isEmpty: function(value) {
        if (typeof(value) != "undefined" && value != "") {
            return false;
        } else {
            return true;
        }
    },
    
    // 正则校验
    regulate: function(content, reg) {
    	return reg.test(content);
    },
    
    // 只能输入[0-9]数字
    isDigits: function(content) {
    	return SWUtils.regulate(content, /^\d+$/);
    },
    
    // 判断英文字符
    isEnglish: function(content) {
    	return SWUtils.regulate(content, /^[A-Za-z]+$/);
    },
    
    // 判断中文字符
    isChinese: function(content) {
    	return SWUtils.regulate(content, /^[\u4E00-\u9FFF]+$/);
    },
    
    // 判断标识ID，包含字母、数字和下划线
    isId: function(content) {
    	return SWUtils.regulate(content, /^\w+$/);
    },
    
    // 判断以字母开头的标识ID，包含字母、数字和下划线
    isIdSWC: function(content) {
    	return SWUtils.regulate(content, /^[a-zA-Z]\w+$/);
    },
    
    // 手机号码验证
    isMobile: function(content) {
    	var length = value.length;
    	return length == 11 && SWUtils.regulate(content, /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/);
    },
    
    // 匹配密码，以字母开头，长度在6-12之间，只能包含字符、数字和下划线
    isPwd: function(content) {
    	return SWUtils.regulate(content, /^[a-zA-Z]\\w{6,12}$/);
    },
    
    // 标识符验证，只能包含字母、数字、下划线字符
    isCharNumLine: function(content) {
    	return SWUtils.regulate(content, /^[a-zA-Z0-9_]+$/);
    },
    
    // 校验邮箱
    isEmail: function(content) {
    	return SWUtils.regulate(content, /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/);
    },
    
    // 跳转方法
    skip: function(url) {
    	window.location.href=url;
    }
};

/** 封装cookie相关方法 */
var cookie = {
	set: function(key, val, time) { // time单位为天
		key = encodeURI(key);
		var date = new Date();
		var expiresDays = time;
		date.setTime(date.getTime() + expiresDays * 24 * 3600 * 1000);
		document.cookie = key + "=" + val + ";expires=" + date.toGMTString() + ";path=/";
	},
	get: function(key) {
		key = encodeURI(key);
		var res = "";
		
		var cookieStr = document.cookie;
		if (!SWUtils.isEmpty(cookieStr)) {
			var getCookie = cookieStr.replace(/[ ]/g,""); //获取cookie，并且将获得的cookie格式化，去掉空格字符
			var arrCookie = getCookie.split(";");
			
			for (var i = 0; i < arrCookie.length; i++) {
				var arr = arrCookie[i].split("=");
				if (key == arr[0]) {
					res = arr[1];
					break;
				}
			}
		}
		
		return res;
	},
	del: function(key) {
		key = encodeURI(key);
		var date = new Date();
		date.setTime(date.getTime() - 10000); //将date设置为过去的时间
		document.cookie = key + "=v; expires =" + date.toGMTString() + ";path=/";//设置cookie
	}
};

// 退出系统
function logout(url) {
	$.post(url + "/auth/logout", {}, function(data) {
		if (data.code == 20) {
            var skip = "http://" + window.location.host + url;
            SWUtils.skip(skip);
		} else {
			SWDialog.errorTips(data.msg);
		}
	});
}

// 登录页面
function followUserLogin(baseUrl) {
	SWDialog.open(baseUrl + "/auth/simpleLogin", "用户登录", "600px", "300px");
}

// 关注用户
function followUser(baseUrl, userId) {
	$.get(baseUrl + "/user/follow/" + userId, {}, function(data) {
		if (data.code == 20) {
			SWDialog.successTips(data.msg);
			
			window.setTimeout("window.location.reload()", 1000);
		} else {
			SWDialog.errorTips(data.msg);
		}
	});
}

// 取消关注用户
function unfollowUser(baseUrl, userId) {
	SWDialog.confirm("确定取消关注该用户吗?", function() {
		$.get(baseUrl + "/user/unfollow/" + userId, {}, function(data) {
			if (data.code == 20) {
				SWDialog.successTips(data.msg);
				
				window.setTimeout("window.location.reload()", 1000);
			} else {
				SWDialog.errorTips(data.msg);
			}
		});
	});
}

//发送私信
function sendMessage(baseUrl, userId) {
	SWDialog.open(baseUrl + "/user/message/add/" + userId, "发送私信", "600px", "300px");
}