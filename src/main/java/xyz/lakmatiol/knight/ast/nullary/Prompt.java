package xyz.lakmatiol.knight.ast.nullary;

import com.oracle.truffle.api.dsl.CachedContext;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import xyz.lakmatiol.knight.Context;
import xyz.lakmatiol.knight.Knight;
import xyz.lakmatiol.knight.ast.Expression;

import java.io.IOException;

@NodeInfo(shortName = "PROMPT")
public abstract class Prompt extends Expression {
    @Specialization
    public String prompt(@CachedContext(Knight.class) Context ctx) {
        try {
            return ctx.in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
