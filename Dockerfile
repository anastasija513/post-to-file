FROM openjdk:8u151

ENV SBT_VERSION 1.1.1

RUN touch /usr/lib/jvm/java-8-openjdk-amd64/release

RUN \
  curl -L -o sbt-$SBT_VERSION.deb https://dl.bintray.com/sbt/debian/sbt-$SBT_VERSION.deb && \
  dpkg -i sbt-$SBT_VERSION.deb && \
  rm sbt-$SBT_VERSION.deb && \
  apt-get update && \
  apt-get install sbt && \
  sbt sbtVersion

WORKDIR /Main
ADD . /Main

VOLUME ["/files"]

EXPOSE 3030

CMD  sbt run