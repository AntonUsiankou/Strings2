import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Runner {
    public static void main(String[] args) {

        final String FILE_NAME = "in";
        final String ERROR_MESSAGE = "File not found";

        final String SUM_MESSAGE = "sum = ";
        final String ERROR_LINES_MESSAGE = "error-lines = ";

        final String KEY_REG_EXP = "index(.*)";
        final String NUM_REG_EXP = "[1-9]\\d*";
        final int TAIL_INDEX = 1;
        final String VALUE = "value";

        double result = 0.0;
        int errors = 0;

        try {
            ResourceBundle rb = ResourceBundle.getBundle(FILE_NAME);
            Enumeration<String> keys = rb.getKeys();
            Pattern keyRegExpPattern = Pattern.compile(KEY_REG_EXP);
            Pattern numRegExpPattern = Pattern.compile(NUM_REG_EXP);

            while (keys.hasMoreElements()) {
                String key = keys.nextElement();

                Matcher keyMatcher = keyRegExpPattern.matcher(key);

                if (keyMatcher.matches()) {
                    String iStr = keyMatcher.group(TAIL_INDEX);
                    String jStr = rb.getString(key).trim();

                    Matcher iMatcher = numRegExpPattern.matcher(iStr);
                    Matcher jMatcher = numRegExpPattern.matcher(jStr);

                    if (iMatcher.matches() && jMatcher.matches()) {
                        String valueIJ = VALUE + iStr + jStr;
                        try {
                            String number = rb.getString(valueIJ).trim();
                            result += Double.parseDouble(number);

                        } catch (MissingResourceException | NumberFormatException e) {
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