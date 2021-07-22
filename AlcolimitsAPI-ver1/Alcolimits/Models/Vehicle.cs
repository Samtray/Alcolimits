
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Alcolimits.Models
{
    public class Vehicle
    {

        public int id_vehicle { get; set; }
        public string plate { get; set; }
        public string model { get; set; }
        public string vyear { get; set; }
        public string gps { get; set; }
        public string color { get; set; }
        //public int status_inf { get; set; }
        //public int alcohol_inf { get; set; }
        //public int temperature_inf { get; set; }
        //public string logs_inf { get; set; }
        public List<AlcoholSensor> alcoholSensor { get; set; }
        public List<TemperatureSensor> temperatureSensor { get; set; }
        public List<VehicleStatus> vehicleStatus { get; set; }
        public List<Log> logs { get; set; }

    }
}