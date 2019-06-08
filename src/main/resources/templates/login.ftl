<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
${message?ifExists}
<h4>Авторизация</h4>
<@l.login "/login" false/>
</@c.page>