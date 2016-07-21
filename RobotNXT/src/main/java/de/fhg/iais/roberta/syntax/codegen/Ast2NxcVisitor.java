package de.fhg.iais.roberta.syntax.codegen;

import java.util.ArrayList;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringEscapeUtils;

import de.fhg.iais.roberta.components.Category;
import de.fhg.iais.roberta.components.NxtConfiguration;
import de.fhg.iais.roberta.components.Sensor;
import de.fhg.iais.roberta.inter.mode.general.IMode;
import de.fhg.iais.roberta.inter.mode.sensor.ISensorPort;
import de.fhg.iais.roberta.mode.action.ActorPort;
import de.fhg.iais.roberta.mode.action.DriveDirection;
import de.fhg.iais.roberta.mode.action.MotorMoveMode;
import de.fhg.iais.roberta.mode.action.MotorStopMode;
import de.fhg.iais.roberta.mode.action.ShowPicture;
import de.fhg.iais.roberta.mode.action.TurnDirection;
import de.fhg.iais.roberta.mode.general.IndexLocation;
import de.fhg.iais.roberta.mode.sensor.MotorTachoMode;
import de.fhg.iais.roberta.mode.sensor.TimerSensorMode;
import de.fhg.iais.roberta.syntax.BlockType;
import de.fhg.iais.roberta.syntax.Phrase;
import de.fhg.iais.roberta.syntax.action.Action;
import de.fhg.iais.roberta.syntax.action.generic.BluetoothConnectAction;
import de.fhg.iais.roberta.syntax.action.generic.BluetoothReceiveAction;
import de.fhg.iais.roberta.syntax.action.generic.BluetoothSendAction;
import de.fhg.iais.roberta.syntax.action.generic.BluetoothWaitForConnectionAction;
import de.fhg.iais.roberta.syntax.action.generic.ClearDisplayAction;
import de.fhg.iais.roberta.syntax.action.generic.DriveAction;
import de.fhg.iais.roberta.syntax.action.generic.LightAction;
import de.fhg.iais.roberta.syntax.action.generic.LightStatusAction;
import de.fhg.iais.roberta.syntax.action.generic.MotorDriveStopAction;
import de.fhg.iais.roberta.syntax.action.generic.MotorGetPowerAction;
import de.fhg.iais.roberta.syntax.action.generic.MotorOnAction;
import de.fhg.iais.roberta.syntax.action.generic.MotorSetPowerAction;
import de.fhg.iais.roberta.syntax.action.generic.MotorStopAction;
import de.fhg.iais.roberta.syntax.action.generic.PlayFileAction;
import de.fhg.iais.roberta.syntax.action.generic.ShowPictureAction;
import de.fhg.iais.roberta.syntax.action.generic.ShowTextAction;
import de.fhg.iais.roberta.syntax.action.generic.ToneAction;
import de.fhg.iais.roberta.syntax.action.generic.TurnAction;
import de.fhg.iais.roberta.syntax.action.generic.VolumeAction;
import de.fhg.iais.roberta.syntax.blocksequence.ActivityTask;
import de.fhg.iais.roberta.syntax.blocksequence.Location;
import de.fhg.iais.roberta.syntax.blocksequence.MainTask;
import de.fhg.iais.roberta.syntax.blocksequence.StartActivityTask;
import de.fhg.iais.roberta.syntax.expr.ActionExpr;
import de.fhg.iais.roberta.syntax.expr.Binary;
import de.fhg.iais.roberta.syntax.expr.Binary.Op;
import de.fhg.iais.roberta.syntax.expr.BoolConst;
import de.fhg.iais.roberta.syntax.expr.ColorConst;
import de.fhg.iais.roberta.syntax.expr.EmptyExpr;
import de.fhg.iais.roberta.syntax.expr.EmptyList;
import de.fhg.iais.roberta.syntax.expr.Expr;
import de.fhg.iais.roberta.syntax.expr.ExprList;
import de.fhg.iais.roberta.syntax.expr.FunctionExpr;
import de.fhg.iais.roberta.syntax.expr.ListCreate;
import de.fhg.iais.roberta.syntax.expr.MathConst;
import de.fhg.iais.roberta.syntax.expr.MethodExpr;
import de.fhg.iais.roberta.syntax.expr.NullConst;
import de.fhg.iais.roberta.syntax.expr.NumConst;
import de.fhg.iais.roberta.syntax.expr.SensorExpr;
import de.fhg.iais.roberta.syntax.expr.ShadowExpr;
import de.fhg.iais.roberta.syntax.expr.StmtExpr;
import de.fhg.iais.roberta.syntax.expr.StringConst;
import de.fhg.iais.roberta.syntax.expr.Unary;
import de.fhg.iais.roberta.syntax.expr.Var;
import de.fhg.iais.roberta.syntax.expr.VarDeclaration;
import de.fhg.iais.roberta.syntax.functions.FunctionNames;
import de.fhg.iais.roberta.syntax.functions.GetSubFunct;
import de.fhg.iais.roberta.syntax.functions.IndexOfFunct;
import de.fhg.iais.roberta.syntax.functions.LengthOfIsEmptyFunct;
import de.fhg.iais.roberta.syntax.functions.ListGetIndex;
import de.fhg.iais.roberta.syntax.functions.ListRepeat;
import de.fhg.iais.roberta.syntax.functions.ListSetIndex;
import de.fhg.iais.roberta.syntax.functions.MathConstrainFunct;
import de.fhg.iais.roberta.syntax.functions.MathNumPropFunct;
import de.fhg.iais.roberta.syntax.functions.MathOnListFunct;
import de.fhg.iais.roberta.syntax.functions.MathPowerFunct;
import de.fhg.iais.roberta.syntax.functions.MathRandomFloatFunct;
import de.fhg.iais.roberta.syntax.functions.MathRandomIntFunct;
import de.fhg.iais.roberta.syntax.functions.MathSingleFunct;
import de.fhg.iais.roberta.syntax.functions.TextJoinFunct;
import de.fhg.iais.roberta.syntax.functions.TextPrintFunct;
import de.fhg.iais.roberta.syntax.methods.MethodCall;
import de.fhg.iais.roberta.syntax.methods.MethodIfReturn;
import de.fhg.iais.roberta.syntax.methods.MethodReturn;
import de.fhg.iais.roberta.syntax.methods.MethodVoid;
import de.fhg.iais.roberta.syntax.sensor.generic.BrickSensor;
import de.fhg.iais.roberta.syntax.sensor.generic.ColorSensor;
import de.fhg.iais.roberta.syntax.sensor.generic.EncoderSensor;
import de.fhg.iais.roberta.syntax.sensor.generic.GetSampleSensor;
import de.fhg.iais.roberta.syntax.sensor.generic.GyroSensor;
import de.fhg.iais.roberta.syntax.sensor.generic.InfraredSensor;
import de.fhg.iais.roberta.syntax.sensor.generic.LightSensor;
import de.fhg.iais.roberta.syntax.sensor.generic.SoundSensor;
import de.fhg.iais.roberta.syntax.sensor.generic.TimerSensor;
import de.fhg.iais.roberta.syntax.sensor.generic.TouchSensor;
import de.fhg.iais.roberta.syntax.sensor.generic.UltrasonicSensor;
import de.fhg.iais.roberta.syntax.stmt.ActionStmt;
import de.fhg.iais.roberta.syntax.stmt.AssignStmt;
import de.fhg.iais.roberta.syntax.stmt.ExprStmt;
import de.fhg.iais.roberta.syntax.stmt.FunctionStmt;
import de.fhg.iais.roberta.syntax.stmt.IfStmt;
import de.fhg.iais.roberta.syntax.stmt.MethodStmt;
import de.fhg.iais.roberta.syntax.stmt.RepeatStmt;
import de.fhg.iais.roberta.syntax.stmt.RepeatStmt.Mode;
import de.fhg.iais.roberta.syntax.stmt.SensorStmt;
import de.fhg.iais.roberta.syntax.stmt.Stmt;
import de.fhg.iais.roberta.syntax.stmt.StmtFlowCon;
import de.fhg.iais.roberta.syntax.stmt.StmtList;
import de.fhg.iais.roberta.syntax.stmt.WaitStmt;
import de.fhg.iais.roberta.syntax.stmt.WaitTimeStmt;
import de.fhg.iais.roberta.typecheck.BlocklyType;
import de.fhg.iais.roberta.util.dbc.Assert;
import de.fhg.iais.roberta.util.dbc.DbcException;
import de.fhg.iais.roberta.visitor.AstVisitor;

/**
 * This class is implementing {@link AstVisitor}. All methods are implemented and they
 * append a human-readable JAVA code representation of a phrase to a StringBuilder. <b>This representation is correct JAVA code.</b> <br>
 */
public class Ast2NxcVisitor implements AstVisitor<Void> {
    public static final String INDENT = "  ";

    private final NxtConfiguration brickConfiguration;
    private final StringBuilder sb = new StringBuilder();
    private int indentation;

    private String left;

    /**
     * initialize the Java code generator visitor.
     *
     * @param brickConfiguration hardware configuration of the brick
     * @param usedFunctions in the current program
     * @param indentation to start with. Will be incr/decr depending on block structure
     */
    public Ast2NxcVisitor(NxtConfiguration brickConfiguration, int indentation) {
        this.brickConfiguration = brickConfiguration;
        this.indentation = indentation;
    }

    /**
     * factory method to generate Java code from an AST.<br>
     *
     * @param programName name of the program
     * @param brickConfiguration hardware configuration of the brick
     * @param phrases to generate the code from
     */
    public static String generate(NxtConfiguration brickConfiguration, ArrayList<ArrayList<Phrase<Void>>> phrasesSet, boolean withWrapping) //
    {
        Assert.notNull(brickConfiguration);
        Assert.isTrue(phrasesSet.size() >= 1);

        final Ast2NxcVisitor astVisitor = new Ast2NxcVisitor(brickConfiguration, withWrapping ? 1 : 0);
        astVisitor.generatePrefix(withWrapping);

        generateCodeFromPhrases(phrasesSet, withWrapping, astVisitor);

        return astVisitor.sb.toString();
    }

    private static void generateCodeFromPhrases(ArrayList<ArrayList<Phrase<Void>>> phrasesSet, boolean withWrapping, Ast2NxcVisitor astVisitor) {
        boolean mainBlock = false;
        for ( final ArrayList<Phrase<Void>> phrases : phrasesSet ) {
            for ( final Phrase<Void> phrase : phrases ) {
                mainBlock = handleMainBlocks(astVisitor, mainBlock, phrase);
                phrase.visit(astVisitor);
            }
        }
        generateSuffix(withWrapping, astVisitor);
    }

    private static boolean handleMainBlocks(Ast2NxcVisitor astVisitor, boolean mainBlock, Phrase<Void> phrase) {
        if ( phrase.getKind().getCategory() != Category.TASK ) {
            astVisitor.nlIndent();
        } else if ( phrase.getKind() != BlockType.LOCATION ) {
            mainBlock = true;
        }
        return mainBlock;
    }

    private static void generateSuffix(boolean withWrapping, Ast2NxcVisitor astVisitor) {
        if ( withWrapping ) {
            astVisitor.sb.append("\n}\n");
        }
    }

    private static String getBlocklyTypeCode(BlocklyType type) {
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
                return "int";
            case ARRAY_NUMBER:
                return "float";
            case ARRAY_STRING:
                return "string";
            case ARRAY_BOOLEAN:
                return "bool";
            case BOOLEAN:
                return "bool";
            case NUMBER:
                return "float";
            case NUMBER_INT:
                return "int";
            case STRING:
                return "string";
            case VOID:
                return "void";
            case COLOR:
                return "int";
            case CONNECTION:
                return "int";
        }
        throw new IllegalArgumentException("unhandled type");
    }

    private static String getEnumCode(IMode value) {
        return value.getClass().getSimpleName() + "." + value;
    }

    /**
     * Get the current indentation of the visitor. Meaningful for tests only.
     *
     * @return indentation value of the visitor.
     */
    int getIndentation() {
        return this.indentation;
    }

    /**
     * Get the string builder of the visitor. Meaningful for tests only.
     *
     * @return (current state of) the string builder
     */
    public StringBuilder getSb() {
        return this.sb;
    }

    //nxc can't cast "(float)", it does it automatically
    @Override
    public Void visitNumConst(NumConst<Void> numConst) {
        this.sb.append(numConst.getValue());
        /*
        if ( isInteger(numConst.getValue()) ) {
            this.sb.append(numConst.getValue());
        } else {
            this.sb.append("(");
            this.sb.append(numConst.getValue());
            this.sb.append(")");
        }*/
        return null;
    }

    @Override
    public Void visitBoolConst(BoolConst<Void> boolConst) {
        this.sb.append(boolConst.isValue());
        return null;
    };

    //now all these constants (except for PI that was originally in nxc) are defined in hal.h
    @Override
    public Void visitMathConst(MathConst<Void> mathConst) {
        switch ( mathConst.getMathConst() ) {
            case PI:
                this.sb.append("PI");
                break;
            case E:
                this.sb.append("E");
                break;
            case GOLDEN_RATIO:
                this.sb.append("GOLDEN_RATIO");
                break;
            case SQRT2:
                this.sb.append("SQRT2");
                break;
            case SQRT1_2:
                this.sb.append("SQRT1_2");
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
    public Void visitColorConst(ColorConst<Void> colorConst) {
        this.sb.append(getEnumCode(colorConst.getValue()));
        return null;
    }

    @Override
    public Void visitStringConst(StringConst<Void> stringConst) {
        this.sb.append("\"").append(StringEscapeUtils.escapeJava(stringConst.getValue())).append("\"");
        return null;
    }

    @Override
    public Void visitNullConst(NullConst<Void> nullConst) {
        this.sb.append("NULL");
        return null;
    }

    @Override
    public Void visitVar(Var<Void> var) {
        this.sb.append(var.getValue());
        return null;
    }

    @Override
    public Void visitVarDeclaration(VarDeclaration<Void> var) {
        this.sb.append(getBlocklyTypeCode(var.getTypeVar())).append(" ");
        this.sb.append(var.getName());
        if ( var.getTypeVar().isArray() ) {
            this.sb.append("[]");
            if ( var.getValue().getKind() == BlockType.LIST_CREATE ) {
                ListCreate<Void> list = (ListCreate<Void>) var.getValue();
                if ( list.getValue().get().size() == 0 ) {
                    return null;
                }
            }

        }

        if ( var.getValue().getKind() != BlockType.EMPTY_EXPR ) {
            this.sb.append(" = ");
            if ( var.getValue().getKind() == BlockType.EXPR_LIST ) {
                ExprList<Void> list = (ExprList<Void>) var.getValue();
                if ( list.get().size() == 2 ) {
                    list.get().get(1).visit(this);
                } else {
                    list.get().get(0).visit(this);
                }
            } else {
                var.getValue().visit(this);
            }
        }
        return null;
    }

    @Override
    public Void visitUnary(Unary<Void> unary) {
        if ( unary.getOp() == Unary.Op.POSTFIX_INCREMENTS ) {
            generateExprCode(unary, this.sb);
            this.sb.append(unary.getOp().getOpSymbol());
        } else {
            this.sb.append(unary.getOp().getOpSymbol());
            generateExprCode(unary, this.sb);
        }
        return null;
    }

    @Override
    public Void visitBinary(Binary<Void> binary) {
        if ( binary.getOp() == Op.EQ || binary.getOp() == Op.NEQ ) {
            if ( isStringExpr(binary.getLeft()) && isStringExpr(binary.getRight()) ) {
                if ( binary.getOp() == Op.NEQ ) {
                    this.sb.append("!");
                }
                generateSubExpr(this.sb, false, binary.getLeft(), binary);
                this.sb.append(".equals(");
                generateSubExpr(this.sb, false, binary.getRight(), binary);
                this.sb.append(")");
                return null;
            }
        }
        generateSubExpr(this.sb, false, binary.getLeft(), binary);
        this.sb.append(whitespace() + binary.getOp().getOpSymbol() + whitespace());
        if ( binary.getOp() == Op.TEXT_APPEND ) {
            this.sb.append("String(");
            generateSubExpr(this.sb, false, binary.getRight(), binary);
            this.sb.append(")");
        } else {
            generateSubExpr(this.sb, parenthesesCheck(binary), binary.getRight(), binary);
        }
        return null;
    }

    @Override
    public Void visitActionExpr(ActionExpr<Void> actionExpr) {
        actionExpr.getAction().visit(this);
        return null;
    }

    @Override
    public Void visitSensorExpr(SensorExpr<Void> sensorExpr) {
        sensorExpr.getSens().visit(this);
        return null;
    }

    @Override
    public Void visitMethodExpr(MethodExpr<Void> methodExpr) {
        methodExpr.getMethod().visit(this);
        return null;
    }

    @Override
    public Void visitEmptyExpr(EmptyExpr<Void> emptyExpr) {
        switch ( emptyExpr.getDefVal().getName() ) {
            case "java.lang.String":
                this.sb.append("\"\"");
                break;
            case "java.lang.Boolean":
                this.sb.append("true");
                break;
            case "java.lang.Integer":
                this.sb.append("0");
                break;
            case "java.util.ArrayList":
                break;
            case "de.fhg.iais.roberta.syntax.expr.NullConst":
                break;
            default:
                this.sb.append("[[EmptyExpr [defVal=" + emptyExpr.getDefVal() + "]]]");
                break;
        }
        return null;
    }

    @Override
    public Void visitShadowExpr(ShadowExpr<Void> shadowExpr) {
        if ( shadowExpr.getBlock() != null ) {
            shadowExpr.getBlock().visit(this);
        } else {
            shadowExpr.getShadow().visit(this);
        }
        return null;
    }

    @Override
    public Void visitExprList(ExprList<Void> exprList) {
        boolean first = true;
        for ( final Expr<Void> expr : exprList.get() ) {
            if ( expr.getKind() != BlockType.EMPTY_EXPR ) {
                if ( first ) {
                    first = false;
                } else {
                    this.sb.append(", ");
                }
                expr.visit(this);
            }
        }
        return null;
    }

    @Override
    public Void visitActionStmt(ActionStmt<Void> actionStmt) {
        actionStmt.getAction().visit(this);
        return null;
    }

    @Override
    public Void visitAssignStmt(AssignStmt<Void> assignStmt) {
        assignStmt.getName().visit(this);
        this.sb.append(" = ");
        assignStmt.getExpr().visit(this);
        this.sb.append(";");
        return null;
    }

    @Override
    public Void visitExprStmt(ExprStmt<Void> exprStmt) {
        exprStmt.getExpr().visit(this);
        this.sb.append(";");
        return null;
    }

    @Override
    public Void visitStmtExpr(StmtExpr<Void> stmtExpr) {
        stmtExpr.getStmt().visit(this);
        return null;
    }

    @Override
    public Void visitIfStmt(IfStmt<Void> ifStmt) {
        generateCodeFromIfElse(ifStmt);
        generateCodeFromElse(ifStmt);
        return null;
    }

    @Override
    public Void visitRepeatStmt(RepeatStmt<Void> repeatStmt) {
        //boolean additionalClosingBracket = false;
        switch ( repeatStmt.getMode() ) {
            case UNTIL:
            case WHILE:
            case FOREVER:
                generateCodeFromStmtCondition("while", repeatStmt.getExpr());
                break;
            case TIMES:
            case FOR:
                generateCodeFromStmtConditionFor("for", repeatStmt.getExpr());
                break;
            case WAIT:
                generateCodeFromStmtCondition("if", repeatStmt.getExpr());
                break;
            case FOR_EACH:
                generateCodeFromStmtCondition("for", repeatStmt.getExpr());
                break;
            default:
                break;
        }
        incrIndentation();
        repeatStmt.getList().visit(this);
        appendBreakStmt(repeatStmt);
        decrIndentation();
        nlIndent();
        this.sb.append("}");
        //if ( additionalClosingBracket ) {
        //    decrIndentation();
        //    nlIndent();
        //    this.sb.append("}");
        //}
        return null;
    }

    @Override
    public Void visitSensorStmt(SensorStmt<Void> sensorStmt) {
        sensorStmt.getSensor().visit(this);
        return null;
    }

    @Override
    public Void visitStmtFlowCon(StmtFlowCon<Void> stmtFlowCon) {
        this.sb.append(stmtFlowCon.getFlow().toString().toLowerCase() + ";");
        return null;
    }

    @Override
    public Void visitStmtList(StmtList<Void> stmtList) {
        for ( final Stmt<Void> stmt : stmtList.get() ) {
            nlIndent();
            stmt.visit(this);
        }
        return null;
    }

    @Override
    public Void visitWaitStmt(WaitStmt<Void> waitStmt) {
        this.sb.append("while ( true ) {");
        incrIndentation();
        visitStmtList(waitStmt.getStatements());
        nlIndent();
        this.sb.append("Wait( 15 );");
        decrIndentation();
        nlIndent();
        this.sb.append("}");
        return null;
    }

    @Override
    public Void visitWaitTimeStmt(WaitTimeStmt<Void> waitTimeStmt) {
        this.sb.append("Wait( ");
        waitTimeStmt.getTime().visit(this);
        this.sb.append(" );");
        return null;
    }

    @Override
    public Void visitClearDisplayAction(ClearDisplayAction<Void> clearDisplayAction) {
        this.sb.append("ClearScreen();");
        return null;
    }

    @Override
    public Void visitVolumeAction(VolumeAction<Void> volumeAction) {
        switch ( volumeAction.getMode() ) {
            case SET:
                this.sb.append("byte NXTNormVolume = ");
                volumeAction.getVolume().visit(this);
                this.sb.append(" * 4 / 100; ");
                nlIndent();
                this.sb.append("SetVolume( NXTNormVolume );");
                break;
            case GET:
                this.sb.append("Volume() * 100 / 4");
                break;
            default:
                throw new DbcException("Invalid volume action mode!");
        }
        return null;
    }

    //no such block for nxt
    @Override
    public Void visitLightAction(LightAction<Void> lightAction) {
        return null;

    }

    //no such block for nxt
    @Override
    public Void visitLightStatusAction(LightStatusAction<Void> lightStatusAction) {
        return null;
    }

    //won't be used
    @Override
    public Void visitPlayFileAction(PlayFileAction<Void> playFileAction) {
        this.sb.append("PlayFile( " + playFileAction.getFileName() + " );");
        return null;
    }

    @Override

    public Void visitShowPictureAction(ShowPictureAction<Void> showPictureAction) {
        this.sb.append("GraphicOut(");
        showPictureAction.getX().visit(this);
        this.sb.append(", ");
        showPictureAction.getY().visit(this);
        this.sb.append(",");
        if ( showPictureAction.getPicture() == ShowPicture.EYESOPEN ) {
            this.sb.append("\"" + "EYESOPEN" + "\"");
        } else {
            if ( showPictureAction.getPicture() == ShowPicture.EYESCLOSED ) {
                this.sb.append("\"" + "EYECLOSED" + "\"");
            }
            if ( showPictureAction.getPicture() == ShowPicture.FLOWERS ) {
                this.sb.append("\"" + "FLOWERS" + "\"");
            }

            if ( showPictureAction.getPicture() == ShowPicture.OLDGLASSES ) {
                this.sb.append("\"" + "OLDGLASSES" + "\"");
            }

        }

        this.sb.append(");");
        return null;
    }

    // TODO: delete arrays
    @Override
    public Void visitShowTextAction(ShowTextAction<Void> showTextAction) {
        switch ( showTextAction.getMsg().getKind() ) {
            case STRING_CONST:
                this.sb.append("TextOut( ");
                showTextAction.getX().visit(this);
                this.sb.append(", LCD_LINE");
                showTextAction.getY().visit(this);
                this.sb.append(", ");
                showTextAction.getMsg().visit(this);
                break;
            case TOUCH_SENSING:
            case BOOL_CONST:
                this.sb.append("BoolOut( ");
                showTextAction.getX().visit(this);
                this.sb.append(", LCD_LINE");
                showTextAction.getY().visit(this);
                this.sb.append(", ");
                showTextAction.getMsg().visit(this);
                break;
            case VAR:
                switch ( showTextAction.getMsg().getVarType() ) {
                    case STRING:
                        this.sb.append("TextOut( ");
                        showTextAction.getX().visit(this);
                        this.sb.append(", LCD_LINE");
                        showTextAction.getY().visit(this);
                        this.sb.append(",");
                        showTextAction.getMsg().visit(this);
                        break;
                    case BOOLEAN:
                        this.sb.append("BoolOut( ");
                        showTextAction.getX().visit(this);
                        this.sb.append(", LCD_LINE");
                        showTextAction.getY().visit(this);
                        this.sb.append(", ");
                        showTextAction.getMsg().visit(this);
                        break;
                    default:
                        this.sb.append("NumOut( ");
                        showTextAction.getX().visit(this);
                        this.sb.append(", LCD_LINE");
                        showTextAction.getY().visit(this);
                        this.sb.append(", ");
                        showTextAction.getMsg().visit(this);
                        break;
                }
                break;
            default:
                this.sb.append("NumOut( ");
                showTextAction.getX().visit(this);
                this.sb.append(", LCD_LINE");
                showTextAction.getY().visit(this);
                this.sb.append(", ");
                showTextAction.getMsg().visit(this);
                break;
        }
        this.sb.append(" );");
        return null;
    }

    @Override
    public Void visitToneAction(ToneAction<Void> toneAction) {
        this.sb.append("PlayTone( ");
        toneAction.getFrequency().visit(this);
        this.sb.append(", ");
        toneAction.getDuration().visit(this);
        this.sb.append(" );");
        return null;
    }

    @Override
    public Void visitMotorOnAction(MotorOnAction<Void> motorOnAction) {
        final boolean isDuration = motorOnAction.getParam().getDuration() != null;
        final boolean isRegulatedDrive = this.brickConfiguration.getActorOnPort(this.brickConfiguration.getLeftMotorPort()).isRegulated();
        if ( isDuration ) {
            this.sb.append("RotateMotor(OUT_" + motorOnAction.getPort() + ", ");
            motorOnAction.getParam().getSpeed().visit(this);
            if ( motorOnAction.getDurationMode() == MotorMoveMode.ROTATIONS ) {
                this.sb.append(", 360 * ");
            } else {
                this.sb.append(", ");
            }
            motorOnAction.getParam().getDuration().getValue().visit(this);
        } else {
            if ( isRegulatedDrive ) {
                this.sb.append("OnFwdReg(OUT_" + motorOnAction.getPort() + ", ");
                motorOnAction.getParam().getSpeed().visit(this);
                this.sb.append(", OUT_REGMODE_SPEED");
            } else {
                this.sb.append("OnFwd(OUT_" + motorOnAction.getPort() + ", ");
                motorOnAction.getParam().getSpeed().visit(this);
            }
        }
        this.sb.append(");");
        return null;
    }

    @Override
    public Void visitMotorSetPowerAction(MotorSetPowerAction<Void> motorSetPowerAction) {

        final String methodName = "OnReg";

        final boolean isRegulated = this.brickConfiguration.isMotorRegulated(motorSetPowerAction.getPort());
        this.sb.append(methodName + "(OUT_" + motorSetPowerAction.getPort() + ",");
        motorSetPowerAction.getPower().visit(this);

        this.sb.append(",OUT_REGMODE_SPEED");
        this.sb.append(");");
        return null;
    }

    private String getPower() {
        // TODO Auto-generated method stub
        return null;
    }

    private String getPort() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void visitMotorGetPowerAction(MotorGetPowerAction<Void> motorGetPowerAction) {
        final String methodName = "MotorPower";
        this.sb.append(methodName + "(OUT_" + motorGetPowerAction.getPort());
        this.sb.append(");");
        return null;
    }

    @Override
    public Void visitMotorStopAction(MotorStopAction<Void> motorStopAction) {
        if ( motorStopAction.getMode() == MotorStopMode.FLOAT ) {
            this.sb.append("Float(OUT_" + motorStopAction.getPort());
        } else {
            this.sb.append("Off(OUT_" + motorStopAction.getPort());
        }
        this.sb.append(");");
        return null;
    }

    @Override
    public Void visitDriveAction(DriveAction<Void> driveAction) {
        final boolean isDuration = driveAction.getParam().getDuration() != null;
        final boolean reverse =
            this.brickConfiguration.getActorOnPort(this.brickConfiguration.getLeftMotorPort()).getRotationDirection() == DriveDirection.BACKWARD
                || this.brickConfiguration.getActorOnPort(this.brickConfiguration.getRightMotorPort()).getRotationDirection() == DriveDirection.BACKWARD;
        String methodName = "";
        if ( isDuration ) {
            methodName = "RotateMotorEx";
        } else {
            methodName = "OnFwdReg";
        }
        this.sb.append(methodName + "(OUT_");
        if ( this.brickConfiguration.getLeftMotorPort().toString().charAt(0) < this.brickConfiguration.getRightMotorPort().toString().charAt(0) ) {
            this.sb.append(this.brickConfiguration.getLeftMotorPort());
            this.sb.append(this.brickConfiguration.getRightMotorPort());
        } else {
            this.sb.append(this.brickConfiguration.getRightMotorPort());
            this.sb.append(this.brickConfiguration.getLeftMotorPort());
        }
        if ( reverse ) {
            this.sb.append(", (-1) * ");
        } else {
            this.sb.append(", ");
        }
        driveAction.getParam().getSpeed().visit(this);
        this.sb.append(", ");
        if ( isDuration ) {
            this.sb.append("(");
            driveAction.getParam().getDuration().getValue().visit(this);
            this.sb.append(" * 360 / (PI * WHEELDIAMETER)), 0, true, true");
        } else {
            this.sb.append("OUT_REGMODE_SYNC");
        }
        this.sb.append(");");
        return null;
    }

    @Override // TURN ACTIONS

    public Void visitTurnAction(TurnAction<Void> turnAction) {
        final boolean isDuration = turnAction.getParam().getDuration() != null;
        final boolean reverse =
            this.brickConfiguration.getActorOnPort(this.brickConfiguration.getLeftMotorPort()).getRotationDirection() == DriveDirection.BACKWARD
                || this.brickConfiguration.getActorOnPort(this.brickConfiguration.getRightMotorPort()).getRotationDirection() == DriveDirection.BACKWARD;
        String methodName = "";
        int turnpct = 100;
        if ( isDuration ) {
            methodName = "RotateMotorEx";
        } else {
            methodName = "OnFwdSync";
        }
        this.sb.append(methodName + "(OUT_");
        if ( this.brickConfiguration.getLeftMotorPort().toString().charAt(0) < this.brickConfiguration.getRightMotorPort().toString().charAt(0) ) {
            turnpct *= -1;
            this.sb.append(this.brickConfiguration.getLeftMotorPort());
            this.sb.append(this.brickConfiguration.getRightMotorPort());
        } else {
            this.sb.append(this.brickConfiguration.getRightMotorPort());
            this.sb.append(this.brickConfiguration.getLeftMotorPort());
        }
        if ( reverse ) {
            this.sb.append(", (-1) * ");
        } else {
            this.sb.append(", ");
        }
        turnAction.getParam().getSpeed().visit(this);
        if ( turnAction.getDirection() == TurnDirection.LEFT ) {
            turnpct *= -1;
        }
        this.sb.append(", ");
        if ( isDuration ) {
            this.sb.append("(");
            turnAction.getParam().getDuration().getValue().visit(this);
            this.sb.append(" * TRACKWIDTH / WHEELDIAMETER), " + turnpct + ", true, true");
        } else {
            this.sb.append(turnpct);
        }
        this.sb.append(");");
        return null;
    }

    @Override
    public Void visitMotorDriveStopAction(MotorDriveStopAction<Void> stopAction) {
        this.sb.append("Off(OUT_");
        if ( this.brickConfiguration.getLeftMotorPort().toString().charAt(0) < this.brickConfiguration.getRightMotorPort().toString().charAt(0) ) {
            this.sb.append(this.brickConfiguration.getLeftMotorPort());
            this.sb.append(this.brickConfiguration.getRightMotorPort());
        } else {
            this.sb.append(this.brickConfiguration.getRightMotorPort());
            this.sb.append(this.brickConfiguration.getLeftMotorPort());
        }
        this.sb.append(");");
        return null;
    }

    @Override
    public Void visitBrickSensor(BrickSensor<Void> brickSensor) {
        String button = null;
        switch ( getEnumCode(brickSensor.getKey()) ) {
            case "BrickKey.ENTER":
                button = "BTNCENTER";
                break;
            case "BrickKey.LEFT":
                button = "BTNLEFT";
                break;
            case "BrickKey.RIGHT":
                button = "BTNRIGHT";
                break;
        }
        this.sb.append("ButtonPressed( " + button + ", false )");
        return null;
    }

    @Override
    public Void visitColorSensor(ColorSensor<Void> colorSensor) {
        this.sb.append("SensorColor( IN_");
        this.sb.append(colorSensor.getPort().getPortNumber());
        this.sb.append(", ");
        switch ( getEnumCode(colorSensor.getMode()) ) {
            case "ColorSensorMode.COLOUR":
                this.sb.append("\"COLOR\"");
                break;
            case "ColorSensorMode.AMBIENTLIGHT":
                this.sb.append("\"AMBIENTLIGHT\"");
                break;
            case "ColorSensorMode.RED":
                this.sb.append("\"LIGHT\"");
                break;
            /*default:
                throw new DbcException("Invalide mode for Color Sensor!");*/
        }
        this.sb.append(" )");
        return null;
    }

    @Override
    public Void visitLightSensor(LightSensor<Void> lightSensor) {
        //final String Port = getEnumCode(lightSensor.getPort());
        this.sb.append("SensorLight( IN_");
        this.sb.append(lightSensor.getPort().getPortNumber());
        this.sb.append(", ");
        switch ( getEnumCode(lightSensor.getMode()) ) {
            case "LightSensorMode.RED":
                this.sb.append("\"LIGHT\"");
                break;
            case "LightSensorMode.AMBIENTLIGHT":
                this.sb.append("\"AMBIENTLIGHT\"");
                break;
            default:
                throw new DbcException("Invalide mode for Color Sensor!");
        }
        this.sb.append(" )");
        return null;
    }

    @Override
    public Void visitSoundSensor(SoundSensor<Void> soundSensor) {
        this.sb.append("Sensor( IN_");
        this.sb.append(soundSensor.getPort().getPortNumber());
        this.sb.append(" )");
        return null;
    }

    @Override
    public Void visitEncoderSensor(EncoderSensor<Void> encoderSensor) {
        ActorPort encoderMotorPort = (ActorPort) encoderSensor.getMotorPort();
        switch ( (MotorTachoMode) encoderSensor.getMode() ) {
            case RESET:
                this.sb.append("ResetTachoCount( OUT_" + encoderMotorPort + " );");
                break;
            case ROTATION:
                this.sb.append("NumberOfRotations( OUT_" + encoderMotorPort + " )");
                break;
            case DEGREE:
                this.sb.append("MotorTachoCount( OUT_" + encoderMotorPort + " )");
                break;
            case DISTANCE:
                this.sb.append("MotorDistance( OUT_" + encoderMotorPort + ", WHEELDIAMETER )");
                break;
        }
        return null;
    }

    @Override // no gyrosensor
    public Void visitGyroSensor(GyroSensor<Void> gyroSensor) {
        return null;
    }

    @Override // no infrared sensor
    public Void visitInfraredSensor(InfraredSensor<Void> infraredSensor) {
        return null;
    }

    @Override
    public Void visitTimerSensor(TimerSensor<Void> timerSensor) {
        switch ( (TimerSensorMode) timerSensor.getMode() ) {
            case GET_SAMPLE:
                this.sb.append("GetTimerValue( timer" + timerSensor.getTimer() + " )");
                break;
            case RESET:
                this.sb.append("ResetTimerValue( timer" + timerSensor.getTimer() + " );");
                break;
            default:
                throw new DbcException("Invalid Time Mode!");
        }
        return null;
    }

    @Override
    public Void visitTouchSensor(TouchSensor<Void> touchSensor) {
        this.sb.append("Sensor( IN_" + touchSensor.getPort().getPortNumber());
        this.sb.append(" )");
        return null;
    }

    @Override
    public Void visitUltrasonicSensor(UltrasonicSensor<Void> ultrasonicSensor) {
        this.sb.append("SensorUS( IN_" + ultrasonicSensor.getPort().getPortNumber() + " )");
        return null;
    }

    @Override
    public Void visitMainTask(MainTask<Void> mainTask) {
        mainTask.getVariables().visit(this);
        return null;
    }

    @Override
    public Void visitActivityTask(ActivityTask<Void> activityTask) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void visitStartActivityTask(StartActivityTask<Void> startActivityTask) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void visitLocation(Location<Void> location) {
        return null;
    }

    @Override
    public Void visitGetSampleSensor(GetSampleSensor<Void> sensorGetSample) {
        return sensorGetSample.getSensor().visit(this);
    }

    //not used
    @Override
    public Void visitTextPrintFunct(TextPrintFunct<Void> textPrintFunct) {
        return null;
    }

    @Override
    public Void visitFunctionStmt(FunctionStmt<Void> functionStmt) {
        functionStmt.getFunction().visit(this);
        this.sb.append(";");
        return null;
    }

    @Override
    public Void visitFunctionExpr(FunctionExpr<Void> functionExpr) {
        functionExpr.getFunction().visit(this);
        return null;
    }

    @Override
    public Void visitGetSubFunct(GetSubFunct<Void> getSubFunct) {
        /*this.sb.append("BlocklyMethods.listsGetSubList( ");
        getSubFunct.getParam().get(0).visit(this);
        this.sb.append(", ");
        final IndexLocation where1 = IndexLocation.get(getSubFunct.getStrParam().get(0));
        this.sb.append(getEnumCode(where1));
        if ( where1 == IndexLocation.FROM_START || where1 == IndexLocation.FROM_END ) {
            this.sb.append(", ");
            getSubFunct.getParam().get(1).visit(this);
        }
        this.sb.append(", ");
        final IndexLocation where2 = IndexLocation.get(getSubFunct.getStrParam().get(1));
        this.sb.append(getEnumCode(where2));
        if ( where2 == IndexLocation.FROM_START || where2 == IndexLocation.FROM_END ) {
            this.sb.append(", ");
            if ( getSubFunct.getParam().size() == 3 ) {
                getSubFunct.getParam().get(2).visit(this);
            } else {
                getSubFunct.getParam().get(1).visit(this);
            }
        }
        this.sb.append(")");*/
        return null;

    }

    @Override
    public Void visitIndexOfFunct(IndexOfFunct<Void> indexOfFunct) {
        //final BlocklyType typeArr = indexOfFunct.getParam().get(0).getVarType();
        String methodName = null;
        if ( indexOfFunct.getLocation() == IndexLocation.LAST ) {
            switch ( indexOfFunct.getParam().get(0).getVarType() ) {
                case ARRAY_NUMBER:
                    methodName = "ArrFindLastNum( ";
                    break;
                case ARRAY_STRING:
                    methodName = "ArrFindLastStr( ";
                    break;
                case ARRAY_BOOLEAN:
                    methodName = "ArrFindLastBool( ";
                    break;
            }
        } else {
            switch ( indexOfFunct.getParam().get(0).getVarType() ) {
                case ARRAY_NUMBER:
                    methodName = "ArrFindFirstNum( ";
                    break;
                case ARRAY_STRING:
                    methodName = "ArrFindFirstStr( ";
                    break;
                case ARRAY_BOOLEAN:
                    methodName = "ArrFindFirstBool( ";
                    break;
            }
        }
        this.sb.append(methodName);
        indexOfFunct.getParam().get(0).visit(this);
        this.sb.append(", ");
        indexOfFunct.getParam().get(1).visit(this);
        this.sb.append(" )");
        return null;
    }

    @Override
    public Void visitLengthOfIsEmptyFunct(LengthOfIsEmptyFunct<Void> lengthOfIsEmptyFunct) {
        String methodName = "ArrayLen( ";
        if ( lengthOfIsEmptyFunct.getFunctName() == FunctionNames.LIST_IS_EMPTY ) {
            methodName = "ArrIsEmpty( ";
        }
        this.sb.append(methodName);
        lengthOfIsEmptyFunct.getParam().get(0).visit(this);
        this.sb.append(" )");
        //this.sb.append(methodName);
        //lengthOfIsEmptyFunct.getParam().get(0).visit(this);
        //this.sb.append(")");
        return null;
    }

    @Override
    public Void visitEmptyList(EmptyList<Void> emptyList) {
        //this.sb.append("");
        return null;
    }

    @Override
    public Void visitListCreate(ListCreate<Void> listCreate) {
        this.sb.append("{");
        listCreate.getValue().visit(this);
        this.sb.append("}");
        return null;
    }

    @Override
    public Void visitListRepeat(ListRepeat<Void> listRepeat) {
        /*this.sb.append("BlocklyMethods.createListWithItem(");
        listRepeat.getParam().get(0).visit(this);
        this.sb.append(", ");
        listRepeat.getParam().get(1).visit(this);
        this.sb.append(")");*/
        return null;
    }

    @Override
    public Void visitListGetIndex(ListGetIndex<Void> listGetIndex) {
        listGetIndex.getParam().get(0).visit(this);
        this.sb.append("[");
        listGetIndex.getParam().get(1).visit(this);
        this.sb.append("]");
        return null;
    }

    @Override
    public Void visitListSetIndex(ListSetIndex<Void> listSetIndex) {
        listSetIndex.getParam().get(0).visit(this);
        this.sb.append("[");
        listSetIndex.getParam().get(1).visit(this);
        this.sb.append("]");
        this.sb.append(" = ");
        listSetIndex.getParam().get(2).visit(this);
        this.sb.append(";");
        return null;
    }

    @Override
    public Void visitMathConstrainFunct(MathConstrainFunct<Void> mathConstrainFunct) {
        this.sb.append("Constrain( ");
        mathConstrainFunct.getParam().get(0).visit(this);
        this.sb.append(", ");
        mathConstrainFunct.getParam().get(1).visit(this);
        this.sb.append(", ");
        mathConstrainFunct.getParam().get(2).visit(this);
        this.sb.append(" )");
        return null;
    }

    @Override
    public Void visitMathNumPropFunct(MathNumPropFunct<Void> mathNumPropFunct) {
        switch ( mathNumPropFunct.getFunctName() ) {
            case EVEN:
                this.sb.append("( ");
                mathNumPropFunct.getParam().get(0).visit(this);
                this.sb.append(" % 2 == 0 )");
                break;
            case ODD:
                this.sb.append("( ");
                mathNumPropFunct.getParam().get(0).visit(this);
                this.sb.append(" % 2 == 1 )");
                break;
            case PRIME:
                this.sb.append("MathPrime( ");
                mathNumPropFunct.getParam().get(0).visit(this);
                this.sb.append(" )");
                break;
            // % in nxc doesn't leave a a fractional residual, e.g. 5.2%1 = 0, so it is not possible to cheack the wholeness by "%1", that is why
            //an additional function is used
            case WHOLE:
                this.sb.append("MathIsWhole( ");
                mathNumPropFunct.getParam().get(0).visit(this);
                this.sb.append(" )");
                break;
            case POSITIVE:
                this.sb.append("( ");
                mathNumPropFunct.getParam().get(0).visit(this);
                this.sb.append(" > 0 )");
                break;
            case NEGATIVE:
                this.sb.append("( ");
                mathNumPropFunct.getParam().get(0).visit(this);
                this.sb.append(" < 0 )");
                break;
            //it would work only for whole numbers, however, I think that it makes sense to talk about being divisible only for the whole numbers
            case DIVISIBLE_BY:
                this.sb.append("( ");
                mathNumPropFunct.getParam().get(0).visit(this);
                this.sb.append(" % ");
                mathNumPropFunct.getParam().get(1).visit(this);
                this.sb.append(" == 0 )");
                break;
            default:
                break;
        }
        return null;
    }

    @Override
    public Void visitMathOnListFunct(MathOnListFunct<Void> mathOnListFunct) {
        switch ( mathOnListFunct.getFunctName() ) {
            case SUM:
                this.sb.append("ArrSum( ");
                mathOnListFunct.getParam().get(0).visit(this);
                break;
            case MIN:
                this.sb.append("ArrMin( ");
                mathOnListFunct.getParam().get(0).visit(this);
                break;
            case MAX:
                this.sb.append("ArrMax( ");
                mathOnListFunct.getParam().get(0).visit(this);
                break;
            case AVERAGE:
                this.sb.append("ArrMean( ");
                mathOnListFunct.getParam().get(0).visit(this);
                break;
            case MEDIAN:
                this.sb.append("ArrMedian( ");
                mathOnListFunct.getParam().get(0).visit(this);
                break;
            case STD_DEV:
                this.sb.append("ArrStandardDeviatioin( ");
                mathOnListFunct.getParam().get(0).visit(this);
                break;
            case RANDOM:
                this.sb.append("ArrRand(");
                mathOnListFunct.getParam().get(0).visit(this);
                break;
            case MODE:
                this.sb.append("ArrMode( ");
                mathOnListFunct.getParam().get(0).visit(this);
                break;
            default:
                break;
        }
        this.sb.append(" )");
        return null;
    }

    @Override
    public Void visitMathRandomFloatFunct(MathRandomFloatFunct<Void> mathRandomFloatFunct) {
        this.sb.append("RandomFloat()");
        return null;
    }

    @Override
    public Void visitMathRandomIntFunct(MathRandomIntFunct<Void> mathRandomIntFunct) {
        this.sb.append("RandomIntegerInRange( ");
        mathRandomIntFunct.getParam().get(0).visit(this);
        this.sb.append(", ");
        mathRandomIntFunct.getParam().get(1).visit(this);
        this.sb.append(" )");
        return null;
    }

    @Override
    public Void visitMathSingleFunct(MathSingleFunct<Void> mathSingleFunct) {
        switch ( mathSingleFunct.getFunctName() ) {
            case ROOT:
                this.sb.append("sqrt( ");
                break;
            case ABS:
                this.sb.append("abs( ");
                break;
            //Taylor Series converge only when value is less than one. Larger values are calculated
            //using a table.
            case LN:
                this.sb.append("MathLn( ");
                break;
            case LOG10:
                this.sb.append("MathLog( ");
                break;
            case EXP:
                this.sb.append("MathPow( E, ");
                break;
            case POW10:
                this.sb.append("MathPow( 10, ");
                break;
            //the 3 functions below accept degrees
            case SIN:
                this.sb.append("MathSin( ");
                break;
            case COS:
                this.sb.append("MathCos( ");
                break;
            case TAN:
                this.sb.append("MathTan( ");
                break;
            case ASIN:
                this.sb.append("MathAsin( ");
                break;
            //Taylor Series converge only when value is less than one. Larger values are calculated
            //using a table.
            case ATAN:
                this.sb.append("MathAtan( ");
                break;
            case ACOS:
                this.sb.append("MathAcos( ");
                break;
            case ROUND:
                this.sb.append("MathRound( ");
                break;
            case ROUNDUP:
                this.sb.append("MathRoundUp( ");
                break;
            //check why there are double brackets
            case ROUNDDOWN:
                this.sb.append("MathFloor( ");
                break;
            default:
                break;
        }
        mathSingleFunct.getParam().get(0).visit(this);
        this.sb.append(" )");

        return null;
    }

    @Override
    public Void visitMathPowerFunct(MathPowerFunct<Void> mathPowerFunct) {
        this.sb.append("MathPow( ");
        mathPowerFunct.getParam().get(0).visit(this);
        this.sb.append(", ");
        mathPowerFunct.getParam().get(1).visit(this);
        this.sb.append(" )");
        return null;
    }

    @Override
    public Void visitTextJoinFunct(TextJoinFunct<Void> textJoinFunct) {
        //smthToString(textJoinFunct.getParam());
        //this.sb.append(")");
        return null;
    }

    @Override
    public Void visitMethodVoid(MethodVoid<Void> methodVoid) {
        this.sb.append("\n").append(INDENT).append("void ");
        this.sb.append(methodVoid.getMethodName() + "(");
        methodVoid.getParameters().visit(this);
        this.sb.append(") {");
        methodVoid.getBody().visit(this);
        this.sb.append("\n").append(INDENT).append("}");
        return null;
    }

    @Override
    public Void visitMethodReturn(MethodReturn<Void> methodReturn) {
        this.sb.append("\n").append(INDENT).append(getBlocklyTypeCode(methodReturn.getReturnType()));
        this.sb.append(" " + methodReturn.getMethodName() + "( ");
        methodReturn.getParameters().visit(this);
        this.sb.append(" ) {");
        methodReturn.getBody().visit(this);
        this.nlIndent();
        this.sb.append("return ");
        methodReturn.getReturnValue().visit(this);
        this.sb.append(";\n").append(INDENT).append("}");
        return null;
    }

    @Override
    public Void visitMethodIfReturn(MethodIfReturn<Void> methodIfReturn) {
        this.sb.append("if ( ");
        methodIfReturn.getCondition().visit(this);
        this.sb.append(" ) ");
        this.sb.append("return ");
        methodIfReturn.getReturnValue().visit(this);
        return null;
    }

    @Override
    public Void visitMethodStmt(MethodStmt<Void> methodStmt) {
        methodStmt.getMethod().visit(this);
        this.sb.append(";");
        return null;
    }

    @Override
    public Void visitMethodCall(MethodCall<Void> methodCall) {
        this.sb.append(methodCall.getMethodName() + "( ");
        methodCall.getParametersValues().visit(this);
        this.sb.append(" )");
        if ( methodCall.getReturnType() == BlocklyType.VOID ) {
            this.sb.append(";");
        }
        return null;
    }

    // TODO: fix calling
    // the function is in hal.h
    @Override
    public Void visitBluetoothReceiveAction(BluetoothReceiveAction<Void> bluetoothReadAction) {
        this.sb.append("BluetoothGetNumber( ");
        //TODO: add these block options:
        //this.sb.append("BluetoothGetString( ");
        //this.sb.append("BluetoothGetBoolean( ");
        // the function accepts inbox address (int)
        bluetoothReadAction.getConnection().visit(this);
        this.sb.append(" )");
        return null;
    }

    // not needed for nxt. Use a block that calls BTCheck(int conn) function instead
    @Override
    public Void visitBluetoothConnectAction(BluetoothConnectAction<Void> bluetoothConnectAction) {
        this.sb.append("BTCheck(");

        /*this.sb.append("hal.establishConnectionTo(");
        if ( bluetoothConnectAction.get_address().getKind() != BlockType.STRING_CONST ) {
            this.sb.append("String.valueOf(");
            bluetoothConnectAction.get_address().visit(this);
            this.sb.append(")");
        } else {
            bluetoothConnectAction.get_address().visit(this);
        }*/
        this.sb.append(")");
        return null;
    }

    // the function is built-in
    @Override
    public Void visitBluetoothSendAction(BluetoothSendAction<Void> bluetoothSendAction) {
        this.sb.append("SendRemoteNumber(");
        //TODO: add these block options: output variable (string, boolean or number. Need to create an enumeration), connection (int, 1-3 for master, always
        // 0 for slave), outbox address (int)
        //this.sb.append("SendRemoteString(");
        //this.sb.append("SendRemoteBool(");
        // the function accepts the following: inbox address

        //if ( bluetoothSendAction.getMsg().getKind() != BlockType.STRING_CONST ) {
        //    String.valueOf(bluetoothSendAction.getMsg().visit(this));
        //} else {
        //    bluetoothSendAction.getMsg().visit(this);
        //}
        //this.sb.append(", ");
        //bluetoothSendAction.getConnection().visit(this);
        this.sb.append(");");
        return null;
    }

    //TODO: add SysCommBTOn
    @Override
    public Void visitBluetoothWaitForConnectionAction(BluetoothWaitForConnectionAction<Void> bluetoothWaitForConnection) {
        return null;
    }

    private void incrIndentation() {
        this.indentation += 1;
    }

    private void decrIndentation() {
        this.indentation -= 1;
    }

    private void indent() {
        if ( this.indentation <= 0 ) {
            return;
        } else {
            for ( int i = 0; i < this.indentation; i++ ) {
                this.sb.append(INDENT);
            }
        }
    }

    private void nlIndent() {
        this.sb.append("\n");
        indent();
    }

    private String whitespace() {
        return " ";
    }

    private boolean isStringExpr(Expr<Void> e) {
        switch ( e.getKind() ) {
            case STRING_CONST:
                return true;
            case VAR:
                return ((Var<?>) e).getTypeVar() == BlocklyType.STRING;
            case FUNCTION_EXPR:
                final BlockType functionKind = ((FunctionExpr<?>) e).getFunction().getKind();
                return functionKind == BlockType.TEXT_JOIN_FUNCT || functionKind == BlockType.LIST_INDEX_OF;
            case METHOD_EXPR:
                final MethodCall<?> methodCall = (MethodCall<?>) ((MethodExpr<?>) e).getMethod();
                return methodCall.getKind() == BlockType.METHOD_CALL && methodCall.getReturnType() == BlocklyType.STRING;
            case ACTION_EXPR:
                final Action<?> action = ((ActionExpr<?>) e).getAction();
                return action.getKind() == BlockType.BLUETOOTH_RECEIVED_ACTION;

            default:
                return false;
        }
    }

    private boolean parenthesesCheck(Binary<Void> binary) {
        return binary.getOp() == Op.MINUS && binary.getRight().getKind() == BlockType.BINARY && binary.getRight().getPrecedence() <= binary.getPrecedence();
    }

    private void generateSubExpr(StringBuilder sb, boolean minusAdaption, Expr<Void> expr, Binary<Void> binary) {
        if ( expr.getPrecedence() >= binary.getPrecedence() && !minusAdaption && expr.getKind() != BlockType.BINARY ) {
            // parentheses are omitted
            expr.visit(this);
        } else {
            sb.append("(" + whitespace());
            expr.visit(this);
            sb.append(whitespace() + ")");
        }
    }

    private void generateExprCode(Unary<Void> unary, StringBuilder sb) {
        if ( unary.getExpr().getPrecedence() < unary.getPrecedence() ) {
            sb.append("( ");
            unary.getExpr().visit(this);
            sb.append(" )");
        } else {
            unary.getExpr().visit(this);
        }
    }

    //TODO: ifStmt.getExpr().get(i) only gives true. Doesn't show body of the block. Old code? Fix.
    private void generateCodeFromIfElse(IfStmt<Void> ifStmt) {
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

    // TODO: is not being shown at all. Fix.
    private void generateCodeFromElse(IfStmt<Void> ifStmt) {
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
        final ExprList<Void> expressions = (ExprList<Void>) expr;
        expressions.get().get(0).visit(this);
        this.sb.append(whitespace() + "=" + whitespace());
        expressions.get().get(1).visit(this);
        this.sb.append(";" + whitespace());
        expressions.get().get(0).visit(this);
        this.sb.append(whitespace());
        this.sb.append("<" + whitespace());
        expressions.get().get(2).visit(this);
        this.sb.append(";" + whitespace());
        expressions.get().get(0).visit(this);
        this.sb.append(whitespace());
        this.sb.append("+=" + whitespace());
        expressions.get().get(3).visit(this);
        this.sb.append(whitespace() + ")" + whitespace() + "{");
    }

    private void appendBreakStmt(RepeatStmt<Void> repeatStmt) {
        if ( repeatStmt.getMode() == Mode.WAIT ) {
            nlIndent();
            this.sb.append("break;");
        }
    }

    private void addConstants() {
        this.sb.append("#define WHEELDIAMETER " + this.brickConfiguration.getWheelDiameterCM() + "\n");
        this.sb.append("#define TRACKWIDTH " + this.brickConfiguration.getTrackWidthCM() + "\n");
        this.sb.append("#include \"hal.h\" \n");
        this.sb.append("#include \"NXCDefs.h\" \n");
    }

    private void generatePrefix(boolean withWrapping) {
        if ( !withWrapping ) {
            return;
        }

        this.addConstants();
        //this.addFunctions();

        this.sb.append("task main(){");

        //add sensors:
        for ( final Entry<ISensorPort, Sensor> entry : this.brickConfiguration.getSensors().entrySet() ) {
            nlIndent();
            this.sb.append("SetSensor( IN_");
            switch ( entry.getValue().getType() ) {
                case COLOR:
                    this.sb.append(entry.getKey().getPortNumber() + ", SENSOR_COLORFULL );");
                    //this.sb.append("SetSensor(IN_" + entry.getKey().getPortNumber() + ", SENSOR_COLORFULL);");
                    break;
                case LIGHT:
                    this.sb.append(entry.getKey().getPortNumber() + ", SENSOR_LIGHT );");
                    break;
                case TOUCH:
                    this.sb.append(entry.getKey().getPortNumber() + ", SENSOR_TOUCH );");
                    break;
                case ULTRASONIC:
                    this.sb.append(entry.getKey().getPortNumber() + ", SENSOR_LOWSPEED );");
                    break;
                case SOUND:
                    this.sb.append(entry.getKey().getPortNumber() + ", SENSOR_SOUND );");
                    break;
                default:
                    break;
            }
        }

        //TODO: hide it after the used block part is implemented
        nlIndent();
        this.sb.append("long timer1;");
        nlIndent();
        this.sb.append("SetTimerValue( timer1 );");
    }

    /**
     * @return Java code used in the code generation to regenerates the same brick configuration
     */
    /*
    public String generateRegenerateConfiguration() {
        StringBuilder sb = new StringBuilder();
        sb.append(" brickConfiguration = new Ev3Configuration.Builder()\n");
        sb.append(INDENT).append(INDENT).append(INDENT).append("    .setWheelDiameter(" + this.brickConfiguration.getWheelDiameterCM() + ")\n");
        sb.append(INDENT).append(INDENT).append(INDENT).append("    .setTrackWidth(" + this.brickConfiguration.getTrackWidthCM() + ")\n");
        appendActors(sb);
        appendSensors(sb);
        sb.append(INDENT).append(INDENT).append(INDENT).append("    .build();");
        return sb.toString();
    }
    private void appendSensors(StringBuilder sb) {
        for ( Map.Entry<SensorPort, EV3Sensor> entry : this.brickConfiguration.getSensors().entrySet() ) {
            sb.append(INDENT).append(INDENT).append(INDENT);
            appendOptional(sb, "    .addSensor(", entry.getKey(), entry.getValue());
        }
    }
    private void appendActors(StringBuilder sb) {
        for ( Map.Entry<ActorPort, EV3Actor> entry : this.brickConfiguration.getActors().entrySet() ) {
            sb.append(INDENT).append(INDENT).append(INDENT);
            appendOptional(sb, "    .addActor(", entry.getKey(), entry.getValue());
        }
    }
    */

    /* NXT can run the sensors quite fast, so this check is unnecessary. The sensors are already added above.
     private static void appendOptional(StringBuilder sb, String type, @SuppressWarnings("rawtypes") Enum port, HardwareComponent hardwareComponent) {
        if ( hardwareComponent != null ) {
            sb.append(type).append(getEnumCode(port)).append(", ");
            if ( hardwareComponent.getCategory() == Category.SENSOR ) {
                sb.append(generateRegenerateEV3Sensor(hardwareComponent));
            } else {
                sb.append(generateRegenerateEV3Actor(hardwareComponent));
            }
            sb.append(")\n");
        }
    }
    private String generateRegenerateUsedSensors() {
        StringBuilder sb = new StringBuilder();
        String arrayOfSensors = "";
        for ( UsedSensor usedSensor : this.usedSensors ) {
            arrayOfSensors += usedSensor.generateRegenerate();
            arrayOfSensors += ", ";
        }
        sb.append("private Set<UsedSensor> usedSensors = " + "new LinkedHashSet<UsedSensor>(");
        if ( this.usedSensors.size() > 0 ) {
            sb.append("Arrays.asList(" + arrayOfSensors.substring(0, arrayOfSensors.length() - 2) + ")");
        }
        sb.append(");");
        return sb.toString();
    }
    */

    /* There is no need in explicit instantiation of the motors and other actors, except for the sensors,
     * in nxc
     private static String generateRegenerateEV3Actor(HardwareComponent actor) {
        StringBuilder sb = new StringBuilder();
        EV3Actor ev3Actor = (EV3Actor) actor;
        sb.append("new EV3Actor(").append(getHardwareComponentTypeCode(actor.getComponentType()));
        sb.append(", ").append(ev3Actor.isRegulated());
        sb.append(", ").append(getEnumCode(ev3Actor.getRotationDirection())).append(", ").append(getEnumCode(ev3Actor.getMotorSide())).append(")");
        return sb.toString();
    }
    private static String generateRegenerateEV3Sensor(HardwareComponent sensor) {
        StringBuilder sb = new StringBuilder();
        sb.append("new EV3Sensor(").append(getHardwareComponentTypeCode(sensor.getComponentType()));
        sb.append(")");
        return sb.toString();
    }
    private static String getHardwareComponentTypeCode(HardwareComponentType type) {
        return type.getClass().getSimpleName() + "." + type.getTypeName();
    }
     */

    private static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch ( final NumberFormatException e ) {
            return false;
        }
    }

}