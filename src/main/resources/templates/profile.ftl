<#import "parts/common.ftl" as c>

<@c.page>
    <h5>${username}</h5>
    ${message?ifExists}
    <form method="post">
      <div class="form-group">
        <label class="col-sm-2 col-form-label"> Password: </label>
        <div class="col-sm-6">
          <input class="form-control" type="password" name="password" placeholder="Password" />
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-2 col-form-label"> Email: </label>
        <div class="col-sm-6">
          <input class="form-control" type="email" name="email" placeholder="my@email.com" value="${email!''}" />
        </div>
      </div>
      <input type="hidden" name="_csrf" value="${_csrf.token}" />
      <button class="btn btn-primary">Save</button>
    </form>
</@c.page>