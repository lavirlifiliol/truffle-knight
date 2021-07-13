package xyz.lakmatiol.knight.ast.unary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import xyz.lakmatiol.knight.ast.Types;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;

@NodeInfo(shortName = "`")
public abstract class Shell extends Unary{
    @Specialization
    public String call(String a) {
        return doExec(a);
    }
    @Specialization
    public String callGen(Object a) {
        return call(Types.stringCoerce(a));
    }
    @CompilerDirectives.TruffleBoundary
    public String doExec(String in) {
        try {
            var r = Runtime.getRuntime();
            var p = r.exec(new String[]{"/usr/bin/sh", "-c", in});
            return new String(p.getInputStream().readAllBytes());
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
