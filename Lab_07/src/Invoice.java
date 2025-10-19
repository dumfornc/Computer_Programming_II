import java.util.Map;

public class Invoice
{
    private final Customer customer;
    private Map<Product, Integer> products;

    private final String invoiceItemFormat = "%30s%3d$%5.02f$%5.02f\n";

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

    @Override
    public String toString()
    {
        StringBuilder invoiceString = new StringBuilder();
        invoiceString.append("Invoice:\n");
        invoiceString.append(customer.getFullAddress()).append("\n");
        invoiceString.append("-".repeat(43)).append("\n");
        invoiceString.append("%30s%3s$%5s$%5sf\n".formatted("Item", "Qty", "Price", "Total"));

        for(Map.Entry<Product, Integer> orderLine : products.entrySet())
        {
            invoiceString.append(invoiceItemFormat.formatted(
                    orderLine.getKey().getName(),
                    orderLine.getValue(),
                    orderLine.getKey().getCost(),
                    orderLine.getKey().getCost(orderLine.getValue())
            ));
        }


        invoiceString.append("-".repeat(43)).append("\n");
        invoiceString.append("Amount Due: $").append(getTotalCost());

        return invoiceString.toString();
    }
}
