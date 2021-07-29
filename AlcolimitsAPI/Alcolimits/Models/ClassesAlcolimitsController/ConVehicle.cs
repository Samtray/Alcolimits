
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Alcolimits.Models.ClassesAlcolimitsController
{
    public class ConVehicle
    {

        public int id { get; set; }
        public string plate { get; set; }
        public string model { get; set; }
        public string year { get; set; }
        public string color { get; set; }
        public string photo { get; set; }
        //public int status_inf { get; set; }
        //public int alcohol_inf { get; set; }
        //public int temperature_inf { get; set; }
        //public string logs_inf { get; set; }
        //public List<AlcoholSensor> alcoholSensor { get; set; }
        public ConLocation location { get; set; }
        public ConAlcoholSensor alcoholSensor { get; set; }
        public ConTemperatureSensor temperatureSensor { get; set; }
        public ConVehicleStatus vehicleStatus { get; set; }
        public List<ConLog> logs { get; set; }

    }
}