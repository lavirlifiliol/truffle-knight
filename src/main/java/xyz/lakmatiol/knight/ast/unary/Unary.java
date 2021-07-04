package xyz.lakmatiol.knight.ast.unary;

import com.oracle.truffle.api.dsl.NodeChild;
import xyz.lakmatiol.knight.ast.Expression;

@NodeChild("child")
public abstract class Unary extends Expression {
}
