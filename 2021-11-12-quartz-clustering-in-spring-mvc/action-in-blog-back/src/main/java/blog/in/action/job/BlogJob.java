package blog.in.action.job;

import blog.in.action.service.BlogService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class BlogJob extends QuartzJobBean {

    private BlogService blogService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            blogService = ((ApplicationContext) jobExecutionContext.getScheduler().getContext().get("applicationContext")).getBean(BlogService.class);
            blogService.updateTest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
