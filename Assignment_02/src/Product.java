import java.util.Objects;

public class Product
{
    public static final int randomAccessIdLength = 6;
    public static final int randomAccessNameLength = 35;
    public static final int randomAccessDescLength = 75;

    private final String id;
    private String name;
    private String description;
    private double cost;

    /**
     * Constructor that creates a Product object
     * @param id - Unique product ID
     * @param name - Product name
     * @param description - Short description of the product
     * @param cost - Product cost
     */
    public Product(String id, String name, String description, double cost)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
    }

    /**
     * Gets the ID tied to this product
     * @return - String ID
     */
    public String getId()
    {
        return id.trim();
    }

    /**
     * Gets the ID tied to this product, always returns a fixed length for RandomAccess files
     * @return - String ID, will always be Product.randomAccessIdLength characters long
     */
    public String getRandomAccessId()
    {
        int idLength = this.id.length();

        if(idLength > Product.randomAccessIdLength)
        {
            return this.id.substring(0, Product.randomAccessIdLength);
        }
        else if(idLength == Product.randomAccessIdLength)
        {
            return this.id;
        }
        else//if(idLength < Product.randomAccessIdLength)
        {
            int paddingSpaces = Product.randomAccessIdLength - idLength;
            return this.id + " ".repeat(paddingSpaces);
        }
    }

    // No ID setter (ID should not change once set)

    /**
     * Gets the name of this product
     * @return - String name
     */
    public String getName()
    {
        return name.trim();
    }

    /**
     * Gets the name of this product, always returns a fixed length for RandomAccess files
     * @return - String name, will always be Product.randomAccessNameLength characters long
     */
    public String getRandomAccessName()
    {
        int nameLength = this.name.length();

        if(nameLength > Product.randomAccessNameLength)
        {
            return this.name.substring(0, Product.randomAccessNameLength);
        }
        else if(nameLength == Product.randomAccessNameLength)
        {
            return this.name;
        }
        else//if(nameLength < this.randomAccessNameLength)
        {
            int paddingSpaces = Product.randomAccessNameLength - nameLength;
            return this.name + " ".repeat(paddingSpaces);
        }
    }

    /**
     * Sets the name of this product
     * @param name - String product name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Gets the description of this product
     * @return - String description
     */
    public String getDescription()
    {
        return description.trim();
    }

    /**
     * Gets the description of this product, always returns a fixed length for RandomAccess files
     * @return - String description, will always be Product.randomAccessDescLength characters long
     */
    public String getRandomAccessDescription()
    {
        int descLength = this.description.length();

        if(descLength > Product.randomAccessDescLength)
        {
            return this.description.substring(0, Product.randomAccessDescLength);
        }
        else if(descLength == Product.randomAccessDescLength)
        {
            return this.description;
        }
        else//if(randomAccessDescLength < this.randomAccessDescLength)
        {
            int paddingSpaces = Product.randomAccessDescLength - descLength;
            return this.description + " ".repeat(paddingSpaces);
        }
    }

    /**
     * Sets the description of this product
     * @param description - String product description
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Gets the cost of this product
     * @return - Double product cost in USD
     */
    public double getCost()
    {
        return cost;
    }

    /**
     * Sets the cost of this product
     * @param cost - Double product cost in USD
     */
    public void setCost(double cost)
    {
        this.cost = cost;
    }

    /**
     * Returns product data formatted like a line in a CSV file
     * @return - String "id, name, description, cost"
     */
    public String toCSV()
    {
        return "%s, %s, %s, %.2f".formatted(this.id, this.name, this.description, this.cost);
    }

    /**
     * Returns product data formatted like a JSON object
     * @return - String of JSON data
     */
    public String toJSON()
    {
        String jsonFormat = """
                {
                  "id": "%s",
                  "name": "%s",
                  "description": "%s",
                  "cost": %.2f
                }
                """;
        return jsonFormat.formatted(this.id, this.name, this.description, this.cost);
    }

    /**
     * Returns product data formatted like an XML object
     * @return - String of XML data
     */
    public String toXML()
    {
        String xmlFormat = """
            <product>
              <id>%s</id>
              <name>%s</name>
              <description>%s</description>
              <cost>%.2f</cost>
            </product>
            """;
        return xmlFormat.formatted(this.id, this.name, this.description, this.cost);
    }

    @Override
    public String toString()
    {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.getCost(), getCost()) == 0 &&
                Objects.equals(getId(), product.getId()) &&
                Objects.equals(getName(), product.getName()) &&
                Objects.equals(getDescription(), product.getDescription());
    }
}