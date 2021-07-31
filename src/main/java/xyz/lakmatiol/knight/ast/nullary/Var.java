package xyz.lakmatiol.knight.ast.nullary;

import com.oracle.truffle.api.dsl.CachedContext;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import xyz.lakmatiol.knight.Context;
import xyz.lakmatiol.knight.Knight;
import xyz.lakmatiol.knight.ast.Expression;
import xyz.lakmatiol.knight.ast.Types;

/*
 * emmited for every variable
 */
public abstract class Var extends Expression {
    public final String name;

    public Var(String name) {
        this.name = name;
    }

    @Specialization
    public Object doLookup(
            @CachedContext(Knight.class)Context c
            ) {
        Object res = c.variables.get(name);
        if(res == null) {
            Types.errorUB();
        }
        return res;
    }

}
