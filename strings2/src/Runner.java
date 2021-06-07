import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Runner {
    public static void main(String[] args) {

        final String SUM_MESSAGE = "sum = ";
        final String ERROR_LINES_MESSAGE = "error-lines = ";
        final String KEY_INDEX = "index";
        final String KEY_VALUE = "value";
        final int KEY_INDEX_LENGHT = KEY_INDEX.length();
        final String PATTERN_VALIDATION = "(^[0]|[^0-9]|^$)";
        final String PATTERN_FOR_INDEX = "[0-9]+";
        final String FILE_NAME = "in2";
        final String ERROR_MESSAGE = "Input file is not found";
        final String LANGUAGE_CONSTANT = "en";
        final String COUNTRY_CONSTANT = "EN";

        double result = 0.0;
        int errors = 0;

        try{
            Locale locale = new Locale(LANGUAGE_CONSTANT,COUNTRY_CONSTANT);
            ResourceBundle rb = ResourceBundle.getBundle(FILE_NAME, locale);
            Enumeration<String> keys = rb.getKeys();
            String key;
            while(keys.hasMoreElements()) {
                key = keys.nextElement();

                if(key.substring(0, KEY_INDEX_LENGHT).equals(KEY_INDEX)){

                    Pattern pattern1 = Pattern.compile(PATTERN_VALIDATION);
                    Matcher matcher1 = pattern1.matcher(key.substring(KEY_INDEX_LENGHT));
                    if(matcher1.find()){
                        errors++;
                        continue;
                    }

                    matcher1 = pattern1.matcher(rb.getString(key));
                    if(matcher1.find()){
                        errors++;
                        continue;
                    }

                    StringBuilder index = new StringBuilder();
                    Pattern pattern2 = Pattern.compile(PATTERN_FOR_INDEX);
                    Matcher matcher2 = pattern2.matcher(key + rb.getString(key));
                    while(matcher2.find()){
                        index.append(matcher2.group());
                    }
                    try{
                        String number = rb.getString(KEY_VALUE + index.toString()).trim();
                        result += Double.parseDouble(number);
                    }catch (MissingResourceException| NumberFormatException e){
                        errors++;
                    }
                }
            }

            System.out.println(SUM_MESSAGE + result);
            System.out.println(ERROR_LINES_MESSAGE + errors);

        }catch (MissingResourceException e){
            System.out.println(ERROR_MESSAGE);
        }
    }
}