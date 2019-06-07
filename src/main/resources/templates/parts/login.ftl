<#macro login path isRegisterForm>
<form action="${path}" method="post">
    <div class="form-group">
        <div class="form-group">
            <label>Имя</label>
            <input type="text" class="form-control col-3" name="username" placeholder="Введите ваше имя"/>
        </div>
        <div class="form-group">
            <label>Пароль</label>
            <input type="password" class="form-control col-3" name="password" placeholder="Введите ваш пароль"/>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary">
            <#if isRegisterForm>Зарегистрироваться<#else>Войти</#if>
        </button>
        <br>
        <#if !isRegisterForm>
            <a href="/registration">Регистрация</a>
        <#else>
            <a href="/login">Назад</a>
        </#if>
    </div>
</form>
</#macro>

<#macro logout>
<form action="/logout" method="post">
    <button type="submit" class="btn btn-primary">Выйти</button>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
</form>
</#macro>