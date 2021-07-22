using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Text;

namespace Alcolimits.Controllers
{
 
    public class Customer
    {
        public string CustomerID { get; set; }
        public string ContactName { get; set; }
        public string Address { get; set; }
        public List<Order> Orders { get; set; }
    }
}
