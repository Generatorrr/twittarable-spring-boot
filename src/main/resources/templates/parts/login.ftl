<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div class="form-group">
            <label class="col-sm-2 col-form-label"> User Name : </label>
            <div class="col-sm-6">
                <input class="form-control ${(usernameError??)?string('is-invalid', '')}"
                       type="text" name="username" placeholder="User Name"
                       value="<#if user??>${user.username}</#if>" />
            <#if usernameError??>
                <div class="invalid-feedback">
                    ${usernameError}
                </div>
            </#if>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 col-form-label"> Password: </label>
            <div class="col-sm-6">
                <input class="form-control ${(passwordError??)?string('is-invalid', '')}"
                       type="password" name="password" placeholder="Password" />
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
        </div>
      <#if isRegisterForm>
      <div class="form-group">
          <label class="col-sm-2 col-form-label"> Password Confirmation: </label>
          <div class="col-sm-6">
              <input class="form-control ${(password2Error??)?string('is-invalid', '')}"
                     type="password" name="password2" placeholder="Retype password" />
                <#if password2Error??>
                    <div class="invalid-feedback">
                        ${password2Error}
                    </div>
                </#if>
          </div>
      </div>
      <div class="form-group">
          <label class="col-sm-2 col-form-label"> Email: </label>
          <div class="col-sm-6">
              <input class="form-control ${(emailError??)?string('is-invalid', '')}"
                     type="email" name="email" placeholder="my@email.com"
                     value="<#if user??>${user.email}</#if>" />
              <#if emailError??>
                    <div class="invalid-feedback">
                        ${emailError}
                    </div>
              </#if>
          </div>
      </div>
      <div class="col-sm-6">
          <div class="g-recaptcha" data-sitekey="6LftN28UAAAAAH-i6PmurJGwVx8ioGVijgwY-jEp"></div>
          <#if captchaError??>
          <div class="alert alert-danger" role="alert">
              ${captchaError}
          </div>
          </#if>
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