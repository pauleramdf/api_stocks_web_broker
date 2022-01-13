package user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import user.model.Stocks;
import user.repository.StocksRepository;

import java.security.Principal;
import java.util.List;

@RestController
    @CrossOrigin
    @ComponentScan("com.user.repository")
    class StocksRestController {

        @Autowired
        private StocksRepository stocksRepository ;


        @GetMapping("/stocks")
        public List<Stocks> getStocks(Principal principal) {
            return stocksRepository.findAll();
        }

    }


