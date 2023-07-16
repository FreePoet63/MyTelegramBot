import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;

import java.io.*;

public class Main {
    public static final String url = "https://api.nasa.gov/planetary/apod?api_key=ВАШ КЛЮЧ";
    public static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();

        CloseableHttpResponse response = httpClient.execute(new HttpGet(url));

        NasaObject nasaObject = mapper.readValue(response.getEntity().getContent(), NasaObject.class);
        System.out.println(nasaObject);

        CloseableHttpResponse imageResponse = httpClient.execute(new HttpGet(nasaObject.getUrl()));
        String[] array = nasaObject.getUrl().split("/");
        String fileName = array[array.length - 1];

        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        imageResponse.getEntity().writeTo(fileOutputStream);
        fileOutputStream.close();
    }
}
