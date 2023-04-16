package user.dto.stocks;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class StockPricesDTO{

    private Long idStock;
    private Double askMin;
    private Double askMax;
    private Double bidMin;
    private Double bidMax;

}
