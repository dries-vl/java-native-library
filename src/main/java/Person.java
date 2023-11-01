import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.struct.CField;
import org.graalvm.nativeimage.c.struct.CStruct;
import org.graalvm.word.PointerBase;

@CContext(Directives.class)
@CStruct(value = "person")
public interface Person extends PointerBase {

    @CField("age")
    int age();

    @CField("age")
    void age(int age);

    @CField("salary")
    double salary();

    @CField("salary")
    void salary(double salary);
}
