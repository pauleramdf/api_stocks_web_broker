package stock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import stock.dto.StockPricesDTO;
import stock.model.Stocks;
import stock.service.StocksService;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@CrossOrigin
@ComponentScan("com.stock.repository")
class StocksRestController {

    @Autowired
    private StocksService stocksService ;

    private ExecutorService nonBlockingService = Executors
            .newCachedThreadPool();


    @GetMapping("/stocks")
    public ResponseEntity<?> getStocks() {
        List<Stocks> ordenada = stocksService.findAll();
        ordenada.sort((o1, o2) ->{ return o1.getId() < o2.getId() ? -1 : (o1.getId() == o2.getId()) ? 0 : 1;});
        return new ResponseEntity<>(ordenada, HttpStatus.OK);
    }

    @GetMapping("/stocks/name/{name}")
    public ResponseEntity<?> getStocksByName(@PathVariable(value="name") String stock_name) {
        return new ResponseEntity<>(stocksService.findStocksByName(stock_name), HttpStatus.OK);
    }

    @GetMapping("/stocks/{id}")
    public ResponseEntity<?> getStocksByName(@PathVariable(value="id") Long id) {
        return new ResponseEntity<>(stocksService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/stocks/askbid")
    public ResponseEntity<?> buyStocks(@Valid @RequestBody StockPricesDTO stockPrices) {
        Stocks stock = stocksService.findById(stockPrices.getId_stock()).orElseThrow(Error::new);
        if(stockPrices.getBidMax() != null){
            stock.setBid_min(stockPrices.getBidMin());
            stock.setBid_max(stockPrices.getBidMax());
        }
        if(stockPrices.getAskMin() != null){
            stock.setAsk_min(stockPrices.getAskMin());
            stock.setAsk_max(stockPrices.getAskMax());
        }

        stock = stocksService.save(stock);
        return new ResponseEntity<>(stock, HttpStatus.OK);

    }

    @GetMapping(value = "/stocks/live", produces = "text/event-stream")
    public SseEmitter handleSse() {
        SseEmitter emitter = new SseEmitter();
        nonBlockingService.execute(() -> {
            try {
                List<Stocks> ordenada = stocksService.findAll();
                ordenada.sort((o1, o2) ->{ return o1.getId() < o2.getId() ? -1 : (o1.getId() == o2.getId()) ? 0 : 1;});
                emitter.send(ordenada);
                emitter.complete();
            } catch (Exception ex) {
                emitter.completeWithError(ex);
            }
        });
        return emitter;
    }
}


