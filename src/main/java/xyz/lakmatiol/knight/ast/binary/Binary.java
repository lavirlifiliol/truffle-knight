package xyz.lakmatiol.knight.ast.binary;

import com.oracle.truffle.api.dsl.NodeChild;
import xyz.lakmatiol.knight.ast.Expression;

@NodeChild("left")
@NodeChild("right")
public abstract class Binary extends Expression {
}
