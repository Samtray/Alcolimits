using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Alcolimits.Models
{
    public class Log
    { 
        public int id_log { get; set; }
        public string date_time { get; set; }
        public string content { get; set; }
        public string VehiclePlate { get; set; }

    }
}