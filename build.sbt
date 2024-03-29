name:="scipr"

version:= "1.0"

scalaVersion:= "2.9.2"

libraryDependencies += "com.twitter" %% "finagle-http" % "6.2.0"

libraryDependencies += "junit" % "junit" % "4.11" % "test"

libraryDependencies += "com.novocode" % "junit-interface" % "0.8" % "test->default"

addCommandAlias("run-scipr", "run src/test/resources/scipr-config.xml")

