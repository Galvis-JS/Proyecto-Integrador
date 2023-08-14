package coworking.digitalBooking.Service;


import coworking.digitalBooking.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmailConfirmation(String to, String subject, String verificationCode) throws MessagingException {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            String htmlMsg = "<div\n" +
                    "  style=\"\n" +
                    "    width: 400px;\n" +
                    "    height: 600px;\n" +
                    "    border-radius: 30px;\n" +
                    "    background-color: #ffffff;\n" +
                    "    padding: 40px;\n" +
                    "    margin: auto;\n" +
                    "    text-align: center;\n" +
                    "  \"\n" +
                    ">\n" +
                    "  <img\n" +
                    "    style=\"width: 100px\"\n" +
                    "    src=\"https://comision3-equipo3.s3.us-east-2.amazonaws.com/img/logo1.png\"\n" +
                    "  />\n" +
                    "  <h1\n" +
                    "    style=\"\n" +
                    "      font-family: 'Tahoma', 'Verdana';\n" +
                    "      font-weight: bold;\n" +
                    "      font-size: x-large;\n" +
                    "      color: #a61f69;\n" +
                    "    \"\n" +
                    "  >\n" +
                    "    Verificación de Registro\n" +
                    "  </h1>\n" +
                    "  <img\n" +
                    "    style=\"width: 250px\"\n" +
                    "    src=\"https://comision3-equipo3.s3.us-east-2.amazonaws.com/img/email.png\"\n" +
                    "  />\n" +
                    "  <p\n" +
                    "    style=\"\n" +
                    "      font-family: 'Tahoma', 'Verdana';\n" +
                    "      font-weight: regular;\n" +
                    "      font-size: large;\n" +
                    "      color: #000000;\n" +
                    "      margin: 20px;\n" +
                    "    \"\n" +
                    "  >\n" +
                    "    ¡Gracias por registrarte!\n" +
                    "  </p>\n" +
                    "  <p\n" +
                    "    style=\"\n" +
                    "      font-family: 'Tahoma', 'Verdana';\n" +
                    "      font-weight: regular;\n" +
                    "      font-size: medium;\n" +
                    "      margin-bottom: 50px;\n" +
                    "      color: #000000;\n" +
                    "    \"\n" +
                    "  >\n" +
                    "    Haz clic en el siguiente botón para activar tu cuenta.\n" +
                    "  </p>\n" +
                    "  <a\n" +
                    "    style=\"\n" +
                    "      background-color: #a61f69;\n" +
                    "      height: 60px;\n" +
                    "      width: 200px;\n" +
                    "      color: #fff;\n" +
                    "      border: none;\n" +
                    "      border-radius: 15px;\n" +
                    "      font-size: 14px;\n" +
                    "      font-family: Rubik, sans-serif;\n" +
                    "      font-weight: 700;\n" +
                    "      text-transform: uppercase;\n" +
                    "      text-decoration: none;\n" +
                    "      margin: auto;\n" +
                    "      padding: 20px;\n" +
                    "      text-align: center;\n" +
                    "    \"\n" +
                    "    href=\"http://frontend-c3-equipo3.s3-website.us-east-2.amazonaws.com/#/confirmRegister/"+verificationCode+"\"\n" +
                    "    >ACTIVAR CUENTA</a\n" +
                    "  >\n" +
                    "</div>\n";
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlMsg, true);
            javaMailSender.send(mimeMessage);
    }

    public void sendEmailSuccess(String to, String subject, User user) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String htmlMsg = "<div\n" +
                "  style=\"\n" +
                "    width: 400px;\n" +
                "    height: 600px;\n" +
                "    border-radius: 30px;\n" +
                "    background-color: #ffffff;\n" +
                "    padding: 40px;\n" +
                "    margin: auto;\n" +
                "    text-align: center;\n" +
                "  \"\n" +
                ">\n" +
                "\n" +
                "  <h1\n" +
                "    style=\"\n" +
                "      font-family: 'Tahoma', 'Verdana';\n" +
                "      font-weight: bold;\n" +
                "      font-size: x-large;\n" +
                "      color: #a61f69;\n" +
                "    \"\n" +
                "  >\n" +
                "    Confirmación de Registro\n" +
                "  </h1>\n" +
                "\n" +
                "  <img\n" +
                "  style=\"width: 300px\"\n" +
                "  src=\"https://comision3-equipo3.s3.us-east-2.amazonaws.com/img/logo1.png\"\n" +
                "/>\n" +
                "  <p\n" +
                "    style=\"\n" +
                "      font-family: 'Tahoma', 'Verdana';\n" +
                "      font-weight: regular;\n" +
                "      font-size: large;\n" +
                "      color: #000000;\n" +
                "      margin: 20px;\n" +
                "    \"\n" +
                "  >\n" +
                "    ¡Bienvenido a Digital Booking!\n" +
                "  </p>\n" +
                "  <p\n" +
                "  style=\"\n" +
                "    font-family: 'Tahoma', 'Verdana';\n" +
                "    font-weight: regular;\n" +
                "    font-size: medium;\n" +
                "    margin-bottom: 50px;\n" +
                "    color: #000000;\n" +
                "  \"\n" +
                ">\n" +
                "Hola\n" + user.getName() +
                "</p>\n" +
                "<p\n" +
                "style=\"\n" +
                "  font-family: 'Tahoma', 'Verdana';\n" +
                "  font-weight: regular;\n" +
                "  font-size: medium;\n" +
                "  margin-bottom: 50px;\n" +
                "  color: #000000;\n" +
                "\"\n" +
                ">\n" +
                "Tu cuenta ha sido activada con el email:\n" + user.getEmail() +
                "</p>\n" +
                "  <p\n" +
                "    style=\"\n" +
                "      font-family: 'Tahoma', 'Verdana';\n" +
                "      font-weight: regular;\n" +
                "      font-size: medium;\n" +
                "      margin-bottom: 50px;\n" +
                "      color: #000000;\n" +
                "    \"\n" +
                "  >\n" +
                "  Empieza a disfrutar de la experiencia y encuentra tu espacio de trabajo ideal.\n" +
                "  </p>\n" +
                "  <a\n" +
                "    style=\"\n" +
                "      background-color: #a61f69;\n" +
                "      height: 60px;\n" +
                "      width: 200px;\n" +
                "      color: #fff;\n" +
                "      border: none;\n" +
                "      border-radius: 15px;\n" +
                "      font-size: 14px;\n" +
                "      font-family: Rubik, sans-serif;\n" +
                "      font-weight: 700;\n" +
                "      text-transform: uppercase;\n" +
                "      text-decoration: none;\n" +
                "      margin: auto;\n" +
                "      padding: 20px;\n" +
                "      text-align: center;\n" +
                "    \"\n" +
                "    href=\"http://frontend-c3-equipo3.s3-website.us-east-2.amazonaws.com/#/login\"\n" +
                "    >COMENZAR</a\n" +
                "  >\n" +
                "</div>";
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlMsg, true);
        javaMailSender.send(mimeMessage);
    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }



}
