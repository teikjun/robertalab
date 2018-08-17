package de.fhg.iais.roberta.syntax.codegen.arduino.arduino;

import org.junit.Test;

import de.fhg.iais.roberta.util.test.ardu.HelperArduinoForXmlTest;

public class CppVisitorTest {
    private final HelperArduinoForXmlTest h = new HelperArduinoForXmlTest();

    private static final String INCLUDE =
        ""
            + "#include<math.h>\n"
            + "#include<Encoder.h>"
            + "#include<DHT.h>"
            + "#include<IRremote.h>"
            + "#include<LiquidCrystal.h>"
            + "#include<LiquidCrystal_I2C.h>"
            + "#include<Servo.h>"
            + "#include<Stepper.h>"
            + "#include<SPI.h>"
            + "#include<MFRC522.h>"
            + "#include<RobertaFunctions.h>//OpenRobertalibrary"
            + "RobertaFunctionsrob;";

    private static final String DEFINES =
        ""
            + "int_spiele_BUZZER=5;"
            + "int_S_DROP=A0;"
            + "int_CLK_ENCODER=6;"
            + "int_DT_ENCODER=5;int_SW_ENCODER=2;"
            + "Encoder_myEncoder_ENCODER(_DT_ENCODER,_CLK_ENCODER);"
            + "#defineDHTPINHUMIDITY2"
            + "#defineDHTTYPEDHT11"
            + "DHT_dht_HUMIDITY(DHTPINHUMIDITY,DHTTYPE);"
            + "int_RECV_PIN_IR=11;"
            + "IRrecv_irrecv_IR(_RECV_PIN_IR);"
            + "decode_results_results_IR;"
            + "int_taster_KEY=1;"
            + "LiquidCrystal_lcd_L(12,11,5,6,3,2);"
            + "LiquidCrystal_I2C_lcd_L(0x27,16,2);"
            + "int_led_LED=13;"
            + "int_led_red_RGBLED=5;"
            + "int_led_green_RGBLED=6;"
            + "int_led_blue_RGBLED=3;"
            + "int_output_LIGHT=A0;"
            + "int_moisturePin_MOISTURE=A0;"
            + "int_output_MOTION=7;"
            + "Servo_servo_SERVO;"
            + "int_SPU_STEP=2048;"
            + "StepperMotor_STEP(_SPU_STEP,6,5,4,3);"
            + "int_output_POTENTIOMETER=A0;"
            + "int_SensorPin_PULSE=A0;"
            + "int_relay_PULSE=6;"
            + "#defineSS_PIN_RFID9"
            + "#defineRST_PIN_RFID10"
            + "MFRC522_mfrc522_RFID(SS_PIN_RFID,RST_PIN_RFID);"
            + "int_TMP36_TEMPERATURE=A0;"
            + "int_trigger_ULTRASONIC=6;"
            + "int_echo_ULTRASONIC=7;"
            + "double_signalToDistance=0.03432/2;";

    private static final String VOID_SETUP =
        ""
            + "double_getUltrasonicDistance()"
            + "{digitalWrite(_trigger_ULTRASONIC,LOW);"
            + "delay(5);"
            + "digitalWrite(_trigger_ULTRASONIC,HIGH);"
            + "delay(10);"
            + "digitalWrite(_trigger_ULTRASONIC,LOW);"
            + "returnpulseIn(_echo_ULTRASONIC,HIGH)*_signalToDistance;}"
            + "String_readRFIDData()"
            + "{if(!_mfrc522_RFID.PICC_IsNewCardPresent()){return\"N/A\";}"
            + "if(!_mfrc522_R.PICC_ReadCardSerial()){return\"N/D\";}"
            + "returnString(((long)(_mfrc522_RFID.uid.uidByte[0])<<24)|((long)(_mfrc522_RFID.uid.uidByte[1])<<16)|((long)(_mfrc522_RFID.uid.uidByte[2])<<8)|((long)_mfrc522_RFID.uid.uidByte[3]),HEX);}"
            + "voidsetup(){"
            + "Serial.begin(9600);"
            + "pinMode(_SW_ENCODER,INPUT);"
            + "attachInterrupt(digitalPinToInterrupt(_SW_ENCODER),Interrupt,CHANGE);"
            + "_dht_HUMIDITY.begin();"
            + "pinMode(13,OUTPUT);"
            + "_irrecv_IR.enableIRIn();"
            + "pinMode(_taster_KEY,INPUT);"
            + "_lcd_L.begin(16,2);"
            + "_lcd_L.begin();"
            + "pinMode(_led_LED,OUTPUT);"
            + "pinMode(_led_red_RGBLED,OUTPUT);"
            + "pinMode(_led_green_RGBLED,OUTPUT);"
            + "pinMode(_led_blue_RGBLED,OUTPUT);"
            + "pinMode(_output_MOTION,INPUT);"
            + "_servo_SERVO.attach(8);"
            + "pinMode(_relay_PULSE,OUTPUT);"
            + "SPI.begin();"
            + "_mfrc522_RFID.PCD_Init();"
            + "pinMode(_trigger_ULTRASONIC,OUTPUT);"
            + "pinMode(_echo_ULTRASONIC,INPUT);";

    @Test
    public void visitShowTextActionTest() throws Exception {
        final String a =
            "" //
                + INCLUDE
                + DEFINES
                + VOID_SETUP
                + "}voidloop(){"
                + "_lcd_L.setCursor(0,1);"
                + "_lcd_L.print(\"Hallo\");"
                + "}\n";

        this.h.assertCodeIsOk(a, "/syntax/code_generator/arduino/show_text.xml", true);
    }

    @Test
    public void visitShowTextActionI2CTest() throws Exception {
        final String a =
            "" //
                + INCLUDE
                + DEFINES
                + VOID_SETUP
                + "}voidloop(){"
                + "_lcd_L.setCursor(0,1);"
                + "_lcd_L.print(\"Hallo\");"
                + "}\n";

        this.h.assertCodeIsOk(a, "/syntax/code_generator/arduino/show_text_i2c.xml", true);
    }

    @Test
    public void visitClearDisplayActionTest() throws Exception {

        final String a =
            "" //
                + INCLUDE
                + DEFINES
                + VOID_SETUP
                + "}voidloop(){"
                + "_lcd_L.clear();"
                + "}\n";

        this.h.assertCodeIsOk(a, "/syntax/code_generator/arduino/clear_display.xml", true);
    }

    @Test
    public void visitLightActionLedTest() throws Exception {

        final String a =
            "" //
                + INCLUDE
                + DEFINES
                + VOID_SETUP
                + "}voidloop(){"
                + "digitalWrite(_led_L2,HIGH);"
                + "delay(2000);"
                + "digitalWrite(_led_L2,LOW);"
                + "delay(2000);"
                + "}\n";

        this.h.assertCodeIsOk(a, "/syntax/code_generator/arduino/led.xml", true);
    }

    @Test
    public void visitToneActionTest() throws Exception {

        final String a =
            "" //
                + INCLUDE
                + DEFINES
                + VOID_SETUP
                + "}voidloop(){"
                + "tone(_spiele_B,300,100);"
                + "delay(2000);"
                + "}\n";

        this.h.assertCodeIsOk(a, "/syntax/code_generator/arduino/buzzer.xml", true);
    }

    @Test
    public void visitLightActionTest() throws Exception {

        final String a =
            "" //
                + INCLUDE
                + DEFINES
                + VOID_SETUP
                + "}voidloop(){"
                + "analogWrite(_led_red_R,120);"
                + "analogWrite(_led_green_R,120);"
                + "analogWrite(_led_blue_R,120);"
                + "delay(1000);"
                + "analogWrite(_led_red_R,0);"
                + "analogWrite(_led_green_R,0);"
                + "analogWrite(_led_blue_R,0);"
                + "delay(1000);"
                + "}\n";

        this.h.assertCodeIsOk(a, "/syntax/code_generator/arduino/rgbled.xml", true);
    }

    @Test
    public void visitMotorOnActionServoTest() throws Exception {

        final String a =
            "" //
                + INCLUDE
                + DEFINES
                + VOID_SETUP
                + "}voidloop(){"
                + "_servo_SERVO.write(90);delay(1000);"
                + "_servo_SERVO.write(270);"
                + "delay(2000);"
                + "}\n";

        this.h.assertCodeIsOk(a, "/syntax/code_generator/arduino/servo.xml", true);
    }

    @Test
    public void visitMotorOnActionStepTest() throws Exception {

        final String a =
            "" //
                + INCLUDE
                + DEFINES
                + VOID_SETUP
                + "}voidloop(){"
                + "Motor_S.setSpeed(10);"
                + "Motor_S.step(_SPU_S*5);"
                + "}\n";

        this.h.assertCodeIsOk(a, "/syntax/code_generator/arduino/step.xml", true);
    }

    @Test
    public void visitUltrasonicSensorTest() throws Exception {

        final String a =
            "" //
                + INCLUDE
                + DEFINES
                + VOID_SETUP
                + "}voidloop(){"
                + "_lcd_LCD.setCursor(0,1);"
                + "_lcd_LCD.print(_getUltrasonicDistance());"
                + "}\n";

        this.h.assertCodeIsOk(a, "/syntax/code_generator/arduino/ultrasonic.xml", true);
    }

    @Test
    public void visitTimeSensorTest() throws Exception {

        final String a =
            "" //
                + INCLUDE
                + DEFINES
                + "unsignedlong__time=millis();"
                + "double_getUltrasonicDistance(){"
                + "digitalWrite(_trigger_ULTRASONIC,LOW);"
                + "delay(5);digitalWrite(_trigger_ULTRASONIC,HIGH);"
                + "delay(10);digitalWrite(_trigger_ULTRASONIC,LOW);"
                + "returnpulseIn(_echo_ULTRASONIC,HIGH)*_signalToDistance;}"
                + "String_readRFIDData()"
                + "{if(!_mfrc522_RFID.PICC_IsNewCardPresent()){return\"N/A\";}"
                + "if(!_mfrc522_R.PICC_ReadCardSerial()){return\"N/D\";}"
                + "returnString(((long)(_mfrc522_RFID.uid.uidByte[0])<<24)|((long)(_mfrc522_RFID.uid.uidByte[1])<<16)|((long)(_mfrc522_RFID.uid.uidByte[2])<<8)|((long)_mfrc522_RFID.uid.uidByte[3]),HEX);}"
                + "voidsetup()"
                + "{Serial.begin(9600);"
                + "pinMode(_SW_ENCODER,INPUT);"
                + "attachInterrupt(digitalPinToInterrupt(_SW_ENCODER),Interrupt,CHANGE);"
                + "_dht_HUMIDITY.begin();"
                + "pinMode(13,OUTPUT);"
                + "_irrecv_IR.enableIRIn();"
                + "pinMode(_taster_KEY,INPUT);"
                + "_lcd_L.begin(16,2);"
                + "_lcd_L.begin();"
                + "pinMode(_led_LED,OUTPUT);"
                + "pinMode(_led_red_RGBLED,OUTPUT);"
                + "pinMode(_led_green_RGBLED,OUTPUT);"
                + "pinMode(_led_blue_RGBLED,OUTPUT);"
                + "pinMode(_output_MOTION,INPUT);"
                + "_servo_SERVO.attach(8);"
                + "pinMode(_relay_PULSE,OUTPUT);"
                + "SPI.begin();"
                + "_mfrc522_RFID.PCD_Init();"
                + "pinMode(_trigger_ULTRASONIC,OUTPUT);"
                + "pinMode(_echo_ULTRASONIC,INPUT);"
                + "}voidloop(){"
                + "_lcd_LCD.setCursor(0,1);"
                + "_lcd_LCD.print((int)(millis()-__time));"
                + "delay(500);"
                + "__time=millis();"
                + "delay(500);"
                + "_lcd_LCD.setCursor(0,1);"
                + "_lcd_LCD.print((int)(millis()-__time));"
                + "delay(500);"
                + "}\n";

        this.h.assertCodeIsOk(a, "/syntax/code_generator/arduino/timer.xml", true);
    }

    @Test
    public void visitLightSensorTest() throws Exception {

        final String a =
            "" //
                + INCLUDE
                + "doubleitem;"
                + DEFINES
                + VOID_SETUP
                + "item=0;"
                + "}voidloop(){"
                + "item=analogRead(_output_L2)/10.24;"
                + "_lcd_L.setCursor(0,0);"
                + "_lcd_L.print(\"Licht:\");"
                + "_lcd_L.setCursor(0,1);"
                + "_lcd_L.print(item);if(item>=10){_lcd_L.setCursor(7,1);"
                + "_lcd_L.print(\"%\");}"
                + "else{"
                + "_lcd_L.setCursor(6,1);"
                + "_lcd_L.print(\"%\");}"
                + "delay(500);}";

        this.h.assertCodeIsOk(a, "/syntax/code_generator/arduino/light_sensor.xml", true);
    }

    @Test
    public void visitMoistureSensorTest() throws Exception {

        final String a =
            "" //
                + INCLUDE
                + DEFINES
                + VOID_SETUP
                + "}voidloop(){"
                + "_lcd_L.setCursor(0,1);"
                + "_lcd_L.print(analogRead(_moisturePin_F)/10.24);"
                + "delay(500);}";

        this.h.assertCodeIsOk(a, "/syntax/code_generator/arduino/moisture_sensor.xml", true);
    }

    @Test
    public void visitVoltageSensorTest() throws Exception {

        final String a =
            "" //
                + INCLUDE
                + DEFINES
                + VOID_SETUP
                + "}voidloop(){"
                + "_lcd_L.setCursor(0,1);"
                + "_lcd_L.print(((double)analogRead(_output_P))*5/1024);"
                + "delay(500);}";

        this.h.assertCodeIsOk(a, "/syntax/code_generator/arduino/potentiometer.xml", true);
    }

    @Test
    public void visitInfraredSensorTest() throws Exception {

        final String a =
            "" //
                + INCLUDE
                + DEFINES
                + "longint_getIRResults(){"
                + "longintresults=0;"
                + "if(_irrecv_I.decode(&_results_I)){"
                + "results=_results_I.value;"
                + "_irrecv_I.resume();}"
                + "returnresults;}"
                + VOID_SETUP
                + "}voidloop(){"
                + "_lcd_L.setCursor(0,1);"
                + "_lcd_L.print(_getIRResults());"
                + "delay(2000);"
                + "_lcd_L.clear();"
                + "_lcd_L.setCursor(0,1);"
                + "_lcd_L.print(_getIRResults());"
                + "delay(2000);"
                + "_lcd_L.clear();}";

        this.h.assertCodeIsOk(a, "/syntax/code_generator/arduino/ir_sensor.xml", true);
    }

    @Test
    public void visitRelayActionTest() throws Exception {

        final String a =
            "" //
                + INCLUDE
                + DEFINES
                + VOID_SETUP
                + "}voidloop(){"
                + "digitalWrite(_relay_R,LOW);"
                + "delay(1000);"
                + "digitalWrite(_relay_R,HIGH);"
                + "delay(1000);}";

        this.h.assertCodeIsOk(a, "/syntax/code_generator/arduino/relay.xml", true);
    }

    @Test
    public void visitTemperatureSensorTest() throws Exception {

        final String a =
            "" //
                + INCLUDE
                + DEFINES
                + VOID_SETUP
                + "}voidloop(){"
                + "_lcd_L.setCursor(0,1);"
                + "_lcd_L.print(map(analogRead(_TMP36_T),0,410,-50,150));"
                + "delay(1000);}";

        this.h.assertCodeIsOk(a, "/syntax/code_generator/arduino/temperature_sensor.xml", true);
    }

    @Test
    public void visitHumiditySensorTest() throws Exception {

        final String a =
            "" //
                + INCLUDE
                + DEFINES
                + VOID_SETUP
                + "}voidloop(){"
                + "_lcd_L.setCursor(0,0);"
                + "_lcd_L.print(_dht_L2.readHumidity());"
                + "_lcd_L.setCursor(0,1);"
                + "_lcd_L.print(_dht_L2.readTemperature());"
                + "delay(1000);}";

        this.h.assertCodeIsOk(a, "/syntax/code_generator/arduino/humidity_sensor.xml", true);
    }

    @Test
    public void visitMotionSensorTest() throws Exception {

        final String a =
            "" //
                + INCLUDE
                + DEFINES
                + VOID_SETUP
                + "}voidloop(){"
                + "_lcd_L.setCursor(0,0);"
                + "_lcd_L.print(digitalRead(_output_B));"
                + "delay(1000);}";

        this.h.assertCodeIsOk(a, "/syntax/code_generator/arduino/motion_sensor.xml", true);
    }

    @Test
    public void visitPulseSensorTest() throws Exception {

        final String a =
            "" //
                + INCLUDE
                + DEFINES
                + VOID_SETUP
                + "}voidloop(){"
                + "_lcd_L.setCursor(0,0);"
                + "_lcd_L.print(analogRead(_SensorPin_P));"
                + "delay(1000);}";

        this.h.assertCodeIsOk(a, "/syntax/code_generator/arduino/pulse_sensor.xml", true);
    }

    @Test
    public void visitDropSensorTest() throws Exception {

        final String a =
            "" //
                + INCLUDE
                + DEFINES
                + VOID_SETUP
                + "}voidloop(){"
                + "_lcd_L.setCursor(0,0);"
                + "_lcd_L.print(analogRead(_S_T)/10.24);"
                + "delay(1000);"
                + "_lcd_L.clear();}";

        this.h.assertCodeIsOk(a, "/syntax/code_generator/arduino/drop_sensor.xml", true);
    }

    @Test
    public void visitRfidSensorTest() throws Exception {

        final String a =
            "" //
                + INCLUDE
                + DEFINES
                + VOID_SETUP
                + "}voidloop(){"
                + "_lcd_L.setCursor(0,1);"
                + "_lcd_L.print(_readRFIDData());"
                + "delay(500);}";

        this.h.assertCodeIsOk(a, "/syntax/code_generator/arduino/rfid_sensor.xml", true);
    }

    @Test
    public void visitBrickSensorTest() throws Exception {

        final String a =
            "" //
                + INCLUDE
                + DEFINES
                + VOID_SETUP
                + "}voidloop(){"
                + "_lcd_L.setCursor(0,1);"
                + "_lcd_L.print(digitalRead(_taster_T));"
                + "delay(10);}";

        this.h.assertCodeIsOk(a, "/syntax/code_generator/arduino/button.xml", true);
    }
}
