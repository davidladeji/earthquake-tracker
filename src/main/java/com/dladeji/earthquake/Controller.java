package com.dladeji.earthquake;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.dladeji.earthquake.dtos.ApiResponse;
import com.dladeji.earthquake.dtos.Feature;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;



@RestController
@AllArgsConstructor
public class Controller {

    private final WebClient webClient = WebClient.create("https://earthquake.usgs.gov/fdsnws/event/1/");
    private final String simpleQueryUri = "/query?format=geojson&starttime=2024-06-20&endtime=2024-07-20";
    private final String largeQueryUri = "/query?format=geojson&starttime=2025-06-20&endtime=2026-06-20";
    private final QuakeRepository quakeRepository;
    

    @GetMapping("/url")
    public ResponseEntity<?> fetchApiData() {
        // var response = webClient.get()
        //         .uri(simpleQueryUri)
        //         .retrieve()
        //         .bodyToMono(ApiResponse.class)
        //         .block();

        var featureFlux = webClient.get()
                .uri(simpleQueryUri)
                .retrieve()
                .bodyToFlux(DataBuffer.class)
                .transform(dataBufferFlux -> parseLargeJsonArray(dataBufferFlux, "features", Feature.class));

            
                

        // createEarthquakeInstances(response.getFeatures());    
        return ResponseEntity.ok().body(featureFlux.collectList().block().size());
    }

    private static <T> Flux<T> parseLargeJsonArray(Flux<DataBuffer> dataBuffers, String arrayField, Class<T> classType){
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

    

    private void createEarthquakeInstances(Feature[] quakeData){
        for (int i=0; i<quakeData.length; i++){
            try {
                // ********* Undo this with updated Feature class ************

                // var quake = new Quake(quakeData[i].getProperties());
                // quakeRepository.save(quake);

            } catch (DataIntegrityViolationException e){
                // Might need better log for Unique quake entry
                System.out.println("Duplicate Earthquake Entry Attempted");
                continue;
            }
        }
    }
    
}

    
        
    