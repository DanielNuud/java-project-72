@import hexlet.code.dto.urls.UrlPage
@import hexlet.code.util.NamedRoutes
@import java.time.format.DateTimeFormatter
@param UrlPage page

!{var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");}

@template.layout.page(
page = page,
content = @`

    <div class="container-lg mt-5">
        <h1>URL: ${page.getUrl().getName()}</h1>

        <table class="table table-bordered table-hover mt-3 table-dark text-light">
            <tbody>
            <tr>
                <td>ID</td>
                <td>${page.getUrl().getId()}</td>
            </tr>
            <tr>
                <td>Name</td>
                <td>${page.getUrl().getName()}</td>
            </tr>
            <tr>
                <td>Created At</td>
                <td>${page.getUrl().getCreatedAt().toLocalDateTime().format(formatter)}</td>
            </tr>
            </tbody>
        </table>

        <form action="${NamedRoutes.urlCheckPath(page.getUrl().getId())}" method="post">
            <button type="submit" class="btn btn-primary">Check</button>
        </form>

        <table class="table table-bordered table-hover mt-3 table-dark text-light">
            <thead>
            <th class="col-1">ID</th>
            <th class="col-1">Status Code</th>
            <th>Title</th>
            <th>H1</th>
            <th>Description</th>
            <th class="col-2">Created At</th>
            </thead>
            @if(page.getUrlCheckList() != null)
                <tbody>
                @for(var check : page.getUrlCheckList())
                    <tr>
                        <td>
                            ${check.getId()}
                        </td>
                        <td>
                            ${check.getStatusCode()}
                        </td>
                        <td>
                            ${check.getTitle()}
                        </td>
                        <td>
                            ${check.getH1()}
                        </td>
                        <td>
                            ${check.getDescription()}
                        </td>
                        <td>
                            ${check.getCreatedAt().toLocalDateTime().format(formatter)}
                        </td>
                    </tr>
                @endfor
                </tbody>
            @endif
        </table>
    </div>
`
)