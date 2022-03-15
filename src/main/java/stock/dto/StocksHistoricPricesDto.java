package stock.dto;

import lombok.*;
import stock.model.StocksHistoricPrices;

import java.util.Date;

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

public StocksHistoricPricesDto (StocksHistoricPrices historic){
    this.idStock = historic.getStock().getId();
    this.open = historic.getOpen();
    this.close = historic.getClose();
    this.high = historic.getHigh();
    this.low = historic.getLow();
    this.createdOn = historic.getCreated();
}
}
