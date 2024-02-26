@import gg.jte.Content
@import hexlet.code.util.NamedRoutes
@param Content content

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Page Analyzer</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <style>
        .bi {
            width: 1em;
            height: 1em;
            vertical-align: -.125em;
            fill: currentcolor;
        }
    </style>
</head>
<body class="d-flex flex-column min-vh-100">
<header>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark bg-gradient">
        <div class="container-fluid fw-bolder">
            <a class="navbar-brand" href="${NamedRoutes.rootPath()}">Page Analyzer</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                    aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="${NamedRoutes.rootPath()}">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${NamedRoutes.urlsPath()}">Pages</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</header>
<main class="flex-grow-1 overflow-y-auto">
    ${content}
</main>
<footer class="footer border-top py-3 mt-5 bg-light">
    <div class="text-center text-muted">
        Created by Daniel Nüüd using Java, Javalin, PostgreSQL, HTML5, Bootstrap 5
    </div>
</footer>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"></script>
</body>
</html>