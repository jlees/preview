package controllers

import play.api.mvc._

import models.HtmlDocument
import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText}
import play.api.i18n.Messages

import org.apache.pdfbox.pdmodel.{PDPage, PDDocument}
import util.pdf.PDF

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import javax.imageio.ImageIO

object JpgController extends Controller {

  private val htmlDocumentBinder: Form[HtmlDocument] = Form(
    mapping(
      "documentContent" -> nonEmptyText
    )(HtmlDocument.apply)(HtmlDocument.unapply)
  )

  def document = Action 
  {
      implicit request =>
      val htmlDocumentForm = htmlDocumentBinder.bindFromRequest()
      htmlDocumentForm.fold(
        hasErrors = { form =>
          Redirect("/error").
            flashing(Flash(form.data) +
            ("error" -> Messages("validation.errors")))
        },
        success = { htmlDocument =>
          val pdfDocument = PDDocument.load(new ByteArrayInputStream(PDF.toBytes(views.html.document.render(htmlDocument.documentContent))))
          val pdfPage = pdfDocument.getDocumentCatalog().getAllPages.iterator().next().asInstanceOf[PDPage]
          val bufferedImage = pdfPage.convertToImage()
          val baos = new ByteArrayOutputStream()
          ImageIO.write( bufferedImage, "jpg", baos )
          baos.flush()
          val jpgData = baos.toByteArray()
          baos.close()
          Ok(jpgData).as("image/jpeg")
        }
      )
  }

}
