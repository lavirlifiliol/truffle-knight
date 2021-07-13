package xyz.lakmatiol.knight.ast.nullary;

import com.oracle.truffle.api.frame.VirtualFrame;
import xyz.lakmatiol.knight.Knight;
import xyz.lakmatiol.knight.ast.Expression;

/*
 * emmited for every variable
 */
public class Var extends Expression {
    public final String name;

    public Var(String name) {
        this.name = name;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        var res = lookupContextReference(Knight.class).get().variables.get(name);
        if(res == null) {
            throw new RuntimeException("Undefined variable: " + name);
        }
        return res;
    }
}
