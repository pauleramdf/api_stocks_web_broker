package stock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stock.model.Stocks;
import stock.repository.StocksRepository;

import java.util.List;
import java.util.Optional;


@Service("stocksService")
public class StocksService {
    @Autowired
    private StocksRepository stocksRepository ;


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
}
