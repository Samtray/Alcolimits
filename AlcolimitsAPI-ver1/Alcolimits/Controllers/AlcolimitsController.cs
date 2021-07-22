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

            DataTable dt = GetData("SELECT * from drivers");
            for (int i = 0; i < dt.Rows.Count; i++)
            {
                Driver driver = new Driver
                {
                    id_driver = Convert.ToInt32(dt.Rows[i]["id_driver"]),
                    first_name = Convert.ToString(dt.Rows[i]["first_name"]),
                    middle_name = Convert.ToString(dt.Rows[i]["middle_name"]),
                    last_name = Convert.ToString(dt.Rows[i]["last_name"]),
                    photo = Convert.ToString(dt.Rows[i]["photo"]),
                    vehicle = Vehicles(Convert.ToString(dt.Rows[i]["VehiclePlate"]))

                };
                drivers.Add(driver);
            }
            var json = new JavaScriptSerializer().Serialize(drivers);
            return Request.CreateResponse(HttpStatusCode.OK, drivers);
        }

        private List<Vehicle> Vehicles(string VehiclePlate)
        {
            List<Vehicle> vehicles = new List<Vehicle>();
            DataTable dt = GetData(string.Format("select * from vehicles where plate ='{0}'", VehiclePlate));
            for (int i = 0; i < dt.Rows.Count; i++)
            {
                vehicles.Add(new Vehicle
                {
                    id_vehicle = Convert.ToInt32(dt.Rows[i]["id_vehicle"]),
                    plate = Convert.ToString(dt.Rows[i]["plate"]),
                    model = Convert.ToString(dt.Rows[i]["model"]),
                    vyear = Convert.ToString(dt.Rows[i]["anio"]),
                    gps = Convert.ToString(dt.Rows[i]["gps"]),
                    color = Convert.ToString(dt.Rows[i]["color"]),
                    alcoholSensor = alcoholSensor(Convert.ToInt32(dt.Rows[i]["alcohol_inf"])),
                    temperatureSensor = temperatureSensor(Convert.ToInt32(dt.Rows[i]["temperature_inf"])),
                    vehicleStatus = vehicleStatus(Convert.ToInt32(dt.Rows[i]["status_inf"])),
                    logs = logs(Convert.ToString(dt.Rows[i]["plate"])),
                    /*status_inf = Convert.ToInt32(dt.Rows[i]["status_inf"]),
                    alcohol_inf = Convert.ToInt32(dt.Rows[i]["alcohol_inf"]),
                    temperature_inf = Convert.ToInt32(dt.Rows[i]["temperature_inf"]),*/

                });
            }
            return vehicles;
        }

        private List<AlcoholSensor> alcoholSensor(int alcohol_inf)
        {
            List<AlcoholSensor> alcohol = new List<AlcoholSensor>();
            DataTable dt = GetData(string.Format("select * from alcoholSensor where id_alcohol ='{0}'", alcohol_inf));
            for (int i = 0; i < dt.Rows.Count; i++)
            {
                alcohol.Add(new AlcoholSensor
                {
                    id_alcohol = Convert.ToInt32(dt.Rows[i]["id_alcohol"]),
                    val = Convert.ToInt32(dt.Rows[i]["val"]),
                    result = Convert.ToString(dt.Rows[i]["result"])
          
              });
            }
            return alcohol;
        }

        private List<TemperatureSensor> temperatureSensor(int temperature_inf)
        {
            List<TemperatureSensor> temperature = new List<TemperatureSensor>();
            DataTable dt = GetData(string.Format("select * from temperatureSensor where id_temperature ='{0}'", temperature_inf));
            for (int i = 0; i < dt.Rows.Count; i++)
            {
                temperature.Add(new TemperatureSensor
                {
                    id_temperature = Convert.ToInt32(dt.Rows[i]["id_temperature"]),
                    val = Convert.ToDecimal(dt.Rows[i]["val"])

                });
            }
            return temperature;
        }

        private List<VehicleStatus> vehicleStatus(int status_inf)
        {
            List<VehicleStatus> status = new List<VehicleStatus>();
            DataTable dt = GetData(string.Format("select * from vehicleStatus where id_status ='{0}'", status_inf));
            for (int i = 0; i < dt.Rows.Count; i++)
            {
                status.Add(new VehicleStatus
                {
                    id_status = Convert.ToInt32(dt.Rows[i]["id_status"]),
                    isDriving = Convert.ToString(dt.Rows[i]["isDriving"]),
                    isOn = Convert.ToString(dt.Rows[i]["isOn"])
                });
            }
            return status;
        }

        private List<Log> logs(string plate)
        {
            List<Log> logs = new List<Log>();
            DataTable dt = GetData(string.Format("select * from logs where VehiclePlate ='{0}'", plate));
            for (int i = 0; i < dt.Rows.Count; i++)
            {
                logs.Add(new Log
                {
                    id_log = Convert.ToInt32(dt.Rows[i]["id_log"]),
                    date_time = Convert.ToDateTime(dt.Rows[i]["date_time"]).ToShortDateString() + " " + Convert.ToDateTime(dt.Rows[i]["date_time"]).ToShortTimeString(),
                    content = Convert.ToString(dt.Rows[i]["content"])
                });
            }
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