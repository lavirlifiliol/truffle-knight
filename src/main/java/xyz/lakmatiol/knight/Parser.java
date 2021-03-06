package xyz.lakmatiol.knight;

import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import xyz.lakmatiol.knight.ast.Expression;
import xyz.lakmatiol.knight.ast.binary.*;
import xyz.lakmatiol.knight.ast.nullary.*;
import xyz.lakmatiol.knight.ast.Root;
import xyz.lakmatiol.knight.ast.other.GetNodeGen;
import xyz.lakmatiol.knight.ast.other.If;
import xyz.lakmatiol.knight.ast.other.SubNodeGen;
import xyz.lakmatiol.knight.ast.unary.*;

import java.io.*;
import java.lang.reflect.GenericArrayType;
import java.nio.CharBuffer;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


public class Parser {
    private record Token(TokenKind k, String data) {
        public boolean isSeq() {
            return k == TokenKind.FUN && ";".equals(data);
        }
    }

    private enum TokenKind {
        FUN,
        VAR,
        NUM,
        STR,
    }

    private BufferedReader read;
    private final Knight lang;

    public Parser(Reader read, Knight lang) {
        this.read = new BufferedReader(read);
        this.lang = lang;
    }

//    public static void main(String... args) throws Exception {
//        var parser = new Parser(new StringReader("Output +++ + 1 2 32423 \"asdfas\ndfa\""), null);
//        System.out.println(parser.tokenize());
//    }

    private static boolean isSpace(char c) {
        return "()[]{}\t\r\n ".contains(Character.toString(c));
    }

    private Iterable<Token> tokenize() throws IOException {
        var l = new ArrayList<Token>();
        int c = read.read();
        var currentData = new StringBuilder();
        TokenKind currentKind = null;
        while (c >= 0) {
            char r = (char) c;
            String s = Character.toString(r);
            if (isSpace(r)) {
                if (currentKind != null) {
                    l.add(new Token(currentKind, currentData.toString()));
                }
                currentKind = null;
                currentData = new StringBuilder();
            } else if (r == '#') {
                int skip;
                do {
                    skip = read.read();
                } while (skip != '\n');
            } else if (Character.isDigit(r)) {
                if (currentKind == null) {
                    currentKind = TokenKind.NUM;
                }
                currentData.append(r);
            } else if ("\"'".contains(s)) {
                var next = (char) read.read();
                while (next != r) {
                    currentData.append(next);
                    next = (char) read.read();
                }
                l.add(new Token(TokenKind.STR, currentData.toString()));
                currentData = new StringBuilder();
            } else if (Character.isLowerCase(r) || r == '_') {
                if (currentKind != TokenKind.VAR) {
                    if(currentKind != null) {
                        l.add(new Token(currentKind, currentData.toString()));
                    }
                    currentData = new StringBuilder();
                    currentKind = TokenKind.VAR;
                }
                currentData.append(r);
            } else if (Character.isUpperCase(r)) {
                if (currentKind != TokenKind.FUN) {
                    if(currentKind != null) {
                        l.add(new Token(currentKind, currentData.toString()));
                    }
                    currentData = new StringBuilder();
                    currentKind = TokenKind.FUN;
                }
                currentData.append(r);
            } else if (":`!+-*/%^<>?&|;=".contains(s)) {
                if (currentKind != null) {
                    l.add(new Token(currentKind, currentData.toString()));
                }
                currentKind = null;
                currentData = new StringBuilder();
                l.add(new Token(TokenKind.FUN, s));
            }
            c = read.read();
        }
        if (currentKind != null) {
            l.add(new Token(currentKind, currentData.toString()));
        }
        return l;
    }

    private Expression parseFunction(Iterator<Token> toParse, String fName) {
        char id = fName.charAt(0);
        return switch(id) {
            case 'N' -> new Null();
            case 'T' -> new Bool(true);
            case 'F' -> new Bool(false);
            case 'R' -> new Random();
            case 'P' -> PromptNodeGen.create();
            case 'B' -> new Block(parseRec(toParse));
            case '`' -> ShellNodeGen.create(parseRec(toParse));
            case 'E' -> EvalNodeGen.create(parseRec(toParse));
            case ':' -> SameNodeGen.create(parseRec(toParse));
            case 'A' -> AsciiNodeGen.create(parseRec(toParse));
            case 'C' -> CallNodeGen.create(parseRec(toParse));
            case 'D' -> DumpNodeGen.create(parseRec(toParse));
            case 'L' -> LenNodeGen.create(parseRec(toParse));
            case '!' -> NegNodeGen.create(parseRec(toParse));
            case 'O' -> OutputNodeGen.create(parseRec(toParse));
            case 'Q' -> QuitNodeGen.create(parseRec(toParse));
            case '&' -> new And(parseRec(toParse), parseRec(toParse));
            case '/' -> DivNodeGen.create(parseRec(toParse), parseRec(toParse));
            case '?' -> EqNodeGen.create(parseRec(toParse), parseRec(toParse));
            case '>' -> GtNodeGen.create(parseRec(toParse), parseRec(toParse));
            case '<' -> LtNodeGen.create(parseRec(toParse), parseRec(toParse));
            case '-' -> MinusNodeGen.create(parseRec(toParse), parseRec(toParse));
            case '%' -> ModNodeGen.create(parseRec(toParse), parseRec(toParse));
            case '|' -> new Or(parseRec(toParse), parseRec(toParse));
            case '+' -> PlusNodeGen.create(parseRec(toParse), parseRec(toParse));
            case '^' -> PowNodeGen.create(parseRec(toParse), parseRec(toParse));
            case ';' -> parseBlock(toParse);
            case '=' -> new Set((Var) parseRec(toParse), parseRec(toParse));
            case '*' -> TimesNodeGen.create(parseRec(toParse), parseRec(toParse));
            case 'W' -> new While(parseRec(toParse), parseRec(toParse));
            case 'I' -> new If(parseRec(toParse), parseRec(toParse), parseRec(toParse));
            case 'G' -> GetNodeGen.create(parseRec(toParse), parseRec(toParse), parseRec(toParse));
            case 'S' -> SubNodeGen.create(parseRec(toParse), parseRec(toParse), parseRec(toParse), parseRec(toParse));
            default -> throw new RuntimeException(String.format("function '%s' not implemented", fName));
        };
    }

    private Expression parseBlock(Iterator<Token> toParse) {
        var args = new ArrayList<Expression>();
        Token right;
        do {
            args.add(parseRec(toParse));
            right = toParse.next();
        } while(right.isSeq());
        args.add(parseRec(toParse, right));
        return new Seq(args.toArray(new Expression[]{}));
    }

    private Expression parseRec(Iterator<Token> toParse) {
        return parseRec(toParse, toParse.next());
    }
    private Expression parseRec(Iterator<Token> toParse, Token tok) {
        return switch (tok.k()) {
            case FUN -> parseFunction(toParse, tok.data());
            case NUM -> new Num(Long.parseLong(tok.data()));
            case VAR -> VarNodeGen.create(tok.data());
            case STR -> new Str(tok.data());
        };
    }

    public RootNode parse() throws IOException {
        Iterable<Token> tokenize = tokenize();
        return new Root(lang, parseRec(tokenize.iterator()));
    }
}
