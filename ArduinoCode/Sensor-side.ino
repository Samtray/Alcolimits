
#include <ESP8266HTTPClient.h>
#define Sober 400 // Define max value that we consider sober
#define Drunk 900   // Define min value that we consider drunk
#include <ESP8266WiFi.h>
#include <PubSubClient.h>
#include <ArduinoJson.h>
#include <Adafruit_Sensor.h>
#include <DHT.h>
#include <DHT_U.h>
#define MQ3pin A0
#define DHTPIN D1
#define DHTTYPE DHT11
DHT dht(DHTPIN, DHTTYPE);
int sensorValue; 
const char* ssid = "INFINITUM2543_2.4";
const char* password = "Jaih130498@";
const char* mqtt_server = "broker.hivemq.com";
const char* serverName = "http://alcolimitstest.azurewebsites.net/api/Alcoholsensor/";
const char* apiLogs= "http://alcolimitstest.azurewebsites.net/api/Log/";
const char* apiTemps= "http://alcolimitstest.azurewebsites.net/api/TemperatureSensor/";
const char* topicCar = "UTT/Alcolimits/Car";
const char* topic = "UTT/Alcolimits/Test";
HTTPClient http;
WiFiClient espClient;
PubSubClient client(espClient);

void reconnect() {
  
  // Loop until we're reconnected
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    // Attempt to connect
    if(client.connect("Sensor")) {
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

  for (int i = 0; i < length; i++) {
    response += (char)payload[i];
  }
  Serial.print("Message arrived [");
  Serial.print(topic);
  Serial.print("] ");
  Serial.println(response);
  if(response == "Test")  // Turn the light on
  {
    sensorValue= analogRead(MQ3pin);
    if( sensorValue<Sober){\
      Serial.print("Your alcohol level is:");
      Serial.println(sensorValue);
      Serial.print("Status published to: UTT/Alcolimits/Car");
      Serial.println("Status: SOBER");
      client.publish(topicCar,"Sober");
   HTTPClient http;
   http.begin(serverName);
   http.addHeader("Content-Type", "application/json");
   
   const size_t CAPACITY = JSON_OBJECT_SIZE(2);
   StaticJsonDocument<CAPACITY> doc;

   JsonObject alc = doc.to<JsonObject>();

   alc["id"] = 1010;
   alc["val"]= sensorValue;
   char alcjson[100];
   serializeJson(doc,alcjson);
   Serial.println(alcjson);

   int httpCode = http.PUT(String(alcjson));

   if (httpCode >0){
    String payload = http.getString();
    Serial.println("\nStatus code: " + String(httpCode));
    Serial.println(payload);
   }

      // Free resources
      http.end();

 
   http.begin(apiLogs);
   http.addHeader("Content-Type", "application/json");

   JsonObject logs = doc.to<JsonObject>();
   
   logs["content"] = "Alcohol Test Passed";
   logs["vehiclePlate"]= "2RYBX43";
   char logjson[100];
   serializeJson(doc,logjson);
   Serial.println(logjson);

   int httpCode2 = http.POST(String(logjson));

   if (httpCode2 >0){
    String payload = http.getString();
    Serial.println("\nStatus code: " + String(httpCode2));
    Serial.println(payload);
   }

      // Free resources
      http.end();

      

      
    }else{
      Serial.print("Your alcohol level is:");
      Serial.print("Status published to: UTT/Alcolimits/Car");
      Serial.println("Status: Drunk");
      client.publish(topicCar,"Drunk");

   http.begin(serverName);
   http.addHeader("Content-Type", "application/json");
   
   const size_t CAPACITY = JSON_OBJECT_SIZE(2);
   StaticJsonDocument<CAPACITY> doc;

   JsonObject alc = doc.to<JsonObject>();

   alc["id"] = 1010;
   alc["val"]= sensorValue;
   char alcjson[100];
   serializeJson(doc,alcjson);
   Serial.println(alcjson);

   int httpCode = http.PUT(String(alcjson));

   if (httpCode >0){
    String payload = http.getString();
    Serial.println("\nStatus code: " + String(httpCode));
    Serial.println(payload);
   }

      // Free resources
      http.end();

 
   http.begin(apiLogs);
   http.addHeader("Content-Type", "application/json");

    
   JsonObject logs = doc.to<JsonObject>();
   
   logs["content"] = "Alcohol Test Failed";
   logs["vehiclePlate"]= "2RYBX43";
   char logjson[100];
   serializeJson(doc,logjson);
   Serial.println(logjson);

   int httpCode2 = http.POST(String(logjson));

   if (httpCode2 >0){
    String payload = http.getString();
    Serial.println("\nStatus code: " + String(httpCode2));
    Serial.println(payload);
   }

      // Free resources
      http.end();
    }
    
    
  }
  
}

void setup(){
Serial.begin(9600);
WiFi.mode(WIFI_STA);
WiFi.begin(ssid, password);
while (WiFi.status() != WL_CONNECTED) {
delay(500);
Serial.print(".");
}
WiFi.setAutoReconnect(true);
Serial.println("WiFi connected.");
Serial.println();
WiFi.printDiag(Serial);
Serial.println();
 Serial.print("IP: ");
 Serial.println(WiFi.localIP());
 client.setServer(mqtt_server, 1883);
 client.setCallback(callback);// Initialize the callback routine
 client.subscribe(topic);
 dht.begin();
}
void loop(){

   delay(5000);

   int temp = dht.readTemperature(true);
   Serial.println(temp);
   http.begin(apiTemps);
   http.addHeader("Content-Type", "application/json");
   const size_t CAPACITY = JSON_OBJECT_SIZE(2);
   StaticJsonDocument<CAPACITY> doc;

   JsonObject temps = doc.to<JsonObject>();
   
   temps["id"] = 1010;
   temps["val"]= temp;
   char tempsjson[100];
   serializeJson(doc,tempsjson);
   Serial.println(tempsjson);

   int httpCode2 = http.PUT(String(tempsjson));

   if (httpCode2 >0){
    String payload = http.getString();
    Serial.println("\nStatus code: " + String(httpCode2));
    Serial.println(payload);
   }

      // Free resources
      http.end();





  
  if (!client.connected())  // Reconnect if connection is lost
  {
    reconnect();
  }
  client.loop();
}
