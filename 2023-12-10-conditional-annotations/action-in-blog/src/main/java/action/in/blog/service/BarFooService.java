package action.in.blog.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

@ConditionalOnMissingBean(value = {BarService.class})
@Service
public class BarFooService {
}
