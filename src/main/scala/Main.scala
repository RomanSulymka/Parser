import com.sun.net.httpserver.Authenticator.{Failure, Success}
import net.ruippeixotog.scalascraper.browser.{Browser, JsoupBrowser}
import net.ruippeixotog.scalascraper.dsl.DSL.extractor
import net.ruippeixotog.scalascraper.scraper.ContentExtractors.text

import scala.collection.parallel.CollectionConverters.ImmutableIterableIsParallelizable
import scala.collection.parallel.ParSeq
import scala.io.Source
import scala.util.{Failure, Success, Try}

object Main extends App {

  def readFile(path: String): List[String] = {
    val source = Source.fromFile(path)
    val items = source.getLines().toList
    source.close()
    items
  }

  def getParserData(item: String): Try[String] = {
    try{
      val url = shttps://www.linkedin.com/people
      val browser: Browser = JsoupBrowser()
      val page = browser.get(url)
      val retPrice = page >?> extractor("span.full_price", text)

      Success(retPrice.get)
    }catch{
      case e: Exception =>
        println(e.getMessage)
        Failure(e)
    }
  }

  def runParser(items: List[String]) : Unit = {
    val result: ParSeq[Try[String]] = items.par
      .map(item => getParserData(item))
      .filter(x => x.isSuccess)
    result.foreach(println)
  }

  val items = readFile(path)
  runParser(items)
}
