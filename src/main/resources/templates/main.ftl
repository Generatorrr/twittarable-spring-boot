<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
    <div>
        <@l.logout />
        <div>
            <form method="post">
                <input type="text" name="text" placeholder="Input your message" />
                <input type="text" name="tag" placeholder="Input your tag" />
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                <button type="submit">Post message</button>
            </form>
        </div>
        <form method="get" action="/main">
            <input type="text" name="filter" placeholder="Input message's tag you want to find"  value="${filter}"/>
            <button type="submit">Find</button>
        </form>
        <div>Messages list:</div>
        <#list messages as message>
        <div>
            <b>${message.id}</b>
            <span>${message.text}</span>
            <i>${message.tag}</i>
            <strong>${message.authorName}</strong>
        </div>
        <#else>
        No messages found
        </#list>
    </div>
</@c.page>