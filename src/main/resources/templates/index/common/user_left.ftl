<div class="col-sm-2">
    <div class="float-e-margins">
        <div class="feed-activity-list">
            <!-- <a href="#">
                <div class="feed-element">
                    <div class="media-body">动态</div>
                </div>
            </a> -->
            <#if (Session["_SESSION_USER"]?exists && user.userId == _SESSION_USER.userId)>
                <a href="${rc.contextPath}/user/message/list">
                    <div class="feed-element">
                        <div class="media-body">消息 <#if (Session["_SESSION_MESSAGE_NUM"]?exists && Session["_SESSION_MESSAGE_NUM"] > 0)><span class="messageNum">$_SESSION_MESSAGE_NUM</span></#if></div>
                    </div>
                </a>
            </#if>
            <a href="${rc.contextPath}/u/${user.userId}/note">
                <div class="feed-element">
                    <div class="media-body">笔记</div>
                </div>
            </a>
            <a href="${rc.contextPath}/u/${user.code}/group">
                <div class="feed-element">
                    <div class="media-body">专栏</div>
                </div>
            </a>
            <a href="${rc.contextPath}/u/${user.userId}/group/follow">
                <div class="feed-element">
                    <#if (Session["_SESSION_USER"]?exists && user.userId == _SESSION_USER.userId)>
                        <div class="media-body">我关注的专栏</div>
                    <#else>
                        <div class="media-body">他关注的专栏</div>
                    </#if>
                </div>
            </a>
            <a href="${rc.contextPath}/u/${user.userId}/note/like">
                <div class="feed-element">
                    <#if (Session["_SESSION_USER"]?exists && user.userId == _SESSION_USER.userId)>
                        <div class="media-body">我收藏的文章</div>
                    <#else>
                        <div class="media-body">他收藏的文章</div>
                    </#if>
                </div>
            </a>
        </div>
    </div>
</div>