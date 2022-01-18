package stock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;
import stock.DTO.StockPricesDTO;
import stock.model.Stocks;
import stock.repository.StocksRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@ComponentScan("com.stock.repository")
class StocksRestController {

    @Autowired
    private StocksRepository stocksRepository ;

    @GetMapping("/stocks")
    public List<Stocks> getStocks() {
        return stocksRepository.findAll();
    }

    @GetMapping("/stocks/name/{name}")
    public Stocks getStocksByName(@PathVariable(value="name") String stock_name) {
        return stocksRepository.findStocksByName(stock_name);
    }

    @GetMapping("/stocks/id/{id}")
    public Optional<Stocks> getStocksByName(@PathVariable(value="id") Long id) {
        return stocksRepository.findById(id);
    }

    @PostMapping("/stocks/sell")
    public void sellStocks(@Valid @RequestBody StockPricesDTO stockPrices) {
        Stocks stock = stocksRepository.findById(stockPrices.getId()).orElseThrow(Error::new);

        stock.setAsk_min(stockPrices.getMinPrice());
        stocksRepository.save(stock);

        stock.setAsk_max(stockPrices.getMaxPrice());
        stocksRepository.save(stock);
    }

    @PostMapping("/stocks/buy")
    public void buyStocks(@Valid @RequestBody StockPricesDTO stockPrices) {
        System.out.println(stockPrices);
        Stocks stock = stocksRepository.findById(stockPrices.getId()).orElseThrow(Error::new);

        stock.setBid_min(stockPrices.getMinPrice());
        stocksRepository.save(stock);

        stock.setBid_max(stockPrices.getMaxPrice());
        stocksRepository.save(stock);
    }
}


