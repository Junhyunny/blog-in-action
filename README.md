
## blog-in-action
- 블로그의 테스트, 예제 코드를 저장하는 원격 저장소입니다.
- 각 디렉토리 별로 back-end / front-end 서비스입니다.
- 서비스 별로 테스트 시나리오에 맞게 동작하도록 코드가 작성되어 있습니다. 
- 원격 저장소의 commit 시점 별로 연관된 포스트들이 존재하며 아래 RELATED POSTS 목록을 참고하시면 됩니다.

### eclipse back-end service 프로젝트 열기
- File > Open Project From File System에서 blog-in-action 폴더를 선택
<p align="left"><img src="/images/action-in-blog-1.JPG"></p>

- 하위 서비스들을 모두 선택 후 import
<p align="left"><img src="/images/action-in-blog-2.JPG"></p>

### vscode front-end service 프로젝트 열기
- action-in-blog-front 폴더를 선택
<p align="left"><img src="/images/action-in-blog-3.JPG"></p>

- `npm install` command 실행 
<p align="left"><img src="/images/action-in-blog-4.JPG"></p>

- `npm run serve` command 실행
<p align="left"><img src="/images/action-in-blog-5.JPG"></p>

### 구성 서비스
#### action-in-blog-back
Spring-Boot 프레임워크를 사용한 back-end service 입니다.

#### action-in-blog-front
Vue.js 프레임워크를 사용한 front-end service 입니다.

### RELATED POSTS
- [Spring Security 기반 JWT 인증 방식 예제][post-1-link]
- [Token Enhancer][post-2-link]
- [HandlerMethodArgumentResolver 인터페이스][post-3-link]
- [CORS(Cross Origin Resource Sharing) 서버 구현][post-4-link]
- [MultipartFile 활용 대용량 파일 업로드 예제][post-5-link]
- [JPA Persistence Context][post-6-link]
- [영속성 컨텍스트(Persistence Context) 사용시 이점][post-7-link]
- [JPA Flush][post-8-link]
- [JPA Clear][post-9-link]
- [JPA Optimistic Lock 구현][post-10-link]
- [JPA Pessimistic Lock 구현][post-11-link]
- [경쟁 상태(Race Condition)][post-12-link]
- [Spring Cloud Openfeign][post-13-link]
- [Openfeign 런타임(runtime) 시 URI 변경][post-14-link]
- [생성자 주입(Constructor Injection) 방식을 권장하는 이유][post-15-link]
- [MVC(Model, View, Controller) Pattern][post-16-link]

[post-1-link]: https://junhyunny.github.io/spring-boot/spring-security/spring-security-example/
[post-2-link]: https://junhyunny.github.io/spring-boot/spring-security/token-enhancer/
[post-3-link]: https://junhyunny.github.io/spring-boot/handler-method-argument-resolver/
[post-4-link]: https://junhyunny.github.io/spring-boot/vue.js/cors-example/
[post-5-link]: https://junhyunny.github.io/spring-boot/vue.js/multipartfile/
[post-6-link]: https://junhyunny.github.io/spring-boot/jpa/junit/jpa-persistence-context/
[post-7-link]: https://junhyunny.github.io/spring-boot/jpa/junit/persistence-context-advantages/\
[post-8-link]: https://junhyunny.github.io/spring-boot/jpa/junit/jpa-flush/
[post-9-link]: https://junhyunny.github.io/spring-boot/jpa/junit/jpa-clear/
[post-10-link]: https://junhyunny.github.io/spring-boot/jpa/junit/jpa-optimistic-lock/
[post-11-link]: https://junhyunny.github.io/spring-boot/jpa/junit/jpa-pessimitic-lock/
[post-12-link]: https://junhyunny.github.io/information/operating-system/junit/race-condition/
[post-13-link]: https://junhyunny.github.io/spring-boot/spring-cloud/spring-cloud-openfeign/
[post-14-link]: https://junhyunny.github.io/spring-boot/spring-cloud/junit/dynamic-uri-using-openfeign/
[post-15-link]: https://junhyunny.github.io/information/spring-boot/junit/reson-of-recommendation-to-use-constructor-injection/
[post-16-link]: https://junhyunny.github.io/information/design-pattern/mvc-pattern/ 
