@import gg.jte.Content
@import hexlet.code.dto.BasePage;
@param Content content
@param BasePage page = null

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Page Analyzer</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
          crossorigin="anonymous">
</head>
<body class="d-flex flex-column bg-dark">
<nav class="navbar navbar-expand-lg navbar-dark bg-dark border-bottom border-secondary">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">Page Analyzer</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/urls">Pages</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<main class="flex-grow-1">
    @if(page != null && page.getFlash() != null)
        <div class="${page.getFlashType()}" role="alert">
            ${page.getFlash()}
        </div>
    @endif

    <div class="container-fluid p-5 text-light">
        ${content}
    </div>
</main>
<footer class="py-3 mt-5 fixed-bottom border-top border-secondary bg-dark">
    <p class="text-center text-light">Created by Daniel Nüüd using Java, HTML5, Bootstrap 5, PostgreSQL</p>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
</body>
</html>
