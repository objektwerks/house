package objektwerks

import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging

import jodd.mail.{Email, MailServer, SmtpServer}

import scala.language.postfixOps
import scala.util.Using

object Emailer:
  def apply(config: Config): Emailer =
    val host = config.getString("email.host")
    val sender = config.getString("email.sender")
    val password = config.getString("email.password")
    val subject = config.getString("email.subject")
    new Emailer(host, sender, password, subject)

private final class Emailer(host: String,
                            sender: String,
                            password: String,
                            subject: String) extends LazyLogging:
  private val smtpServer: SmtpServer = MailServer.create
    .host(host)
    .ssl(true)
    .auth(sender, password)
    .buildSmtpMailServer

  private def sendEmail(recipients: List[String],
                        message: String): Boolean =
    Using( smtpServer.createSession ) { session =>
      val email = Email.create
        .from(sender)
        .subject(subject)
        .textMessage(message, "UTF-8")
        .cc(sender)
      recipients.foreach( recipient => email.to(recipient) )
      session.open()
      val messageId = session.sendMail(email)
      logger.info("*** Emailer subject: {} to: {} message id: {}", subject, recipients.mkString, messageId)
      true
    }.recover { error =>
      logger.error("*** Emailer subject: {} to: {} failed: {}",  subject, recipients.mkString, error.getMessage)
      false
    }.get

  def send(recipients: List[String],
           message: String): Boolean = sendEmail(recipients, message)