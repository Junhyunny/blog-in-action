package blog.in.action.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import blog.in.action.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String>{

}
