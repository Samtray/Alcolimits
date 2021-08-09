import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AlcolimitsService {
  
  readonly APIUrl= 'https://alcolimitstest.azurewebsites.net/api';
  readonly Photos = 'https://alcolimitstest.azurewebsites.net/Photos/';

  constructor(private http:HttpClient){}

  getDrivers():Observable<any[]>{
    return this.http.get<any>(this.APIUrl+'/driver');
  }

  getDriverList():Observable<any[]>{
    return this.http.get<any>(this.APIUrl+'/DriverList');
  }

  addDriver(val:any){
    return this.http.post(this.APIUrl+'/driver', val);
  }

  updateDriver(val:any){
    return this.http.put(this.APIUrl+'/driver', val);
  }

  deleteDriver(val:any){
    return this.http.delete(this.APIUrl+'/driver/' + val);
  }
  
  uploadPhoto(val:any){
    return this.http.post(this.APIUrl+'/driver/saveFiles', val)
  }

  getVehiclePlates():Observable<any[]>{
    return this.http.get<any>(this.APIUrl+'/vehicle/getAllPlates');
  }
  
  deleteVehicle(val: any){
    return this.http.delete(this.APIUrl+'/vehicle/' + val);
  }

  addVehicle(val:any){
    return this.http.post(this.APIUrl+'/vehicle', val);
  }

  updateVehicle(val:any){
    return this.http.put(this.APIUrl+'/vehicle', val);
  }

  getUnassigned():Observable<any[]>{
    return this.http.get<any>(this.APIUrl+'/vehicle/unassigned');
  }

  getALH1():Observable<any[]>{
    return this.http.get<any>(this.APIUrl+'/alcoholSensor/alh1');
  }
  getALH2():Observable<any[]>{
    return this.http.get<any>(this.APIUrl+'/alcoholSensor/alh2');
  }
  getALH3():Observable<any[]>{
    return this.http.get<any>(this.APIUrl+'/alcoholSensor/alh3');
  }

  getVHC1():Observable<any[]>{
    return this.http.get<any>(this.APIUrl+'/VehicleStatus/vhc1');
  }

  getVHC2():Observable<any[]>{
    return this.http.get<any>(this.APIUrl+'/VehicleStatus/vhc2');
  }

  getVHC3():Observable<any[]>{
    return this.http.get<any>(this.APIUrl+'/VehicleStatus/vhc3');
  }

}