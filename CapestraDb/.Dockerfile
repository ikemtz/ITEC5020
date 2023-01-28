FROM bitnami/mysql:5.7
ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=orderentrysystem
EXPOSE 3306

#  The line below is handy if you have your tables and data scripted out
#  The container will run your scripts on the first run
#  Strongly recommend create a backup DUMP file from within MySQL WorkBench
 
COPY *.sql /docker-entrypoint-initdb.d