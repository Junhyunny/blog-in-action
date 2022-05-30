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

    SimpleClient targetClient;
    InterfaceHistoryRepository mockRepository;
    InterfaceHistoryInterceptor aspectInterceptor;

    LocalDateTime requestTime;
    LocalDateTime responseTime;

    @BeforeEach
    void setUp() {

        targetClient = Mockito.mock(SimpleClient.class);
        mockRepository = Mockito.mock(InterfaceHistoryRepository.class);

        factory = new AspectJProxyFactory(targetClient);
        aspectInterceptor = new InterfaceHistoryInterceptor(mockRepository);

        factory.addAspect(aspectInterceptor);

        requestTime = LocalDateTime.now();
        responseTime = requestTime.plusSeconds(1);
    }

    @Test
    public void whenCallHomeMethod_thenCallSaveMethodWithHistory() {
        MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class);
        when(targetClient.home()).thenReturn("home");
        Mockito.when(LocalDateTime.now())
                .thenReturn(requestTime, responseTime)
                .thenCallRealMethod();


        SimpleClient proxyWrappedClient = factory.getProxy();
        proxyWrappedClient.home();


        ArgumentCaptor<InterfaceHistory> argumentCaptor = ArgumentCaptor.forClass(InterfaceHistory.class);
        verify(mockRepository).save(argumentCaptor.capture());

        InterfaceHistory interfaceHistory = argumentCaptor.getValue();
        assertThat(interfaceHistory.getServiceId(), equalTo("0001"));
        assertThat(interfaceHistory.getExplainText(), equalTo("블로그 홈"));
        assertThat(interfaceHistory.getPath(), equalTo(new String[]{"/"}));
        assertThat(interfaceHistory.getRequestTime(), equalTo(Timestamp.valueOf(requestTime)));
        assertThat(interfaceHistory.getResponseTime(), equalTo(Timestamp.valueOf(responseTime)));

        mockedStatic.close();
    }

    @Test
    public void whenCallAboutMethod_thenCallSaveMethodWithHistory() {
        MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class);
        when(targetClient.home()).thenReturn("about");
        Mockito.when(LocalDateTime.now())
                .thenReturn(requestTime, responseTime)
                .thenCallRealMethod();


        SimpleClient proxyWrappedClient = factory.getProxy();
        proxyWrappedClient.about();


        ArgumentCaptor<InterfaceHistory> argumentCaptor = ArgumentCaptor.forClass(InterfaceHistory.class);
        verify(mockRepository).save(argumentCaptor.capture());

        InterfaceHistory interfaceHistory = argumentCaptor.getValue();
        assertThat(interfaceHistory.getServiceId(), equalTo("0002"));
        assertThat(interfaceHistory.getExplainText(), equalTo("자기소개"));
        assertThat(interfaceHistory.getPath(), equalTo(new String[]{"/about/"}));
        assertThat(interfaceHistory.getRequestTime(), equalTo(Timestamp.valueOf(requestTime)));
        assertThat(interfaceHistory.getResponseTime(), equalTo(Timestamp.valueOf(responseTime)));

        mockedStatic.close();
    }
}