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

    public double getTotalCost()
    {
        double totalCost = 0;
        for(Map.Entry<Product, Integer> orderLine : products.entrySet())
        {
            totalCost += orderLine.getKey().getCost(orderLine.getValue());
        }

        return totalCost;
    }
}
