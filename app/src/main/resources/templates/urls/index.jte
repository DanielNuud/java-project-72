@import hexlet.code.dto.urls.UrlsPage

@param UrlsPage page

@template.layout.page(
    page = page,
    content = @`
    <table class="table">
        <thead>
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Name</th>
            <th scope="col">Created At</th>
        </tr>
        </thead>
        @for(var url : page.getUrls())
        <tbody>
        <tr>
            <th scope="row">${url.getId()}</th>
            <td>${url.getName()}</td>
            <td></td>
        </tr>
        </tbody>
        @endfor
    </table>
    <button type="button" class="btn btn-primary"><a href="/">Back</a></button>
`)


