# ohlc_plotter
OHLC Plotting for FCDA data

Very much WIP. Built with a custom pipeline in IntelliJ Idea Community Edition.

### Run Configuration:
* Command line: `tomcat7:run-war`
* Maven goals:
    * Maven webapp: `clean`
    * `build`
    * Maven webapp: `compile`
    * Maven webapp: `package`

### Notes
* Tomcat runs on port `8080` by default
* To deploy, the `.war` file from the `target` directory has to be copied to the tomcat webapps directory - then the server should automatically deploy it.
* SQL query string and processing isn't entirely implemented yet: current project is using random data for the most part
