<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*

- [bieber-tweets](#bieber-tweets)
  - [Build and Execute](#build-and-execute)
  - [Output](#output)
  - [Test](#test)
    - [Test coverage](#test-coverage)
    - [Proof in Test Codes](#proof-in-test-codes)
  - [Folder Structure](#folder-structure)
  - [Sample success log](#sample-success-log)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# bieber-tweets
The application is created for the requirements in [TASK_DESCRIPTION.md](./TASK_DESCRIPTION.md)

The application tracks tweets on the text "bieber" by using Twitter API. The streaming process is depends on streaming duration and streamed message count. Which of them is exceed, then the streaming process is stopped.

The application is a `spring-boot` application. It uses `twitter4j` for Twitter API processes.

## Build and Execute
The command triggers compiling code, executing unit tests, creating docker image and run a docker container.
```
./docker/run.sh
 
```

## Output
Output is a file that is located at ./output/result.json. The data that is JSON-formatted message array, is in the file.
Just give the file, It is easy to parse and process by a machine.

To see the results run following commands
```
cat ./output/result.json
```

## Test

### Test coverage
`%96 154/160`

### Proof in Test Codes
+ Filter messages that track on "bieber"
> `find proof at TwitterStreamerTest.testStartStreaming()`

+ Retrieve the incoming messages for 30 seconds or up to 100 messages, whichever comes first
> `find proof at TwitterStreamerTest.testStartStreamingMaxStreamingDuration() and TwitterStreamerTest.testStartStreamingMaxCount()`

+ Your application should return the messages grouped by user (users sorted chronologically, ascending)
> `find proof at MessageRepositoryTest.testAddGroupByAuthor()`

+ The messages per user should also be sorted chronologically, ascending
> `find proof at MessageRepositoryTest.testAddGroupByAuthor()`

+ All the above infomation is provided in either SDTOUT or a log file
> `All received and sorted messages stored in ./output/result.json file as JSON`

## Folder Structure

```
├── docker
│   ├── Dockerfile
│   └── run.sh
├── output
├── pom.xml
├── README.md
├── src
│   ├── main
│   │   ├── java
│   │   │   └── org
│   │   │       └── interview
│   │   │           ├── AppConstant.java
│   │   │           ├── Application.java
│   │   │           ├── model
│   │   │           │   ├── Author.java
│   │   │           │   └── Message.java
│   │   │           ├── repository
│   │   │           │   └── MessageRepository.java
│   │   │           └── twitter
│   │   │               ├── TweetStatusTask.java
│   │   │               ├── TwitterService.java
│   │   │               └── TwitterStreamer.java
│   │   └── resources
│   │       └── application.properties
│   └── test
│       ├── java
│       │   └── org
│       │       └── interview
│       │           ├── AppConstantTest.java
│       │           ├── ApplicationTest.java
│       │           ├── model
│       │           │   └── ModelTest.java
│       │           ├── repository
│       │           │   └── MessageRepositoryTest.java
│       │           ├── TestConstants.java
│       │           ├── TestUtils.java
│       │           └── twitter
│       │               ├── TwitterServiceTest.java
│       │               ├── TwitterStatusTaskTest.java
│       │               └── TwitterStreamerTest.java
│       └── resources
```

## Sample success log
```
2018-07-09 15:33:29.087  INFO 1 --- [           main] org.interview.Application                : Started Application in 0.95 seconds (JVM running for 3.909)
2018-07-09 15:33:30.036  INFO 1 --- [           main] org.interview.twitter.TwitterService     : 
Go to the following link in your browser:
https://api.twitter.com/oauth/authorize?oauth_token=lwY4iwAAAAAAt7ElAAABZH-tILY

2018-07-09 15:33:30.036  INFO 1 --- [           main] org.interview.twitter.TwitterService     : 
Please enter the retrieved PIN:
2493830
2018-07-09 15:33:56.405  INFO 1 --- [           main] org.interview.twitter.TwitterService     : Received Token key: 1191148813-YHpwYbzAayYfeoZQ3jiYxu0kuwZjsYwIl2DaeAv secret: 2nJnoas8JGpTCGCav428813t9M5hodajsPeuU6xPR5fcq
2018-07-09 15:33:56.442  INFO 1 --- [][initializing]] twitter4j.TwitterStreamImpl              : Establishing connection.
2018-07-09 15:33:57.895  INFO 1 --- [ing connection]] twitter4j.TwitterStreamImpl              : Connection established.
2018-07-09 15:33:57.896  INFO 1 --- [ing connection]] twitter4j.TwitterStreamImpl              : Receiving status stream.
2018-07-09 15:33:58.106  INFO 1 --- [c Dispatcher[0]] org.interview.twitter.TwitterStreamer    : StatusListener onStatus
2018-07-09 15:33:58.109  INFO 1 --- [c Dispatcher[0]] org.interview.twitter.TwitterStreamer    : StatusListener onStatus
2018-07-09 15:33:58.110  INFO 1 --- [c Dispatcher[0]] org.interview.twitter.TwitterStreamer    : StatusListener onStatus
.
.
.
.

2018-07-09 15:34:19.903  INFO 1 --- [c Dispatcher[0]] org.interview.twitter.TwitterStreamer    : StatusListener onStatus
2018-07-09 15:34:19.906  INFO 1 --- [c Dispatcher[0]] org.interview.twitter.TwitterStreamer    : StatusListener onStatus
2018-07-09 15:34:19.907  INFO 1 --- [c Dispatcher[0]] org.interview.twitter.TwitterStreamer    : StatusListener onStatus
2018-07-09 15:34:19.908  INFO 1 --- [           main] org.interview.twitter.TwitterStreamer    : Wait for destroying twitterStream
2018-07-09 15:34:20.062  INFO 1 --- [           main] org.interview.twitter.TwitterStreamer    : twitterStream destroyed:true elapsed time: 23478, tweet count: 100
2018-07-09 15:34:20.129  INFO 1 --- [           main] org.interview.twitter.TwitterStreamer    : FINISHED! elapsedTime: 23478, received message count:100. Run the following command to see the received messages
2018-07-09 15:34:20.129  INFO 1 --- [           main] org.interview.twitter.TwitterStreamer    : 
Run: cat ./output/result.json

2018-07-09 15:34:20.140  INFO 1 --- [       Thread-2] s.c.a.AnnotationConfigApplicationContext : Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@277d4bef: startup date [Mon Jul 09 15:33:28 UTC 2018]; root of context hierarchy
2018-07-09 15:34:20.150  INFO 1 --- [       Thread-2] o.s.j.e.a.AnnotationMBeanExporter        : Unregistering JMX-exposed beans on shutdown

```