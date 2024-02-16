@import hexlet.code.dto.BasePage
@import hexlet.code.util.NamedRoutes
@param BasePage page

@template.layout.page(
page = page,
content = @`
    <div class="col-md-10 col-lg-8 mx-auto text-white">
        <h2 class="display-3 mb-3 my-4" style="text-align: center">Page Analyzer</h2>
        <p class="lead my-2" style="text-align: center">Check websites for SEO fitness for free :)</p>
        <div class="container-lg my-4 text-center">
            <form action="${NamedRoutes.urlsPath()}" method="post">
                <div class="mb-6">
                    <div class="input-group flex-nowrap w-50 mx-auto">
                        <span class="input-group-text" id="addon-wrapping">#</span>
                        <input type="text" class="form-control " placeholder="Put your URL here" aria-label="Url" aria-describedby="addon-wrapping"
                               name="url" id="inputUrl" required>
                        <button type="submit" class="btn btn-primary">Submit</button>
                    </div>
                    <div id="emailHelp" class="form-text" style="color: #6c757d;">Example: https://www.example.com</div>
                </div>
            </form>
        </div>
    </div>


`)