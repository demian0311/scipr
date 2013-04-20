# Scipr

## Description
HTTP Server and Proxy in Scala.  Right now it'll just serve static files.
This is just me learning Finagle, I think it's a great library and playing
with some HTTP concepts in Scala.

## Getting Started
- Start it up from sbt by running `sbt run-scipr`.
  This will load it with the configuration file found in
  `src/test/resources/scipr-config.xml`.
- Then go to [http://localhost:8099/index.html](http://localhost:8099/index.html),
  you should see a bit of text and an image of my face.  This is served up
  by scipr.

## References
- [Finagle project](https://github.com/twitter/finagle)
- [Finagle quickstart](http://twitter.github.io/finagle/guide/Quickstart.html)
- [HTTP 1.1 RFC](https://tools.ietf.org/html/rfc2616)
