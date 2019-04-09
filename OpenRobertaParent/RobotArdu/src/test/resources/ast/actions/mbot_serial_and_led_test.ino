// This file is automatically generated by the Open Roberta Lab.

#define ANALOG2PERCENT 0.0978

#include <math.h>
#include <MeMCore.h>
#include <Wire.h>
#include <SoftwareSerial.h>
#include <MeDrive.h>
#include <NEPODefs.h>
#include <RobertaFunctions.h>

MeRGBLed _meRgbLed(7, 2);
RobertaFunctions rob;

unsigned int item;

void setup()
{
    Serial.begin(9600); 
    item = RGB(0xFF, 0xFF, 0xFF);
}

void loop()
{
    Serial.println("Hallo");
    _meRgbLed.setColor(2, 0xcc, 0x00, 0x00);
    _meRgbLed.show();
    _meRgbLed.setColor(1, 0xcc, 0x00, 0x00);
    _meRgbLed.show();
    _meRgbLed.setColor(2, RCHANNEL(item), GCHANNEL(item), BCHANNEL(item));
    _meRgbLed.show();
    _meRgbLed.setColor(1, RCHANNEL(item), GCHANNEL(item), BCHANNEL(item));
    _meRgbLed.show();
    _meRgbLed.setColor(2, 20, 150, 255);
    _meRgbLed.show();
    _meRgbLed.setColor(1, 20, 150, 255);
    _meRgbLed.show();
    _meRgbLed.setColor(2, 0, 0, 0);
    _meRgbLed.show();
    _meRgbLed.setColor(1, 0, 0, 0);
    _meRgbLed.show();
}