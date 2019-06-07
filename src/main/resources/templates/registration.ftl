<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
<h4>Регистрация нового пользователя</h4>
${message?ifExists}
<@l.login "/registration" true/>
</@c.page>