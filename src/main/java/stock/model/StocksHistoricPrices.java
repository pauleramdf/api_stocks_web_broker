package stock.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
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
    private Long Id;
    @ManyToOne
    @JoinColumn(name="id_stock")
    private Stocks stock;
    private Double open;
    private Double close;
    private Double high;
    private Double low;
    @CreationTimestamp
    @Column(name = "created_on")
    private Timestamp created_on;

    public StocksHistoricPrices(Stocks stocks){
        Date date = new Date();
        this.setOpen(stocks.getAsk_min());
        this.setClose(stocks.getAsk_min());
        this.setHigh(stocks.getAsk_min());
        this.setLow(stocks.getAsk_min());
        this.setStock(stocks);
        this.setCreated_on(new Timestamp(date.getTime()));

    }
    @PrePersist
    public void func(){
        //faz alguma coisa
    }

}
