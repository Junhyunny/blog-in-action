package blog.in.action;

import blog.in.action.hello.MemberRepository;
import blog.in.action.world.FriendRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MultiSchemaTests {

    @Autowired
    FriendRepository friendRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void countFromFriendTable() {
        assertThat(friendRepository.count(), greaterThanOrEqualTo(0L));
    }

    @Test
    void countFromMemberTable() {
        assertThat(memberRepository.count(), greaterThanOrEqualTo(0L));
    }
}
