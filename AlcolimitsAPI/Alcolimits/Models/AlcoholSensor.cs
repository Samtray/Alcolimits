using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Alcolimits.Models
{
    public class AlcoholSensor
    {
        public int id {get; set;}
        public int val { get; set; }
        public string status { get; set; } 
    }
}