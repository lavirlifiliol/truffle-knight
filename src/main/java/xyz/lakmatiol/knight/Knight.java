package xyz.lakmatiol.knight;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.instrumentation.ProvidedTags;
import com.oracle.truffle.api.instrumentation.StandardTags;

import java.util.HashMap;

@TruffleLanguage.Registration
        (id = Knight.ID
                , name = "Knight"
                , defaultMimeType = Knight.MIME
                , characterMimeTypes = {Knight.MIME}
                , contextPolicy = TruffleLanguage.ContextPolicy.EXCLUSIVE
        )
@ProvidedTags({StandardTags.ExpressionTag.class, StandardTags.ReadVariableTag.class, StandardTags.WriteVariableTag.class, StandardTags.CallTag.class, StandardTags.RootTag.class})
public class Knight extends TruffleLanguage<Context> {
    final static public String ID = "Knight";
    final static public String MIME = "application/x-knight";

    @Override
    protected CallTarget parse(ParsingRequest request) throws Exception {
        return Truffle.getRuntime().createCallTarget(new Parser(request.getSource().getReader(), this).parse());
    }

    @Override
    protected Context createContext(Env env) {
        return new Context(new HashMap<>(), env);
    }

    @Override
    protected Object getLanguageView(Context context, Object value) {
        return super.getLanguageView(context, value);
    }

    @Override
    protected boolean isVisible(Context context, Object value) {
        return true;
    }

    @Override
    protected Object getScope(Context context) {
        return context.variables;
    }
}
