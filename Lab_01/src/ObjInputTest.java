public class ObjInputTest
{
    public static void main(String[] args)
    {
        SafeInputObj safeInput = new SafeInputObj();

        String nonZeroStr = safeInput.getNonZeroLenString("Input a string");
        String minStr = safeInput.getMinLenString("Input a string longer than 6 characters", 6);
        int rangedInt = safeInput.getRangedInt("Input an integer between 1 & 10", 1, 10);
        int justInt = safeInput.getInt("Input an integer");
        double rangedDouble = safeInput.getRangedDouble("Input a double between e & pi", 2.718, 3.141);
        double justDouble = safeInput.getDouble("Input a double");
        boolean yesNo = safeInput.getYNConfirm("Input yes or no");
        String regexPhone = safeInput.getRegExString("Input a phone number", "\\(?\\d{3}\\)?[ -]?\\d{3}[ -]?\\d{4}");

        System.out.println("Non Zero String Input: '" + nonZeroStr + "'");
        System.out.println("Min Length String Input: '" + minStr + "'");
        System.out.println("Ranged Int Input: " + rangedInt);
        System.out.println("Just Int Input: " + justInt);
        System.out.println("Ranged Double Input: " + rangedDouble);
        System.out.println("Just Double Input: " + justDouble);
        System.out.println("Yes/No Confirmation: " + yesNo);
        System.out.println("Phone Number (Regex) Input: '" + regexPhone + "'");
    }
}
