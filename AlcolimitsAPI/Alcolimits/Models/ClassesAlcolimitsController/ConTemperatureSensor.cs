using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Alcolimits.Models.ClassesAlcolimitsController
{
    public class ConTemperatureSensor
    {
        public int id { get; set; }
        public int val { get; set; }
        public List<ConSensorRange> ranges { get; set; }
        public ConSensorRange current { get; set; }
    }
}