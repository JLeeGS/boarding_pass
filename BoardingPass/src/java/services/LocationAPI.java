package services;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import domain.BoardingPassTicket;
import org.json.*;

public class LocationAPI {
    private static final String API_KEY="";
    public void getRequest() throws IOException, InterruptedException {
        try {
            String url = "https://www.googleapis.com/geolocation/v1/geolocate?key=" + API_KEY;
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .header("accept", "application/json")
                    .uri(URI.create(url))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // print response
            HttpHeaders headers = response.headers();
            headers.map().forEach((k, v) -> System.out.println(k + ":" + v));
            System.out.println(response.statusCode());
            System.out.println(response.body());
        }
        catch(Exception e){

        }
    }
    public String postLocation() throws IOException, InterruptedException{
        double latitude=0.0,  longitude=0.0;
        try {
            HttpClient client = HttpClient.newHttpClient();
            String url = "https://www.googleapis.com/geolocation/v1/geolocate?key=" + API_KEY;
            String data = "";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .POST(HttpRequest.BodyPublishers.ofString(data))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            JSONObject obj=new JSONObject(response.body());
            latitude=obj.getJSONObject("location").getDouble("lat");
            longitude=obj.getJSONObject("location").getDouble("lng");

            //System.out.println(response.body());
        }
        catch(Exception e){
        }
        String coords=latitude+"%2C"+longitude;
        return coords;
    }

    public int postRequestETA(String origin, String destination) throws IOException, InterruptedException{
        int getTime=0;
        try {
            HttpClient client = HttpClient.newHttpClient();
            //origin=postLocation();
            String encodedOrigin=URLEncoder.encode(origin, StandardCharsets.UTF_8);
            String encodedDestination=URLEncoder.encode(destination, StandardCharsets.UTF_8);;
            String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="+encodedOrigin+"&destinations="+encodedDestination+"&key="+API_KEY
                    .replaceAll(" ", "%20");

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .POST(HttpRequest.BodyPublishers.ofString(""))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            JSONObject obj=new JSONObject(response.body());
            String distance=obj.getJSONArray("rows").getJSONObject(0)
                    .getJSONArray("elements").getJSONObject(0)
                    .getJSONObject("distance").getString("text");
            double getDistance= Integer.parseInt(distance.replaceAll(" km","").replaceAll(",",""))*0.6214;
            double getFlightDuration=getDistance/550;//550mph flightspeed
            double timeFormat=Double.parseDouble(String.format("%.2f",getFlightDuration));
            double addWaitTime=(timeFormat+0.30)*60;

            getTime= (int) addWaitTime;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return getTime;
    }
    public void generateETA(BoardingPassTicket bpt) throws ParseException, IOException, InterruptedException {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date d = df.parse(bpt.getBp().getDeparture());
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int allTimeMinutes=postRequestETA(bpt.getBp().getOrigin(), bpt.getBp().getDestination());
        int hour=allTimeMinutes/60, minute=allTimeMinutes%60;
        cal.add(Calendar.HOUR, hour);
        cal.add(Calendar.MINUTE, minute);
        String newTime = df.format(cal.getTime());
        bpt.getBp().setEta(newTime);
    }
}
