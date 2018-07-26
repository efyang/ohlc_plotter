<html>
    <head>
        <script src="https://cdn.plot.ly/plotly-latest.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    </head>
    <body>
        <h2>
            Hello World!
        </h2>

        <div height=400 width=700 id="plot-container"></div>

        <script>
            $(document).ready(function() {
                $("#plot-container").load("ohlc-data-servlet");
            });
        </script>

        <a href="servlet">Click to see servlet</a>
    </body>
</html>
