package week5.lecture3_designing

import java.util.concurrent.Executor

import com.ning.http.client.AsyncHttpClient
import org.jsoup.Jsoup

import scala.concurrent.{Future, Promise}
import scala.collection.JavaConverters._

import akka.pattern.PipeableFuture
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Hierro on 3/5/16.
  */
object WebClient {

  val client = new AsyncHttpClient
  def get(url: String)(implicit exec: Executor): PipeableFuture[String] = {
    val f = client.prepareGet(url).execute()
    val p = Promise[String]()
    f.addListener(new Runnable {
      override def run(): Unit = {
        val response = f.get
        if (response.getStatusCode < 400)
          p success response.getResponseBodyExcerpt(131072) //check first 128 kb of body
        else p failure BadStatus(response.getStatusCode)
      }
    }, exec)
    new PipeableFuture(p.future)
  }

  def findLinks(body: String, url: String): Iterator[String] = {
      val document = Jsoup.parse(body,url)
      val links = document.select("A[HREF]")
      for {
        link <- links.iterator().asScala
      } yield link.absUrl("href")
    }

  def shutdown(): Unit = {
    if(!client.isClosed) client.close()
  }

  case class BadStatus(statusCode: Int) extends Throwable
}