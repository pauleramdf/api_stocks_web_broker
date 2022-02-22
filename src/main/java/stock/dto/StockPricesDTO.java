package stock.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class StockPricesDTO {

    @NotNull
    private Long id_stock;
    private Double ask_min;
    private Double ask_max;
    private Double bid_min;
    private Double bid_max;
}

