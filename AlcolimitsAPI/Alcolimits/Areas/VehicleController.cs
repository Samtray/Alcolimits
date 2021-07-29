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
    public class VehicleController : ApiController
    {

        public HttpResponseMessage Get()
        {

            string query = "exec GetVehicles;";
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
                string query = "select * from vehicles where id_vehicle = '" + id + "';";
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

        /*public HttpResponseMessage GetAllPLates()
        {
            string query = "exec GetAllVehiclePlates";
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
        }*/

        public string Post(Vehicle vhc)
        {
            try
            {
                string query = @"
                          exec AddNewVehicle '" + vhc.plate + @"'
                         ,'" + vhc.model + @"'
                        ,'" + vhc.vyear + @"'
                        ,'" + vhc.gps + @"'
                        ,'" + vhc.color + @"'
                        ,'" + vhc.photo + @"'
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

        public string Put(Vehicle vhc)
        {
            try
            {
                string query = @"
                          exec UpdateVehicle '" + vhc.id_vehicle + @"'
                          ,'" + vhc.plate + @"'
                         ,'" + vhc.model + @"'
                        ,'" + vhc.vyear + @"'
                        ,'" + vhc.gps + @"'
                        ,'" + vhc.color + @"'
                        ,'" + vhc.photo + @"'
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
                return "Modication failed.";
            }

        }

        public string Delete(int id)
        {
            try
            {
                string query = "exec DeleteVehicle '" + id + "';";
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

        }

    }
}
