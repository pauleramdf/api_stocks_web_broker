package stock.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import stock.dto.StocksHistoricPricesDto;
import stock.model.Stocks;
import stock.service.StocksService;
import user.dto.stocks.StockPricesDTO;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stocks")
class StocksRestController {

    private final StocksService stocksService;

    @GetMapping()
    public ResponseEntity<List<Stocks>> getStocks() {
        List<Stocks> ordenada = stocksService.findAll();

        return new ResponseEntity<>(ordenada, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Stocks> getStocksByName(@PathVariable(value = "name") String stockName) {
        return new ResponseEntity<>(stocksService.findStocksByName(stockName), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stocks> getStocksByName(@PathVariable(value = "id") Long id) {
        Optional<Stocks> stock  = stocksService.findById(id);
        if(stock.isPresent())
            return new ResponseEntity<>(stock.get(), HttpStatus.OK);

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/askbid")
    public ResponseEntity<Stocks> updateStocks(@Valid @RequestBody StockPricesDTO stockPrices) {
        Stocks stock = stocksService.askBid(stockPrices);
        return new ResponseEntity<>(stock, HttpStatus.OK);

    }

    @GetMapping("/live")
    public SseEmitter handle(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store");

        SseEmitter emitter = new SseEmitter();

        this.stocksService.addEmitter(emitter);

        return emitter;
    }

    @GetMapping("/historic/{id}")
    public ResponseEntity<List<StocksHistoricPricesDto>> getStockHistoricPrices(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(stocksService.getStockHistoricPrices(id), HttpStatus.OK);
    }
}

