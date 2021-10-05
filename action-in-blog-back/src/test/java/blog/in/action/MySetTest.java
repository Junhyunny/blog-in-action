package blog.in.action;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 */
public class MySetTest {

    MySet mySet;

    @BeforeEach
    public void beforeEach() {
        mySet = new MySet();
    }

    @Test
    void test_size_returnZero_whenEmpty() {
        assertThat(mySet.size(), equalTo(0));
    }

    @Test
    void test_size_returnOne_whenAddItem() {
        mySet.add(222);
        assertThat(mySet.size(), equalTo(1));
    }

    @Test
    void test_contains_returnTrue_whenGiveSameItem() {
        mySet.add(222);
        assertThat(mySet.contains(222), equalTo(true));
    }

    @Test
    void test_returnOne_whenGivenDuplicatedItems() {
        mySet.add(222);
        mySet.add(222);
        assertThat(mySet.size(), equalTo(1));
    }

    @Test
    void test_contains_returnFalse_whenGiveDifferentItem() {
        mySet.add(222);
        assertThat(mySet.contains(123), equalTo(false));
    }

    @Test
    void test_size_returnTwo_WhenAddTwoItems() {
        mySet.add(123);
        mySet.add(321);
    }

    @Test
    public void test_decrease_returnZero_whenAddDeleteSameItem() {
        mySet.add(123);
        mySet.remove(123);
        assertThat(mySet.size(), equalTo(0));
    }

    @Test
    public void test_decrease_returnZero_whenAddDeleteDifferentItem() {
        mySet.add(123);
        mySet.remove(321);
        assertThat(mySet.size(), equalTo(1));
    }

    @Test
    public void test_getAtIndex_whenAtIndex() {
        mySet.add(123);
        assertThat(mySet.get(0), equalTo(123));
    }

    @Test
    public void test_getAtIndex_whenAtBiggerIndex() {
        mySet.add(123);
        mySet.add(123);
    }
}
