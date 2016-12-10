#include <DHT.h>

int inputPin = 11;
double input;

DHT dht11(inputPin, DHT11);

void setup() {
  // put your setup code here, to run once:
  //pinMode(inputPin, INPUT);
  Serial.begin(9600);
  dht11.begin();
}

void loop() {
  // put your main code here, to run repeatedly:
  //input chk = DHT11.read(inputPin);
  Serial.print("Humidity: ");
  Serial.println((float)dht11.readHumidity());
  Serial.print("Temperature in Celcius:");
  Serial.println((float)dht11.readTemperature());
  delay(500);
}
