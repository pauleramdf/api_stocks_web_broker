package stock.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import stock.dto.StocksHistoricPricesDto;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;



//@NamedNativeQuery(name = "StocksHistoricPrices.findAllByTimeInterval",
//        query = """
//            SELECT shp.id_stock as idStock, shp.ask_min as open, shp.ask_min as close, shp.ask_min as high, shp.ask_min as low, created_on as createdOn
//            FROM STOCKS_HISTORIC_PRICES as shp
//            WHERE shp.id_stock = :id_stock
//            GROUP BY shp.id_stock, created_on, shp.ask_min
//            ORDER BY createdOn
//""",
//        resultSetMapping = "Mapping.StocksHistoricPricesDto")
//@SqlResultSetMapping(name = "Mapping.StocksHistoricPricesDto",
//        classes ={
//                @ConstructorResult(
//                        targetClass = stock.dto.StocksHistoricPricesDto.class,
//                        columns = {
//                                @ColumnResult(name = "idStock",type = Long.class),
//                                @ColumnResult(name = "open", type = Double.class),
//                                @ColumnResult(name = "close", type = Double.class),
//                                @ColumnResult(name = "high",type = Double.class),
//                                @ColumnResult(name = "low",type = Double.class),
//                                @ColumnResult(name = "createdOn",type = Timestamp.class)
//                        })})
//Long id_stock, Double open, Double close, Double high, Double low, Timestamp created_on
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="STOCKS_HISTORIC_PRICES")
public class StocksHistoricPrices {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Long id_stock;
    private Double ask_min;
    private Double ask_max;
    private Double bid_min;
    private Double bid_max;
    @CreationTimestamp
    @Column(name = "created_on")
    private Timestamp created_on;

    public StocksHistoricPrices(Stocks stocks){
        this.id_stock = stocks.getId();
        this.ask_min = stocks.getAsk_min();
        this.ask_max = stocks.getAsk_max();
        this.bid_min = stocks.getBid_min();
        this.bid_max = stocks.getBid_max();
    }
}
