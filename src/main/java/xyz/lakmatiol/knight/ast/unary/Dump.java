package xyz.lakmatiol.knight.ast.unary;

import com.oracle.truffle.api.dsl.Specialization;
import xyz.lakmatiol.knight.types.Null;

public abstract class Dump extends Unary {
    @Specialization
    public Null dumpNull(Null n) {
        System.out.print("Null()");
        return Null.SINGLETON;
    }
    @Specialization
    public Null dumpString(String n) {
        System.out.print("String(");
        System.out.print(n);
        System.out.print(')');
        return Null.SINGLETON;
    }
    @Specialization
    public Null dumpBoolean(boolean n) {
        System.out.printf("Boolean(%s)", n?"true":"false");
        return Null.SINGLETON;
    }
    @Specialization
    public Null dumpNumber(long n) {
        System.out.printf("Number(%d)", n);
        return Null.SINGLETON;
    }
    @Specialization
    public Null dumpGeneric(Object c) {
        System.out.printf("Other(%s)", c.toString());
        return Null.SINGLETON;
    }
}
