package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("RequestUtil 테스트")
public class RequestHeaderParserTest {
    @DisplayName("request 요청 경로 가져오기")
    @ParameterizedTest
    @CsvSource(value = {"GET / HTTP/1.1:/", "GET /favicon.ico HTTP/1.1:/favicon.ico",
            "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1:/user/create"}, delimiter = ':')
    public void getPath(String requestHeaderFirstLine, String expectedPath) {
        String path = RequestHeaderParser.getRequestPath(requestHeaderFirstLine);

        assertThat(path).isEqualTo(expectedPath);
    }

    @Test
    public void getRequestParam() {
        Map<String, String> requestParam = RequestHeaderParser.getRequestParams("GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1:/user/create");

        assertThat(requestParam.get("userId")).isEqualTo("javajigi");
        assertThat(requestParam.get("password")).isEqualTo("password");
        assertThat(requestParam.get("name")).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
        assertThat(requestParam.get("email")).isEqualTo("javajigi%40slipp.net");
    }
}