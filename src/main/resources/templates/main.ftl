<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
    <div>
        <@l.logout />
        <a href="/user">Go to User List</a>
        <div>
            <form method="post" enctype="multipart/form-data">
                <input type="text" name="text" placeholder="Input your message" />
                <input type="text" name="tag" placeholder="Input your tag" />
                <input type="file" name="file" />
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                <button type="submit">Post message</button>
            </form>
        </div>
        <form method="get" action="/main">
            <input type="text" name="filter" placeholder="Input message's tag you want to find"  value="${filter?ifExists}"/>
            <button type="submit">Find</button>
        </form>
        <div>Messages list:</div>
        <#list messages as message>
        <div>
            <b>${message.id}</b>
            <span>${message.text}</span>
            <i>${message.tag}</i>
            <strong>${message.authorName}</strong>
            <div>
                <#if message.filename??>
                    <img src="/img/${message.filename}" alt="${message.filename}" style="max-width: 250px; max-height: 150px">
                </#if>
            </div>
        </div>
        <#else>
        No messages found
        </#list>
    </div>
</@c.page>