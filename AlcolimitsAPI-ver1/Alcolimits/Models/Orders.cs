using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Alcolimits.Controllers
{
    
    public class Order
    {
        public int OrderID { get; set; }
        public string ShipCity { get; set; }
        public decimal Freight { get; set; }
        public string OrderDate { get; set; }
    }
}
