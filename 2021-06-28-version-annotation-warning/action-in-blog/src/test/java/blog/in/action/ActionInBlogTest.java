package blog.in.action;

import blog.in.action.domain.ChildEntity;
import blog.in.action.domain.ParentEntity;
import blog.in.action.repository.ChildRepository;
import blog.in.action.repository.ParentRepository;
import org.hibernate.TransientPropertyValueException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(
        properties = {"spring.jpa.hibernate.ddl-auto=create-drop"}
)
public class ActionInBlogTest {

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private ChildRepository childRepository;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Test
    void zeroDefaultValueForVersionNo_throwTransientPropertyValueException() {

        var parentEntity = new ParentEntity();
        parentRepository.save(parentEntity);


        var childEntity = ChildEntity.create(null, "CREATED", parentEntity);
        childEntity.update();


        var throwable = assertThrows(InvalidDataAccessApiUsageException.class, () -> childRepository.save(childEntity));
        assertInstanceOf(TransientPropertyValueException.class, throwable.getRootCause());
        assertNull(parentEntity.getId());
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Test
    void defaultValueIsNullForVersionNo_updateStatus() {

        var parentEntity = new ParentEntity();
        parentRepository.save(parentEntity);


        var childEntity = ChildEntity.create(null, "CREATED", parentEntity);
        childEntity.update();


        var result = childRepository.save(childEntity);
        assertEquals("UPDATED", result.getState());
        assertNotNull(parentEntity.getId());
    }
}
