package xyz.lakmatiol.knight.ast.unary;

import com.oracle.truffle.api.dsl.Specialization;
import xyz.lakmatiol.knight.ast.Types;

public abstract class Quit extends Unary {
    @Specialization
    public Object quitNum(long status) {
        System.exit((int)status);
        return null;
    }
    @Specialization
    public Object quitGen(Object a) {
        quitNum(Types.longCoerce(a));
        return null;
    }
}
