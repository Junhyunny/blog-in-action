#!/bin/sh

sudo yum -y upgrade
sudo yum install -y httpd

sudo systemctl start httpd
sudo systemctl enable httpd

sudo echo "<h1>Welcome to Apache</h1>" > index.html
sudo mv index.html /var/www/html
