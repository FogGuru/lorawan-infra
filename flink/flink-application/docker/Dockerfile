FROM fogguru/flink

COPY flink-application-1.0-SNAPSHOT.jar $FLINK_HOME/bin
COPY docker-entrypoint.sh /
COPY log4j-console.properties $FLINK_HOME/conf

RUN ["chmod", "+x", "/docker-entrypoint.sh"]

EXPOSE 8081 6123 9250-9260

ENTRYPOINT ["/docker-entrypoint.sh"]
CMD ["--help"]
