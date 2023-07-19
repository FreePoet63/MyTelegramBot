import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;

import java.io.IOException;

public class Utils {
    private static CloseableHttpClient httpClient = HttpClientBuilder.create()
            .setDefaultRequestConfig(RequestConfig.custom()
                    .setConnectTimeout(5000)
                    .setSocketTimeout(30000)
                    .setRedirectsEnabled(false)
                    .build())
            .build();

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String getUrl(String url) throws IOException {
        CloseableHttpResponse response = httpClient.execute(new HttpGet(url));
        NasaObject nasaObject = objectMapper.readValue(response.getEntity().getContent(), NasaObject.class);
        return nasaObject.getUrl();
    }
}
