<#macro login path isRegisterForm>
<form action="${path}" method="post">
    <div class="form-group">
        <div class="form-group">
            <label>Имя</label>
            <input type="text" class="form-control ${(usernameError??)?string('is-invalid', '')} col-5"
                   name="username" value="<#if userEntity??>${userEntity.username}</#if>" placeholder="Введите ваше имя"/>
            <#if usernameError??>
                <div class="invalid-feedback">
                    ${usernameError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label>Пароль</label>
            <input type="password" class="form-control ${(passwordError??)?string('is-invalid', '')} col-5"
                   name="password" placeholder="Введите ваш пароль"/>
            <#if passwordError??>
                <div class="invalid-feedback">
                    ${passwordError}
                </div>
            </#if>
        </div>
        <#if isRegisterForm>
            <div class="form-group">
                <label>Пароль</label>
                <input type="password" class="form-control ${(password2Error??)?string('is-invalid', '')} col-5"
                       name="password2" placeholder="Повторите ваш пароль"/>
                <#if password2Error??>
                <div class="invalid-feedback">
                    ${password2Error}
                </div>
            </#if>
            </div>
            <div class="form-group">
                <label>E-Mail</label>
                <input type="email" class="form-control ${(emailError??)?string('is-invalid', '')} col-5"
                       name="email" value="<#if userEntity??>${userEntity.email}</#if>" placeholder="user@mail.site"/>
                <#if emailError??>
                <div class="invalid-feedback">
                    ${emailError}
                </div>
            </#if>
            </div>
        </#if>
        <div class="mb-3">
            <div class="g-recaptcha" data-sitekey="6LeWz6cUAAAAADc4Kdq2TwkosFH3QaSKLJn4S42p"></div>
            <#if captchaError??>
                <div class="alert alert-danger mt-1" role="alert">
                    ${captchaError}
                </div>
            </#if>
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