## post-to-file

### Инструкция по запуску


#### 1) Запуск через sbt 
   1. Установить sbt 1.1.1+
   2. В терминале из корня проекта выполнить команду `sbt run`
   3. После сборки и запуска проекта(в терминале видим сообщения:
   Send POST request to http://localhost:3030/store...
   Push enter for stop service) отправляем запросы.
   4. Пример:
   ```
   curl -d "I'm the first message" -X POST http://localhost:3030/store
   ```
   - в директории /tmp создастся файл outputFIle.txt с сообщением 'I'm the first message', 
   ```
   curl -d "я второе сообщение" -X POST http://localhost:3030/store 
   ```
   - в файл добавляется запись 'я второе сообщение'
  
   5. Для остановки сервиса нажать enter
   
   
#### 2)Запуск в Docker контейнере
   1. Установить Docker
   1. В reference.conf прописать host = "0.0.0.0" вместо
   https://github.com/anastasija513/post-to-file-test/blob/master/src/main/resources/reference.conf#L3
   2. Из корня проекта запускаем build:      
      ```
      docker build -t post-to-file:v1 .
      ```
   3. После сборки запускаем контейнер:
      ```
      docker run -p 3030:3030 -t -i post-to-file:v1
      ```
   4. Отправляем с хост машины запросы(аналогично пукнту 1) )
   5. Файл пишется в volume:
   
   Пример:
   
   На хосте файл лежит в директории
    /var/lib/docker/vfs/dir/e761f8e336fa571488d67d1a5233c4be99312d0c899a03853d422231838af1dd
   ```
   docker ps
   
   CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS              PORTS                    NAMES
   92b8b7ae7679        post-to-file:v1     "sh -c 'sbt run'"   3 minutes ago       Up 3 minutes        0.0.0.0:3030->3030/tcp   silly_goldstine
   
  ```
  ```
   docker inspect 92b8b7ae7679
   .............
   "Volumes": {
           "/tmp": "/var/lib/docker/vfs/dir/e761f8e336fa571488d67d1a5233c4be99312d0c899a03853d422231838af1dd"
       },
   .............    
   ```
  
   В контейнере соответственно:
   ```
   docker exec -t -i 92b8b7ae7679 bash
   root@92b8b7ae7679:/Main# cd /tmp
   root@92b8b7ae7679:/tmp# cat outputFile.txt 
   the first message
   второе сообщение
   ```
   
   
   
   
   
   
   
