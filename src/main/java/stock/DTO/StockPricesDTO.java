package stock.DTO;

public class StockPricesDTO {

    private Long Id;
    private String stock_symbol;
    private String stock_name;
    private Double price;


    public StockPricesDTO(){};

    public StockPricesDTO(Long id, String stock_symbol, String stock_name, Double price) {
        Id = id;
        this.stock_symbol = stock_symbol;
        this.stock_name = stock_name;
        this.price = price;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getStock_symbol() {
        return stock_symbol;
    }

    public void setStock_symbol(String stock_symbol) {
        this.stock_symbol = stock_symbol;
    }

    public String getStock_name() {
        return stock_name;
    }

    public void setStock_name(String stock_name) {
        this.stock_name = stock_name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
