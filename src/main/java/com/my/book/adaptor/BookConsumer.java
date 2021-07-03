package com.my.book.adaptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.book.config.KafkaProperties;
import com.my.book.domain.event.StockChanged;
import com.my.book.service.BookService;
import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.PostConstruct;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BookConsumer {

    private final Logger log = LoggerFactory.getLogger(BookConsumer.class);
    private final AtomicBoolean closed = new AtomicBoolean(false);

    // 토픽명
    public static final String TOPIC = "topic_book";
    private final KafkaProperties kafkaProperties;
    private KafkaConsumer<String, String> kafkaConsumer;
    private BookService bookService;
    private ExecutorService executorService = Executors.newCachedThreadPool();

    public BookConsumer(KafkaProperties kafkaProperties, BookService bookService) {
        this.kafkaProperties = kafkaProperties;
        this.bookService = bookService;
    }

    @PostConstruct
    public void start() {
        log.info("Kafka consumer starting...");
        this.kafkaConsumer = new KafkaConsumer<String, String>(kafkaProperties.getConsumerProps());
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));

        // 토픽 구독
        kafkaConsumer.subscribe(Collections.singleton(TOPIC));
        log.info("Kafka consumer started");

        executorService.execute(
            () -> {
                try {
                    while (!closed.get()) {
                        ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(3));
                        for (ConsumerRecord<String, String> record : records) {
                            log.info("Consumer message in {} : {}", TOPIC, record.value());
                            ObjectMapper objectMapper = new ObjectMapper();

                            // kafka에서 읽은 메시지를 대출 마이크로서비스가 보낸 StockChanged 도메인 이벤트로 변환
                            StockChanged stockChanged = objectMapper.readValue(record.value(), StockChanged.class);
                            // 도메인 이벤트 정보로 bookService를 호출하여 도서 재고 상태를 update
                            bookService.processChangeBookState(stockChanged.getBookId(), stockChanged.getBookStatus());
                        }
                    }
                    kafkaConsumer.commitSync();
                } catch (WakeupException e) {
                    if (!closed.get()) {
                        throw e;
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                } finally {
                    log.info("kafka consumer close");
                    kafkaConsumer.close();
                }
            }
        );
    }

    public KafkaConsumer<String, String> getKafkaConsumer() {
        return kafkaConsumer;
    }

    public void shutdown() {
        log.info("Shutdown kafka consumer");
        closed.set(true);
        kafkaConsumer.wakeup();
    }
}
