int wartoscAnalog = 0;
byte wartoscZmapowana = 0;        
char info[96];  
void setup() {
   Serial.begin(9600); 
} 

void loop() {
  wartoscAnalog = analogRead(1);
  wartoscZmapowana = map(wartoscAnalog, 0, 1023, 0, 100);
  sprintf(info, "MQ-2: %d (%d)", wartoscAnalog, wartoscZmapowana);
  if (wartoscZmapowana > 20) {
     Serial.print(info);
     Serial.println(F(" UWAGA! WYKRYTO GAZ LUB DYM "));
  }
  else {
     Serial.println(info);
  }
  delay(1000);
}
