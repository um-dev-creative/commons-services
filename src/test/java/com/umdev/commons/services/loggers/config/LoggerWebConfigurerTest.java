package com.umdev.commons.services.loggers.config;

import com.umdev.commons.services.loggers.interceptor.LogInterceptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

class LoggerWebConfigurerTest {

    @Test
    @DisplayName("addInterceptors registers provided LogInterceptor with the registry")
    void addInterceptors_registersProvidedInterceptor() {
        LogInterceptor interceptor = Mockito.mock(LogInterceptor.class);
        InterceptorRegistry registry = Mockito.mock(InterceptorRegistry.class);

        LoggerWebConfigurer configurer = new LoggerWebConfigurer(interceptor);

        configurer.addInterceptors(registry);

        Mockito.verify(registry, Mockito.times(1)).addInterceptor(interceptor);
    }

    @Test
    @DisplayName("addInterceptors with null interceptor still calls registry.addInterceptor with null")
    void addInterceptors_withNullInterceptor_callsRegistryWithNull() {
        InterceptorRegistry registry = Mockito.mock(InterceptorRegistry.class);

        LoggerWebConfigurer configurer = new LoggerWebConfigurer(null);

        configurer.addInterceptors(registry);

        Mockito.verify(registry, Mockito.times(1)).addInterceptor((HandlerInterceptor) null);
    }

    @Test
    @DisplayName("addInterceptors called multiple times registers interceptor each invocation")
    void addInterceptors_calledMultipleTimes_registersEachTime() {
        LogInterceptor interceptor = Mockito.mock(LogInterceptor.class);
        InterceptorRegistry registry = Mockito.mock(InterceptorRegistry.class);

        LoggerWebConfigurer configurer = new LoggerWebConfigurer(interceptor);

        configurer.addInterceptors(registry);
        configurer.addInterceptors(registry);

        Mockito.verify(registry, Mockito.times(2)).addInterceptor(interceptor);
    }

    @Test
    @DisplayName("addInterceptors with null registry throws NullPointerException")
    void addInterceptors_withNullRegistry_throwsNPE() {
        LogInterceptor interceptor = Mockito.mock(LogInterceptor.class);
        LoggerWebConfigurer configurer = new LoggerWebConfigurer(interceptor);

        Assertions.assertThrows(NullPointerException.class, () -> configurer.addInterceptors(null));
    }

    @Test
    @DisplayName("addInterceptors propagates exception thrown by registry.addInterceptor")
    void addInterceptors_whenRegistryThrows_propagatesException() {
        LogInterceptor interceptor = Mockito.mock(LogInterceptor.class);
        InterceptorRegistry registry = Mockito.mock(InterceptorRegistry.class);

        Mockito.doThrow(new RuntimeException("boom")).when(registry).addInterceptor(Mockito.any());

        LoggerWebConfigurer configurer = new LoggerWebConfigurer(interceptor);

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> configurer.addInterceptors(registry));
        Assertions.assertEquals("boom", thrown.getMessage());
    }
}

