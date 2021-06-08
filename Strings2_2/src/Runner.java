import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Runner {
    public static void main(String[] args) {

        final String FILE_NAME = "in";
        final String ERROR_MESSAGE = "Input file is not found";
        final String LANGUAGE_CONSTANT = "en";
        final String COUNTRY_CONSTANT = "EN";

        final String SUM_MESSAGE = "sum = ";
        final String ERROR_LINES_MESSAGE = "error-lines = ";

        final String KEY_REG_EXP = "index(.*)";
        final String NUM_REG_EXP = "[1-9]\\d*";
        final int TAIL_INDEX = 1;
        final String VALUE = "value";

        double result = 0.0;
        int errors = 0;

        try {
            Locale locale = new Locale(LANGUAGE_CONSTANT,COUNTRY_CONSTANT);
            ResourceBundle rb = ResourceBundle.getBundle(FILE_NAME, locale);
            Enumeration<String> keys = rb.getKeys();
            String key;
            while(keys.hasMoreElements()) {
                key = keys.nextElement();
                Pattern pattern = Pattern.compile(KEY_REG_EXP);
                Matcher keyMatcher = pattern.matcher(key);

                if(keyMatcher.matches()) {
                    String iStr = keyMatcher.group(TAIL_INDEX);
                    String jStr = rb.getString(key).trim();

                    pattern = Pattern.compile(NUM_REG_EXP);
                    Matcher iMatcher = pattern.matcher(iStr);
                    Matcher jMatcher = pattern.matcher(jStr);

                    if(iMatcher.matches() && jMatcher.matches()) {
                        String valueIJ = VALUE + iStr + jStr;
                        try {
                            String number = rb.getString(valueIJ).trim();
                            result += Double.parseDouble(number);

                        } catch (MissingResourceException| NumberFormatException e){
                            errors++;
                        }
                    } else {
                        errors++;
                    }
                }
            }
            System.out.println(SUM_MESSAGE + result);
            System.out.println(ERROR_LINES_MESSAGE + errors);
        } catch (MissingResourceException e) {
            System.out.println(ERROR_MESSAGE);
        }
    }
}