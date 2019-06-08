<#import "parts/common.ftl" as c>
<@c.page>
<form method="get" action="/main">
    <div class="input-group mb-3 mr-sm-2">
        <div class="input-group-prepend">
            <div class="input-group-text">Поиск</div>
        </div>
        <input type="text" class="form-control col-6" name="tag" value="${tag?ifExists}" placeholder="Введите тэг">
        <button type="submit" class="btn btn-primary ml-3">Найти</button>
    </div>
</form>

<#include "parts/messageEdit.ftl" />

<#include "parts/messageList.ftl" />
</@c.page>