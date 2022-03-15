package stock.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import stock.model.Stocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StocksRepository extends JpaRepository<Stocks, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM Stocks s where s.stock_name = :stock_name")
    Stocks findStocksByName(@Param("stock_name") String stockName);
}
