<#macro login path isRegisterForm>
    <form action="${path}" method="post">
      <div class="form-group">
        <label class="col-sm-2 col-form-label"> User Name : </label>
        <div class="col-sm-6">
          <input class="form-control" type="text" name="username" placeholder="User Name" />
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-2 col-form-label"> Password: </label>
        <div class="col-sm-6">
          <input class="form-control" type="password" name="password" placeholder="Password" />
        </div>
      </div>
      <#if isRegisterForm>
      <div class="form-group">
        <label class="col-sm-2 col-form-label"> Email: </label>
        <div class="col-sm-6">
          <input class="form-control" type="email" name="email" placeholder="my@email.com" />
        </div>
      </div>
      </#if>
      <input type="hidden" name="_csrf" value="${_csrf.token}" />
      <#if !isRegisterForm>
      <div>
        Don't have user yet?
        <a href="/registration">Join us!!!</a>
      </div>
      </#if>
      <button class="btn btn-primary"><#if isRegisterForm>Sign Up<#else>Sign In</#if></button>
    </form>
</#macro>

<#macro logout>
    <div>
      <form action="/logout" method="post">
        <button class="btn btn-primary">Sign Out</button>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
      </form>
    </div>
</#macro>