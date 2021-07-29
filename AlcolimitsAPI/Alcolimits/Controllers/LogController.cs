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
    public class LogController : ApiController
    {
       
            public HttpResponseMessage Get()
            {
                string query = "exec GetLogs;";
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

        public HttpResponseMessage Get(string plate)
        {
            string query = "exec GetLogsId '" + plate + "';";
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

        public string Post(Log log)
        {
            try
            {
                string query = @"
                          exec AddLog '" + log.dateTime + @"'
                         ,'" + log.content + @"'
                         ,'" + log.vehiclePlate + @"'
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

        public string Put(Log log)
            {
                try
                {
                    string query = @"
                          exec UpdateLogs '" + log.id + @"'
                         ,'" + log.dateTime + @"'
                         ,'" + log.content + @"'
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

