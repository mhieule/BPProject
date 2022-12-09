package excelchaos_model;

import java.util.Locale;

public class CountryModel {
    public static String[] getCountries(){
        String[] countryCodes = Locale.getISOCountries();
        String[] countries=new String[countryCodes.length];
        for (int i=0;i< countryCodes.length;i++) {
            Locale obj = new Locale("", countryCodes[i]);
            countries[i]= obj.getDisplayCountry();
        }
        return countries;
    }

    public static String[] getCountriesWithKeine(){
        String[] countryCodes = Locale.getISOCountries();
        String[] countries=new String[countryCodes.length+1];
        countries[0]="Keine";
        for (int i=0;i< countryCodes.length;i++) {
            Locale obj = new Locale("", countryCodes[i]);
            countries[i+1]= obj.getDisplayCountry();
        }
        return countries;
    }
}
