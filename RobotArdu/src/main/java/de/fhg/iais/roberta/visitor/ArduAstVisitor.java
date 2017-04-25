package de.fhg.iais.roberta.visitor;

import de.fhg.iais.roberta.syntax.sensor.arduino.VoltageSensor;
import de.fhg.iais.roberta.visitor.actor.AstActorCommunicationVisitor;
import de.fhg.iais.roberta.visitor.actor.AstActorDisplayVisitor;
import de.fhg.iais.roberta.visitor.actor.AstActorLightVisitor;
import de.fhg.iais.roberta.visitor.actor.AstActorMotorVisitor;
import de.fhg.iais.roberta.visitor.actor.AstActorSoundVisitor;
import de.fhg.iais.roberta.visitor.sensor.AstSensorsVisitor;

/**
 * Interface to be used with the visitor pattern to traverse an AST (and generate code, e.g.).
 */
public interface ArduAstVisitor<V> extends AstVisitor<V>, AstLanguageVisitor<V>, AstSensorsVisitor<V>, AstActorCommunicationVisitor<V>,
    AstActorDisplayVisitor<V>, AstActorMotorVisitor<V>, AstActorLightVisitor<V>, AstActorSoundVisitor<V> {

    /**
     * visit a {@link VoltageSensor}.
     *
     * @param voltageSensor to be visited
     */
    V visitVoltageSensor(VoltageSensor<V> voltageSensor);

}
