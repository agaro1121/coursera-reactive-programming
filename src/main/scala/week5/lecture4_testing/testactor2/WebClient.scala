package week5.lecture4_testing.testactor2

import java.util.concurrent.Executor

import com.ning.http.client.{AsyncHttpClient, ListenableFuture, Response}
import org.jsoup.Jsoup

import scala.concurrent.{Future, Promise}
import scala.collection.JavaConverters._

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Hierro on 3/5/16.
  */
trait WebClient {
  def get(url: String)(implicit exec: Executor): Future[String]
}

object AsyncWebClient extends WebClient {

  def client = new AsyncHttpClient

  def get(url: String)(implicit exec: Executor): Future[String] = {
    val responseFuture:ListenableFuture[Response] = client.prepareGet(url).execute()
    val p = Promise[String]()
    responseFuture.addListener(new Runnable {
      override def run(): Unit = {
        val response = responseFuture.get
        if (response.getStatusCode < 400)
          p success response.getResponseBodyExcerpt(131072) //check first 128 kb of body
        else p failure BadStatus(response.getStatusCode)
      }
    }, exec)
    p.future
  }


  def findLinks(body: String, url: String): Iterator[String] = {
    val document = Jsoup.parse(body,url)
    val links = document.select("A[HREF]") //anchor tags with href attribute
    for {
      link <- links.iterator().asScala
    } yield link.absUrl("href")
  }


  def shutdown(): Unit = {
    if(!client.isClosed) client.close()
  }

  case class BadStatus(statusCode: Int) extends Throwable
}