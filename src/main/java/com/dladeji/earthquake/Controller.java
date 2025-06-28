package com.dladeji.earthquake;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.dladeji.earthquake.dtos.Feature;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;



@RestController
@AllArgsConstructor
public class Controller {

    private final WebClient webClient = WebClient.create("https://earthquake.usgs.gov/fdsnws/event/1/");
    private final String simpleQueryUri = "/query?format=geojson&starttime=2025-06-20&endtime=2026-06-20&alertlevel=green";
    private final String largeQueryUri = "/query?format=geojson&starttime=2024-06-20&endtime=2024-07-20";
    private final QuakeRepository quakeRepository;
    

    @GetMapping("/url")
    public ResponseEntity<?> fetchApiData() {
        var featureFlux = webClient.get()
                .uri(largeQueryUri)
                .retrieve()
                .bodyToFlux(DataBuffer.class)
                .transform(dataBufferFlux -> parseLargeJsonArray(dataBufferFlux, "features", Feature.class));
  
        return ResponseEntity.ok().body(featureFlux.collectList().block().size());
    }

    // TODO: Create a truly unique column to differentiate earthquake table entries
    // I'm thinking (Title) + (Time) in milliseconds as that is simpler

    private <T> Flux<T> parseLargeJsonArray(Flux<DataBuffer> dataBuffers, String arrayField, Class<T> classType){
        ObjectMapper mapper = new ObjectMapper();

        return Flux.create(sink -> {
            JsonFactory factory = mapper.getFactory();
            JsonParser parser = null;

            try (InputStream is = DataBufferUtils.join(dataBuffers).toFuture().get().asInputStream(true)) {
                parser = factory.createParser(is);

                // Find the start of features array
                while(!parser.isClosed()){
                    JsonToken token = parser.nextToken();
                    if (JsonToken.FIELD_NAME.equals(token) && arrayField.equals(parser.getCurrentName())){
                        token = parser.nextToken();
                        if( !JsonToken.START_ARRAY.equals(token)){
                            sink.error(new RuntimeException("Expected an array for field " + arrayField));
                            return;
                        }

                        // Start reading array elements
                        while (parser.nextToken() != JsonToken.END_ARRAY) {
                            T item = mapper.readValue(parser, classType);
                            sink.next(item);
                            createEarthquakeInstance(item); // Create Quake object
                        }
                        sink.complete();
                        return;
                    }
                }
                sink.error(new RuntimeException("Array field '" + arrayField + "' not found"));

            } catch(Exception e){
                sink.error(e);
            } finally {
                if (parser != null) {
                    try {
                        parser.close();
                    } catch (IOException ignored) {}
                }
            }
        });
    }

    // TODO: Write better logs for Exception handlers
    private <T> void createEarthquakeInstance(T item){
        try {
            var featureObj = (Feature)item;
            var quake = new Quake(featureObj);
            quakeRepository.save(quake);

        } catch (DataIntegrityViolationException e){
            System.out.println("Duplicate Earthquake Entry Attempted");
        } 
        catch (ClassCastException e){
            System.out.println("Incorrect Casting occured. Expected a Feature object\n" + e);
        }
    }
    
}

    
        
    