name := "dividi"

version := "0.1"

scalaVersion := "2.13.4"

enablePlugins(ScalaJSBundlerPlugin)

// This is an application with a main method
scalaJSUseMainModuleInitializer := true

// npm dependencies
useYarn := true
//npmDependencies in Compile += "libp2p" -> "^0.29.0"
npmDependencies in Compile ++= Seq(
  "babel-polyfill" -> "^6.26.0",
  "libp2p" -> "^0.29.0",
  "libp2p-bootstrap" -> "^0.12.1",
  "libp2p-gossipsub" -> "^0.7.0",
  "libp2p-mplex" -> "^0.10.0",
  "libp2p-noise" -> "^2.0.0",
  "libp2p-secio" -> "^0.13.1",
  "libp2p-webrtc-star" -> "^0.20.0",
  "libp2p-websockets" -> "^0.14.0")