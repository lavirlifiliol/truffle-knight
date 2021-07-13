package xyz.lakmatiol.knight.ast.other;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import xyz.lakmatiol.knight.ast.Expression;
import xyz.lakmatiol.knight.ast.Types;

@NodeChild("a")
@NodeChild("s")
@NodeChild("l")
@NodeChild("n")
public abstract class Sub extends Expression {
    private String doSub(String a, long start, long length, String n) {
        int s = (int) start;
        int l = (int) length;
        return String.join("", a.substring(0, s), n, a.substring(s + l));
    }
    @Specialization
    String sub(String a, long s, long l, String n) {
        return doSub(a, s, l, n);
    }
    @Specialization
    String subGen(Object a, Object s, Object l, Object n) {
        return doSub(Types.stringCoerce(a), Types.longCoerce(s), Types.longCoerce(l), Types.stringCoerce(n));
    }
}
