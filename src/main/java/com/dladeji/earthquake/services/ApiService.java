package com.dladeji.earthquake.services;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.dladeji.earthquake.QuakeConfig;
import com.dladeji.earthquake.QuakeRepository;
import com.dladeji.earthquake.dtos.Feature;
import com.dladeji.earthquake.entities.Quake;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;


@Service
@AllArgsConstructor
public class ApiService {
    private final QuakeConfig quakeConfig;
    private final QuakeRepository quakeRepository;
    

    // TODO: Make fetch faster somehow. Large query takes up a lot of time
    public int fetchDataFromApi(){
        var webClient = WebClient.create(quakeConfig.getBaseUrl());
        var featureFlux = webClient.get()
                .uri(quakeConfig.getSmallQueryUri())
                .retrieve()
                .bodyToFlux(DataBuffer.class)
                .transform(dataBufferFlux -> parseResponseJsonArray(dataBufferFlux, "features", Feature.class));

        return featureFlux.collectList().block().size();
    }

    private <T> Flux<T> parseResponseJsonArray(Flux<DataBuffer> dataBuffers, String arrayField, Class<T> classType){
        ObjectMapper mapper = new ObjectMapper();

        return Flux.create(sink -> {
            JsonFactory factory = mapper.getFactory();
            JsonParser parser = null;

            try (InputStream is = DataBufferUtils.join(dataBuffers).toFuture().get().asInputStream(true)) {
                parser = factory.createParser(is);

                // Find the start of features array
                while(!parser.isClosed()){
                    JsonToken token = parser.nextToken();
                    if (JsonToken.FIELD_NAME.equals(token) && arrayField.equals(parser.currentName())){
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
