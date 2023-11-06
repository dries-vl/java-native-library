package country;

import org.graalvm.nativeimage.UnmanagedMemory;
import org.graalvm.word.PointerBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountryEntryPointTest {

    @Test
    void testForMemoryCrash() {
        CountryRepository.loadCountries();
        PointerBase
        Country country = (Country) CountryRepository.getCountry(0);
        country.id().write(0, (byte) 1);

    }
}
