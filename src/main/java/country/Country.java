package country;

import api.CApiDefinition;
import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.struct.CField;
import org.graalvm.nativeimage.c.struct.CStruct;
import org.graalvm.nativeimage.c.type.CCharPointer;
import org.graalvm.word.PointerBase;

@CContext(CApiDefinition.class)
@CStruct(value = "country")
public interface Country extends PointerBase {

    // A String: never move it to a new location after creation, because rust depends on it being there
    @CField("id")
    CCharPointer id();

    @CField("id")
    void id(CCharPointer id);

    @CField("name")
    CCharPointer name();

    @CField("name")
    void name(CCharPointer name);
}
