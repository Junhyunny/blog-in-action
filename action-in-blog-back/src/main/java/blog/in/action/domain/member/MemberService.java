package blog.in.action.domain.member;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService implements UserDetailsService {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Member registMember(Member member) {
		String encodedPassword = passwordEncoder.encode(member.getPassword());
		member.setPassword(encodedPassword);
		return memberRepository.save(member);
	}

	public Member findById(String id) {
		Optional<Member> option = memberRepository.findById(id);
		if (!option.isPresent()) {
			return null;
		}
		return option.get();
	}

	public List<Member> findAll() {
		return memberRepository.findAll();
	}

	// 계정이 갖고있는 권한 목록을 return
	private Collection<? extends GrantedAuthority> authorities(Member member) {
		return member.getAuthorities().stream().map(authority -> new SimpleGrantedAuthority(authority)).collect(Collectors.toList());
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Member> option = memberRepository.findById(username);
		if (!option.isPresent()) {
			throw new UsernameNotFoundException(username);
		}
		// ID, PASSWORD, AUTHORITIES 반환
		Member member = option.get();
		return new User(member.getId(), member.getPassword(), authorities(member));
	}
}