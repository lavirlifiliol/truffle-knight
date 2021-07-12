package xyz.lakmatiol.knight.ast.unary;

import com.oracle.truffle.api.dsl.Specialization;
import xyz.lakmatiol.knight.ast.Types;

public abstract class Neg extends Unary{
    @Specialization
    public boolean negBool(boolean b) {
        return !b;
    }
    @Specialization
    public boolean negGeneric(Object b) {
        return negBool(Types.boolCoerce(b));
    }
}
