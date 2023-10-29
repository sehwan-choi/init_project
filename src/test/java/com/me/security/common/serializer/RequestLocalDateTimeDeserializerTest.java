package com.me.security.common.serializer;

import com.fasterxml.jackson.core.JsonParser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RequestLocalDateTimeDeserializerTest {

    RequestLocalDateTimeDeserializer localDateTimeDeserializer = new RequestLocalDateTimeDeserializer();

    JsonParser parser = mock(JsonParser.class);

    private static final String SUCCESS_DATE_TIME = "2023-10-10 11:00";
    private static final String DESERIALIZE_SUCCESS_DATE_TIME = "2023-10-10T11:00";
    private static final String FAIL_DATE_TIME = "2023-10-10 11:00:00";

    @Test
    @DisplayName("정상 파싱 테스트")
    void parsingSuccess() throws IOException {
        when(parser.getText()).thenReturn(SUCCESS_DATE_TIME);
        LocalDateTime deserialize = localDateTimeDeserializer.deserialize(parser, null);
        Assertions.assertThat(deserialize.toString()).isEqualTo(DESERIALIZE_SUCCESS_DATE_TIME);
    }

    @Test
    @DisplayName("파싱 실패")
    void parsingFail() throws IOException {
        when(parser.getText()).thenReturn(FAIL_DATE_TIME);
        Assertions.assertThatThrownBy(() -> localDateTimeDeserializer.deserialize(parser, null)).isInstanceOf(DateTimeParseException.class);
    }
}