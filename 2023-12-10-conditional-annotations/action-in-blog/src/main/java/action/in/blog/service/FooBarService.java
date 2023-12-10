package action.in.blog.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

@ConditionalOnBean(value = {BarService.class})
@Service
public class FooBarService {
}
