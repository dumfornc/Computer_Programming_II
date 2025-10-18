public class Customer {
    private String name;
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;

    public Customer(String name, String streetAddress, String state, String zipCode)
    {
        this.name = name;
        this.streetAddress = streetAddress;
        this.state = state;
        this.zipCode = zipCode;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getStreetAddress()
    {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress)
    {
        this.streetAddress = streetAddress;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getZipCode()
    {
        return zipCode;
    }

    public void setZipCode(String zipCode)
    {
        this.zipCode = zipCode;
    }

    // The format string must take 4 arguments
    public String getFullAddress(String format)
    {
        return format.formatted(
                getStreetAddress(),
                getCity(),
                getState(),
                getZipCode()
        );
    }

    public String getFullAddress()
    {
        String defaultFormat = "%s %s, %s %s";

        return getFullAddress(defaultFormat);
    }
}