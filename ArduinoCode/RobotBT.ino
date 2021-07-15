//--------Declaramos un nombre a los pines del Arduino--------------------------------------------
#define izqA 5             
#define izqB 6              
#define derA 9              
#define derB 10                                  
#define buzzer 3             
#define leds 4
//------------------------------------------------------------------------------------------------


//--------Declaracion de variables----------------------------------------------------------------
int vel;
char estado = "";  
String velocidad = "";
//------------------------------------------------------------------------------------------------



void setup()  {
  Serial.begin(9600); //Establecemos la velocidad para la comunicacion en serie a 9600 baudios 
//--------Declaramos los Pines correspondientes como Salidas--------------------------------------     
  pinMode(derA, OUTPUT);
  pinMode(derB, OUTPUT);
  pinMode(izqA, OUTPUT);
  pinMode(izqB, OUTPUT);
  pinMode(buzzer, OUTPUT);
  pinMode(leds, OUTPUT);
//------------------------------------------------------------------------------------------------

              
} 
 
void loop()  { 
//--------Si recibimos algun dato al Modulo Bluetooth lo guardamos en la variable "estado"--------
  if(Serial.available()>0){ 
    estado = Serial.read();
    delay(2);
  }
//------------------------------------------------------------------------------------------------
  if(estado == 'A'){  //Si se recibe el carcarter 'A' enseguida esperamos un comando hacia el carrito
    comandos();
  }else if(estado >= '0' && estado <= '9'){ //Si recibimos un numero entre '1' y '9' esperamos que nos lleguen mas numeros para establecer la velocidad de los motores
    velocidad += estado; //le ponemos el valor del primer dato recibido a la variable velocidad
    while(Serial.available()>0){  //Mientras tengamos datos en la comunicacion serie, guardaremos esto en la String velocidad
      char c = Serial.read();
      velocidad += c;
      delay(2);  //Establecemos un retardo de 2ms para evitar que no nos llegue un dato
    }

    if(velocidad.length()>0){
      vel = velocidad.toInt();  //le establecemos el dato recibido a la variable vel, con esto hemos establecido la velocidad a los motores
      velocidad = "";
    }
    estado = "";
  }
}

void comandos(){
  if(Serial.available()>0){ 
    estado = Serial.read();
  }
  //--------Segun el valor de la valiable "estado" realizamos una accion--------------------------
  switch(estado){
    //--------Generamos un pitido en el Buzzer----------------------------------------------------
    case 'p':                
      digitalWrite(buzzer,HIGH);
      break;
    //--------------------------------------------------------------------------------------------
    //--------Apagamos el pitido del Buzzer-------------------------------------------------------
    case 'z':                 
      digitalWrite(buzzer,LOW);
      break;
    //--------------------------------------------------------------------------------------------
    //--------Encendemos los led's----------------------------------------------------------------
    case 'q':                 
      digitalWrite(leds,HIGH);
      break;
    //--------------------------------------------------------------------------------------------
    //--------Apagamos los led's------------------------------------------------------------------
    case 'w':                 
      digitalWrite(leds,LOW);
      break;
    //--------------------------------------------------------------------------------------------
    //--------Avanzamos---------------------------------------------------------------------------
    case 'a':                 
      avanzarder();
      avanzarizq();
      break;
    //--------Giramos a la izquierda--------------------------------------------------------------
    case 'b':                
      avanzarder();
      pararizq();
      break;
    //--------------------------------------------------------------------------------------------
    //--------Lo Detenemos------------------------------------------------------------------------
    case 'c':                 
      pararder();
      pararizq();
      break;
    //--------------------------------------------------------------------------------------------
    //--------Giramos a la derecha----------------------------------------------------------------
    case 'd':                
      pararder();
      avanzarizq();
      break;
    //--------------------------------------------------------------------------------------------
    //--------Retrocedemos------------------------------------------------------------------------
    case 'e':                
      retrocederder();
      retrocederizq();
      break;
    //--------------------------------------------------------------------------------------------
    //--------Si el valor de "estado" es distinto a los anteriores detenemos el carro-------------
    default:                  
      pararder();
      pararizq();
      break;
    //--------------------------------------------------------------------------------------------
  }
  estado = "";
  //----------------------------------------------------------------------------------------------
}

//--------Avanzamos con el motor derecho----------------------------------------------------------
void avanzarder(){
  analogWrite(derA, vel);
  analogWrite(derB, 0);
}
//------------------------------------------------------------------------------------------------

//--------Avanzamos con el motor izquierdo--------------------------------------------------------
void avanzarizq(){
  analogWrite(izqA, vel);
  analogWrite(izqB, 0);
}
//------------------------------------------------------------------------------------------------

//--------Retrocedemos con el motor derecho-------------------------------------------------------
void retrocederder(){
  analogWrite(derA, 0);
  analogWrite(derB, vel);  
}
//------------------------------------------------------------------------------------------------

//--------Retrocedemos con el motor izquierdo-----------------------------------------------------
void retrocederizq(){
  analogWrite(izqA, 0);
  analogWrite(izqB, vel);
}
//------------------------------------------------------------------------------------------------

//--------Detenemos el motor derecho--------------------------------------------------------------
void pararder(){
  analogWrite(derA, 0);
  analogWrite(derB, 0);
}
//------------------------------------------------------------------------------------------------

//--------Detenemos el motor izquierdo------------------------------------------------------------
void pararizq(){
  analogWrite(izqA, 0);
  analogWrite(izqB, 0);
}
//------------------------------------------------------------------------------------------------
