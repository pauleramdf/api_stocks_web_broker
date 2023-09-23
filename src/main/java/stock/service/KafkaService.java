package stock.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import user.dto.stocks.StockPricesDTO;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaService {

    private final StocksService stocksService;

    @KafkaListener(topics = "stocks", groupId = "GROUP-1", containerFactory = "kafkaListenerStockPricesFactory")
    public void listenWithHeaders(StockPricesDTO stockPricesDto, @Header(KafkaHeaders.PARTITION) int partition) {
        log.info( "Received Message: " + stockPricesDto + "from partition: " + partition);
        stocksService.askBid(stockPricesDto);
    }
}