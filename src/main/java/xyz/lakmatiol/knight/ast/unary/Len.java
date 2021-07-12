package xyz.lakmatiol.knight.ast.unary;

import com.oracle.truffle.api.dsl.Specialization;
import xyz.lakmatiol.knight.ast.Types;

public abstract class Len extends Unary{
    @Specialization
    public long len(String s) {
        return s.length();
    }
    @Specialization
    public long lenGen(Object s) {
        return len(Types.stringCoerce(s));
    }
}
