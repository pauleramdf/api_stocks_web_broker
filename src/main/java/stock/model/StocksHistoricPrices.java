package stock.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "stocks_historic_prices")
public class StocksHistoricPrices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="id_stock")
    private Stocks stock;
    private Double open;
    private Double close;
    private Double high;
    private Double low;
    @CreationTimestamp
    @Column(name = "created_on")
    private Timestamp created;

    public StocksHistoricPrices(Stocks stocks){
        Date date = new Date();
        this.setOpen(stocks.getAskMin());
        this.setClose(stocks.getAskMin());
        this.setHigh(stocks.getAskMin());
        this.setLow(stocks.getAskMin());
        this.setStock(stocks);
        this.setCreated(new Timestamp(date.getTime()));
    }
}
