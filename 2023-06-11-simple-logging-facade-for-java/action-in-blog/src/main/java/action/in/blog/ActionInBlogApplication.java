package action.in.blog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class ActionInBlogApplication {

    public static void main(String[] args) {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();


        Logger slf4jLogger = LoggerFactory.getLogger(ActionInBlogApplication.class);
        java.util.logging.Logger javaUtilLogger = java.util.logging.Logger.getLogger("action.in.blog.ActionInBlogApplication");
        org.apache.commons.logging.Log apacheCommonsLogger = org.apache.commons.logging.LogFactory.getLog(ActionInBlogApplication.class);


        java.util.logging.Logger parentJavaUtilLogger = javaUtilLogger.getParent();
        java.util.logging.Handler handlerInJavaUtilLogger = parentJavaUtilLogger.getHandlers()[0];


        slf4jLogger.info("Hello World {}", slf4jLogger.getClass().getName());
        javaUtilLogger.info("Hello World " + handlerInJavaUtilLogger.getClass().getName());
        apacheCommonsLogger.info("Hello World " + apacheCommonsLogger.getClass().getName());
    }
}
