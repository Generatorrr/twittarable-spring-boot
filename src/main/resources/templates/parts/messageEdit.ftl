<div>
    <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
        Message Editor
    </a>
    <div class="collapse <#if message??>show</#if>" id="collapseExample">
        <div class="form-group mt-3">
            <form method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <input class="form-control ${(textError??)?string('is-invalid', '')}"
                           value="<#if message??>${message.text}</#if>" type="text" name="text" placeholder="Input your message" />
                            <#if textError??>
                            <div class="invalid-feedback">
                                ${textError}
                            </div>
                            </#if>
                </div>
                <div class="form-group">
                    <input class="form-control ${(tagError??)?string('is-invalid', '')}" type="text" name="tag" placeholder="Input your tag"
                           value="<#if message??>${message.tag}</#if>" />
                          <#if tagError??>
                            <div class="invalid-feedback">
                                ${tagError}
                            </div>
                          </#if>
                </div>
                <div class="custom-file">
                    <input id="customFile" type="file" name="file" />
                    <label for="customFile" class="custom-file-label">Choose file</label>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                <input type="hidden" name="id" value="<#if message??>${message.id}</#if>" />
                <div class="form-group">
                    <button class="btn btn-primary ml-2" type="submit">Save message</button>
                </div>
            </form>
        </div>
    </div>
</div>