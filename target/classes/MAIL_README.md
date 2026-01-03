Mail configuration

This project supports sending mail via a configured SMTP server or a local developer SMTP sink (MailHog/MailDev).

Recommended approaches:

1) Local development (no external SMTP credentials)
- Start MailHog (Docker):
  docker run -p 1025:1025 -p 8025:8025 mailhog/mailhog
- Run your app with the `local` profile:
  mvn spring-boot:run -Dspring-boot.run.profiles=local
- Open MailHog web UI at http://localhost:8025 to view messages.

2) Real SMTP (Gmail example)
- Create an App Password for your Google account or use another SMTP provider.
- Export credentials to environment variables (recommended):
  PowerShell example:
    $env:SPRING_MAIL_USERNAME = "your.email@gmail.com"; $env:SPRING_MAIL_PASSWORD = "your_app_password"
- Run with the gmail profile or default profile which reads environment variables:
  mvn spring-boot:run -Dspring-boot.run.profiles=gmail

Security notes:
- Never hard-code credentials in `application.properties` or commit them to source control.
- Use environment variables, secrets manager, or Kubernetes secrets in production.

Provider examples
-----------------

Office365 / Microsoft 365
- Use smtp.office365.com on port 587 with STARTTLS. Ensure your tenant allows SMTP client submission or use an app-specific credential.
- PowerShell env example:
  $env:SPRING_MAIL_USERNAME = "user@yourdomain.com"; $env:SPRING_MAIL_PASSWORD = "your_password_or_app_secret"
  mvn spring-boot:run -Dspring-boot.run.profiles=office365

SendGrid (SMTP)
- Use smtp.sendgrid.net with username `apikey` and password equal to your SendGrid API key.
- PowerShell env example:
  $env:SPRING_MAIL_USERNAME = "apikey"; $env:SENDGRID_API_KEY = "SG.xxxxx"
  mvn spring-boot:run -Dspring-boot.run.profiles=sendgrid

Amazon SES (SMTP)
- Use the SMTP endpoint for your AWS region (e.g. email-smtp.us-east-1.amazonaws.com). Generate SMTP credentials from the SES console or create IAM credentials for SMTP.
- PowerShell env example:
  $env:SES_SMTP_HOST = "email-smtp.us-east-1.amazonaws.com"; $env:SES_SMTP_PORT = "587"; $env:SES_SMTP_USERNAME = "SMTP_USERNAME"; $env:SES_SMTP_PASSWORD = "SMTP_PASSWORD"
  mvn spring-boot:run -Dspring-boot.run.profiles=ses

