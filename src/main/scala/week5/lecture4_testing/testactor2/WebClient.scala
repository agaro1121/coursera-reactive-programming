package week5.lecture4_testing.testactor2

import java.util.concurrent.Executor

import com.ning.http.client.AsyncHttpClient
import week5.lecture3_designing.WebClient.BadStatus
import akka.pattern.PipeableFuture

import scala.concurrent.Promise

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by anthonygaro on 3/25/16.
  */
trait Web{
  def get(url: String)(implicit exec: Executor): PipeableFuture[String]
}

class WebClient extends Web {
  val client = new AsyncHttpClient
  def get(url: String)(implicit exec: Executor): PipeableFuture[String] = {
   val f = client.prepareGet(url).execute()
    val p = Promise[String]


    f.addListener(new Runnable {
      override def run(): Unit = {
        val response = f.get()
        if(response.getStatusCode < 400)
          p success(response.getResponseBody)
        else p failure(BadStatus(response.getStatusCode))
      }
    },exec)

    new PipeableFuture(p.future)
  }

}

object WebClient extends WebClient{
  case class BadStatus(status: Int) extends Throwable
}
