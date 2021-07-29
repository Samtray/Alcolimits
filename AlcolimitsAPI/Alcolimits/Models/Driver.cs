using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Alcolimits.Models
{
    public class Driver {

        public int id { get; set; }
        public string firstName { get; set; }
        public string middleName { get; set; }
        public string lastName { get; set; }
        public string profilePhoto { get; set; }
        public string licensePhoto { get; set; }
        public string vehiclePlate { get; set; }

    }
}