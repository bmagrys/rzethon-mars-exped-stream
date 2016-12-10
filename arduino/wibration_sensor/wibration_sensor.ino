long input;
int inputPin = 12;

void setup() {
  // put your setup code here, to run once:
  pinMode(inputPin, INPUT);
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
  input = pulseIn(inputPin, HIGH);
  Serial.println(input);
}
