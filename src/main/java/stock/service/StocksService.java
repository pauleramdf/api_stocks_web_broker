package stock.service;

import org.springframework.beans.factory.annotation.Autowired;
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


@Service("stocksService")
public class StocksService {
    @Autowired
    private StocksRepository stocksRepository;

    @Autowired
    private StocksHistoricPricesRepository historicPricesRepository;

    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public Optional<Stocks> findById(Long id) {
        return stocksRepository.findById(id);
    }

    public Stocks save(Stocks stock) {
        return stocksRepository.save(stock);
    }

    public Stocks findStocksByName(String stock_name) {
        return stocksRepository.findStocksByName(stock_name);
    }

    public List<Stocks> findAll() {
        return stocksRepository.findAll();
    }

    public Stocks askBid(StockPricesDto stockPrices) {
        Stocks stock = findById(stockPrices.getId_stock()).orElseThrow(Error::new);
        if (stockPrices.getBid_max() != null && stockPrices.getBid_min() != null) {
            stock.setBid_min(stockPrices.getBid_min());
            stock.setBid_max(stockPrices.getBid_max());
        }
        if (stockPrices.getAsk_min() != null && stockPrices.getAsk_max() != null) {
            stock.setAsk_min(stockPrices.getAsk_min());
            stock.setAsk_max(stockPrices.getAsk_max());
        }


        stock = save(stock);
        atualizaPrices(stock.getId(), stock);
        publish();

        return stock;
    }

    private void atualizaPrices(Long id_stock, Stocks stocks) {
        Date date = new Date();
        Optional<StocksHistoricPrices> historic2 = historicPricesRepository.findByIdAndDate(id_stock, new Timestamp(date.getTime()));

        if(historic2.isPresent()) {
            if (historic2.get().getHigh() < stocks.getAsk_min()) {
                historic2.get().setHigh(stocks.getAsk_min());
            }
            if (historic2.get().getLow() > stocks.getAsk_min()) {
                historic2.get().setLow(stocks.getAsk_min());
            }
            historic2.get().setClose(stocks.getAsk_min());
            historicPricesRepository.save(historic2.get());
        }
        else{
            historicPricesRepository.save(new StocksHistoricPrices(stocks));
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
                ordenada.sort((o1, o2) -> {
                    return o1.getId() < o2.getId() ? -1 : (o1.getId() == o2.getId()) ? 0 : 1;
                });
                listenner.send(ordenada);

            } catch (Exception ex) {
                listenner.completeWithError(ex);
            }
        }

    }

    public List<StocksHistoricPricesDto> getStockHistoricPrices(Long id) {
        return historicPricesRepository.findAll().stream().map((StocksHistoricPrices e) -> new StocksHistoricPricesDto(e)).toList();
    }
}
