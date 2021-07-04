package xyz.lakmatiol.knight.ast;

import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;

public class Root extends RootNode {
    @Child private Expression program;
    public Root(TruffleLanguage<?> language, Expression program) {
        super(language);
        this.program = program;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return program.execute(frame);
    }
}
