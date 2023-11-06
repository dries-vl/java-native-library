package api;

import org.graalvm.nativeimage.c.CContext;

import java.util.Collections;
import java.util.List;

public class CApiDefinition implements CContext.Directives {
    @Override
    public List<String> getHeaderFiles() {
        return Collections.singletonList(
                "\"C:\\Dries Van Leuvenhaege\\Organisatie\\Computer Science\\Java\\nativelib\\src\\main\\resources\\c_api.h\"");
    }
}
