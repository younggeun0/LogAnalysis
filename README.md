# 로그 분석 시스템

* 제작기간 일주일 (2018.12.22~2018.12.28)

![logFile](https://github.com/younggeun0/logAnalysisApp/blob/master/img/logFile.jpg?raw=true)

* **로그 기록이 담긴 txt파일에서 원하는 정보를 얻기 위한 프로그램**
* **원하는 로그 정보**
  * 최다 사용 key의 이름, 횟수
  * 브라우저별 요청 횟수, 비율(%)
  * 서비스 성공 횟수(200), 실패(404) 횟수
  * 요청이 가장 많은 시간
  * 비정상적인 요청(403)이 발생한 횟수, 비율(%)
  * 입력 라인에 해당하는 최다 사용 키의 이름과 횟수

![ui01](https://github.com/younggeun0/logAnalysisApp/blob/master/img/UI01.jpg?raw=true))

* **기본 UI**
  * Login 후 사용 가능
    * 아이디 "admin", 비밀번호 "1234" 또는 아이디 "root", 비밀번호 "1111"인 유저만 접속 가능
  * View 버튼을 누르면 FileDialog로 log파일을 선택가능
    * 로그파일 선택 후 Result JDialog 창이 뜨며 결과를 보여줌.
  * Report 버튼을 누르면 해당 경로("C:/dev/report")에 현재 시간정보를 가진 `.dat`파일 생성
    * 단, Report 버튼은 View 버튼 클릭을 수행 후에만 클릭이 가능.


* **클래스 다이어그램**

![class diagram](https://github.com/younggeun0/logAnalysisApp/blob/master/img/classDiagram(logAnalysis).jpg?raw=true)

* **업무분장**
  * **younggeun0**
    * base code 작성, 내용 통합, 입력된 줄 로그처리 구현, 예외처리
  * **gkwl7878**
    * browser 로그처리, Report 구현, 테스팅
  * **jeongmipark94**
    * httpStatus코드 로그처리, Result JDialog 구현, 테스팅
  * **hyewon0218**
    * hour 로그처리, Login JFrame 구현, 테스팅
  * **kimkunha**
    * key 로그처리, 테스팅
