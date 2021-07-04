package xyz.lakmatiol.knight.types;

import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.utilities.TriState;
import xyz.lakmatiol.knight.Knight;

@ExportLibrary(InteropLibrary.class)
public class Null implements TruffleObject {
    public static final Null SINGLETON = new Null();
    private static final int ID_HASH = System.identityHashCode(SINGLETON);

    @Override
    public String toString() {
        return "null";
    }

    private Null() {
        super();
    }

    @ExportMessage
    boolean hasLanguage() {
        return true;
    }

    @ExportMessage
    Class<? extends TruffleLanguage<?>> getLanguage() {
        return Knight.class;
    }

    @ExportMessage
    boolean hasMetaObject() {
        return true;
    }

    @ExportMessage
    Object getMetaObject() {
        return Type.NULL;
    }

    @ExportMessage
    static TriState isIdenticalOrUndefined(Null r, Object other) {
        return TriState.valueOf(Null.SINGLETON == other);
    }

    @ExportMessage
    static int identityHashCode(Null r) {
        return ID_HASH;
    }

    @ExportMessage
    Object toDisplayString(boolean ignored) {
        return "null";
    }
}
