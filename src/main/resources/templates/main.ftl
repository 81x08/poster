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

<button class="btn btn-primary mb-3" type="button" data-toggle="collapse" data-target="#collapseForm" aria-expanded="false" aria-controls="collapseExample">
    + Пост
</button>

<div class="collapse <#if message??>show</#if>" id="collapseForm">
    <div class="form-group">
        <form method="post" enctype="multipart/form-data">
            <div class="form-group">
                <input type="text" class="form-control ${(textError??)?string('is-invalid', '')} col-8"
                       value="<#if message??>${message.text}</#if>" name="text" placeholder="Введите сообщение"/>
                <#if textError??>
                    <div class="invalid-feedback">
                        ${textError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <input type="text" class="form-control ${(tagError??)?string('is-invalid', '')} col-8"
                       value="<#if message??>${message.tag}</#if>" name="tag" placeholder="Введите тэг"/>
                <#if tagError??>
                    <div class="invalid-feedback">
                        ${tagError}
                    </div>
                </#if>
            </div>
            <div class="custom-file">
                <input type="file" class="custom-file-input" id="customFile" name="file">
                <label class="custom-file-label col-8" for="customFile">Желаемый файл</label>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <div class="form-group">
                <button type="submit" class="btn btn-primary mt-3">Добавить</button>
            </div>
        </form>
    </div>
</div>

<div class="card-columns">
    <#list messages as message>
        <div class="card my-3">
            <#if message.fileName??>
            <img src="/img/${message.fileName}" class="card-img-top">
            </#if>
            <div class="m-2">
                <i>${message.text}</i>
            </div>
            <b>${message.tag}</b>
            <div class="card-footer text-muted">
                ${message.authorName}
            </div>
        </div>
    </#list>
</div>
</@c.page>