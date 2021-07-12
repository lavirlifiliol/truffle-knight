package xyz.lakmatiol.knight.ast.unary;

import com.oracle.truffle.api.dsl.Specialization;

public abstract class Ascii extends Unary{
    @Specialization
    public String asciiNum(long n) {
        return Character.toString((int)n);
    }
    @Specialization
    public long asciiString(String s) {
        return s.charAt(0);
    }
    @Specialization
    public Object asciiGeneric(Object ignored) {
        throw new RuntimeException("UB");
    }
}
