package blog.in.action.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class BlogService {

    public String foo() {
        log.info("==========\tservice foo");
        return "foo";
    }
}
