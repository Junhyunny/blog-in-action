package blog.in.action.domain;

import lombok.Builder;

import java.util.List;

public record Post(
        long id,
        String content,
        List<Reply> replies
) {
    @Builder
    public Post {
    }

    public void addReply(Reply reply) {
        this.replies.add(reply);
    }
}
