@import hexlet.code.dto.urls.BuildUrlPage
@param BuildUrlPage page

@template.layout.page(content = @`
    <form class="row g-3 needs-validation" novalidate="" action="/urls" method="post">
        <div class="input-group mb-3">
            <span class="input-group-text" id="inputGroup-sizing-default">Url</span>
            <input type="text" class="form-control" aria-label="Sizing example input"
                   aria-describedby="inputGroup-sizing-default" name="url" required>
        </div>
        <div class="col-12">
            <button class="btn btn-primary" type="submit">Submit</button>
        </div>
    </form>
`
)