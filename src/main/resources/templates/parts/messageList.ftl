<#include "security.ftl">

<div>Messages list:</div>
<div class="card-columns">
    <#list messages as message>
        <div class="card my-3">
            <div>
            <#if message.filename??>
                <img class="card-img-top" src="/img/${message.filename}" alt="${message.filename}" style="max-width: 250px; max-height: 150px">
            </#if>
            </div>
            <div class="m-2">
                <span>${message.text}</span><br>
                <i>#${message.tag}</i>
            </div>
            <div class="card-footer text-muted">
                <a class="pr-5" href="/user-messages/${message.author.id}">${message.authorName}</a>
                <b>
                    <span>
                        <#if message.edited>edited</#if>
                    </span>
                </b>

                <#if message.author.id == currentUser.id>
                    <a class="btn btn-primary" href="/user-messages/${message.author.id}?message=${message.id}">
                        Edit
                    </a>
                </#if>
            </div>
        </div>
    <#else>
    No messages found
    </#list>
</div>