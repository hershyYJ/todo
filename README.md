# WhatTodo
Todo 리스트 기록 서비스
</br>
</br>

## 개발 기간
* 2024.05.023 ~ 2024.06.16
</br>
</br>

## 멤버 구성
* 선우예림
* 정유진
</br>
</br>

## 개발 환경
### Frontend
* **React 17.0.2**
  </br>
### Backend
* **JAVA 17**
* **JDK 19**
* **Framework** : Springboot(3.x)
* **Database** : MySQL
* **ORM** : JPA
</br>
</br>
 
## 역할 분담
* 선우예림 : 회원가입, 로그인, 마감 기한 알림
* 정유진 : Todo 생성, 수정, 조회, 삭제, 필터링(우선 순위, 마감 기한순)
</br>
</br>

## 파일 구조
### Frontend
C:.
│   AddTodo.js
│   app-config.js
│   App.css
│   App.js
│   App.test.js
│   AppRouter.js
│   index.css
│   index.js
│   logo.svg
│   reportWebVitals.js
│   setupTests.js
│   Todo.js
│
└───service
        ApiService.js
        Login.js
        SignUp.js
</br>
### Backend
C:.
├───.gradle
│   ├───8.7
│   │   ├───checksums
│   │   ├───dependencies-accessors
│   │   ├───executionHistory
│   │   ├───expanded
│   │   ├───fileChanges
│   │   ├───fileHashes
│   │   └───vcsMetadata
│   ├───buildOutputCleanup
│   └───vcs-1
├───build
│   ├───classes
│   │   └───java
│   │       └───main
│   │           └───com
│   │               └───service
│   │                   └───todo
│   │                       ├───config
│   │                       ├───controller
│   │                       ├───dto
│   │                       ├───global
│   │                       ├───model
│   │                       ├───persistence
│   │                       ├───security
│   │                       └───service
│   ├───generated
│   │   └───sources
│   │       ├───annotationProcessor
│   │       │   └───java
│   │       │       └───main
│   │       └───headers
│   │           └───java
│   │               └───main
│   ├───resources
│   │   └───main
│   └───tmp
│       └───compileJava
│           └───compileTransaction
│               ├───backup-dir
│               └───stash-dir
├───gradle
│   └───wrapper
└───src
    ├───main
    │   ├───java
    │   │   └───com
    │   │       └───service
    │   │           └───todo
    │   │               ├───config
    │   │               ├───controller
    │   │               ├───dto
    │   │               ├───global
    │   │               ├───model
    │   │               ├───persistence
    │   │               ├───security
    │   │               └───service
    │   └───resources
    └───test
        └───java
            └───com
                └───service
                    └───todo
</br>
</br>
