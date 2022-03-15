package stock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import stock.dto.StockPricesDto;
import stock.dto.StocksHistoricPricesDto;
import stock.model.Stocks;
import stock.model.StocksHistoricPrices;
import stock.repository.StocksHistoricPricesRepository;
import stock.repository.StocksRepository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@RequiredArgsConstructor
@Service("stocksService")
public class StocksService {
    private final StocksRepository stocksRepository;

    private final StocksHistoricPricesRepository historicPricesRepository;

    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public Optional<Stocks> findById(Long id) {
        return stocksRepository.findById(id);
    }

    public Stocks save(Stocks stock) {
        return stocksRepository.save(stock);
    }

    public Stocks findStocksByName(String stockName) {
        return stocksRepository.findStocksByName(stockName);
    }

    public List<Stocks> findAll() {
        List<Stocks> ordenada = stocksRepository.findAll();
        ordenada.sort(this::ordena);
        return ordenada ;
    }

    private int ordena(Stocks o1, Stocks o2){
        if(o1.getId() < o2.getId()){
            return -1;
        }
        else{
            if(o1.getId().equals(o2.getId()))
                return 0;
            else
                return 1;
        }
    }

    public Stocks askBid(StockPricesDto stockPrices) {
        Stocks stock = findById(stockPrices.getIdStock()).orElseThrow(Error::new);
        if (stockPrices.getBidMax() != null && stockPrices.getBidMin() != null) {
            stock.setBidMin(stockPrices.getBidMin());
            stock.setBidMax(stockPrices.getBidMax());
        }
        if (stockPrices.getAskMin() != null && stockPrices.getAskMax() != null) {
            stock.setAskMin(stockPrices.getAskMin());
            stock.setAskMax(stockPrices.getAskMax());
        }

        stock = save(stock);
        atualizaPrices(stock.getId(), stock);
        publish();

        return stock;
    }

    private void atualizaPrices(Long idStock, Stocks stocks) {
        Date date = new Date();
        Optional<StocksHistoricPrices> historic2 = historicPricesRepository.findByIdAndDate(idStock, new Timestamp(date.getTime()));

        if(stocks.getAskMin() != 0){
            if(historic2.isPresent() ) {
                if (historic2.get().getHigh() < stocks.getAskMin()) {
                    historic2.get().setHigh(stocks.getAskMin());
                }
                if (historic2.get().getLow() > stocks.getAskMin()) {
                    historic2.get().setLow(stocks.getAskMin());
                }
                historic2.get().setClose(stocks.getAskMin());
                historicPricesRepository.save(historic2.get());
            }
            else{
                historicPricesRepository.save(new StocksHistoricPrices(stocks));
            }
        }
    }

    public void addEmitter(SseEmitter emitter) {
        this.emitters.add(emitter);

        emitter.onCompletion(() -> this.emitters.remove(emitter));
        emitter.onTimeout(() -> {
            emitter.complete();
            this.emitters.remove(emitter);});
    }

    public void publish() {
        for (SseEmitter listenner :
                emitters) {
            try {
                List<Stocks> ordenada = findAll();
                ordenada.sort(this::ordena);
                listenner.send(ordenada);

            } catch (Exception ex) {
                listenner.completeWithError(ex);
            }
        }
    }

    public List<StocksHistoricPricesDto> getStockHistoricPrices(Long id) {
        return historicPricesRepository.findAllByIdStock(id).stream().map( StocksHistoricPricesDto::new).toList();
    }
}
