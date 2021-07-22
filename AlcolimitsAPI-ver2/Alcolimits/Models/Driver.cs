using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Alcolimits.Models
{
    public class Driver {

        public int id_driver { get; set; }
        public string first_name { get; set; }
        public string middle_name { get; set; }
        public string last_name { get; set; }
        public string photo { get; set; }
        public string VehiclePlate { get; set; }
        public Vehicle vehicle { get; set; }

    }
}