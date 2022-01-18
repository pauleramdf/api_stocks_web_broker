package stock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;
import stock.DTO.StockPricesDTO;
import stock.model.Stocks;
import stock.repository.StocksRepository;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping("/stocks/{name}")
    public Stocks getStocksByName(@PathVariable(value="name") String stock_name) {
        return stocksRepository.findStocksByName(stock_name);
    }

    @PostMapping("/stocks/sell")
    public void sellStocks(@Valid @RequestBody StockPricesDTO stockPrices) {
        System.out.println("vamooooo");
        System.out.println(stockPrices.getPrice());
        Stocks stock = stocksRepository.findById(stockPrices.getId()).orElseThrow(Error::new);
        System.out.println(stock.getStock_name());
        if (stock.getAsk_min() > stockPrices.getPrice()){
            stock.setAsk_min(stockPrices.getPrice());
            stocksRepository.save(stock);
        }
        if(stock.getAsk_max() < stockPrices.getPrice()){
            stock.setAsk_max(stockPrices.getPrice());
            stocksRepository.save(stock);
        }
    }

    @PostMapping("/stocks/buy")
    public void buyStocks(@Valid @RequestBody StockPricesDTO stockPrices) {
        System.out.println(stockPrices);
        Stocks stock = stocksRepository.findById(stockPrices.getId()).orElseThrow(Error::new);


        if (stock.getBid_min() > stockPrices.getPrice()){
            stock.setAsk_min(stockPrices.getPrice());
            stocksRepository.save(stock);
        }
        if(stock.getBid_max() < stockPrices.getPrice()){
            stock.setAsk_max(stockPrices.getPrice());
            stocksRepository.save(stock);
        }
    }

}


