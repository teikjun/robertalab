package de.fhg.iais.roberta.syntax.sensor.generic;

import de.fhg.iais.roberta.blockly.generated.Block;
import de.fhg.iais.roberta.factory.IRobotFactory;
import de.fhg.iais.roberta.inter.mode.sensor.IColorSensorMode;
import de.fhg.iais.roberta.inter.mode.sensor.ISensorPort;
import de.fhg.iais.roberta.mode.sensor.ColorSensorMode;
import de.fhg.iais.roberta.mode.sensor.SensorPort;
import de.fhg.iais.roberta.syntax.BlockTypeContainer;
import de.fhg.iais.roberta.syntax.BlocklyBlockProperties;
import de.fhg.iais.roberta.syntax.BlocklyComment;
import de.fhg.iais.roberta.syntax.Phrase;
import de.fhg.iais.roberta.syntax.sensor.ExternalSensor;
import de.fhg.iais.roberta.syntax.sensor.SensorMetaDataBean;
import de.fhg.iais.roberta.transformer.Jaxb2AstTransformer;
import de.fhg.iais.roberta.visitor.AstVisitor;
import de.fhg.iais.roberta.visitor.sensor.AstSensorsVisitor;

/**
 * This class represents the <b>robSensors_colour_getMode</b>, <b>robSensors_colour_getSample</b> and <b>robSensors_colour_setMode</b> blocks from Blockly into
 * the AST (abstract syntax
 * tree).
 * Object from this class will generate code for setting the mode of the sensor or getting a sample from the sensor.<br/>
 * <br>
 * The client must provide the {@link SensorPort} and {@link ColorSensorMode}. See enum {@link ColorSensorMode} for all possible modes of the sensor.<br>
 * <br>
 * To create an instance from this class use the method {@link #make(ColorSensorMode, SensorPort, BlocklyBlockProperties, BlocklyComment)}.<br>
 */
public class ColorSensor<V> extends ExternalSensor<V> {

    private ColorSensor(IColorSensorMode mode, ISensorPort port, BlocklyBlockProperties properties, BlocklyComment comment) {
        super(mode, port, BlockTypeContainer.getByName("COLOR_SENSING"), properties, comment);
        setReadOnly();
    }

    /**
     * Create object of the class {@link ColorSensor}.
     *
     * @param mode in which the sensor is operating; must be <b>not</b> null; see enum {@link ColorSensorMode} for all possible modes that the sensor have,
     * @param port on where the sensor is connected; must be <b>not</b> null; see enum {@link SensorPort} for all possible sensor ports,
     * @param properties of the block (see {@link BlocklyBlockProperties}),
     * @param comment added from the user,
     * @return read only object of class {@link ColorSensor}
     */
    static <V> ColorSensor<V> make(IColorSensorMode mode, ISensorPort port, BlocklyBlockProperties properties, BlocklyComment comment) {
        return new ColorSensor<V>(mode, port, properties, comment);
    }

    @Override
    public String toString() {
        return "ColorSensor [" + this.getMode() + ", " + this.getPort() + "]";
    }

    @Override
    protected V accept(AstVisitor<V> visitor) {
        return ((AstSensorsVisitor<V>) visitor).visitColorSensor(this);
    }

    /**
     * Transformation from JAXB object to corresponding AST object.
     *
     * @param block for transformation
     * @param helper class for making the transformation
     * @return corresponding AST object
     */
    public static <V> Phrase<V> jaxbToAst(Block block, Jaxb2AstTransformer<V> helper) {
        IRobotFactory factory = helper.getModeFactory();
        SensorMetaDataBean sensorData = extracPortAndMode(block, helper);
        String mode = sensorData.getMode();
        String port = sensorData.getPort();
        return ColorSensor
            .make(factory.getColorSensorMode(mode), factory.getSensorPort(port), helper.extractBlockProperties(block), helper.extractComment(block));
    }

}
