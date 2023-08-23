# Use a base image with Java and Nginx installed
FROM openjdk:11-jre-slim

# Install Nginx
RUN apt-get update && apt-get install -y nginx

# Copy Nginx configuration
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copy your Java application JAR/WAR file
COPY target/gitprac.jar /app.jar

# Expose ports for Nginx and Java application
EXPOSE 80
EXPOSE 8080

# Start Nginx and Java application
CMD service nginx start && java -jar /app.jar
