package stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import stock.dto.StocksHistoricPricesDto;
import stock.model.StocksHistoricPrices;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface StocksHistoricPricesRepository extends JpaRepository<StocksHistoricPrices, Long> {

    @Query(value= """
                      select new stock.dto.StocksHistoricPricesDto(shp.id_stock, max(shp.ask_min), max(shp.ask_min)
                      , max(shp.ask_min), min(shp.ask_min), function('date_trunc', 'hour', shp.created_on))
                      FROM StocksHistoricPrices shp
                      WHERE shp.id_stock = ?1
                      GROUP BY shp.id_stock, function('date_trunc', 'hour', shp.created_on)
       
""")
    List<StocksHistoricPricesDto> findAllByTimeInterval(Long id_stock);
}

//function('data_trunc', 'minute', shp.created_on)
//                      ORDER BY function('data_trunc', 'minute', shp.created_on) ASC

