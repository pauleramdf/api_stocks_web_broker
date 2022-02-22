package stock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import stock.dto.StockPricesDTO;
import stock.model.Stocks;
import stock.repository.StocksRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;


@Service("stocksService")
public class StocksService {
    @Autowired
    private StocksRepository stocksRepository ;

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

    public Stocks askBid(StockPricesDTO stockPrices) {
        Stocks stock = findById(stockPrices.getId_stock()).orElseThrow(Error::new);
        if (stockPrices.getBid_max() != null) {
            stock.setBid_min(stockPrices.getBid_min());
            stock.setBid_max(stockPrices.getBid_max());
        }
        if (stockPrices.getAsk_min() != null) {
            stock.setAsk_min(stockPrices.getAsk_min());
            stock.setAsk_max(stockPrices.getAsk_max());
        }

        stock = save(stock);
        publish();

        return stock;
    }

    public void addEmitter(SseEmitter emitter) {
        this.emitters.add(emitter);

        emitter.onCompletion(() -> this.emitters.remove(emitter));
        emitter.onTimeout(() -> this.emitters.remove(emitter));
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
}
