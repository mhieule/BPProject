package excelchaos_model.datamodel.employeedataoperations;

import java.util.Arrays;
import java.util.Locale;

public class CountryModel {

    /**
     * Returns an array of strings representing the names of all countries in the world,
     * sorted alphabetically.
     *
     * @return an array of strings representing the names of all countries in the world, sorted alphabetically.
     */
    public static String[] getCountries() {
        String[] countryCodes = Locale.getISOCountries();
        String[] countries = new String[countryCodes.length];
        for (int i = 0; i < countryCodes.length; i++) {
            Locale obj = new Locale("", countryCodes[i]);
            countries[i] = obj.getDisplayCountry();
        }
        //sort array alphabetically
        Arrays.sort(countries);
        return countries;
    }

    /**
     * Returns an array of strings representing the names of all countries in the world,
     * sorted alphabetically. Standard value is "Keine".
     *
     * @return an array of strings representing the names of all countries in the world, sorted alphabetically.
     */
    public static String[] getCountriesWithKeine() {
        String[] countryCodes = Locale.getISOCountries();
        String[] countries = new String[countryCodes.length + 1];
        countries[0] = "Keine";
        for (int i = 0; i < countryCodes.length; i++) {
            Locale obj = new Locale("", countryCodes[i]);
            countries[i + 1] = obj.getDisplayCountry();
        }
        Arrays.sort(countries);
        // "Keine" steht an erster Stelle als Vorauswahl
        if (countries[0] != "Keine") {
            int j = 0;
            for (String s : countries) {

                if (s == "Keine") {
                    for (int i = j; i < 1; i--) {
                        countries[i] = countries[i - 1];
                    }
                    countries[0] = s;
                    break;
                }
                j++;
            }
        }

        return countries;
    }
}
