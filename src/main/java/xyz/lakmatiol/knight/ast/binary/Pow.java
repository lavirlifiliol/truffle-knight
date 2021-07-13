package xyz.lakmatiol.knight.ast.binary;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import xyz.lakmatiol.knight.ast.TypesGen;

@NodeInfo(shortName = "^")
public abstract class Pow extends Binary {
    public long ipow(long base, long exp) {
        long res = 1;
        while(true) {
            if(exp % 2 == 1) {
                res *= base;
            }
            exp /= 2;
            if(exp <= 0) {
                break;
            }
            base *= base;
        }
        return res;
    }
    @Specialization
    public long doPow(long l, long r){
        return ipow(l, r);
    }
    @Specialization
    public long doPowGen(long l, Object r) {
        return ipow(l, TypesGen.longCoerce(r));
    }
    @Specialization
    public long doUB(Object l, Object r) {
        throw new RuntimeException("UB");
    }
}
