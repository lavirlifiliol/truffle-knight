package xyz.lakmatiol.knight.ast.nullary;

import com.oracle.truffle.api.frame.VirtualFrame;
import xyz.lakmatiol.knight.ast.Expression;

public class Null extends Expression {

    @Override
    public Object execute(VirtualFrame frame) {
        return xyz.lakmatiol.knight.types.Null.SINGLETON;
    }
}
