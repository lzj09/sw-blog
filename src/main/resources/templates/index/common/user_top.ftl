<div class="member-banner">
    <div class="container">
        <div class="container content">
            <div class="left">
                <div class="avatar">
                    <#if (user.picture)?exists>
                        <img src="${rc.contextPath}/img/avatar/${user.picture}" class="img-circle" width="80px" height="80px"/>
                    <#else>
                        <img src="${rc.contextPath}/static/img/default-avatar.png" class="img-circle" width="80px" height="80px"/>
                    </#if>
                </div>
                <div class="info">
                    <div class="name">
                        <#if (user.realName)?exists> ${user.realName} <#else> ${user.loginName}</#if>
                    </div>
                    <div class="statis">
                        <ul>
                            <li>
                                <div class="meta-block">
                                    <a href="${rc.contextPath}/u/${user.userId}/following">
                                        <p><b>${user.follows!}</b></p>
                                        关注 &gt;
                                    </a>
                                </div>
                            </li>
                            <li>
                                <div class="meta-block">
                                    <a href="${rc.contextPath}/u/${user.userId}/followers">
                                        <p><b>${user.fans!}</b></p>
                                        粉丝 &gt;
                                    </a>
                                </div>
                            </li>
                            <li>
                                <div class="meta-block">
                                    <a href="${rc.contextPath}/u/${user.userId}/note">
                                        <p><b>${user.noteNum!}</b></p>
                                        文章 &gt;
                                    </a>
                                </div>
                            </li>
                            <#if !(Session["_SESSION_USER"]?exists && user.userId == _SESSION_USER.userId)>
                                <#if Session["_SESSION_USER"]?exists>
                                    <li>
                                        <a class="btn btn-primary follows" href="javascript:followUser('${rc.contextPath}', '${user.userId}');">
                                        <i class="fa fa-heart-o"></i> 关注
                                    </a>
                                    </li>
                                    <li>
                                        <a class="btn btn-primary comments" href="javascript:sendMessage('${rc.contextPath}', '${user.userId}');">
                                            <i class="fa fa-comments"></i> 私信
                                        </a>
                                    </li>
                                <#else>
                                    <li>
                                        <a class="btn btn-primary follows" href="javascript:followUserLogin('${rc.contextPath}');">
                                            <i class="fa fa-heart-o"></i> 关注
                                        </a>
                                    </li>

                                    <li>
                                        <a class="btn btn-primary comments" href="javascript:followUserLogin('${rc.contextPath}');">
                                            <i class="fa fa-comments"></i> 私信
                                        </a>
                                    </li>
                                </#if>
                            </#if>
                        </ul>
                    </div>

                    <p class="operator">
                        <#if (Session["_SESSION_USER"]?exists && user.userId == _SESSION_USER.userId)>
                            <a class="btn btn-primary" href="${rc.contextPath}/user/edit">
                                <i class="fa fa-edit"></i> 编辑个人资料
                            </a>
                        </#if>
                    </p>
                </div>
            </div>
            <div class="right">
                <#if (user.introduce)?exists>
                    <div class="title">个人介绍</div>
                    <div class="description">${user.introduce}</div>
                </#if>
            </div>
            <div class="clear"></div>
        </div>
    </div>
</div>