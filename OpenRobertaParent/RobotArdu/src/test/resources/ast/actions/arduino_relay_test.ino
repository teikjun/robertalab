// This file is automatically generated by the Open Roberta Lab.

#include <math.h>
#include <RobertaFunctions.h>   // Open Roberta library
#include <NEPODefs.h>

RobertaFunctions rob;

int _relay_R = 6;
void setup()
{
    Serial.begin(9600); 
    pinMode(_relay_R, OUTPUT);
}

void loop()
{
    digitalWrite(_relay_R, LOW);
}