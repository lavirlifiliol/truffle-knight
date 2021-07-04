package xyz.lakmatiol.knight.ast.unary;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import xyz.lakmatiol.knight.ast.Types;

import java.io.IOException;
import java.nio.file.Files;

@NodeInfo(shortName = "`")
public abstract class Shell extends Unary{
    public String call(String a) {
        try {
            var r = Runtime.getRuntime();
            var p = r.exec(a);
            return new String(p.getInputStream().readAllBytes());
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Specialization
    public String callGen(Object a) {
        return call(Types.stringCoerce(a));
    }
}
