package xyz.lakmatiol.knight.types;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import xyz.lakmatiol.knight.Knight;

@ExportLibrary(InteropLibrary.class)
public class Type implements TruffleObject {

    private final String name;
    private final TypeCheck check;

    public static final Type NUMBER = new Type("NUMBER", InteropLibrary::fitsInLong);
    public static final Type STRING = new Type("STRING", InteropLibrary::isString);
    public static final Type BOOLEAN = new Type("BOOLEAN", InteropLibrary::isBoolean);
    public static final Type NULL = new Type("NULL", InteropLibrary::isNull);
    public static final Type PROC = new Type("PROC", (l, v) -> v instanceof Proc);

    private Type(String name, TypeCheck check) {
        this.name = name;
        this.check = check;
    }
    public boolean isInstance(Object v, InteropLibrary l) {
        CompilerAsserts.partialEvaluationConstant(this);
        return check.check(l, v);
    }

    @ExportMessage
    boolean hasLanguage(){return true;}

    @ExportMessage
    Class<? extends TruffleLanguage<?>> getLanguage(){return Knight.class;}

    @ExportMessage
    boolean isMetaObject(){return true;}

    @ExportMessage(name = "getMetaQualifiedName")
    @ExportMessage(name = "getMetaSimpleName")
    public Object getName(){return name;}

    @ExportMessage(name = "toDisplayString")
    Object toDisplayString(@SuppressWarnings("unused") boolean allowSideEffects) {
        return name;
    }

    @Override
    public String toString() {
        return "Type{" +
                "name='" + name + '\'' +
                '}';
    }

    @ExportMessage
    static class IsMetaInstance {

        /*
         * We assume that the same type is checked at a source location. Therefore we use an inline
         * cache to specialize for observed types to be constant. The limit of "3" specifies that we
         * specialize for 3 different types until we rewrite to the doGeneric case. The limit in
         * this example is somewhat arbitrary and should be determined using careful tuning with
         * real world benchmarks.
         */
        @Specialization(guards = "type == cachedType", limit = "3")
        static boolean doCached(@SuppressWarnings("unused") Type type, Object value,
                                @Cached("type") Type cachedType,
                                @CachedLibrary("value") InteropLibrary valueLib) {
            return cachedType.check.check(valueLib, value);
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization(replaces = "doCached")
        static boolean doGeneric(Type type, Object value) {
            return type.check.check(InteropLibrary.getFactory().getUncached(), value);
        }
    }



    @FunctionalInterface
    public interface TypeCheck {
        boolean check(InteropLibrary l, Object v);
    }
}
