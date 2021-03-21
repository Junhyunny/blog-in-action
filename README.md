
## action-in-blog
- 블로그의 테스트, 예제 코드를 저장하는 원격 저장소입니다.
- 각 디렉토리 별로 back-end / front-end 서비스입니다.
- 서비스 별로 테스트 시나리오에 맞게 동작하도록 코드가 작성되어 있습니다. 
- 원격 저장소의 commit 시점 별로 연관된 포스트들이 존재하며 아래 RELATED POSTS 목록을 참고하시면 됩니다.

### eclipse back-end service 프로젝트 열기
- File > Open Project From File System에서 action-in-blog 폴더를 선택
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

[post-1-link]: https://junhyunny.github.io/spring-boot/spring-security/spring-security-example/