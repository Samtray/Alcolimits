using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Alcolimits.Models.ClassesAlcolimitsController
{
    public class ConSensorRange
    {
        public string id { get; set; }
        public string name { get; set; }
        public int minimum { get; set; }
        public int maximum { get; set; }
        public string color { get; set; }
        public string icon { get; set; }
    }
}