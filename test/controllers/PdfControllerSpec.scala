package controllers

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class PdfControllerSpec extends Specification {
  
  "PdfController" should {
    
    "render a PDF given a valid request containing a documentContent " in {
      running(FakeApplication()) {

        val documentContent = """<div ink2_template="5x7" name="Ink2_canvas" id="Ink2_canvas" height="519px" width="375px" style="overflow: hidden; position: relative; width: 375px; height: 519px;">
                                   <div id="archive_front" style="overflow: hidden; position: absolute; left: 0px; top: 0px; width: 375px; height: 519px;">
                                     <div class="divImg" name="divImgarchive_front" id="divImgarchive_front" locked="1" rotate="0" style="position: absolute; top: 0px; left: -201px; width: 780px; height: 519px; ">
                                       <img id="imgarchive_front" name="imgarchive_front" width="780" height="519" src="http://useast-thumb.imgag.com/image/k1/Ycr27xINz6zgmXSzm7UFJBk9uIuQjv-TwKCksDcn1lnnI52dcPDsi5Kdf3oF7hKhGbGO8Bz8dde0RLeVvpzelfS0kdmiLiKTsM.VwiW3QHz7vlc355iL6p9FJ5gnOJ98TR9GEdlVhUFx-JVitFOyjEslpdQzPktCJlEVUqUTt0kNMOCnKiNFs1ABurzwhC1M0HxC1IoZ1sFM3EMmn-4NHXTYzyTEg8QZ9VAS1zzMNVBjFbiV2h7.8.AQgWTao5ObpZAxWYXebR2VzA5teFLd59-IQVmV.2KAEt0hMmV81lZHVhtXW4P0IY-iY3CQ93CEELTdmZ3xbB6SE8TJRoUQ3LvItKcplpBkkKDUr1P9QevtXAO0O4k37nofoPskdRspG7azBnFAxOc_.jpg"/>
                                     </div>
                                   </div>
                                 </div>"""

        val request = FakeRequest().withFormUrlEncodedBody("documentContent" -> documentContent)

        val action = controllers.PdfController.document()

        val response = action(request)
        
        status(response) must equalTo(OK)
        contentType(response) must beSome.which(_ == "application/pdf")

        val responseBytes = contentAsBytes(response)
        responseBytes.length must be_>(0)
      }
    }
  }
}