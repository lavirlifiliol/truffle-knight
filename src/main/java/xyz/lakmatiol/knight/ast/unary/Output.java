package xyz.lakmatiol.knight.ast.unary;

import com.oracle.truffle.api.dsl.CachedContext;
import com.oracle.truffle.api.dsl.Specialization;
import xyz.lakmatiol.knight.Context;
import xyz.lakmatiol.knight.Knight;
import xyz.lakmatiol.knight.ast.Types;
import xyz.lakmatiol.knight.types.Null;

public abstract class Output extends Unary{
    @Specialization
    public Null outputString(String s, @CachedContext(Knight.class)Context c) {
        if(s.endsWith("\\")) {
            c.out.print(s.subSequence(0, s.length() - 1));
        }
        else {
            c.out.println(s);
        }
        return Null.SINGLETON;
    }
    @Specialization
    public Null outputGeneric(Object s, @CachedContext(Knight.class)Context c) {
        return outputString(Types.stringCoerce(s), c);
    }
}
