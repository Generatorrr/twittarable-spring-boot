#!/usr/bin/env bash

mvn clean package

echo 'Copying files...'

scp -i ~/.ssh/your_ssh_key \
    target/twittarable-spring-boot-1.0-SNAPSHOT.jar \
    server_user_name@server_host:/path_to_locate/

echo 'Files copied successfully'

echo 'Restarting server...'

ssh -i ~/.ssh/your_ssh_key server_user_name@server_host << EOF

pgrep java | xargs kill -9
nohup java -jar twittarable-spring-boot-1.0-SNAPSHOT.jar > log.txt &

EOF

echo 'All done!'