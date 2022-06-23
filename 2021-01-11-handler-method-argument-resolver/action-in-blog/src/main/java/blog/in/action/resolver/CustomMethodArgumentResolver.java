package blog.in.action.resolver;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.fasterxml.jackson.databind.ObjectMapper;

import blog.in.action.annotation.TokenMember;
import blog.in.action.entity.Member;

@Component
public class CustomMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		// parameter의 annotation이 @TokenMember 이면서 parameter 타입이 Member 클래스인 경우에만 해당 Resolver의 resolveArgument 메소드가 동작
		return parameter.getParameterAnnotation(TokenMember.class) != null && parameter.getParameterType().equals(Member.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		// Header에서 Authorization 정보 획득
		String authorizationHeader = webRequest.getHeader("Authorization");
		if (authorizationHeader == null) {
			return null;
		}

		// Barear 를 제외한 토큰을 추출
		String jwtToken = authorizationHeader.substring(7);

		// JWT 토큰 decoder 생성
		Jwt decodedToken = JwtHelper.decode(jwtToken);

		@SuppressWarnings("unchecked")
		Map<String, String> claims = objectMapper.readValue(decodedToken.getClaims(), Map.class);

		String memberId = String.valueOf(claims.get("memberId"));

		Member member = new Member();
		member.setId(memberId);
		return member;
	}

}
