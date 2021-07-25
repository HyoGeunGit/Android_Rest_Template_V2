# Android_Rest_Template_V2

안드로이드용 RESTFUL API에 맞춘 로그인 템플릿입니다.

# Uses Library, Tech Spec
Kotlin, anko, Retrofit2, okhttp3, TedKeyBoardObserver Library, Material Design, glide

# Server API


# AUTH

* POST /auth/signin ( 유저 로그인 )

> Params 

    id : String ( ID ) 

    password : String ( 비밀번호 )

> Response

    200 : {
      "uuid": "feX3f8j0ee1hjpFy8picmqc7SoOxPdAFJG4jlgYU",
      "_id": "60fc531c47635d497029c9bf",
      "name": "test123123",
      "email": "asdasdsad@asdsad.com",
      "birth": "20210202",
      "gender": true,
      "password": "test123",
      "phone": "01011112222",
      "id": "test1232313",
      "profile": "http://3.34.140.195:5000/userman_default",
      "__v": 0
    }

    400 : {message: "Missing Params"}

    404 : {message: "Users Not Found"}


* POST /auth ( 유저 회원가입 )

> Params 

    id : String ( ID ) 
    
    gender : Boolean ( gender ) true/man, false/woman
    
    email : String ( email )

    name : String ( name ) 
    
    birth : String ( birth ) 
    
    phone : String ( phone ) 

    password : String ( 비밀번호 )

> Response

    200 : {
      "uuid": "feX3f8j0ee1hjpFy8picmqc7SoOxPdAFJG4jlgYU",
      "_id": "60fc531c47635d497029c9bf",
      "name": "test123123",
      "email": "asdasdsad@asdsad.com",
      "birth": "20210202",
      "gender": true,
      "password": "test123",
      "phone": "01011112222",
      "id": "test1232313",
      "profile": "http://3.34.140.195:5000/userman_default",
      "__v": 0
    }

    409 : {message: "ID Duplicate"}

    500 : {message: "Server Error"}


* GET /auth ( 유저 자동 로그인 )

> Headers 

    uuid : String ( uuid ) 

> Response

    200 : {
      "uuid": "feX3f8j0ee1hjpFy8picmqc7SoOxPdAFJG4jlgYU",
      "_id": "60fc531c47635d497029c9bf",
      "name": "test123123",
      "email": "asdasdsad@asdsad.com",
      "birth": "20210202",
      "gender": true,
      "password": "test123",
      "phone": "01011112222",
      "id": "test1232313",
      "profile": "http://3.34.140.195:5000/userman_default",
      "__v": 0
    }

    404 : {message: "Users Not Found"}


* PUT /auth/profile ( 유저 프로필 변경 )

> Params

    uuid : String ( uuid ) 

    image : Image File

> Response

    200 : {message: "success"}

    404 : {message: "Users Not Found"}

    500 : {message: "Server Error"}



* GET /auth/id/check/:id ( 유저 아이디 중복 체크 )

> Params

    id : String ( ID )

> Response

    200 : {message: "Not Duplicate"}

    409 : {message: "ID Duplicate"}



* PUT /auth ( 유저 정보 변경 )

> Params

    uuid : String ( uuid ) 

    name? : String ( name )
    
    password? : String ( password )
    changePassword?: String ( password )

    phone? : String ( phone )

    birth? : String ( birth )

    gender? : Boolean ( gender )

    email? : String ( email )

> Response

    200 : {message: "success"}

    401 : {message: "Password Not Match"}

    500 : {message: "Server Error"}


* DELETE /auth ( 유저 삭제 )

> Params

    uuid : String ( uuid ) 

> Response

    200 : {message: "success"}
    
    404 : {message: "Users Not Found"}
    
    500 : {message: "Server Error"}



# FEED

* POST /feed ( 피드 등록하기 )

> Params

    title : String ( Feed Title )

    content : String ( Feed Content )

    uuid : String ( user uuid )

    images : [{
      url: String, // 이미지 url
      description: String // 설명
    }]

> Response

    200 : {
      "uuid": "65whDD2cPcypqh57FdpjwytOm56U7U8Bv2uzOPI6wgmo6KqMImKRXYTL",
      "created_date": "2021-07-24T21:18:18.252Z",
      "last_edit_date": "2021-07-24T21:18:18.252Z",
      "share_type": "ALL",
      "_id": "60fc839bba8cf52df4f7a0d3",
      "title": "test feed 1",
      "content": "테스트로 작성한 피드입니다 123123123",
      "user": "feX3f8j0ee1hjpFy8picmqc7SoOxPdAFJG4jlgYU",
      "images": [
          {
              "_id": "60fc839bba8cf52df4f7a0d4",
              "url": "http://3.34.140.195:5000/feed/Iyr0sftlJ7Eoz11smj6qTub1l.jpg",
              "description": "이미지 테스트"
          },
          {
              "_id": "60fc839bba8cf52df4f7a0d5",
              "url": "http://3.34.140.195:5000/feed/DIupNgk5FtZKM7jygJk3oLbks.png",
              "description": "이미지 테스트"
          }
      ],
      "comment": [],
      "__v": 0
    }

    500: {message: "Server Error"}


* PUT /feed ( 피드 수정하기 )

> Params
    
    feedUUID : String ( feed UUID )

    title? : String ( Feed Title )

    content? : String ( Feed Content )

    images? : [{
      url: String, // 이미지 url
      description: String // 설명
    }]

    share_type? : String

> Response

    200 : {
      "uuid": "65whDD2cPcypqh57FdpjwytOm56U7U8Bv2uzOPI6wgmo6KqMImKRXYTL",
      "created_date": "2021-07-24T21:18:18.252Z",
      "last_edit_date": "2021-07-24T21:23:27.653Z",
      "share_type": "ALL",
      "_id": "60fc839bba8cf52df4f7a0d3",
      "title": "test feed 2",
      "content": "테스트로 작성한 피드입니다 32123123",
      "user": "feX3f8j0ee1hjpFy8picmqc7SoOxPdAFJG4jlgYU",
      "images": [
          {
              "_id": "60fc84cf83a4be156c965cb8",
              "url": "http://3.34.140.195:5000/feed/Iyr0sftlJ7Eoz11smj6qTub1l.jpg",
              "description": "이미지 테스트1122"
          },
          {
              "_id": "60fc84cf83a4be156c965cb9",
              "url": "http://3.34.140.195:5000/feed/DIupNgk5FtZKM7jygJk3oLbks.png",
              "description": "이미지 테스트2211"
          }
      ],
      "comment": [],
      "__v": 1
    }

    500: {message: "Server Error"}

* POST /get/image ( 피드 사진 -> url )

> Params

    images: Array ( Image File Array )

> Response

    200 : [
      "http://3.34.140.195:5000/feed/Iyr0sftlJ7Eoz11smj6qTub1l.jpg",
      "http://3.34.140.195:5000/feed/DIupNgk5FtZKM7jygJk3oLbks.png"
    ]


* GET /feed/list ( 피드 리스트 불러오기 )

> Query

    page : Number ( page 1 ~ N )

> Response

    200 : [
      {
          "uuid": "65whDD2cPcypqh57FdpjwytOm56U7U8Bv2uzOPI6wgmo6KqMImKRXYTL",
          "created_date": "2021-07-24T21:18:18.252Z",
          "last_edit_date": "2021-07-24T21:23:27.653Z",
          "share_type": "ALL",
          "_id": "60fc839bba8cf52df4f7a0d3",
          "title": "test feed 2",
          "content": "테스트로 작성한 피드입니다 32123123",
          "user": "feX3f8j0ee1hjpFy8picmqc7SoOxPdAFJG4jlgYU",
          "images": [
              {
                  "_id": "60fc84cf83a4be156c965cb8",
                  "url": "http://3.34.140.195:5000/feed/Iyr0sftlJ7Eoz11smj6qTub1l.jpg",
                  "description": "이미지 테스트1122"
              },
              {
                  "_id": "60fc84cf83a4be156c965cb9",
                  "url": "http://3.34.140.195:5000/feed/DIupNgk5FtZKM7jygJk3oLbks.png",
                  "description": "이미지 테스트2211"
              }
          ],
          "comment": [],
          "__v": 1
      }
    ]


* GET /feed ( 피드 상세 불러오기 )

> Query

    feedUUID : String ( feedUUID )

> Response

    200 : {
      uuid: String, ( Feed UUID ) 

      created_date: Date, // 생성일

      last_edit_date: Date, // 마지막 수정일

      title: String, // Feed Title

      content: String, // Feed 내용
      
      user: String, // writer user uuid
      
      share_type: String

      images: [{
        url: String,
        description: String
      }],
    }

    404: {message: "Feed Not Found"}



* DELETE /feed/comment ( 피드 삭제 )

> Params

    feedUUID : String ( feedUUID )

    uuid : String ( uuid )

> Response

    200 : {message: "success"}
    
    404 : {message: "Feed Not Found"}
    
    500 : {message: "Server Error"}



# COMMENT

* POST /feed/get/comment/image ( 댓글 이미지 url )

> Params

    image: Image File

> Response

    200 : "http://3.34.140.195:5000/feed/c1EgVUgBy9xEGvovyUjiA8ddG.jpg"


* PUT /feed/comment ( 댓글 등록 )

> Params

    feedUUID : String ( feedUUID )

    uuid : String ( user uuid )

    comment : String ( comment ) // 댓글 내용

    image? : String ( Image Url )

> Response

    200 : {message: "success"}
    
    404 : {message: "Feed Not Found"}
    
    500 : {message: "Server Error"}



* DELETE /feed/comment ( 댓글 삭제 )

> Params

    feedUUID : String ( feedUUID )

    commentUUID : String ( commentUUID )

> Response

    200 : {message: "success"}
    
    404 : {message: "Feed Not Found"}
    
    500 : {message: "Server Error"}


* GET /feed/comment ( 댓글 불러오기 )

> Params

    feedUUID : String ( feedUUID )

> Response

    200 : [
      {
          "writer": "feX3f8j0ee1hjpFy8picmqc7SoOxPdAFJG4jlgYU",
          "name": "test123123",
          "profile": "http://3.34.140.195:5000/userman_default",
          "created_date": "2021-07-24T21:38:06.673Z",
          "uuid": "CvFUis8nDr4SxGxN7Mmt6ZlHdXVB1a3e1XdN2LVaimTPMVMe"
      }
    ]
    
    404 : {message: "Feed Not Found"}


