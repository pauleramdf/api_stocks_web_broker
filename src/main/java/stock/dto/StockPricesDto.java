package stock.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class StockPricesDto {

    private Long idStock;
    private Double askMin;
    private Double askMax;
    private Double bidMin;
    private Double bidMax;

}

