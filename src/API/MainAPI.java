package API;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MainAPI {
	public String StreamAPIEnpoint = "http://localhost:5000";
	public String DatabaseAPIEndpoint = "http://localhsost:3000";

	public File GetWavStream()
	{
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
		.uri(URI.create(StreamAPIEnpoint + "/stream?file=Quran.wav"))
		.build();
		try {
            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
        	
            if (response.statusCode() == 200) 
			{
                try (InputStream inputStream = response.body();
                     FileOutputStream outputStream = new FileOutputStream("output.wav")) 
				{
                    inputStream.transferTo(outputStream);
                    System.out.println("WAV datoteka je uspješno preuzeta.");
                    return new File("output.wav");
                }
            } 
            else 
			{
                System.out.println("Neuspješan zahtjev. Status kod: " + response.statusCode());
            }
        } 
		catch (IOException | InterruptedException e) 
		{
            e.printStackTrace();
        }
		return null;
	}
}
