package stock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import stock.dto.StockPricesDto;
import stock.model.Stocks;
import stock.service.StocksService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@ComponentScan("com.stock.repository")
class StocksRestController {

    @Autowired
    private StocksService stocksService;


    @GetMapping("/stocks")
    public ResponseEntity<?> getStocks() {
        List<Stocks> ordenada = stocksService.findAll();
        ordenada.sort((o1, o2) -> {
            return o1.getId() < o2.getId() ? -1 : (o1.getId() == o2.getId()) ? 0 : 1;
        });
        return new ResponseEntity<>(ordenada, HttpStatus.OK);
    }

    @GetMapping("/stocks/name/{name}")
    public ResponseEntity<?> getStocksByName(@PathVariable(value = "name") String stock_name) {
        return new ResponseEntity<>(stocksService.findStocksByName(stock_name), HttpStatus.OK);
    }

    @GetMapping("/stocks/{id}")
    public ResponseEntity<?> getStocksByName(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(stocksService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/stocks/askbid")
    public ResponseEntity<?> buyStocks(@Valid @RequestBody StockPricesDto stockPrices) {
        Stocks stock = stocksService.askBid(stockPrices);
        return new ResponseEntity<>(stock, HttpStatus.OK);

    }

    @GetMapping("/stocks/live")
    public SseEmitter handle(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store");

        SseEmitter emitter = new SseEmitter();

        this.stocksService.addEmitter(emitter);

        return emitter;
    }

    @GetMapping("/stocks/historic/{id}")
    public ResponseEntity<?> getStockHistoricPrices(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(stocksService.getStockHistoricPrices(id), HttpStatus.OK);
    }
}

