import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.concurrent.ExecutionContext.Implicits.global
import scala.async.Async.{async, await}

@js.native
@JSImport("libp2p", JSImport.Default)
object Libp2p extends js.Object {
  def create(args: js.Dynamic): js.Promise[libp2pObj] = js.native
}

@js.native
trait libp2pObj extends js.Object {
  def start(): js.Promise[js.Dynamic] = js.native
  def peerId: js.Dynamic = js.native
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

object Libp2pConfig {
  val lala = "Lululu"
}

// @ScalaJSDefined
object Foo extends js.Object {
  val x: js.UndefOr[Int] = 4
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
      val libp2p: libp2pObj = await(Libp2p.create(js.Dynamic.literal(
        addresses = js.Dynamic.literal(
          listen = js.Array("/dns4/signaling.dividi.xyz/tcp/443/wss/p2p-webrtc-star/")
        ),
        modules = js.Dynamic.literal(
          transport = js.Array(Websockets, WebRTCStar),
          connEncryption = js.Array(NOISE),
          streamMuxer = js.Array(Mplex)
        )
      )).toFuture)

      await(libp2p.start().toFuture)
      window.libp2p = libp2p
      status.innerText = "libp2p started!"
      log(s"libp2p id is ${libp2p.peerId.toB58String()}")
    }
  }
}
