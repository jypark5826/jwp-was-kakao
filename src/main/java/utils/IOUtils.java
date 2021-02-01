package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IOUtils {
    /**
     * @param BufferedReader는
     *            Request Body를 시작하는 시점이어야
     * @param contentLength는
     *            Request Header의 Content-Length 값이다.
     * @return
     * @throws IOException
     */
    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }

    public static String buildString(InputStream in) throws IOException {
        InputStreamReader is = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(is);

        List<String> lines = new ArrayList<>();
        String line = "";

        while ((line = br.readLine()) != null && !line.isEmpty()) {
            lines.add(line);
        }

        int contentLength = getContentLength(lines);
        if (contentLength > 0) {
            line = "\n\n" + readData(br, contentLength);
        }

        return lines.stream()
                .collect(Collectors.joining("\n")) + line;
    }

    private static int getContentLength(List<String> lines) {
        String contentLengthLine = lines.stream()
                .filter(it -> it.split(": ", 2)[0].equals("Content-Length"))
                .findAny()
                .orElse(null);

        if (contentLengthLine == null) {
            return 0;
        }
        return Integer.parseInt(contentLengthLine.split(": ", 2)[1]);
    }
}
