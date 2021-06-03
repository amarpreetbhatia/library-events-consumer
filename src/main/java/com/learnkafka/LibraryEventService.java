package com.learnkafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnkafka.entity.LibraryEvent;
import com.learnkafka.jpa.LibraryEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class LibraryEventService {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private LibraryEventRepository libraryEventRepository;

    public void processLibrary(ConsumerRecord<Integer, String> consumerRecord) throws JsonProcessingException {
        LibraryEvent libraryEvent = objectMapper.readValue(consumerRecord.value(), LibraryEvent.class);
        log.info("LibraryEvent : {}", libraryEvent);

        switch (libraryEvent.getLibraryEventType()){
            case NEW:
                save(libraryEvent);
                break;
            case UPDATE:
                validateEvent(libraryEvent);
                save(libraryEvent);
                break;
            default:
                log.info("Invalid Library Event Type");
        }
    }

    private void validateEvent(LibraryEvent libraryEvent) {
        if(libraryEvent.getLibraryEventId()==null){
            throw new IllegalArgumentException("Library Event Id is missing");
        }
        Optional<LibraryEvent> LibraryEventOptional = libraryEventRepository.findById(libraryEvent.getLibraryEventId());
        if(!LibraryEventOptional.isPresent()){
            throw new IllegalArgumentException("Not a valid Library Event");
        }
        log.info("validate is successful of the library event : {}", libraryEvent);
    }

    private void save(LibraryEvent libraryEvent) {
        libraryEvent.getBook().setLibraryEvent(libraryEvent);
        libraryEventRepository.save(libraryEvent);
        log.info("Successfully Library in inserted as {}", libraryEvent);
    }
}
