package stock.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import stock.dto.StockPricesDto;
import stock.dto.StocksHistoricPricesDto;
import stock.model.Stocks;
import stock.service.StocksService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
class StocksRestController {

    private final StocksService stocksService;

    @GetMapping("/stocks")
    public ResponseEntity<List<Stocks>> getStocks() {
        List<Stocks> ordenada = stocksService.findAll();

        return new ResponseEntity<>(ordenada, HttpStatus.OK);
    }

    @GetMapping("/stocks/name/{name}")
    public ResponseEntity<Stocks> getStocksByName(@PathVariable(value = "name") String stockName) {
        return new ResponseEntity<>(stocksService.findStocksByName(stockName), HttpStatus.OK);
    }

    @GetMapping("/stocks/{id}")
    public ResponseEntity<Stocks> getStocksByName(@PathVariable(value = "id") Long id) {
        Optional<Stocks> stock  = stocksService.findById(id);
        if(stock.isPresent())
            return new ResponseEntity<>(stock.get(), HttpStatus.OK);

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/stocks/askbid")
    public ResponseEntity<Stocks> updateStocks(@Valid @RequestBody StockPricesDto stockPrices) {
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
    public ResponseEntity<List<StocksHistoricPricesDto>> getStockHistoricPrices(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(stocksService.getStockHistoricPrices(id), HttpStatus.OK);
    }
}

