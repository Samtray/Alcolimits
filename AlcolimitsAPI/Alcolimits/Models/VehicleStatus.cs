using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Alcolimits.Models
{
    public class VehicleStatus
    {
        public int id { get; set; }
        public bool isOn { get; set; }
        public bool isDriving { get; set; }
        public string status { get; set; }
    }
}