Java RESTish Lab
================

A Java project to accompany [Sam Berry's RESTish API](http://goo.gl/BFDKcs) presentation.

This project provides a basic working REST API built using Java (specifically RestEasy,
Jackson, and Spring). A lab is intended to be held to walk developers through the steps
required to add their own REST API and cover topics such as HTTP verbs, status codes,
and other RESTish best practices.

## Packaging

If you want to build and run this project just follow these steps:

1. Clone this project from [GitHub](https://github.com/egillespie/rest-lab-java.git).
2. Build the project using Maven: `mvn package`
3. Launch the project in Jetty using Maven: `mvn jetty:run-war`
4. Navigate to [http://localhost:8080/greetings](http://localhost:8080/greetings). If you see an empty JSON list then it works!

## Testing

This project provides a Postman collection for testing. If you want to try these out
then you'll have to get Postman and import the collection:

1. Install [Chrome](https://www.google.com/chrome/browser/).
2. Add the [Postman](https://chrome.google.com/webstore/detail/postman-rest-client-packa/fhbjgbiflinjbdggehcddcbncdddomop) extension to Chrome.
3. Launch the Postman app within Chrome.
4. Import the file "src/postman/rest-lab.json" into Postman.

You will see a number of resources appear in the left panel. With your Jetty server running
you can click through any of them and send them to your server to see what kinds of responses
you get.

## Lab Links

* RESTish API Lab Resource Documentation: [API Docs](https://docs.google.com/document/d/1IBja69PpfL1LnK4mdrs9TSYAbEKrEcAQEnEJToQpiQY/edit?usp=sharing)
* RESTish API Lab Slides: [Lab Slides](https://docs.google.com/presentation/d/1aHXwmcNsGJ1WWp0Z-x5Y3Ny8phtteolrSMwe6gHHvNs/edit?usp=sharing)
* RESTish API Lab Technical Guide: [Tech Guide](https://docs.google.com/document/d/1aD81njIQ0ZcRgeA2GOxtvt9bJD3P2-lWOXNdiWRPlBw/edit#)
