package xyz.lakmatiol.knight;

import com.oracle.truffle.api.TruffleLanguage;
import xyz.lakmatiol.knight.types.Type;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Map;

public final class Context {
    public Map<String, Object> variables;
    public TruffleLanguage.Env env;
    public BufferedReader in;
    public PrintStream out;
    public Context(Map<String, Object> variables, TruffleLanguage.Env env) {
        this.env = env;
        this.variables = variables;
        this.in = new BufferedReader(new InputStreamReader(env.in()));
        this.out = new PrintStream(env.out());
    }
}
