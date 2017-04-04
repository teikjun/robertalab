package de.fhg.iais.roberta.syntax.codegen;

import java.util.ArrayList;

import de.fhg.iais.roberta.components.Category;
import de.fhg.iais.roberta.syntax.Phrase;
import de.fhg.iais.roberta.syntax.expr.EmptyExpr;
import de.fhg.iais.roberta.syntax.expr.EmptyList;
import de.fhg.iais.roberta.syntax.expr.Expr;
import de.fhg.iais.roberta.syntax.expr.ExprList;
import de.fhg.iais.roberta.syntax.expr.ListCreate;
import de.fhg.iais.roberta.syntax.expr.MathConst;
import de.fhg.iais.roberta.syntax.expr.NullConst;
import de.fhg.iais.roberta.syntax.expr.VarDeclaration;
import de.fhg.iais.roberta.syntax.functions.MathSingleFunct;
import de.fhg.iais.roberta.syntax.methods.MethodCall;
import de.fhg.iais.roberta.syntax.methods.MethodIfReturn;
import de.fhg.iais.roberta.syntax.methods.MethodReturn;
import de.fhg.iais.roberta.syntax.methods.MethodVoid;
import de.fhg.iais.roberta.syntax.stmt.AssignStmt;
import de.fhg.iais.roberta.syntax.stmt.ExprStmt;
import de.fhg.iais.roberta.syntax.stmt.FunctionStmt;
import de.fhg.iais.roberta.syntax.stmt.IfStmt;
import de.fhg.iais.roberta.syntax.stmt.MethodStmt;
import de.fhg.iais.roberta.syntax.stmt.RepeatStmt;
import de.fhg.iais.roberta.syntax.stmt.StmtFlowCon;
import de.fhg.iais.roberta.typecheck.BlocklyType;
import de.fhg.iais.roberta.visitor.AstVisitor;
import de.fhg.iais.roberta.visitor.CommonLanguageVisitor;

/**
 * This class is implementing {@link AstVisitor}. All methods are implemented and they append a human-readable C++ code representation of a phrase to a
 * StringBuilder. <b>This representation is correct C++ code.</b> <br>
 */
public abstract class Ast2CppVisitor extends CommonLanguageVisitor {
    protected ArrayList<ArrayList<Phrase<Void>>> phrases;

    /**
     * initialize the cpp code generator visitor.
     *
     * @param indentation to start with. Will be ince/decr depending on block structure
     */
    public Ast2CppVisitor(ArrayList<ArrayList<Phrase<Void>>> programPhrases, int indentation) {
        super(programPhrases, indentation);
    }

    @Override
    public Void visitMathConst(MathConst<Void> mathConst) {
        switch ( mathConst.getMathConst() ) {
            case PI:
                this.sb.append("PI");
                break;
            case E:
                this.sb.append("M_E");
                break;
            case GOLDEN_RATIO:
                this.sb.append("GOLDEN_RATIO");
                break;
            case SQRT2:
                this.sb.append("M_SQRT2");
                break;
            case SQRT1_2:
                this.sb.append("M_SQRT1_2");
                break;
            // IEEE 754 floating point representation
            case INFINITY:
                this.sb.append("INFINITY");
                break;
            default:
                break;
        }
        return null;
    }

    @Override
    public Void visitNullConst(NullConst<Void> nullConst) {
        this.sb.append("NULL");
        return null;
    }

    @Override
    public Void visitVarDeclaration(VarDeclaration<Void> var) {
        this.sb.append(getLanguageVarTypeFromBlocklyType(var.getTypeVar())).append(" ");
        this.sb.append(var.getName());
        if ( var.getTypeVar().isArray() ) {
            if ( var.toString().contains("false, false") ) {
                this.sb.append("[]");
            } else {
                this.sb.append("Raw");
                if ( var.getValue().toString().equals("ListCreate [NUMBER, ]")
                    || var.getValue().toString().equals("ListCreate [BOOLEAN, ]")
                    || var.getValue().toString().equals("ListCreate [STRING, ]")
                    || var.getValue().getKind().hasName("EMPTY_EXPR") ) {
                    this.sb.append("[0];");
                    nlIndent();
                    this.sb.append(getLanguageVarTypeFromBlocklyType(var.getTypeVar())).append("* ");
                    this.sb.append(var.getName() + " = " + var.getName() + "Raw");
                } else if ( var.getValue().getKind().hasName("SENSOR_EXPR") ) {
                    this.sb.append("[3]");
                } else {
                    ListCreate<Void> list = (ListCreate<Void>) var.getValue();
                    this.sb.append("[" + list.getValue().get().size() + "]");
                }
                if ( var.getValue().getKind().hasName("LIST_CREATE") ) {
                    ListCreate<Void> list = (ListCreate<Void>) var.getValue();
                    if ( list.getValue().get().size() == 0 ) {
                        return null;
                    }
                }
            }

        }

        if ( !var.getValue().getKind().hasName("EMPTY_EXPR") ) {
            this.sb.append(" = ");
            if ( var.getValue().getKind().hasName("EXPR_LIST") ) {
                ExprList<Void> list = (ExprList<Void>) var.getValue();
                if ( list.get().size() == 2 ) {
                    list.get().get(1).visit(this);
                } else {
                    list.get().get(0).visit(this);
                }
            } else {
                var.getValue().visit(this);
                if ( var.getTypeVar().isArray() ) {
                    this.sb.append(";");
                    nlIndent();
                    this.sb.append(getLanguageVarTypeFromBlocklyType(var.getTypeVar())).append("* ");
                    this.sb.append(var.getName() + " = " + var.getName() + "Raw");
                }
            }
        }
        return null;
    }

    @Override
    public Void visitEmptyExpr(EmptyExpr<Void> emptyExpr) {
        switch ( emptyExpr.getDefVal() ) {
            case STRING:
                this.sb.append("\"\"");
                break;
            case BOOLEAN:
                this.sb.append("true");
                break;
            case NUMBER_INT:
                this.sb.append("0");
                break;
            case ARRAY:
                break;
            case NULL:
                break;
            default:
                this.sb.append("NULL");
                break;
        }
        return null;
    }

    @Override
    public Void visitAssignStmt(AssignStmt<Void> assignStmt) {
        super.visitAssignStmt(assignStmt);
        this.sb.append(";");
        return null;
    }

    @Override
    public Void visitExprStmt(ExprStmt<Void> exprStmt) {
        super.visitExprStmt(exprStmt);
        this.sb.append(";");
        return null;
    }

    @Override
    public Void visitRepeatStmt(RepeatStmt<Void> repeatStmt) {
        boolean additionalClosingBracket = false;
        boolean isWaitStmt = repeatStmt.getMode() == RepeatStmt.Mode.WAIT;
        switch ( repeatStmt.getMode() ) {
            case FOREVER:
                /*
                 *This ""if ( true ) {" statement is needed because when we have code after the "while ( true ) "
                 *statement is unreachable
                 */
                this.sb.append("if ( true ) {");
                additionalClosingBracket = true;
                incrIndentation();
                nlIndent();
            case UNTIL:
            case WHILE:
                addLabelToLoop();
                generateCodeFromStmtCondition("while", repeatStmt.getExpr());
                break;
            case TIMES:
            case FOR:
                addLabelToLoop();
                generateCodeFromStmtConditionFor("for", repeatStmt.getExpr());
                break;
            case WAIT:
                generateCodeFromStmtCondition("if", repeatStmt.getExpr());
                break;
            case FOR_EACH:
                addLabelToLoop();
                generateCodeFromStmtCondition("for", repeatStmt.getExpr());
                break;
            default:
                break;
        }
        incrIndentation();
        repeatStmt.getList().visit(this);
        if ( !isWaitStmt ) {
            this.currenLoop.removeLast();
        } else {
            appendBreakStmt();
        }
        decrIndentation();
        nlIndent();
        this.sb.append("}");
        if ( additionalClosingBracket ) {
            decrIndentation();
            nlIndent();
            this.sb.append("}");
        }
        return null;
    }

    @Override
    public Void visitFunctionStmt(FunctionStmt<Void> functionStmt) {
        super.visitFunctionStmt(functionStmt);
        this.sb.append(";");
        return null;
    }

    @Override
    public Void visitStmtFlowCon(StmtFlowCon<Void> stmtFlowCon) {
        if ( this.loopsLabels.get(this.currenLoop.getLast()) != null ) {
            if ( this.loopsLabels.get(this.currenLoop.getLast()) ) {
                this.sb.append("goto " + stmtFlowCon.getFlow().toString().toLowerCase() + "_loop" + this.currenLoop.getLast() + ";");
                return null;
            }
        }
        this.sb.append(stmtFlowCon.getFlow().toString().toLowerCase() + ";");
        return null;
    }

    @Override
    public Void visitEmptyList(EmptyList<Void> emptyList) {
        return null;
    }

    @Override
    public Void visitMathSingleFunct(MathSingleFunct<Void> mathSingleFunct) {
        switch ( mathSingleFunct.getFunctName() ) {
            case ROOT:
                this.sb.append("sqrt(");
                break;
            case ABS:
                this.sb.append("abs(");
                break;
            case LN:
                this.sb.append("log(");
                break;
            case LOG10:
                this.sb.append("log10(");
                break;
            case EXP:
                this.sb.append("exp(");
                break;
            case POW10:
                this.sb.append("pow(10.0, ");
                break;
            case SIN:
                this.sb.append("sin(PI / 180.0 * ");
                break;
            case COS:
                this.sb.append("cos(PI / 180.0 * ");
                break;
            case TAN:
                this.sb.append("tan(PI / 180.0 * ");
                break;
            case ASIN:
                this.sb.append("180.0 / PI * asin(");
                break;
            case ATAN:
                this.sb.append("180.0 / PI * atan(");
                break;
            case ACOS:
                this.sb.append("180.0 / PI * acos(");
                break;
            case ROUND:
                this.sb.append("round(");
                break;
            case ROUNDUP:
                this.sb.append("ceil(");
                break;
            //check why there are double brackets
            case ROUNDDOWN:
                this.sb.append("floor(");
                break;
            default:
                break;
        }
        mathSingleFunct.getParam().get(0).visit(this);
        this.sb.append(")");

        return null;
    }

    @Override
    public Void visitMethodVoid(MethodVoid<Void> methodVoid) {
        this.sb.append("\n").append("void ");
        this.sb.append(methodVoid.getMethodName() + "(");
        methodVoid.getParameters().visit(this);
        this.sb.append(") {");
        methodVoid.getBody().visit(this);
        this.sb.append("\n").append("}");
        return null;
    }

    @Override
    public Void visitMethodReturn(MethodReturn<Void> methodReturn) {
        this.sb.append("\n").append(getLanguageVarTypeFromBlocklyType(methodReturn.getReturnType()));
        this.sb.append(" " + methodReturn.getMethodName() + "(");
        methodReturn.getParameters().visit(this);
        this.sb.append(") {");
        methodReturn.getBody().visit(this);
        this.nlIndent();
        this.sb.append("return ");
        methodReturn.getReturnValue().visit(this);
        this.sb.append(";\n").append("}");
        return null;
    }

    @Override
    public Void visitMethodIfReturn(MethodIfReturn<Void> methodIfReturn) {
        this.sb.append("if (");
        methodIfReturn.getCondition().visit(this);
        this.sb.append(") ");
        this.sb.append("return ");
        methodIfReturn.getReturnValue().visit(this);
        return null;
    }

    @Override
    public Void visitMethodStmt(MethodStmt<Void> methodStmt) {
        methodStmt.getMethod().visit(this);
        if ( methodStmt.getProperty().getBlockType().equals("robProcedures_ifreturn") ) {
            this.sb.append(";");
        }
        return null;
    }

    @Override
    public Void visitMethodCall(MethodCall<Void> methodCall) {
        super.visitMethodCall(methodCall);
        if ( methodCall.getReturnType() == BlocklyType.VOID ) {
            this.sb.append(";");
        }
        return null;
    }

    @Override
    protected String getLanguageVarTypeFromBlocklyType(BlocklyType type) {
        switch ( type ) {
            case ANY:
            case COMPARABLE:
            case ADDABLE:
            case NULL:
            case REF:
            case PRIM:
            case NOTHING:
            case CAPTURED_TYPE:
            case R:
            case S:
            case T:
                return "";
            case ARRAY:
                return "double";
            case ARRAY_NUMBER:
                return "double";
            case ARRAY_STRING:
                return "String";
            case ARRAY_BOOLEAN:
                return "bool";
            case BOOLEAN:
                return "bool";
            case NUMBER:
                return "double";
            case NUMBER_INT:
                return "int";
            case STRING:
                return "String";
            case VOID:
                return "void";
            case COLOR:
                return "String";
            case CONNECTION:
                return "int";
            default:
                throw new IllegalArgumentException("unhandled type");
        }
    }

    protected void generateUserDefinedMethods() {
        //TODO: too many nested loops and condition there must be a better way this to be done
        if ( this.programPhrases.size() > 1 ) {
            for ( ArrayList<Phrase<Void>> phrases : this.programPhrases ) {
                for ( Phrase<Void> phrase : phrases ) {
                    boolean isCreateMethodPhrase = phrase.getKind().getCategory() == Category.METHOD && !phrase.getKind().hasName("METHOD_CALL");
                    if ( isCreateMethodPhrase ) {
                        this.incrIndentation();
                        phrase.visit(this);
                        this.sb.append("\n");
                        this.decrIndentation();
                    }

                }
            }
        }
    }

    @Override
    protected void generateCodeFromTernary(IfStmt<Void> ifStmt) {
        this.sb.append("(" + whitespace());
        ifStmt.getExpr().get(0).visit(this);
        this.sb.append(whitespace() + ")" + whitespace() + "?" + whitespace());
        ((ExprStmt<Void>) ifStmt.getThenList().get(0).get().get(0)).getExpr().visit(this);
        this.sb.append(whitespace() + ":" + whitespace());
        ((ExprStmt<Void>) ifStmt.getElseList().get().get(0)).getExpr().visit(this);
    }

    @Override
    protected void generateCodeFromIfElse(IfStmt<Void> ifStmt) {
        for ( int i = 0; i < ifStmt.getExpr().size(); i++ ) {
            if ( i == 0 ) {
                generateCodeFromStmtCondition("if", ifStmt.getExpr().get(i));
            } else {
                generateCodeFromStmtCondition("else if", ifStmt.getExpr().get(i));
            }
            incrIndentation();
            ifStmt.getThenList().get(i).visit(this);
            decrIndentation();
            if ( i + 1 < ifStmt.getExpr().size() ) {
                nlIndent();
                this.sb.append("}").append(whitespace());
            }
        }
    }

    @Override
    protected void generateCodeFromElse(IfStmt<Void> ifStmt) {
        if ( ifStmt.getElseList().get().size() != 0 ) {
            nlIndent();
            this.sb.append("}").append(whitespace()).append("else").append(whitespace() + "{");
            incrIndentation();
            ifStmt.getElseList().visit(this);
            decrIndentation();
        }
        nlIndent();
        this.sb.append("}");
    }

    private void generateCodeFromStmtCondition(String stmtType, Expr<Void> expr) {
        this.sb.append(stmtType + whitespace() + "(" + whitespace());
        expr.visit(this);
        this.sb.append(whitespace() + ")" + whitespace() + "{");
    }

    private void generateCodeFromStmtConditionFor(String stmtType, Expr<Void> expr) {
        this.sb.append(stmtType + whitespace() + "(" + whitespace() + "float" + whitespace());
        ExprList<Void> expressions = (ExprList<Void>) expr;
        expressions.get().get(0).visit(this);
        this.sb.append(whitespace() + "=" + whitespace());
        expressions.get().get(1).visit(this);
        this.sb.append(";" + whitespace());
        expressions.get().get(0).visit(this);
        int posOpenBracket = expressions.get().toString().lastIndexOf("[");
        int posClosedBracket = expressions.get().toString().lastIndexOf("]");
        int counterPos = expressions.get().toString().lastIndexOf("-");
        if ( counterPos > posOpenBracket && counterPos < posClosedBracket ) {
            this.sb.append(">" + whitespace());
        } else {
            this.sb.append("<" + whitespace());
        }
        expressions.get().get(2).visit(this);
        this.sb.append(";" + whitespace());
        expressions.get().get(0).visit(this);
        this.sb.append("+=" + whitespace());
        expressions.get().get(3).visit(this);
        this.sb.append(whitespace() + ")" + whitespace() + "{");
    }

    private void appendBreakStmt() {
        nlIndent();
        this.sb.append("break;");
    }

    protected boolean handleMainBlocks(boolean mainBlock, Phrase<Void> phrase) {
        if ( phrase.getKind().getCategory() != Category.TASK ) {
            nlIndent();
        } else if ( !phrase.getKind().hasName("LOCATION") ) {
            mainBlock = true;
        }
        return mainBlock;
    }

    private void addLabelToLoop() {
        increaseLoopCounter();
        if ( this.loopsLabels.get(this.currenLoop.getLast()) ) {
            this.sb.append("loop" + this.currenLoop.getLast() + ":");
            nlIndent();
        }
    }

}
