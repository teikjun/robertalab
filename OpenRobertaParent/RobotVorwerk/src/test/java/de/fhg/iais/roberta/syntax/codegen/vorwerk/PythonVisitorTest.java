package de.fhg.iais.roberta.syntax.codegen.vorwerk;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import de.fhg.iais.roberta.components.Configuration;
import de.fhg.iais.roberta.components.vorwerk.VorwerkConfiguration;
import de.fhg.iais.roberta.util.test.ev3.HelperVorwerkForXmlTest;

public class PythonVisitorTest {
    private final HelperVorwerkForXmlTest h = new HelperVorwerkForXmlTest();

    private static final String IMPORTS =
        "" //
            + "#!/usr/bin/python\n\n"
            + "from __future__ import absolute_import\n"
            + "from roberta import Hal\n"
            + "from roberta import BlocklyMethods\n"
            + "import math\n\n"
            + "class BreakOutOfALoop(Exception): pass\n"
            + "class ContinueLoop(Exception): pass\n\n";

    private static final String GLOBALS = "hal = Hal()\n";

    private static final String MAIN_METHOD =
        "" //
            + "def main():\n"
            + "    try:\n"
            + "        run()\n"
            + "    except Exception as e:\n"
            + "        print('Fehler im Vorwerk')\n"
            + "        print(e.__class__.__name__)\n"
            + "        print(e)\n"
            + "if __name__ == \"__main__\":\n"
            + "    main()";
    private static Configuration brickConfiguration;

    @BeforeClass
    public static void setupConfigurationForAllTests() {
        Configuration.Builder<?> builder = new VorwerkConfiguration.Builder();
        brickConfiguration = builder.build();
    }

    @Test
    public void visitTouchSensor_GetValuesFromAllPortsAndSlots_ReturnsCorrectPythonProgram() throws Exception {
        String expectedResult =
            "" //
                + IMPORTS
                + GLOBALS
                + "\nitem = True\n"
                + "def run():\n"
                + "    global item\n"
                + "    item = hal.sample_touch_sensor('left', 'front')\n"
                + "    item = hal.sample_touch_sensor('left', 'side')\n"
                + "    item = hal.sample_touch_sensor('right', 'front')\n"
                + "    item = hal.sample_touch_sensor('right', 'side')\n"
                + "\n"
                + MAIN_METHOD;

        assertCodeIsOk(expectedResult, "/sensors/touch.xml");
    }

    @Test
    public void visitUltrasonicsSensor_GetValuesFromAllPortsAndSlots_ReturnsCorrectPythonProgram() throws Exception {
        String expectedResult =
            "" //
                + IMPORTS
                + GLOBALS
                + "\nitem = 0\n"
                + "def run():\n"
                + "    global item\n"
                + "    item = hal.sample_ultrasonic_sensor('left', 'left')\n"
                + "    item = hal.sample_ultrasonic_sensor('left', 'center')\n"
                + "    item = hal.sample_ultrasonic_sensor('left', 'right')\n"
                + "    item = hal.sample_ultrasonic_sensor('center', 'left')\n"
                + "    item = hal.sample_ultrasonic_sensor('center', 'center')\n"
                + "    item = hal.sample_ultrasonic_sensor('center', 'right')\n"
                + "    item = hal.sample_ultrasonic_sensor('right', 'left')\n"
                + "    item = hal.sample_ultrasonic_sensor('right', 'center')\n"
                + "    item = hal.sample_ultrasonic_sensor('right', 'right')\n"
                + "\n"
                + MAIN_METHOD;

        assertCodeIsOk(expectedResult, "/sensors/ultrasonic.xml");
    }

    @Test
    public void visitAccelerometerSensor_GetValuesFromAllPortsAndSlots_ReturnsCorrectPythonProgram() throws Exception {
        String expectedResult =
            "" //
                + IMPORTS
                + GLOBALS
                + "\nitem = 0\n"
                + "def run():\n"
                + "    global item\n"
                + "    item = hal.sample_accelerometer_sensor('x')\n"
                + "    item = hal.sample_accelerometer_sensor('y')\n"
                + "    item = hal.sample_accelerometer_sensor('z')\n"
                + "\n"
                + MAIN_METHOD;

        assertCodeIsOk(expectedResult, "/sensors/accelerometer.xml");
    }

    @Test
    public void visitDropOffSensor_GetValuesFromAllPortsAndSlots_ReturnsCorrectPythonProgram() throws Exception {
        String expectedResult =
            "" //
                + IMPORTS
                + GLOBALS
                + "\nitem = 0\n"
                + "def run():\n"
                + "    global item\n"
                + "    item = hal.sample_dropoff_sensor('left')\n"
                + "    item = hal.sample_dropoff_sensor('right')\n"
                + "\n"
                + MAIN_METHOD;

        assertCodeIsOk(expectedResult, "/sensors/drop_off.xml");
    }

    @Test
    public void visitWallSensor_GetValuesFromAllPortsAndSlots_ReturnsCorrectPythonProgram() throws Exception {
        String expectedResult =
            "" //
                + IMPORTS
                + GLOBALS
                + "\nitem = 0\n"
                + "def run():\n"
                + "    global item\n"
                + "    item = hal.sample_wall_sensor()\n"
                + "\n"
                + MAIN_METHOD;

        assertCodeIsOk(expectedResult, "/sensors/wall.xml");
    }

    @Test
    public void visitDriveForward_GetValuesFromAllPortsAndSlots_ReturnsCorrectPythonProgram() throws Exception {
        String expectedResult =
            "" //
                + IMPORTS
                + GLOBALS
                + "\ndef run():\n"
                + "    hal.drive_distance('foreward', 30, 20)\n"
                + "    hal.drive_distance('backward', 30, 20)\n"
                + "\n"
                + MAIN_METHOD;

        assertCodeIsOk(expectedResult, "/actors/drive.xml");
    }

    @Test
    public void visitMotorStop_GetValuesFromAllPortsAndSlots_ReturnsCorrectPythonProgram() throws Exception {
        String expectedResult =
            "" //
                + IMPORTS
                + GLOBALS
                + "\ndef run():\n"
                + "    hal.stop_motors()\n"
                + "\n"
                + MAIN_METHOD;

        assertCodeIsOk(expectedResult, "/actors/stop.xml");
    }

    private void assertCodeIsOk(String a, String fileName) throws Exception {
        String b = this.h.generatePython(fileName, brickConfiguration);
        Assert.assertEquals(a, b);
        //Assert.assertEquals(a.replaceAll("\\s+", ""), b.replaceAll("\\s+", ""));
    }
}
