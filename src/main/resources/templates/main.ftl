<#import "parts/common.ftl" as c>

<@c.page>
    <div>
        <div class="form-row">
            <div class="form-group col-md-6">
                <form class="form-inline" method="get" action="/main">
                    <input type="text" name="filter" placeholder="Search by tag"  value="${filter?ifExists}"/>
                    <button class="btn btn-primary ml-2" type="submit">Search</button>
                </form>
            </div>
        </div>
        <div>
            <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
                Add new message
            </a>
            <div class="collapse" id="collapseExample">
                <div class="form-group mt-3">
                    <form method="post" enctype="multipart/form-data">
                        <div class="form-group">
                            <input class="form-control" type="text" name="text" placeholder="Input your message" />
                        </div>
                        <div class="form-group">
                            <input class="form-control" type="text" name="tag" placeholder="Input your tag" />
                        </div>
                        <div class="custom-file">
                            <input id="customFile" type="file" name="file" />
                            <label for="customFile" class="custom-file-label">Choose file</label>
                        </div>
                        <input type="hidden" name="_csrf" value="${_csrf.token}" />
                        <div class="form-group">
                            <button class="btn btn-primary ml-2" type="submit">Post message</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div>Messages list:</div>
        <div class="card-columns">
            <#list messages as message>
            <div class="card my-3">
                <div>
                    <#if message.filename??>
                        <img class="card-img-top" src="/img/${message.filename}" alt="${message.filename}" style="max-width: 250px; max-height: 150px">
                    </#if>
                </div>
                <div class="m-2">
                    <span>${message.text}</span>
                    <i>${message.tag}</i>
                </div>
                <div class="card-footer text-muted">${message.authorName}</div>
            </div>
            <#else>
            No messages found
            </#list>
        </div>
    </div>
</@c.page>