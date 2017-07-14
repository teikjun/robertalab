package de.fhg.iais.roberta.syntax.check.program;

import java.util.ArrayList;

import de.fhg.iais.roberta.components.BotNrollConfiguration;
import de.fhg.iais.roberta.syntax.Phrase;
import de.fhg.iais.roberta.syntax.lang.expr.VarDeclaration;
import de.fhg.iais.roberta.syntax.sensor.botnroll.VoltageSensor;
import de.fhg.iais.roberta.syntax.sensor.generic.TemperatureSensor;
import de.fhg.iais.roberta.visitor.BotnrollAstVisitor;

/**
 * This visitor collects information for used actors and sensors in blockly program.
 *
 * @author kcvejoski
 */
public class BotNrollCodePreprocessVisitor extends PreprocessProgramVisitor implements BotnrollAstVisitor<Void> {
    private ArrayList<VarDeclaration<Void>> visitedVars = new ArrayList<VarDeclaration<Void>>();

    public BotNrollCodePreprocessVisitor(ArrayList<ArrayList<Phrase<Void>>> phrasesSet, BotNrollConfiguration configuration) {
        super(configuration);
        check(phrasesSet);
    }

    @Override
    public Void visitVarDeclaration(VarDeclaration<Void> var) {
        if ( !var.toString().contains("false, false") ) {
            this.visitedVars.add(var);
        }
        return null;
    }

    public ArrayList<VarDeclaration<Void>> getvisitedVars() {
        return this.visitedVars;
    }

    @Override
    public Void visitVoltageSensor(VoltageSensor<Void> voltageSensor) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void visitTemperatureSensor(TemperatureSensor<Void> temperatureSensor) {
        // TODO Auto-generated method stub
        return null;
    }

}
