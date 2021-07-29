using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Alcolimits.Models.ClassesAlcolimitsController
{
    public class ConLocation
    {

        public int id { get; set; }
        public string address { get; set; }
        public double latitude { get; set; }
        public double longitude { get; set; }
        public string icon { get; set; }
    }
}