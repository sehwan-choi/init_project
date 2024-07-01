package com.me.security.config.executor;

import com.me.security.common.executor.MDCCopyTaskDecorator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

class MDCCopyTaskDecoratorTest {

    private static final String MDC_VALUE = "MDC_VALUE";
    private static final String MDC_KEY = "MDC_KEY";

    MDCCopyTaskDecorator task = new MDCCopyTaskDecorator();
    @Test
    @DisplayName("MDC copy Test")
    void MDCCopy() {
        MDC.put(MDC_KEY, MDC_VALUE);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Assertions.assertThat(MDC.get(MDC_KEY)).isEqualTo(MDC_VALUE);
            }
        };

        task.decorate(runnable).run();
    }
}