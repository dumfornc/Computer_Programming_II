import java.util.Objects;

public class Worker extends Person
{
    double hourlyPayRate;

    /**
     * Constructor that creates a worker object
     * @param firstName - A string holding a person's first name
     * @param lastName - A string holding a person's last name
     * @param ID - A unique 6 digit string of numbers
     * @param title - A string holding the honorific or formal title of a person
     * @param YoB - An integer in the range 1940 - 2010
     * @param hourlyPayRate - A double representing the hourly rate of a worker person
     */
    public Worker(String ID, String firstName, String lastName, String title, int YoB, double hourlyPayRate)
    {
        super(ID, firstName, lastName, title, YoB);
        this.hourlyPayRate = hourlyPayRate;
    }

    /**
     * Gets the hourly rate of the worker this object represents
     * @return - Double the number of dollars per hour the worker this object represents is paid
     */
    public double getHourlyPayRate()
    {
        return hourlyPayRate;
    }

    /**
     * Sets the hourly pay rate of the worker this instance of the object represents
     * @param hourlyPayRate - Double
     */
    public void setHourlyPayRate(double hourlyPayRate)
    {
        this.hourlyPayRate = hourlyPayRate;
    }

    /**
     * Calculates the amount of pay a worker earned in a week given the number of hours they worked. Factors in overtime.
     * @param hoursWorked - The number of hours the worker worked
     * @return Double - The number of USD the worker earned in the week
     */
    public double calculateWeeklyPay(double hoursWorked)
    {
        double weeklyPay = 0;

        if (hoursWorked <= 40)
        {
            weeklyPay = hoursWorked * this.hourlyPayRate;
        } else if (hoursWorked > 40)
        {
            double overtimeHours = hoursWorked - 40;
            double normalHours = 40;
            weeklyPay = ((overtimeHours * 1.5) + normalHours) * this.hourlyPayRate;
        }

        return weeklyPay;
    }

    /**
     * Outputs to the console the number of hours worked and pay due, sorted by regular, overtime, and the total combined values.
     * @param hoursWorked - The number of hours the worker worked
     */
    public void displayWeeklyPay(double hoursWorked)
    {
        double regularHours = 0;
        double regularPay = 0;
        double overtimeHours = 0;
        double overtimePay = 0;
        double totalPay;

        if (hoursWorked <= 40)
        {
            regularHours = hoursWorked;
            regularPay = hoursWorked * this.hourlyPayRate;

            overtimeHours = 0;
            overtimePay = 0;
        } else if (hoursWorked > 40)
        {
            regularHours = 40;
            regularPay = 40 * this.hourlyPayRate;

            overtimeHours = hoursWorked - 40;
            overtimePay = overtimeHours * 1.5 * this.hourlyPayRate;
        }

        totalPay = regularPay + overtimePay;

        System.out.println(super.getFirstName() + " worked " + hoursWorked + " hours:");
        System.out.println(regularHours + " at regular pay; earning: $" + regularPay);
        System.out.println(overtimeHours + " at overtime pay; earning: $" + overtimePay);
        System.out.println("Earning a total of $" + totalPay);
    }

    /**
     * Returns worker data formatted like a JSON object
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
                  "yob": %d,
                  "hourlyPayRate": %d
                }
                """;

        return jsonFormat.formatted(super.getID(), super.getFirstName(), super.getLastName(), super.getTitle(), super.getYoB(), this.hourlyPayRate);
    }

    /**
     * Returns worker data formatted like an XML object
     * @return - String of xml data
     */
    public String toXML()
    {
        String xmlFormat = """
            <worker>
              <id>%s</id>
              <firstName>%s</firstName>
              <lastName>%s</lastName>
              <title>%s</title>
              <yob>%d</yob>
              <hourlyPayRate>%d</hourlyPayRate>
            </worker>
            """;

        return xmlFormat.formatted(super.getID(), super.getFirstName(), super.getLastName(), super.getTitle(), super.getYoB(), this.hourlyPayRate);
    }

    /**
     * Returns overrides worker string return to be more legible
     * @return - String of worker data
     */
    @Override
    public String toString()
    {
        return "Person{" +
                "firstName='" + super.getFirstName() + '\'' +
                ", lastName='" + super.getLastName() + '\'' +
                ", ID='" + super.getID() + '\'' +
                ", title='" + super.getTitle() + '\'' +
                ", YoB=" + super.getYoB() + '\'' +
                ", hourlyPayRate=" + this.hourlyPayRate +
                '}';
    }
}
