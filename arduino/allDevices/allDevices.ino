#include <DHT.h>
#include <SoftwareSerial.h>

SoftwareSerial bluetoothSerial(0, 1);

int wibrSensInputPin = 12;
long wibrSensInput = 0;

int dhtInputPin = 11;
float dhtTemperature = 0;
float dhtHumidity = 0;
DHT dht11(dhtInputPin, DHT11);

int lightIntensityAnalogInputPin = 0;
int lightIntensitySensorValue = 0;

int gasConAnlInputPin = 1;
int gasConAnlVal = 0;

unsigned long time;

void setup() {
  pinMode(wibrSensInputPin, INPUT);
  bluetoothSerial.begin(9600);
  bluetoothSerial.flush();
  dht11.begin();
  time = millis();
}

void loop() {
  if(millis() - time < 1000) {
    wibrSensInput = (wibrSensInput + pulseIn(wibrSensInputPin, HIGH))/2;
    dhtTemperature = (dhtTemperature + (float)dht11.readTemperature())/2;
    dhtHumidity = (dhtHumidity + (float)dht11.readHumidity())/2;
    lightIntensitySensorValue = (lightIntensitySensorValue + analogRead(lightIntensityAnalogInputPin))/2;
    gasConAnlVal = (gasConAnlVal + analogRead(gasConAnlInputPin))/2;
  } else {
    //String tmp = "ASDASF";
    String tmp = "{" + String(0) + "," + String(0) + "," + String(dhtTemperature) + "," + String(dhtHumidity) + "," + String(lightIntensitySensorValue) + "," + String(wibrSensInput) + "," + String(gasConAnlVal) + "}"; 
    for(int i = 0; i < tmp.length(); i++) {
      bluetoothSerial.write(tmp[i]);
    }
    bluetoothSerial.flush();
    time = millis();
  }
}
