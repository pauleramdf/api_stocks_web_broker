package stock.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;


@Getter
@Setter
@Entity
@Table(name ="stocks")
public class Stocks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "stock_symbol")
    private String stockSymbol;

    @Column(name = "stock_name")
    private String stockName;

    @Column(name = "ask_min")
    private Double askMin;

    @Column(name = "ask_max")
    private Double askMax;

    @Column(name = "bid_min")
    private Double bidMin;

    @Column(name = "bid_max")
    private Double bidMax;

    @CreationTimestamp
    @Column(name = "created_on")
    private Timestamp created;

    @UpdateTimestamp
    @Column(name = "updated_on")
    private Timestamp updated;


    public Stocks(){
        //construtor vazio
    }
}
