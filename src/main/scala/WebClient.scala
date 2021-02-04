import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.concurrent.ExecutionContext.Implicits.global
import scala.async.Async.{async, await}

@js.native
@JSImport("libp2p", JSImport.Default)
object Libp2pLib extends js.Object {
  def create(args: js.Dynamic): js.Promise[libp2p] = js.native
}

@js.native
trait libp2p extends js.Object {
  def start(): js.Promise[js.Any] = js.native
  def peerId: js.Dynamic = js.native

  def on(event: String, callback: js.Function1[js.Dynamic,js.Any]): Unit = js.native
}

@js.native
@JSImport("libp2p-websockets", JSImport.Default)
object Websockets extends js.Object

@js.native
@JSImport("libp2p-webrtc-star", JSImport.Default)
object WebRTCStar extends js.Object

@js.native
@JSImport("libp2p-mplex", JSImport.Default)
object Mplex extends js.Object

@js.native
@JSImport("libp2p-noise", "NOISE")
object NOISE extends js.Object

@js.native
@JSImport("libp2p-gossipsub", JSImport.Default)
class Gossipsub(libp2p: libp2p) extends js.Object {
  def start(): Unit = js.native

  def on(topicName: js.Any, callBack: js.Function1[js.Dynamic,js.Dynamic]): js.Any = js.native

  def subscribe(topicName: String): js.Any = js.native
}

// https://www.scala-js.org/doc/interoperability/sjs-defined-js-classes.html
object Libp2pConfig {
  val lala = "Lululu"
}

object WebClient {
  val window: js.Dynamic = js.Dynamic.global.window
  val document: js.Dynamic = js.Dynamic.global.document
  val status: js.Dynamic = document.getElementById("status")
  val output: js.Dynamic = document.getElementById("output")

  def log (txt: String): Unit = {
    println(txt)
    output.textContent = output.textContent + s"$txt\n"
  }

  def main(args: Array[String]): Unit = {
    async{
      val libp2p: libp2p = await(Libp2pLib.create(js.Dynamic.literal(
        addresses = js.Dynamic.literal(
          listen = js.Array("/dns4/signaling.dividi.xyz/tcp/443/wss/p2p-webrtc-star/")
        ),
        modules = js.Dynamic.literal(
          transport = js.Array(Websockets, WebRTCStar),
          connEncryption = js.Array(NOISE),
          streamMuxer = js.Array(Mplex)
        ),
        config = js.Dynamic.literal(
          peerDiscovery = js.Dynamic.literal(
            autoDial = true
          )
        )
      )).toFuture)

//      val gsub = new Gossipsub(libp2p)
//      window.gsub = gsub
//      gsub.start()
//      log("gossipsub started")
//
//      gsub.on("fruit", data => println(data))
//      gsub.subscribe("fruit")

      await(libp2p.start().toFuture)
      window.libp2p = libp2p
      status.innerText = "libp2p started!"
      log(s"libp2p id is ${libp2p.peerId.toB58String()}")

      // Listen for new peers
      libp2p.on("peer:discovery", peerId => log(s"Found peer ${peerId.toB58String()}"))
    }
  }
}
