package com.learnkafka.libraryeventsconsumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnkafka.service.LibraryEventService;
import com.learnkafka.consumer.LibraryEventConsumer;
import com.learnkafka.entity.Book;
import com.learnkafka.entity.LibraryEvent;
import com.learnkafka.entity.LibraryEventType;
import com.learnkafka.jpa.LibraryEventRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.context.TestPropertySource;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;


@SpringBootTest
@EmbeddedKafka(topics = {"library-events"}, partitions = 3)
@TestPropertySource(properties = {"spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "spring.kafka.consumer.bootstrap-servers=${spring.embedded.kafka.brokers}"
})
public class LibraryEventConsumerIntegrationTest {

    @Autowired
    EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    KafkaTemplate<Integer,String> kafkaTemplate;

    @Autowired
    KafkaListenerEndpointRegistry endpointRegistry;

    @SpyBean
    LibraryEventConsumer libraryEventConsumerSpy;

    @SpyBean
    LibraryEventService libraryEventServiceSpy;

    @Autowired
    LibraryEventRepository libraryEventsRepository;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        for(MessageListenerContainer messageListenerContainer: endpointRegistry.getAllListenerContainers()){
            ContainerTestUtils.waitForAssignment(messageListenerContainer, embeddedKafkaBroker.getPartitionsPerTopic());
        }
    }

    @AfterEach
    void tearDown() {
        libraryEventsRepository.deleteAll();
    }

    @Test
    void publishNewLibraryEvent() throws ExecutionException, InterruptedException, JsonProcessingException {
        String json = "{\"libraryEventId\":null,\"book\":{\"bookId\":456,\"bookName\":\"Kafka Using Spring Boot\",\"bookAuthor\":\"Dilip\"}}";
        kafkaTemplate.sendDefault(json).get();

        CountDownLatch latch = new CountDownLatch(1);
        latch.await(10, TimeUnit.SECONDS);

        verify(libraryEventConsumerSpy, times(10)).onMessage(isA(ConsumerRecord.class));
        verify(libraryEventServiceSpy, times(10)).processLibrary(isA(ConsumerRecord.class));

//        List<LibraryEvent> libraryEventList = (List<LibraryEvent>) libraryEventsRepository.findAll();
//        assert libraryEventList.size() == 1;
//        libraryEventList.forEach(libraryEvent -> {
//            assert libraryEvent.getLibraryEventId() != null;
//            assertEquals(456,libraryEvent.getBook().getBookId());
//                });
    }

    @Test
    void testPublishUpdateLibraryEvent() throws JsonProcessingException, ExecutionException, InterruptedException {
        String json = "{\"libraryEventId\":null,\"book\":{\"bookId\":456,\"bookName\":\"Kafka Using Spring Boot\",\"bookAuthor\":\"Dilip\"}}";
        LibraryEvent libraryEvent = objectMapper.readValue(json, LibraryEvent.class);
        libraryEvent.getBook().setLibraryEvent(libraryEvent);
        libraryEventsRepository.save(libraryEvent);

        Book updatedBook = Book.builder().bookId(456).bookAuthor("Amarpreet").bookName("new version").build();

        libraryEvent.setLibraryEventType(LibraryEventType.UPDATE);
        libraryEvent.setBook(updatedBook);
        String updatedJson = objectMapper.writeValueAsString(libraryEvent);

        kafkaTemplate.sendDefault(libraryEvent.getLibraryEventId(), updatedJson).get();

        // To lockdown thread
        CountDownLatch latch = new CountDownLatch(1);
        latch.await(3, TimeUnit.SECONDS);
//        verify(libraryEventConsumerSpy, times(1)).onMessage(isA(ConsumerRecord.class));
//        verify(libraryEventServiceSpy, times(1)).processLibrary(isA(ConsumerRecord.class));
        LibraryEvent persistedLibraryEvent = libraryEventsRepository.findById(libraryEvent.getLibraryEventId()).get();
        assertEquals("Amarpreet",persistedLibraryEvent.getBook().getBookAuthor());
    }

    @Test
    void testPublishModifyLibraryEvent() throws JsonProcessingException, ExecutionException, InterruptedException {
        String json = "{\"libraryEventId\":null,\"book\":{\"bookId\":456,\"bookName\":\"Kafka Using Spring Boot\",\"bookAuthor\":\"Dilip\"}}";
        LibraryEvent libraryEvent = objectMapper.readValue(json, LibraryEvent.class);
        libraryEvent.getBook().setLibraryEvent(libraryEvent);
        libraryEventsRepository.save(libraryEvent);
        System.out.println("------- ****** " + libraryEvent.getLibraryEventId() + " ****** -------") ;
        Book updatedBook = Book.builder().bookId(456).bookAuthor("Amarpreet").bookName("new version").build();

        libraryEvent.setLibraryEventType(LibraryEventType.UPDATE);
        libraryEvent.setBook(updatedBook);
        String updatedJson = objectMapper.writeValueAsString(libraryEvent);

        kafkaTemplate.sendDefault(libraryEvent.getLibraryEventId(), updatedJson).get();

        // To lockdown thread
        CountDownLatch latch = new CountDownLatch(1);
        latch.await(3, TimeUnit.SECONDS);
//        verify(libraryEventConsumerSpy, times(1)).onMessage(isA(ConsumerRecord.class));
//        verify(libraryEventServiceSpy, times(1)).processLibrary(isA(ConsumerRecord.class));
        LibraryEvent persistedLibraryEvent = libraryEventsRepository.findById(libraryEvent.getLibraryEventId()).get();
        assertEquals("Amarpreet",persistedLibraryEvent.getBook().getBookAuthor());
    }

    @Test
    void testPublishNullBookLibraryEvent() throws JsonProcessingException, ExecutionException, InterruptedException {
        String json = "{\"libraryEventId\":null,\"book\":{\"bookId\":456,\"bookName\":\"Kafka Using Spring Boot\",\"bookAuthor\":\"Dilip\"}}";
        LibraryEvent libraryEvent = objectMapper.readValue(json, LibraryEvent.class);
        libraryEvent.getBook().setLibraryEvent(libraryEvent);
        libraryEventsRepository.save(libraryEvent);
        System.out.println("------- ****** " + libraryEvent.getLibraryEventId() + " ****** -------") ;
//        Book updatedBook = Book.builder().bookId(456).bookAuthor("Amarpreet").bookName("new version").build();

        libraryEvent.setLibraryEventType(LibraryEventType.UPDATE);
        libraryEvent.setBook(null);
        String updatedJson = objectMapper.writeValueAsString(libraryEvent);

        kafkaTemplate.sendDefault(libraryEvent.getLibraryEventId(), updatedJson).get();

        // To lockdown thread
        CountDownLatch latch = new CountDownLatch(1);
        latch.await(3, TimeUnit.SECONDS);
//        verify(libraryEventConsumerSpy, times(1)).onMessage(isA(ConsumerRecord.class));
//        verify(libraryEventServiceSpy, times(1)).processLibrary(isA(ConsumerRecord.class));
        LibraryEvent persistedLibraryEvent = libraryEventsRepository.findById(libraryEvent.getLibraryEventId()).get();

    }
}
