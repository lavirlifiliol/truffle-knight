package xyz.lakmatiol.knight.ast.other;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import xyz.lakmatiol.knight.ast.Expression;
import xyz.lakmatiol.knight.ast.Types;

@NodeChild("a")
@NodeChild("s")
@NodeChild("e")
public abstract class Get extends Expression {
    @Specialization
    String substr(String a, long s, long e) {
        return a.substring((int) s, (int) e);
    }
    @Specialization
    String substrGen(Object a, Object s, Object e) {
        return Types.stringCoerce(a).substring((int) Types.longCoerce(s), (int) Types.longCoerce(e));
    }
}
