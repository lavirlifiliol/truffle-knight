package xyz.lakmatiol.knight.ast.binary;

import com.oracle.truffle.api.frame.VirtualFrame;
import xyz.lakmatiol.knight.Knight;
import xyz.lakmatiol.knight.ast.Expression;
import xyz.lakmatiol.knight.ast.nullary.Var;

public class Set extends Expression {
    @Child
    public Var name;
    @Child
    public Expression value;

    public Set(Var name, Expression value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        Object res = value.execute(frame);
        lookupContextReference(Knight.class).get().variables.put(name.name, res);
        return res;
    }
}
