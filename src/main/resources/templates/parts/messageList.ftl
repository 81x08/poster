<#include "security.ftl">

<div class="card-columns" id="message-list">
    <#list messages as message>
        <div class="card my-3" data-id="${message.id}">
            <#if message.fileName??>
                <img src="/img/${message.fileName}" class="card-img-top"/>
            </#if>
            <div class="m-2">
                <i>${message.text}</i><br/>
                <b>#${message.tag}</b>
            </div>
            <div class="card-footer text-muted">
                <a href="/user-messages/${message.author.id}">${message.authorName}</a>
                <#if message.author.id == currentUserId>
                    <a class="btn btn-primary" href="/user-messages/${message.author.id}?message=${message.id}">Изменить</a>
                </#if>
            </div>
        </div>
    </#list>
</div>