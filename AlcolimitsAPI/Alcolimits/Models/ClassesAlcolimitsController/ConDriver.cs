using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Alcolimits.Models.ClassesAlcolimitsController
{
    public class ConDriver {

        public int id { get; set; }
        public string firstName { get; set; }
        public string middleName { get; set; }
        public string lastName { get; set; }
        public string profilePhoto { get; set; }
        public string licensePhoto { get; set; }
        public ConVehicle vehicle { get; set; }

    }
}