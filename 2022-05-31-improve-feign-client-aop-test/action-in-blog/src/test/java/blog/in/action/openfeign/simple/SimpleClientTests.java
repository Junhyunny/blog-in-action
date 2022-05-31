package blog.in.action.openfeign.simple;

import blog.in.action.aop.InterfaceHistoryInterceptor;
import blog.in.action.client.SimpleClient;
import blog.in.action.repository.InterfaceHistory;
import blog.in.action.repository.InterfaceHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SimpleClientTests {

    AspectJProxyFactory factory;

    SimpleClient mockClient;
    InterfaceHistoryRepository mockRepository;
    InterfaceHistoryInterceptor interceptor;

    LocalDateTime requestTime;
    LocalDateTime responseTime;

    @BeforeEach
    void setUp() {
        mockClient = Mockito.mock(SimpleClient.class);
        mockRepository = Mockito.mock(InterfaceHistoryRepository.class);

        interceptor = new InterfaceHistoryInterceptor(mockRepository);
        factory = new AspectJProxyFactory(mockClient);
        factory.addAspect(interceptor);

        requestTime = LocalDateTime.now();
        responseTime = requestTime.plusSeconds(1);
    }

    @Test
    void whenCallHomeMethod_thenExistsHistoryData() {
        MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class);
        when(LocalDateTime.now())
                .thenReturn(requestTime, responseTime)
                .thenCallRealMethod();
        when(mockClient.home()).thenReturn("home");


        SimpleClient proxy = factory.getProxy();
        proxy.home();


        ArgumentCaptor<InterfaceHistory> argumentCaptor = ArgumentCaptor.forClass(InterfaceHistory.class);
        verify(mockRepository).save(argumentCaptor.capture());

        InterfaceHistory entity = argumentCaptor.getValue();
        assertThat(entity.getServiceId(), equalTo("0001"));
        assertThat(entity.getPath(), equalTo(new String[]{"/"}));
        assertThat(entity.getExplainText(), equalTo("블로그 홈"));
        assertThat(entity.getResponse(), equalTo("home"));
        assertThat(entity.getRequestTime(), equalTo(Timestamp.valueOf(requestTime)));
        assertThat(entity.getResponseTime(), equalTo(Timestamp.valueOf(responseTime)));

        mockedStatic.close();
    }

    @Test
    void whenCallAboutMethod_thenExistsHistoryData() {
        MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class);
        when(LocalDateTime.now())
                .thenReturn(requestTime, responseTime)
                .thenCallRealMethod();
        when(mockClient.about()).thenReturn("about");


        SimpleClient proxy = factory.getProxy();
        proxy.about();


        ArgumentCaptor<InterfaceHistory> argumentCaptor = ArgumentCaptor.forClass(InterfaceHistory.class);
        verify(mockRepository).save(argumentCaptor.capture());

        InterfaceHistory entity = argumentCaptor.getValue();
        assertThat(entity.getServiceId(), equalTo("0002"));
        assertThat(entity.getPath(), equalTo(new String[]{"/about/"}));
        assertThat(entity.getExplainText(), equalTo("자기소개"));
        assertThat(entity.getResponse(), equalTo("about"));
        assertThat(entity.getRequestTime(), equalTo(Timestamp.valueOf(requestTime)));
        assertThat(entity.getResponseTime(), equalTo(Timestamp.valueOf(responseTime)));

        mockedStatic.close();
    }
}