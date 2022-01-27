package stock.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name ="stocks")
public class Stocks implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String stock_symbol;
    private String stock_name;
    private Double ask_min;
    private Double ask_max;
    private Double bid_min;
    private Double bid_max;
    @CreationTimestamp
    @Column(name = "created_on")
    private Timestamp created_on;
    @UpdateTimestamp
    @Column(name = "updated_on")
    private Timestamp updated_on;


    public Stocks(){
    }

    public Stocks(Long id, String stock_symbol, String stock_name, Timestamp created_on, Timestamp updated_on) {
        Id = id;
        this.stock_symbol = stock_symbol;
        this.stock_name = stock_name;
        this.created_on = created_on;
        this.updated_on = updated_on;
    }

    public Stocks(Long id, String stock_symbol, String stock_name, Double ask_min, Double ask_max, Double bid_min, Double bid_max, Timestamp created_on, Timestamp updated_on) {
        Id = id;
        this.stock_symbol = stock_symbol;
        this.stock_name = stock_name;
        this.ask_min = ask_min;
        this.ask_max = ask_max;
        this.bid_min = bid_min;
        this.bid_max = bid_max;
    }
}
