package clients;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskServerClient {
    private HttpClient client = HttpClient.newHttpClient();
    URI uri;
    public KVTaskServerClient(String url) throws IOException, InterruptedException {
        this.uri = URI.create(url);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .version(HttpClient.Version.HTTP_1_1)
                .build();
    }

    
}
