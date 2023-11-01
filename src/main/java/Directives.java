import org.graalvm.nativeimage.c.CContext;

import java.util.Collections;
import java.util.List;

public class Directives implements CContext.Directives {
    @Override
    public List<String> getHeaderFiles() {
        return Collections.singletonList("\"C:\\Dries Van Leuvenhaege\\Organisatie\\Computer Science\\Java\\nativelib\\src\\main\\java\\person.h\"");
    }
}
