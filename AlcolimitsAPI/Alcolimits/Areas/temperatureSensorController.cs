using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using Alcolimits.Models;
namespace Alcolimits.Controllers
{
    public class temperatureSensorController : ApiController
    {
       
            public HttpResponseMessage Get()
            {
                string query = "exec GetTemperature;";
                DataTable table = new DataTable();
                using (var con = new SqlConnection(ConfigurationManager.
                    ConnectionStrings["AlcolimitsDB"].ConnectionString))
                using (var cmd = new SqlCommand(query, con))
                using (var da = new SqlDataAdapter(cmd))
                {

                    cmd.CommandType = CommandType.Text;
                    da.Fill(table);

                }

                return Request.CreateResponse(HttpStatusCode.OK, table);
            }

        public HttpResponseMessage Get(int id)
        {
            string query = "exec GetTemperatureId '" + id + "';";
            DataTable table = new DataTable();
            using (var con = new SqlConnection(ConfigurationManager.
                ConnectionStrings["AlcolimitsDB"].ConnectionString))
            using (var cmd = new SqlCommand(query, con))
            using (var da = new SqlDataAdapter(cmd))
            {

                cmd.CommandType = CommandType.Text;
                da.Fill(table);

            }

            return Request.CreateResponse(HttpStatusCode.OK, table);
        }



            public string Put(TemperatureSensor tmp)
            {
                try
                {
                    string query = @"
                          exec UpdateTemperature '" + tmp.id_temperature + @"'
                         ,'" + tmp.val + @"'
                         ,'" + tmp.result + @"'
                         ,'" + tmp.icon + @"'
                        ";
                    DataTable table = new DataTable();
                    using (var con = new SqlConnection(ConfigurationManager.
                        ConnectionStrings["AlcolimitsDB"].ConnectionString))
                    using (var cmd = new SqlCommand(query, con))
                    using (var da = new SqlDataAdapter(cmd))
                    {

                        cmd.CommandType = CommandType.Text;
                        da.Fill(table);

                    }
                    return "Modification successful.";
                }
                catch (Exception)
                {
                    return "Modification failed.";
                }

            }
            
      }
 }

