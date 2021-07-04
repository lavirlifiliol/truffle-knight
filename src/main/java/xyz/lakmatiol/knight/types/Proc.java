package xyz.lakmatiol.knight.types;

import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.utilities.TriState;
import xyz.lakmatiol.knight.Knight;
import xyz.lakmatiol.knight.ast.Call;

@ExportLibrary(InteropLibrary.class)
public class Proc implements TruffleObject {

    @Override
    public String toString() {
        return "PROC";
    }

    private Proc() {
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
        return Type.PROC;
    }

    @ExportMessage
    Object toDisplayString(boolean ignored) {
        return "PROC";
    }
}
