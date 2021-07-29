
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Alcolimits.Models
{
    public class Vehicle
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
        public string location { get; set; }
        public string alcoholSensor { get; set; }
        public string temperatureSensor { get; set; }
        public string vehicleStatus { get; set; }
        //public ConLog logs { get; set; }

    }
}