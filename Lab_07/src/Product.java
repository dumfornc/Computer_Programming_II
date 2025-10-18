public class Product
{
    private String name;
    private double unitCost;

    public Product(String name, double unitCost)
    {
        this.name = name;
        this.unitCost = unitCost;
    }

    //No setters for name because a product shouldn't change names, if it does it's a new product.
    public String getName()
    {
        return this.name;
    }

    public double getCost(int numberOfUnits) {
        return this.unitCost * numberOfUnits;
    }

    public double getCost()
    {
        return getCost(1);
    }

    public void setUnitCost(double newUnitCost)
    {
        this.unitCost = newUnitCost;
    }
}
