import scalajsbundler.util.JSON._

name := "dividi"

version := "0.1"

scalaVersion := "2.13.4"

enablePlugins(ScalaJSBundlerPlugin)
// This is an application with a main method
scalaJSUseMainModuleInitializer := true

// scala-async
libraryDependencies += "org.scala-lang.modules" %% "scala-async" % "0.10.0"
libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value % Provided
scalacOptions += "-Xasync"

// npm dependencies
useYarn := true
npmDependencies in Compile ++= Seq(
  "babel-polyfill" -> "^6.26.0",
  "libp2p" -> "^0.30.12",
  "libp2p-bootstrap" -> "^0.12.3",
  "libp2p-gossipsub" -> "^0.8.0",
  "libp2p-mplex" -> "^0.10.3",
  "libp2p-noise" -> "^2.0.5",
  "libp2p-webrtc-star" -> "^0.21.2",
  "libp2p-websockets" -> "^0.15.6"
)
// dependabot alerts
additionalNpmConfig in Compile ++= Map(
  "resolutions" -> obj(
    // force netmask upgrade due to https://github.com/advisories/GHSA-pch5-whg9-qr2r
    "netmask" -> str("^2.0.2"),
    // force upgrade due to https://github.com/advisories/GHSA-p9pc-299p-vxgp
    "yargs-parser" -> str("^13.1.2"),
    // force upgrade due to https://github.com/advisories/GHSA-c9g6-9335-x697
    "sockjs" -> str("^0.3.20"),
    // https://github.com/advisories/GHSA-vx3p-948g-6vhq
    "ssri" -> str("^6.0.2")
  )
)