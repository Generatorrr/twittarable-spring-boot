<#import "parts/common.ftl" as c>

<@c.page>
    <#if currentUser>
        <#include "parts/messageEdit.ftl" />
    </#if>
    <#include "parts/messageList.ftl" />
</@c.page>