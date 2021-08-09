#include <PubSubClient.h>

// NodeMCU Based WIFI Controlled Car//

#define ENA   14          // Enable/speed motors Right        GPIO14(D5)
#define ENB   12          // Enable/speed motors Left         GPIO12(D6)
#define IN_1  15          // L298N in1 motors Right           GPIO15(D8)
#define IN_2  13          // L298N in2 motors Right           GPIO13(D7)
#define IN_3  2           // L298N in3 motors Left            GPIO2(D4)
#define IN_4  0           // L298N in4 motors Left            GPIO0(D3)
#define Relay 16
#include <ArduinoJson.h>
#include <ESP8266HTTPClient.h>
#include <ESP8266WiFi.h>
#include <WiFiClient.h> 
#include <ESP8266WebServer.h>
int status = WL_IDLE_STATUS; 
String command;             //String to store app command state.
int speedCar = 800;         // 400 - 1023.
int speed_Coeff = 3;
const char* ssid = "INFINITUM2543_2.4";
const char* password = "Jaih130498@";
const char* mqtt_server = "broker.hivemq.com";
const char* serverName = "http://alcolimitstest.azurewebsites.net/api/VehicleStatus/";
const char* topic = "UTT/Alcolimits/Car";
const char* topic2 = "UTT/Alcolimits/Test";
WiFiClient espClient;
PubSubClient client(espClient);

const char* APssid = "NodeMCU Car";
ESP8266WebServer server(80);

void reconnect() {
  // Loop until we're reconnected
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    // Attempt to connect
    if(client.connect("Car")) {
      client.subscribe(topic);
      Serial.println("connected");
      Serial.print("Subcribed to: ");
      Serial.println(topic);
      Serial.println('\n');

    } else {
      Serial.println(" try again in 5 seconds");
      // Wait 5 seconds before retrying
      delay(5000);
    }
  }
}

  void callback(char* topic, byte* payload, unsigned int length) {
  String response;
  Serial.begin(9600);
  for (int i = 0; i < length; i++) {
    response += (char)payload[i];
  }
  Serial.print("Message arrived [");
  Serial.print(topic);
  Serial.print("] ");
  Serial.println(response);
  if(response == "Drunk")  // Turn the light on
  {
   digitalWrite(Relay, HIGH);
   client.publish(topic2, "High Alcohol Detected");
   
   HTTPClient http;
   http.begin(serverName);
   http.addHeader("Content-Type", "application/json");
   
   const size_t CAPACITY = JSON_OBJECT_SIZE(3);
   StaticJsonDocument<CAPACITY> doc;

   JsonObject obj = doc.to<JsonObject>();

   obj["id"] = 1010;
   obj["isOn"]= false;
   obj["isDriving"] = false;
   char jsonOutput[100];
   serializeJson(doc,jsonOutput);
   Serial.println(jsonOutput);

   int httpCode = http.PUT(String(jsonOutput));

   if (httpCode >0){
    String payload = http.getString();
    Serial.println("\nStatus code: " + String(httpCode));
    Serial.println(payload);
   }

      // Free resources
      http.end();
    
    
  }else {
    digitalWrite(Relay,  LOW);
    client.publish(topic2, "No Alcohol Detected");
    
  }
  
}

void Httput () {
   Serial.begin(9600);
   HTTPClient http;
   http.begin(serverName);
   http.addHeader("Content-Type", "application/json");
   
   const size_t CAPACITY = JSON_OBJECT_SIZE(3);
   StaticJsonDocument<CAPACITY> doc;

   JsonObject obj = doc.to<JsonObject>();

   obj["id"] = 1010;
   obj["isOn"]= true;
   obj["isDriving"] = true;
   char jsonOutput[100];
   serializeJson(doc,jsonOutput);
   Serial.println(jsonOutput);

   int httpCode = http.PUT(String(jsonOutput));

   if (httpCode >0){
    String payload = http.getString();
    Serial.println("\nStatus code: " + String(httpCode));
    Serial.println(payload);
   }

      // Free resources
      http.end();
}
void setup() {
 
 pinMode(ENA, OUTPUT);
 pinMode(ENB, OUTPUT);  
 pinMode(IN_1, OUTPUT);
 pinMode(IN_2, OUTPUT);
 pinMode(IN_3, OUTPUT);
 pinMode(IN_4, OUTPUT); 
 pinMode(Relay, OUTPUT);
  
  Serial.begin(115200);
 
// Connecting WiFi
  
    Serial.println();
  Serial.print("Configuring access point...");
  
  WiFi.mode(WIFI_AP_STA);
  WiFi.softAP(APssid);
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
   }
  
  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println(WiFi.localIP());
 
 // Starting WEB-server 
     server.on ( "/", HTTP_handleRoot );
     server.onNotFound ( HTTP_handleRoot );
     server.begin();    
// MQTT
  client.setServer(mqtt_server, 1883);
  client.setCallback(callback);
  client.subscribe(topic);
  Httput();
}


void goAhead(){ 

      digitalWrite(IN_1, LOW);
      digitalWrite(IN_2, HIGH);
      analogWrite(ENA, speedCar);

      digitalWrite(IN_3, LOW);
      digitalWrite(IN_4, HIGH);
      analogWrite(ENB, speedCar);
  }

void goBack(){ 

      digitalWrite(IN_1, HIGH);
      digitalWrite(IN_2, LOW);
      analogWrite(ENA, speedCar);

      digitalWrite(IN_3, HIGH);
      digitalWrite(IN_4, LOW);
      analogWrite(ENB, speedCar);
  }

void goRight(){ 

      digitalWrite(IN_1, HIGH);
      digitalWrite(IN_2, LOW);
      analogWrite(ENA, speedCar);

      digitalWrite(IN_3, LOW);
      digitalWrite(IN_4, HIGH);
      analogWrite(ENB, speedCar);
  }

void goLeft(){

      digitalWrite(IN_1, LOW);
      digitalWrite(IN_2, HIGH);
      analogWrite(ENA, speedCar);

      digitalWrite(IN_3, HIGH);
      digitalWrite(IN_4, LOW);
      analogWrite(ENB, speedCar);
  }

void goAheadRight(){
      
      digitalWrite(IN_1, HIGH);
      digitalWrite(IN_2, LOW);
      analogWrite(ENA, speedCar/speed_Coeff);
 
      digitalWrite(IN_3, LOW);
      digitalWrite(IN_4, HIGH);
      analogWrite(ENB, speedCar);
   }

void goAheadLeft(){
      
      digitalWrite(IN_1, LOW);
      digitalWrite(IN_2, HIGH);
      analogWrite(ENA, speedCar);

      digitalWrite(IN_3, HIGH);
      digitalWrite(IN_4, LOW);
      analogWrite(ENB, speedCar/speed_Coeff);
  }

void goBackRight(){ 

      digitalWrite(IN_1, LOW);
      digitalWrite(IN_2, HIGH);
      analogWrite(ENA, speedCar/speed_Coeff);

      digitalWrite(IN_3, HIGH);
      digitalWrite(IN_4, LOW);
      analogWrite(ENB, speedCar);
  }

void goBackLeft(){ 

      digitalWrite(IN_1, HIGH);
      digitalWrite(IN_2, LOW);
      analogWrite(ENA, speedCar);

      digitalWrite(IN_3, LOW);
      digitalWrite(IN_4, HIGH);
      analogWrite(ENB, speedCar/speed_Coeff);
  }

void stopRobot(){  

      digitalWrite(IN_1, LOW);
      digitalWrite(IN_2, LOW);
      analogWrite(ENA, speedCar);

      digitalWrite(IN_3, LOW);
      digitalWrite(IN_4, LOW);
      analogWrite(ENB, speedCar);
 }

void loop() {
    server.handleClient();
    
      command = server.arg("State");
      if (command == "F") goAhead();
      else if (command == "B") goBack();
      else if (command == "L") goLeft();
      else if (command == "R") goRight();
      else if (command == "I") goAheadRight();
      else if (command == "G") goAheadLeft();
      else if (command == "J") goBackRight();
      else if (command == "H") goBackLeft();
      else if (command == "0") speedCar = 400;
      else if (command == "1") speedCar = 470;
      else if (command == "2") speedCar = 540;
      else if (command == "3") speedCar = 610;
      else if (command == "4") speedCar = 680;
      else if (command == "5") speedCar = 750;
      else if (command == "6") speedCar = 820;
      else if (command == "7") speedCar = 890;
      else if (command == "8") speedCar = 960;
      else if (command == "9") speedCar = 1023;
      else if (command == "S") stopRobot();

       if (!client.connected())  // Reconnect if connection is lost
  {
    reconnect();
  }
  client.loop();
}

void HTTP_handleRoot(void) {

if( server.hasArg("State") ){
       Serial.println(server.arg("State"));
  }
  server.send ( 200, "text/html", "" );
  delay(1);
}
