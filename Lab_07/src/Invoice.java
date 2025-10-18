import java.util.Map;

public class Invoice
{
    private Customer customer;
    private Map<Product, Integer> products;

    public Invoice(Customer customer, Map<Product, Integer> products) {
        this.customer = customer;
        this.products = products;
    }

    public void addProduct(Product newProduct, int numberOfUnits)
    {
        products.put(newProduct, numberOfUnits);
    }
}
