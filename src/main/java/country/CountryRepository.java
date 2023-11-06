package country;

import org.graalvm.nativeimage.UnmanagedMemory;
import org.graalvm.nativeimage.c.struct.SizeOf;
import org.graalvm.nativeimage.c.type.CCharPointer;
import org.graalvm.nativeimage.c.type.CTypeConversion;
import org.graalvm.word.Pointer;

import java.nio.charset.StandardCharsets;
import java.sql.*;

public class CountryRepository {

    public static Pointer[] countries = null;
    public static long pointerToId = 0L;
    public static long pointerToStruct = 0L;

    public static Pointer getCountry(int index) {
        return countries[index];
    }

    public static void loadCountries() {
        countries = new Pointer[100];
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Dries Van Leuvenhaege/Organisatie/Computer Science/Godot/Godot Projects/Godot-rust/data.db");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM country")) {

            int countryIndex = 0;
            while (rs.next()) {
                // Pointer for Country object
                Pointer countryPointer = UnmanagedMemory.malloc(SizeOf.get(Country.class));
                Country country = (Country) countryPointer;
                // Pointer for id string
                Pointer idPointer = UnmanagedMemory.malloc(64); // 64 bytes, 512 bit
                if (pointerToId == 0L) {
                    pointerToId = idPointer.rawValue();
                }
                if (pointerToStruct == 0L) {
                    pointerToStruct = countryPointer.rawValue();
                }
                CCharPointer charPointer = (CCharPointer) idPointer;
                System.out.println("id pointer" + idPointer.rawValue());
                System.out.println("id pointer" + charPointer.rawValue());
                country.id(charPointer);
                // fill pointer location with string
                int char_index = 0;
                for (byte character : rs.getString("id").getBytes(StandardCharsets.UTF_8)) {
                    charPointer.write(char_index, character);
                    char_index++;
                }
                country.id().write(char_index, (byte) 0);
                // rest
                country.name(CTypeConversion.toCString(rs.getString("name")).get());
                System.out.println("Java: " + CTypeConversion.toJavaString(country.id()));
                System.out.println("Java: " + CTypeConversion.toJavaString(country.name()));
                countries[countryIndex] = ((Pointer) country);
                countryIndex++;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
