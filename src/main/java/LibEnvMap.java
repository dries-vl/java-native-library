import org.graalvm.nativeimage.IsolateThread;
import org.graalvm.nativeimage.UnmanagedMemory;
import org.graalvm.nativeimage.c.function.CEntryPoint;
import org.graalvm.nativeimage.c.struct.SizeOf;
import org.graalvm.nativeimage.c.type.CCharPointer;
import org.graalvm.nativeimage.c.type.CTypeConversion;
import org.graalvm.word.Pointer;

import java.util.Map;

public class LibEnvMap {

    public static void main(String[] args) {
        System.out.println("Hello world !?");
    }

    @CEntryPoint(name = "create_person")
    public static Pointer createPerson(IsolateThread thread, int age, double salary) {
        Pointer pointer = UnmanagedMemory.malloc(SizeOf.get(Person.class));
        Person person = (Person) pointer;
        person.age(age);
        person.salary(salary);
        System.out.println(person.age() + ", " + person.salary());
        System.out.println(pointer.rawValue());
        return pointer;
    }

    @CEntryPoint(name = "filter_env")
    private static int filterEnv(IsolateThread thread, CCharPointer cFilter) {
        String filter = CTypeConversion.toJavaString(cFilter);
        Map<String, String> env = System.getenv();
        int count = 0;
        for (String envName : env.keySet()) {
            if(!envName.contains(filter)) continue;
            System.out.format("%s=%s%n",
                    envName,
                    env.get(envName));
            count++;
        }
        return count;
    }
}
