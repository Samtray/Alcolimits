using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Web.Script.Serialization;

namespace Alcolimits.Controllers
{
    public class NorthController : ApiController
    {
        public HttpResponseMessage Get()
        {
            List<Customer> customers = new List<Customer>();

            DataTable dt = GetData("SELECT Top 3 CustomerID,ContactName,Address From Customers");
            for (int i = 0; i < dt.Rows.Count; i++)
            {
                Customer customer = new Customer
                {
                    CustomerID = Convert.ToString(dt.Rows[i]["CustomerId"])
                    ,
                    ContactName = Convert.ToString(dt.Rows[i]["ContactName"])
                    ,
                    Address = Convert.ToString(dt.Rows[i]["Address"])
                    ,
                    Orders = GetOrders(Convert.ToString(dt.Rows[i]["CustomerId"]))

                };
                customers.Add(customer);
            }
            var json = new JavaScriptSerializer().Serialize(customers);
            return Request.CreateResponse(HttpStatusCode.OK, customers);
        }

        public List<Order> GetOrders(string customerId)
        {
            List<Order> orders = new List<Order>();
            DataTable dt = GetData(string.Format("SELECT Top 2 OrderID,CustomerID,ShipCity,Freight,OrderDate FROM Orders Where CustomerID ='{0}'", customerId));
            for (int i = 0; i < dt.Rows.Count; i++)
            {
                orders.Add(new Order
                {
                    OrderID = Convert.ToInt32(dt.Rows[i]["OrderID"])
                    ,
                    ShipCity = Convert.ToString(dt.Rows[i]["ShipCity"])
                    ,
                    Freight = Convert.ToDecimal(dt.Rows[i]["Freight"])
                    ,
                    OrderDate = Convert.ToDateTime(dt.Rows[i]["OrderDate"]).ToShortDateString()
                });
            }
            return orders;
        }

        private DataTable GetData(string query)
        {
            string conString = ConfigurationManager.ConnectionStrings["AlcolimitsDB"].ConnectionString;
            SqlCommand cmd = new SqlCommand(query);
            using (SqlConnection con = new SqlConnection(conString))
            {
                using (SqlDataAdapter sda = new SqlDataAdapter())
                {
                    cmd.Connection = con;
                    sda.SelectCommand = cmd;
                    using (DataTable dt = new DataTable())
                    {
                        sda.Fill(dt);
                        return dt;

                    }
                }
            }
        }
    }
}
