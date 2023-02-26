### Setup
#### Spring Datasource, Email credentials, JWT secret
1. Create database with your name
2. Add the environment variables for
   ```
        url: ${SPRING_DATASOURCE_JDBC_URL}
        username: ${SPRING_DATASOURCE_USERNAME}
        password: ${SPRING_DATASOURCE_PASSWORD}
   ```
3. The datasource URL typically looks like this: ``jdbc:postgresql://localhost:5432/my-database``
4. Add the environment variables for email account to send mail messages
   ```
        email: ${EMAIL}
        password: ${EMAIL_PASSWORD}
   ```
5. For gmail account you need to use app-specific password. To understand better please visit this link [https://support.google.com/mail/answer/185833?hl=en](https://support.google.com/mail/answer/185833?hl=en)
6. Add environment variable with your `secret` (it can be any word) to make jwt works
   ```
      secret: ${JWT_SECRET}
   ```