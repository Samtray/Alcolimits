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
    public class DriverListController : ApiController
    {
        public HttpResponseMessage Get()
        {

            string query = "exec GetDrivers;";
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


        /*public string Post(Driver drv)
        {
            try
            {
                string query = @"
                          exec AddNewDriver '" + drv.first_name + @"'
                        ,'" + drv.middle_name + @"'
                        ,'" + drv.last_name + @"'
                        ,'" + drv.photo + @"'
                        ,'" + drv.vehiclePlate + @"'
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
                return "Add successful.";
            }
            catch (Exception)
            {
                return "Add failed.";
            }

        }

        public string Put(Driver drv)
        {
            try
            {
                string query = @"
                          exec UpdateDriver '" + drv.id_driver + @"'
                         ,'" + drv.first_name + @"'
                        ,'" + drv.middle_name + @"'
                        ,'" + drv.last_name + @"'
                        ,'" + drv.photo + @"'
                        ,'" + drv.vehiclePlate + @"'
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

        public string Delete(int id)
        {
            try
            {
                string query = "exec DeleteDriver '" + id + "';";
                DataTable table = new DataTable();
                using (var con = new SqlConnection(ConfigurationManager.
                    ConnectionStrings["AlcolimitsDB"].ConnectionString))
                using (var cmd = new SqlCommand(query, con))
                using (var da = new SqlDataAdapter(cmd))
                {

                    cmd.CommandType = CommandType.Text;
                    da.Fill(table);

                }
                return "Delete successful.";
            }
            catch (Exception)
            {
                return "Delete failed.";
            }

        }*/
    }
}