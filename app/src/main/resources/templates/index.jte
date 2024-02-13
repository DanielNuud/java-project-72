@import hexlet.code.dto.BasePage
@import hexlet.code.util.NamedRoutes
@param BasePage page

@template.layout.page(
    page = page,
content = @`
    <form action="${NamedRoutes.urlsPath()}" method="post">
        <div class="mb-3">
            <label for="inputUrl" class="form-label">Put your URL here:</label>
            <input type="text" class="form-control" id="inputUrl" aria-describedby="emailHelp"
            name="url" required>
            <div id="emailHelp" class="form-text">Example: https://www.example.com</div>
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
`)

