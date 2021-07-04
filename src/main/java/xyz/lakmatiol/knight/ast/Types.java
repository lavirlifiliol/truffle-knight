package xyz.lakmatiol.knight.ast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.TypeCast;
import com.oracle.truffle.api.dsl.TypeCheck;
import com.oracle.truffle.api.dsl.TypeSystem;
import xyz.lakmatiol.knight.types.Null;

@TypeSystem({long.class, boolean.class})
public class Types {
    @TypeCheck(Null.class)
    public static boolean isNull(Object v) {
        return v == Null.SINGLETON;
    }

    @TypeCast(Null.class)
    public static Null asNull(Object v) {
        assert isNull(v);
        return Null.SINGLETON;
    }

    @CompilerDirectives.TruffleBoundary
    public static long longCoerce(Object v) {
        if (v instanceof String s) {
            return Long.parseLong(s);
        }
        if (v instanceof Long l) {
            return l;
        }
        if (v == Null.SINGLETON) {
            return 0;
        }
        if (v instanceof Boolean b) {
            return b ? 1 : 0;
        }
        throw new RuntimeException("Cannot coerce non Knight objects");
    }

    @CompilerDirectives.TruffleBoundary
    public static String stringCoerce(Object v) {
        return v.toString();
    }

    public static boolean boolCoerce(Object v) {
        if (v instanceof String s) {
            return !s.isEmpty();
        }
        if (v instanceof Long l) {
            return l != 0;
        }
        if (v == Null.SINGLETON) {
            return false;
        }
        if (v instanceof Boolean b) {
            return b;
        }
        throw new RuntimeException("Cannot coerce non Knight objects");
    }
}
