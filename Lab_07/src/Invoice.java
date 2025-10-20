import java.util.Map;

public class Invoice
{
    private final Customer customer;
    private Map<Product, Integer> products;

    private final String invoiceItemFormat = "%-30s%5d%11.02f%11.02f\n";

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
        invoiceString.append("\nInvoice:\n");
        invoiceString.append(customer.getFullAddress()).append("\n");
        invoiceString.append("-".repeat(57)).append("\n");
        invoiceString.append("%-30s%5s%11s%11s\n".formatted("Item", "Qty", "Price ($)", "Total ($)"));

        for(Map.Entry<Product, Integer> orderLine : products.entrySet())
        {
            invoiceString.append(invoiceItemFormat.formatted(
                    orderLine.getKey().getName(),
                    orderLine.getValue(),
                    orderLine.getKey().getCost(),
                    orderLine.getKey().getCost(orderLine.getValue())
            ));
        }


        invoiceString.append("-".repeat(57)).append("\n");
        invoiceString.append("Amount Due: $").append(getTotalCost());

        return invoiceString.toString();
    }
}
