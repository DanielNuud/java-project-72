run-dist:
	./build/install/app/bin/app

clean:
	./gradlew clean

install:
	./gradlew installDist

reinstall:
	./gradlew clean
	./gradlew installDist

run:
	./build/install/app/bin/app

lint:
	./gradlew checkstyleMain

test:
	./gradlew test

report:
	./gradlew jacocoTestReport

report2:
	./gradlew clean test jacocoTestReport

build:
	./gradlew clean build

.PHONY: build