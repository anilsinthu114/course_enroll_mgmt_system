
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error Page</title>
    <style>
        .error-container {
            margin: 50px auto;
            padding: 20px;
            width: 70%;
            border: 1px solid #ccc;
            border-radius: 5px;
            background-color: #f8d7da;
            color: #721c24;
            font-size: 16px;
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <h2>Error</h2>
        <p>${errorMessage}</p>
        <a href="javascript:history.back()">Go Back</a>
    </div>
</body>
</html>
