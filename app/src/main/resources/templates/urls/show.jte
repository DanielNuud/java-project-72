@import hexlet.code.dto.urls.UrlPage
@import java.time.format.DateTimeFormatter
@param UrlPage page

!{var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");}

@template.layout.page(
content = @`
    <div class="container-lg mt-5">
        <h1>Сайт: ${page.getUrl().getName()}</h1>

        <table class="table table-bordered table-hover mt-3">
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
    </div>
`
)
