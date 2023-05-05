package blog.in.action.transcation;

import blog.in.action.domain.Child;
import blog.in.action.domain.Parent;
import blog.in.action.repository.ChildRepository;
import blog.in.action.repository.ParentRepository;
import blog.in.action.service.ChildService;
import blog.in.action.service.ParentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import(value = {ParentService.class, ChildService.class})
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class RequiredTests {

    @Autowired
    ParentService parentService;
    @Autowired
    ParentRepository parentRepository;
    @Autowired
    ChildService childService;
    @Autowired
    ChildRepository childRepository;

    @Test
    void all_transaction_rollback_when_parent_and_child_are_required() {

        final String id = "required-id";


        assertThrows(UnexpectedRollbackException.class, () -> parentService.createRequiredAndChildRequired(id));


        Optional<Parent> parentResult = parentRepository.findById(id);
        Optional<Child> childResult = childRepository.findById(id);
        assertThat(parentResult.isPresent(), equalTo(false));
        assertThat(childResult.isPresent(), equalTo(false));
    }

    @Test
    void rollback_child_transaction_when_parent_without_transactional_and_child_is_required() {

        final String id = "required-id-without-parent-transaction";


        parentService.createWithoutTransactionAndChildRequired(id);


        Optional<Parent> parentResult = parentRepository.findById(id);
        Optional<Child> childResult = childRepository.findById(id);
        assertThat(parentResult.isPresent(), equalTo(true));
        assertThat(childResult.isPresent(), equalTo(false));
    }
}
