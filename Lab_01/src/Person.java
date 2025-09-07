import java.util.Calendar;
import java.util.Objects;

public class Person
{
    private final String ID;
    private String firstName;
    private String lastName;
    private String title;
    private int YoB;

    private final int currentYear;

    /**
     * Constructor that creates a person object
     * @param firstName - A string holding a person's first name
     * @param lastName - A string holding a person's last name
     * @param ID - A unique 6 digit string of numbers
     * @param title - A string holding the honorific or formal title of a person
     * @param YoB - An integer in the range 1940 - 2010
     */
    public Person(String ID, String firstName, String lastName, String title, int YoB)
    {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.YoB = YoB;

        Calendar calendar = Calendar.getInstance();
        this.currentYear = calendar.get(Calendar.YEAR);
    }

    /**
     * Gets the ID tied to the person this instance of the object represents
     * @return - String of 6 numeric digits
     */
    public String getID()
    {
        return ID;
    }

//    No ID setter because the ID shouldn't ever change

    /**
     * Gets the first name of the person this instance of the object represents
     * @return - String
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * Sets the first name of the person this instance of the object represents
     * @param firstName - String
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the person this instance of the object represents
     * @return - String
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * Sets the last name of the person this instance of the object represents
     * @param lastName - String
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    /**
     * Gets the formal title of the person this instance of the object represents
     * @return - String
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Sets the formal title of the person this instance of the object represents
     * @param title  - String
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * Gets the year of birth of the person this instance of the object represents
     * @return - Integer in range 1940 - 2010
     */
    public int getYoB()
    {
        return YoB;
    }

    /**
     * Sets the year of birth of the person this instance of the object represents
     * @param YoB  - Integer in range 1940 - 2010
     */
    public void setYoB(int YoB)
    {
        this.YoB = YoB;
    }

    /**
     * Gets the full name of the person this instance of the object represents
     * @return - String "firstName lastName"
     */
    public String fullName()
    {
        return this.firstName + " " + this.lastName;
    }

    /**
     * Gets the full formal name (including title) of the person this instance of the object represents
     * @return - String "title firstName lastName"
     */
    public String formalName()
    {
        return this.title + " " + this.fullName();
    }

    /**
     * Gets the age (assuming the current year) of the person this instance of the object represents
     * @return - Integer
     */
    public int getAge()
    {
        return this.currentYear - this.YoB;
    }

    /**
     * Gets the age that the person was or will be in the specified year
     * @return - Integer
     */
    public int getAge(int year)
    {
        return year - this.YoB;
    }

    /**
     * Returns person data formatted like a line in a csv file
     * @return - String "ID, firstName, lastName, title, YoB"
     */
    public String toCSV()
    {
        return "%s, %s, %s, %s, %d".formatted(this.ID, this.firstName, this.lastName, this.title, this.YoB);
    }

    /**
     * Returns person data formatted like a JSON object
     * @return - String of JSON data
     */
    public String toJSON()
    {
        String jsonFormat = """
                {
                  "id": "%s",
                  "firstName": "%s",
                  "lastName": "%s",
                  "title": "%s",
                  "yob": %d
                }
                """;

        return jsonFormat.formatted(this.ID, this.firstName, this.lastName, this.title, this.YoB);
    }

    /**
     * Returns person data formatted like an XML object
     * @return - String of xml data
     */
    public String toXML()
    {
        String xmlFormat = """
            <person>
              <id>%s</id>
              <firstName>%s</firstName>
              <lastName>%s</lastName>
              <title>%s</title>
              <yob>%d</yob>
            </person>
            """;

        return xmlFormat.formatted(this.ID, this.firstName, this.lastName, this.title, this.YoB);
    }

    @Override
    public String toString()
    {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", ID='" + ID + '\'' +
                ", title='" + title + '\'' +
                ", YoB=" + YoB +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return getYoB() == person.getYoB() && Objects.equals(getFirstName(), person.getFirstName()) && Objects.equals(getLastName(), person.getLastName()) && Objects.equals(getID(), person.getID()) && Objects.equals(getTitle(), person.getTitle());
    }
}