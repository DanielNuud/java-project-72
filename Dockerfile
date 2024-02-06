FROM amazoncorretto:20.0.2

WORKDIR /app

COPY /app .

RUN chmod +x ./gradlew

RUN ./gradlew installDist

CMD ./build/install/app/bin/app