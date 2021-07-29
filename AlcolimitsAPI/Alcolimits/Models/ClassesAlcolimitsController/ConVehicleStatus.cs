using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Alcolimits.Models.ClassesAlcolimitsController
{
    public class ConVehicleStatus
    {
        public int id { get; set; }
        public bool isOn { get; set; }
        public bool isDriving { get; set; }
        public List<ConVehicleStatusRange> ranges { get; set; }
        public ConVehicleStatusRange current { get; set; }
    }
}