using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Script.Serialization;
using Alcolimits.Models;

namespace Alcolimits.Controllers
{
    public class AlcolimitsController : ApiController
    {
        public HttpResponseMessage Get()
        {
            List<Driver> drivers = new List<Driver>();
            DataTable dt = GetData("exec GetDrivers");
            for (int i = 0; i < dt.Rows.Count; i++)
            {
                Driver driver = new Driver
                {
                    id_driver = Convert.ToInt32(dt.Rows[i]["id_driver"]),
                    first_name = Convert.ToString(dt.Rows[i]["first_name"]),
                    middle_name = Convert.ToString(dt.Rows[i]["middle_name"]),
                    last_name = Convert.ToString(dt.Rows[i]["last_name"]),
                    photo = Convert.ToString(dt.Rows[i]["photo"]),
                    VehiclePlate = Convert.ToString(dt.Rows[i]["VehiclePlate"]),
                    vehicle = Vehicles(Convert.ToString(dt.Rows[i]["VehiclePlate"]))

                };
                drivers.Add(driver);
            }
            var json = new JavaScriptSerializer().Serialize(drivers);
            return Request.CreateResponse(HttpStatusCode.OK, drivers);
        }

        private Vehicle Vehicles(string VehiclePlate)
        { 
            DataTable dt = GetData(string.Format("exec SelectVehiclePlate '{0}'", VehiclePlate));
            //for (int i = 0; i < dt.Rows.Count; i++)
            //{
            Vehicle vehicles = new Vehicle { 
                id_vehicle = Convert.ToInt32(dt.Rows[0]["id_vehicle"]),
                plate = Convert.ToString(dt.Rows[0]["plate"]),
                model = Convert.ToString(dt.Rows[0]["model"]),
                vyear = Convert.ToString(dt.Rows[0]["vyear"]),
                gps = Convert.ToString(dt.Rows[0]["gps"]),
                color = Convert.ToString(dt.Rows[0]["color"]),
                alcoholSensor = alcoholSensor(Convert.ToInt32(dt.Rows[0]["alcohol_inf"])),
                temperatureSensor = temperatureSensor(Convert.ToInt32(dt.Rows[0]["temperature_inf"])),
                vehicleStatus = vehicleStatus(Convert.ToInt32(dt.Rows[0]["status_inf"])),
                logs = logs(Convert.ToString(dt.Rows[0]["plate"])),
                /*status_inf = Convert.ToInt32(dt.Rows[i]["status_inf"]),
                alcohol_inf = Convert.ToInt32(dt.Rows[i]["alcohol_inf"]),
                temperature_inf = Convert.ToInt32(dt.Rows[i]["temperature_inf"]),*/

            };
            //}
            return vehicles;
        }

        private AlcoholSensor alcoholSensor(int alcohol_inf)
        {    
            DataTable dt = GetData(string.Format("exec GetAlcoholId '{0}'", alcohol_inf));
            //for (int i = 0; i < dt.Rows.Count; i++)
            //{
                AlcoholSensor alcohol = new AlcoholSensor 
                {
                    id_alcohol = Convert.ToInt32(dt.Rows[0]["id_alcohol"]),
                    val = Convert.ToInt32(dt.Rows[0]["val"]),
                    result = Convert.ToString(dt.Rows[0]["result"])
          
              };
            //}
            return alcohol;
        }

        private TemperatureSensor temperatureSensor(int temperature_inf)
        {
            DataTable dt = GetData(string.Format("exec GetTemperatureId '{0}'", temperature_inf));
            //for (int i = 0; i < dt.Rows.Count; i++)
            //{
                TemperatureSensor temperature = new TemperatureSensor
                {
                    id_temperature = Convert.ToInt32(dt.Rows[0]["id_temperature"]),
                    val = Convert.ToDecimal(dt.Rows[0]["val"])

                };
            //}
            return temperature;
        }

        private VehicleStatus vehicleStatus(int status_inf) { 
        
            DataTable dt = GetData(string.Format("exec GetStatusId '{0}'", status_inf));
            //for (int i = 0; i < dt.Rows.Count; i++)
            //{
                VehicleStatus status = new VehicleStatus
                {
                    id_status = Convert.ToInt32(dt.Rows[0]["id_status"]),
                    isDriving = Convert.ToString(dt.Rows[0]["isDriving"]),
                    isOn = Convert.ToString(dt.Rows[0]["isOn"])
                };
            //}
            return status;
        }

        private Log logs(string plate) { 
        
            DataTable dt = GetData(string.Format("exec GetLogsId '{0}'", plate));
            //for (int i = 0; i < dt.Rows.Count; i++)
            //{
                Log logs = new Log
                {
                    id_log = Convert.ToInt32(dt.Rows[0]["id_log"]),
                    date_time = Convert.ToString(dt.Rows[0]["date_time"]),//.ToShortDateString() + " " + Convert.ToDateTime(dt.Rows[0]["date_time"]).ToShortTimeString(),
                    content = Convert.ToString(dt.Rows[0]["content"]),
                    VehiclePlate = Convert.ToString(dt.Rows[0]["VehiclePlate"])

                };
            //}
            return logs;
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