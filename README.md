## post-to-file

###Инструкция по применению 


####Запуск через sbt 
   1. Для запуска необходимо установить sbt 1.1.1+
   2. Из корня проета выполнить команду sbt run
   3. После того как проект соберется и запустится(в терминал появились сообщения:
   Send post message to http://localhost:3030/store...
   Push enter for stop service) можно отправлять запросы.
   4. Пример:
   ```
   curl  -d "the first message" -X POST http://localhost:3030/store
   ```
   - в корне проекта создасться файл outputFIle.txt с сообщением the first message, 
   ```
   curl  -d "второе сообщение" -X POST http://localhost:3030/store 
   ```
   - в файл добавляется запись 'второе сообщение'
  
   3. Для остановки сервиса нажать enter
   
   
####Запуск в Docker контейнере
   1. В reference.conf выставляем host = "0.0.0.0"
   https://github.com/anastasija513/post-to-file-test/blob/master/src/main/resources/reference.conf#L3
   2. Из корня проекта запускаем build:      
      ```
      docker build -t post-to-file:v1
      ```
   3. После сборки запускаем контейнер:
      ```
      docker run -p 3030:3030 -t -i post-to-file
      ```
   4. Отправляем с хост машины запросы
   5. Результат находим в вольюме
