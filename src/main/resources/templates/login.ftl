<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
    Login Page
    <@l.login "/login" />
    <div>
        Don't have user yet?
        <a href="/registration">Join us!!!</a>
    </div>
</@c.page>