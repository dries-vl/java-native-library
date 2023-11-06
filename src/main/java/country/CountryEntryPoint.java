package country;

import org.graalvm.nativeimage.IsolateThread;
import org.graalvm.nativeimage.c.function.CEntryPoint;
import org.graalvm.word.Pointer;

import java.nio.charset.StandardCharsets;

public class CountryEntryPoint {
    // method to load all the data and keep it active
    // method to modify a country data to see if it reflects in gdscript, ie. if it uses the same memory

    // All the references passed to Godot Engine get freed when it goes down
    // results in trying to access freed memory
    @CEntryPoint(name = "load_countries")
    public static void loadCountries(IsolateThread thread) {
        System.out.println("Java: call to reload countries");
        CountryRepository.loadCountries();
        System.out.println("Java: countries reloaded");
    }

    @CEntryPoint(name = "get_country")
    public static Pointer getCountry(IsolateThread thread, int index) {
        System.out.println("Java: called get");
        Country country = (Country) CountryRepository.getCountry(index);
        System.out.println("Java: gotten pointer to id string: " + country.id().rawValue());
        return (Pointer) country;
    }

    @CEntryPoint(name = "update_country")
    public static void updateCountry(IsolateThread thread, int index) {
        System.out.println("Java: called update");
        Country country = (Country) CountryRepository.getCountry(index);
        System.out.println("Java: gotten pointer to struct: " + country.rawValue());
        System.out.println("Java: memorized pointer to struct: " + CountryRepository.pointerToStruct);
        System.out.println("Java: gotten pointer to id string: " + country.id().rawValue());
        System.out.println("Java: gotten pointer to id string: " + country.id().rawValue());
        System.out.println("Java: gotten pointer to id string: " + country.id().rawValue());
        System.out.println("Java: gotten pointer to id string: " + country.id().rawValue());
        System.out.println("Java: memorized pointer to id string: " + CountryRepository.pointerToId);
        int char_index = 0;
        if (country.id().rawValue() == CountryRepository.pointerToId) {
            for (byte character : "A new Id".getBytes(StandardCharsets.UTF_8)) {
                System.out.println("Java: replace at index: " + char_index);
                country.id().write(char_index, character);
                char_index++;
            }
            country.id().write(char_index, (byte) 0);
        } else {
            System.out.println("CORRUPTED POINTER TO ID");
        }
        System.out.println("Java: update done");
    }
}
