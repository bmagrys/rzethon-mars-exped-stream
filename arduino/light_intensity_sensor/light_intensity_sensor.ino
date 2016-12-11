
int inputPin = 0;
int sensorValue;

void setup() {
  // put your setup code here, to run once:
  //pinMode(inputPin, INPUT);
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
  sensorValue = analogRead(inputPin);
  Serial.println(sensorValue);
  delay(500);
}
