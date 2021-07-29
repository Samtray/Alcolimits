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
using Alcolimits.Models.ClassesAlcolimitsController;

namespace Alcolimits.Controllers
{
    public class TemperatureSensorController : ApiController
    {

        public HttpResponseMessage Get()
        {
            List<ConTemperatureSensor> temperatures = new List<ConTemperatureSensor>();
            DataTable dt = GetData("exec GetTemperature");
            for (int i = 0; i < dt.Rows.Count; i++)
            {
                ConTemperatureSensor temperature = new ConTemperatureSensor
                {
                    id = Convert.ToInt32(dt.Rows[i]["id"]),
                    val = Convert.ToInt32(dt.Rows[i]["val"]),
                    ranges = sensorRangesTemperature(),
                    current = sensorCurrentTemperature(Convert.ToString(dt.Rows[i]["status"])),

                };
                //ConDriverParent dp = new ConDriverParent
                //{
                //  driver = driverchild
                //};
                temperatures.Add(temperature);
            }
            var json = new JavaScriptSerializer().Serialize(temperatures);
            return Request.CreateResponse(HttpStatusCode.OK, temperatures);
        }

        public HttpResponseMessage Get(int id)
        {
            //List<ConAlcoholSensor> alcohols = new List<ConAlcoholSensor>();
            DataTable dt = GetData(string.Format("exec GetTemperatureId '{0}'", id));
            //for (int i = 0; i < dt.Rows.Count; i++)
            //{
                ConAlcoholSensor temperature = new ConAlcoholSensor
                {
                    id = Convert.ToInt32(dt.Rows[0]["id"]),
                    val = Convert.ToInt32(dt.Rows[0]["val"]),
                    ranges = sensorRangesTemperature(),
                    current = sensorCurrentTemperature(Convert.ToString(dt.Rows[0]["status"])),

                };
                //ConDriverParent dp = new ConDriverParent
                //{
                //  driver = driverchild
                //};
                //alcohols.Add(alcohol);
            //}
            var json = new JavaScriptSerializer().Serialize(temperature);
            return Request.CreateResponse(HttpStatusCode.OK, temperature);
        }


        /*private ConVehicle Vehicles(string vehiclePlate)
        { 
            DataTable dt = GetData(string.Format("exec SelectVehiclePlate '{0}'", vehiclePlate));
            //for (int i = 0; i < dt.Rows.Count; i++)
            //{
            ConVehicle vehicle = new ConVehicle { 
                id = Convert.ToInt32(dt.Rows[0]["id"]),
                plate = Convert.ToString(dt.Rows[0]["plate"]),
                model = Convert.ToString(dt.Rows[0]["model"]),
                year = Convert.ToString(dt.Rows[0]["year"]),
                color = Convert.ToString(dt.Rows[0]["color"]),
                photo = Convert.ToString(dt.Rows[0]["photo"]),
                location = location(Convert.ToInt32(dt.Rows[0]["location"])),
                alcoholSensor = alcoholSensor(Convert.ToInt32(dt.Rows[0]["alcohol_inf"])),
                temperatureSensor = temperatureSensor(Convert.ToInt32(dt.Rows[0]["temperature_inf"])),
                vehicleStatus = vehicleStatus(Convert.ToInt32(dt.Rows[0]["status_inf"])),
                logs = logs(Convert.ToString(dt.Rows[0]["plate"])),
                /*status_inf = Convert.ToInt32(dt.Rows[i]["status_inf"]),
                alcohol_inf = Convert.ToInt32(dt.Rows[i]["alcohol_inf"]),
                temperature_inf = Convert.ToInt32(dt.Rows[i]["temperature_inf"]),

            };
            //}
            return vehicle;
        }*/

        private ConLocation location(int location)
        {
            DataTable dt = GetData(string.Format("exec GetLocation '{0}'", location));
            //for (int i = 0; i < dt.Rows.Count; i++)
            //{
            ConLocation loc = new ConLocation
            {
                id = Convert.ToInt32(dt.Rows[0]["id"]),
                address = Convert.ToString(dt.Rows[0]["address"]),
                latitude = Convert.ToDouble(dt.Rows[0]["latitude"]),
                longitude = Convert.ToDouble(dt.Rows[0]["longitude"])

            };
            //}
            return loc;
        }

        private ConAlcoholSensor alcoholSensor(int alcohol_inf)
        {    
            DataTable dt = GetData(string.Format("exec GetAlcoholId '{0}'", alcohol_inf));
            //for (int i = 0; i < dt.Rows.Count; i++)
            //{
                ConAlcoholSensor alcohol = new ConAlcoholSensor 
                {
                    id = Convert.ToInt32(dt.Rows[0]["id"]),
                    val = Convert.ToInt32(dt.Rows[0]["val"]),
                    ranges = sensorRangesAlcohol(),
                    current = sensorCurrentAlcohol(Convert.ToString(dt.Rows[0]["status"])),

                };
            //}
            return alcohol;
        }

        private List<ConSensorRange> sensorRangesAlcohol() {

            List<ConSensorRange> ranges = new List<ConSensorRange>();
            DataTable dt = GetData("exec GetAlcoholRanges");
            for (int i = 0; i < dt.Rows.Count; i++)
            {
                ConSensorRange sr = new ConSensorRange
                {
                    id = Convert.ToString(dt.Rows[i]["id"]),
                    name = Convert.ToString(dt.Rows[i]["name"]),
                    minimum = Convert.ToInt32(dt.Rows[i]["minimum"]),
                    maximum = Convert.ToInt32(dt.Rows[i]["maximum"]),
                    color = Convert.ToString(dt.Rows[i]["color"]),
                    icon = Convert.ToString(dt.Rows[i]["icon"]),

                };
                ranges.Add(sr);
            }
            //var json = new JavaScriptSerializer().Serialize(ranges);
            return ranges;

        }

        private ConSensorRange sensorCurrentAlcohol(string id)
        {
            DataTable dt = GetData(string.Format("exec GetRangesId '{0}'", id));
            //for (int i = 0; i < dt.Rows.Count; i++)
            //{
            ConSensorRange sr = new ConSensorRange
            {
                id = Convert.ToString(dt.Rows[0]["id"]),
                name = Convert.ToString(dt.Rows[0]["name"]),
                minimum = Convert.ToInt32(dt.Rows[0]["minimum"]),
                maximum = Convert.ToInt32(dt.Rows[0]["maximum"]),
                color = Convert.ToString(dt.Rows[0]["color"]),
                icon = Convert.ToString(dt.Rows[0]["icon"]),

            };
            //}
            return sr;

        }

        private ConTemperatureSensor temperatureSensor(int temperature_inf)
        {
            DataTable dt = GetData(string.Format("exec GetTemperatureId '{0}'", temperature_inf));
            //for (int i = 0; i < dt.Rows.Count; i++)
            //{
                ConTemperatureSensor temperature = new ConTemperatureSensor
                {
                    id = Convert.ToInt32(dt.Rows[0]["id"]),
                    val = Convert.ToInt32(dt.Rows[0]["val"]),
                    ranges = sensorRangesTemperature(),
                    current = sensorCurrentTemperature(Convert.ToString(dt.Rows[0]["status"])),

                };
            //}
            return temperature;
        }

        private List<ConSensorRange> sensorRangesTemperature()
        {

            List<ConSensorRange> ranges = new List<ConSensorRange>();
            DataTable dt = GetData("exec GetTemperatureRanges");
            for (int i = 0; i < dt.Rows.Count; i++)
            {
                ConSensorRange sr = new ConSensorRange
                {
                    id = Convert.ToString(dt.Rows[i]["id"]),
                    name = Convert.ToString(dt.Rows[i]["name"]),
                    minimum = Convert.ToInt32(dt.Rows[i]["minimum"]),
                    maximum = Convert.ToInt32(dt.Rows[i]["maximum"]),
                    color = Convert.ToString(dt.Rows[i]["color"]),
                    icon = Convert.ToString(dt.Rows[i]["icon"]),

                };
                ranges.Add(sr);
            }
            //var json = new JavaScriptSerializer().Serialize(ranges);
            return ranges;

        }

        private ConSensorRange sensorCurrentTemperature(string id)
        {
            DataTable dt = GetData(string.Format("exec GetRangesId '{0}'", id));
            //for (int i = 0; i < dt.Rows.Count; i++)
            //{
            ConSensorRange sr = new ConSensorRange
            {
                id = Convert.ToString(dt.Rows[0]["id"]),
                name = Convert.ToString(dt.Rows[0]["name"]),
                minimum = Convert.ToInt32(dt.Rows[0]["minimum"]),
                maximum = Convert.ToInt32(dt.Rows[0]["maximum"]),
                color = Convert.ToString(dt.Rows[0]["color"]),
                icon = Convert.ToString(dt.Rows[0]["icon"]),

            };
            //}
            return sr;

        }

        private ConVehicleStatus vehicleStatus(int status_inf) { 
        
            DataTable dt = GetData(string.Format("exec GetStatusId '{0}'", status_inf));
            //for (int i = 0; i < dt.Rows.Count; i++)
            //{
                ConVehicleStatus status = new ConVehicleStatus
                {
                    id = Convert.ToInt32(dt.Rows[0]["id"]),
                    isOn = Convert.ToBoolean(dt.Rows[0]["isOn"]),
                    isDriving = Convert.ToBoolean(dt.Rows[0]["isDriving"]),
                    ranges = statusRanges(),
                    current = statusCurrent(Convert.ToString(dt.Rows[0]["status"]))
                };
            //}
            return status;
        }

        private List<ConVehicleStatusRange> statusRanges()
        {

            List<ConVehicleStatusRange> ranges = new List<ConVehicleStatusRange>();
            DataTable dt = GetData("exec GetVehicleRanges");
            for (int i = 0; i < dt.Rows.Count; i++)
            {
                ConVehicleStatusRange vr = new ConVehicleStatusRange
                {
                    id = Convert.ToString(dt.Rows[i]["id"]),
                    isOn = Convert.ToString(dt.Rows[i]["isOn"]),
                    isDriving = Convert.ToString(dt.Rows[i]["isDriving"]),
                    isOnColor = Convert.ToString(dt.Rows[i]["isOnColor"]),
                    isDrivingColor = Convert.ToString(dt.Rows[i]["isDrivingColor"]),
                    iconOn = Convert.ToString(dt.Rows[i]["iconOn"]),
                    iconDriving = Convert.ToString(dt.Rows[i]["iconDriving"])

                };
                ranges.Add(vr);
            }
            //var json = new JavaScriptSerializer().Serialize(ranges);
            return ranges;

        }

        private ConVehicleStatusRange statusCurrent(string id)
        {
            DataTable dt = GetData(string.Format("exec GetVehicleRangesId '{0}'", id));
            //for (int i = 0; i < dt.Rows.Count; i++)
            //{
            ConVehicleStatusRange sr = new ConVehicleStatusRange
            {
                id = Convert.ToString(dt.Rows[0]["id"]),
                isOn = Convert.ToString(dt.Rows[0]["isOn"]),
                isDriving = Convert.ToString(dt.Rows[0]["isDriving"]),
                isOnColor = Convert.ToString(dt.Rows[0]["isOnColor"]),
                isDrivingColor = Convert.ToString(dt.Rows[0]["isDrivingColor"]),
                iconOn = Convert.ToString(dt.Rows[0]["iconOn"]),
                iconDriving = Convert.ToString(dt.Rows[0]["iconDriving"])

            };
            //}
            return sr;

        }

        private ConLog logs(string plate) { 
        
            DataTable dt = GetData(string.Format("exec GetLogsId '{0}'", plate));
            //for (int i = 0; i < dt.Rows.Count; i++)
            //{
                ConLog logs = new ConLog
                {
                    id = Convert.ToInt32(dt.Rows[0]["id"]),
                    dateTime = Convert.ToString(dt.Rows[0]["dateTime"]),//.ToShortDateString() + " " + Convert.ToDateTime(dt.Rows[0]["date_time"]).ToShortTimeString(),
                    content = Convert.ToString(dt.Rows[0]["content"]),
                    //vehiclePlate = Convert.ToString(dt.Rows[0]["vehiclePlate"])
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

        public string Put(TemperatureSensor tmp)
        {
            try
            {
                string query = @"
                          exec UpdateTemperature '" 
                           + tmp.id + @"'
                         ,'" + tmp.val + @"'";
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