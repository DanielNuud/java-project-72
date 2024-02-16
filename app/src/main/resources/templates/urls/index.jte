@import hexlet.code.dto.urls.UrlsPage
@import hexlet.code.util.NamedRoutes
@import java.time.format.DateTimeFormatter
@param UrlsPage page

!{var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");}

@template.layout.page(
    page = page,
    content = @`
    <table class="table table-dark text-light">
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
            <td><a href="${NamedRoutes.urlPath(url.getId())}">${url.getName()}</a></td>
            <td>${url.getCreatedAt().toLocalDateTime().format(formatter)}</td>
        </tr>
        </tbody>
        @endfor
    </table>
    <button type="button" class="btn btn-primary" onclick="window.location.href='/'">Back</button>
`)


