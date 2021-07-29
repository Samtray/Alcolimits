using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Alcolimits.Models
{
    public class Log
    { 
        public int id { get; set; }
        public string dateTime { get; set; }
        public string content { get; set; }
        public string vehiclePlate { get; set; }

    }
}