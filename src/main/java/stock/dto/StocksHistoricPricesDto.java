package stock.dto;

import lombok.*;

import java.util.Date;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StocksHistoricPricesDto {
    private Long idStock;
    private Double open;
    private Double close;
    private Double high;
    private Double low;
    private Date createdOn;

//    public StocksHistoricPricesDto(Long id_stock, Double open, Double close, Double high, Double low, Timestamp created_on) {
//        this.id_stock = id_stock;
//        this.open = open;
//        this.close = close;
//        this.high = high;
//        this.low = low;
//        this.created_on = created_on;
//    }
}
