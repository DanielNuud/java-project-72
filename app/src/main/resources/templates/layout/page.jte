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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We"
          crossorigin="anonymous">
</head>
<body>
@if(page != null && page.getFlash() != null)
    <div class="${page.getFlashType()}" role="alert">
        ${page.getFlash()}
    </div>
@endif
<div class="mx-auto p-4 py-md-5">
    ${content}
</div>

</body>
</html>
