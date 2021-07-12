package xyz.lakmatiol.knight.ast.unary;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import xyz.lakmatiol.knight.ast.Expression;

public abstract class Call extends Unary {
    @Specialization
    public Object callNode( VirtualFrame frame, Expression s) {
        return s.execute(frame);
    }
    @Specialization
    public Object callGeneric(Object s) {
        throw new RuntimeException("UB");
    }
}
