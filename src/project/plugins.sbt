addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.5")

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.9.0")

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

addSbtPlugin("pl.project13.scala" % "sbt-jmh" % "0.2.27")
