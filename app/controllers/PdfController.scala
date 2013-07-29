package controllers

import play.api._
import play.api.mvc._

import models.HtmlDocument
import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText}
import play.api.i18n.Messages

import util.pdf.PDF

object PdfController extends Controller {

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
          val pdfData = PDF.toBytes(views.html.document.render(htmlDocument.documentContent))
          Ok(pdfData).as("application/pdf")
        }
      )
  }

}
