package stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import stock.model.StocksHistoricPrices;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface StocksHistoricPricesRepository extends JpaRepository<StocksHistoricPrices, Long> {
    @Query( nativeQuery = true, value = """
    select * from stocks_historic_prices as shp where
    shp.id_stock = :id_stock and 
    date_trunc('hour', shp.created_on) = date_trunc('hour', cast (:now as Timestamp))
""")
    Optional<StocksHistoricPrices> findByIdAndDate(@Param("id_stock") Long id_stock, @Param("now") Timestamp agora);

    @Query(nativeQuery = true, value = """
    select * from stocks_historic_prices as shp where
    shp.id_stock = :id_stock
""")
    List<StocksHistoricPrices> findAllByIdStock(@Param("id_stock") Long id_stock);
}
