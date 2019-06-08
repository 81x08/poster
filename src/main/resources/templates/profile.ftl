<#import "parts/common.ftl" as c>

<@c.page>
${message?ifExists}
<h4>Личный кабинет</h4>
<form method="post">
    <div class="form-group">
        <div class="form-group">
            <label>E-Mail</label>
            <input type="email" class="form-control col-8" name="email" placeholder="user@mail" value="${email!''}"/>
        </div>
        <div class="form-group">
            <label>Пароль</label>
            <input type="password" class="form-control col-3" name="password" placeholder="Введите ваш пароль"/>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary">Сохранить</button>
    </div>
</form>
</@c.page>