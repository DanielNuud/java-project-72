@import hexlet.code.model.Url
@import hexlet.code.util.NamedRoutes
@import hexlet.code.dto.urls.UrlsPage
@import org.apache.commons.lang3.StringUtils
@import java.text.SimpleDateFormat
@param UrlsPage page

@template.layout.page(
content = @`
    <svg xmlns="http://www.w3.org/2000/svg" class="d-none">
        <symbol id="check-circle-fill" viewBox="0 0 16 16">
            <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z"/>
        </symbol>
        <symbol id="exclamation-triangle-fill" viewBox="0 0 16 16">
            <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
        </symbol>
    </svg>
    @if(StringUtils.contains(page.getMessage(), "Url is successfully added!"))
        <div class="alert alert-success d-flex align-items-center alert-dismissible fade show m-0" role="alert">
            <svg class="bi flex-shrink-0 me-2" role="img" aria-label="Success:"><use xlink:href="#check-circle-fill"/></svg>
            <div>
                ${page.getMessage()}
            </div>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    @elseif(StringUtils.contains(page.getMessage(), "Url is already exists!"))
        <div class="alert alert-warning d-flex align-items-center alert-dismissible fade show m-0" role="alert">
            <svg class="bi flex-shrink-0 me-2" role="img" aria-label="Danger:"><use xlink:href="#exclamation-triangle-fill"/></svg>
            <div>
                ${page.getMessage()}
            </div>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    @endif

    <section>
        <div class="container-lg mt-5">
            !{var urls = page.getUrls();}

            <div class="mx-4 mt-4 mb-0">
                @if(urls.isEmpty())
                    <div class="fs-4">
                        List is empty!
                    </div>
                @else
                    <div class="container-lg mt-5 table-responsive">
                        <table class="table table-light table-hover table-bordered border-white border-4 caption-top">
                            <caption class="fs-3 fw-bolder">Pages</caption>
                            <thead class="align-middle">
                            <tr>
                                <th class="col-1">ID</th>
                                <th>Name</th>
                                <th class="col-2">Last check</th>
                                <th class="col-2">Status Code</th>
                            </tr>
                            </thead>
                            <tbody>
                            @for(Url item : urls)
                                <tr>
                                    <td>${item.getId()}</td>
                                    <td><a href="${NamedRoutes.urlPath(item.getId())}">${item.getName()}</a></td>
                                    @if(page.getChecks().containsKey(item.getId()))
                                        <td>${new SimpleDateFormat("dd/MM/yy HH:mm").format(page.getChecks().get(item.getId()).getCreatedAt())}</td>
                                        <td>${page.getChecks().get(item.getId()).getStatusCode()}</td>
                                    @else
                                        <td></td>
                                        <td></td>
                                        @endif
                                </tr>
                            @endfor
                            </tbody>
                        </table>
                    </div>

                    <div class="btn-toolbar position-relative text-light py-6 d-flex justify-content-center" role="toolbar" aria-label="Toolbar with button groups">
                        <div class="btn-group me-2" role="group" aria-label="First group">
                            @for(int i = 1; i <= page.getPageNumber(); i++)
                                <a class="btn btn-success opacity-75" href="${NamedRoutes.urlsPath(i)}" role="button">${i}</a>
                            @endfor
                        </div>
                    </div>
                @endif
            </div>
        </div>
    </section>
`
)