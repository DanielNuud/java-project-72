@import hexlet.code.util.NamedRoutes
@import hexlet.code.dto.BasePage
@import org.apache.commons.lang3.StringUtils
@param BasePage page

@template.layout.page(
content = @`
    <svg xmlns="http://www.w3.org/2000/svg" class="d-none">
        <symbol id="exclamation-triangle-fill" viewBox="0 0 16 16">
            <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
        </symbol>
    </svg>
    @if(StringUtils.contains(page.getMessage(), "Wrong URL"))
        <div class="alert alert-danger d-flex align-items-center alert-dismissible fade show m-0" role="alert">
            <svg class="bi flex-shrink-0 me-2" role="img" aria-label="Danger:"><use xlink:href="#exclamation-triangle-fill"/></svg>
            <div>
                ${page.getMessage()}
            </div>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    @endif

    <section>
        <div class="container-fluid bg-dark p-5">
            <div class="row">
                <div class="col-md-10 col-lg-8 mx-auto text-white">
                    <h1 class="display-4 mb-0">Page Analyzer</h1>
                    <p class="lead fw-lighter">Check websites for SEO suitability for free</p>
                    <form action="${NamedRoutes.urlsPath()}" method="post" class="rss-form text-muted">
                        <div class="row">
                            <div class="col">
                                <div class="form-floating">
                                    <input id="url-input" autofocus="" type="text" required="" name="url" aria-label="url" class="form-control w-100" placeholder="Url" autocomplete="off">
                                    <label for="url-input">Put your URL here</label>
                                </div>
                            </div>
                            <div class="col-auto">
                                <button type="submit" class="h-100 btn btn-lg btn-success opacity-75 px-sm-5">Submit</button>
                            </div>
                        </div>
                    </form>
                    <p class="opacity-50 mt-2 mb-0">Example: https://www.example.com</p>
                </div>
            </div>
        </div>
    </section>
`
)