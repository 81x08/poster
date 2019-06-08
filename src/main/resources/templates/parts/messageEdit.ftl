<button class="btn btn-primary mb-3" type="button" data-toggle="collapse" data-target="#collapseForm" aria-expanded="false" aria-controls="collapseExample">
    Изменить пост
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
            <input type="hidden" name="id" value="<#if message??>${message.id}</#if>"/>
            <div class="form-group">
                <button type="submit" class="btn btn-primary mt-3">Сохранить</button>
            </div>
        </form>
    </div>
</div>