using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Alcolimits.Models
{
    public class Location
    {

        public int id { get; set; }
        public string address { get; set; }
        public double latitude { get; set; }
        public double longitude { get; set; }

    }
}