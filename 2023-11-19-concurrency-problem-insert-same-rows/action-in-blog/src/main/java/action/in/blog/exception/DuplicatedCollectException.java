package action.in.blog.exception;

public class DuplicatedCollectException extends RuntimeException {

    private static final String MESSAGE = "Already collected card";

    public DuplicatedCollectException() {
        super(MESSAGE);
    }
}
