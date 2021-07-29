import { Component, OnInit } from '@angular/core';
import {AlcolimitsService} from '../../../alcolimits.service';
import {ShowUnassignedComponent} from './../show-unassigned/show-unassigned.component'
import {Chart} from 'chart.js';

@Component({
  selector: 'app-show-vhc',
  templateUrl: './show-vhc.component.html',
  styleUrls: ['./show-vhc.component.css'],
  providers:[ShowUnassignedComponent]
})
export class ShowVhcComponent implements OnInit {

  constructor(private service:AlcolimitsService, private comp: ShowUnassignedComponent) { }

    Vehicle: any=[]; 
    //DriverList: any=[]; 
    ModalTitle!: string;
    ActivateAddEditVhcComponent:Boolean=false;
    ActivateViewLocation:Boolean=false;  
    vhc :any;  
    loc : any;

    ngOnInit(): void {
      this.refreshVhcList();
      this.comp.refreshVhcUnassignedList();

    }
  
    refreshVhcList(){
      this.service.getDrivers().subscribe(data=>{
        this.Vehicle=data;
      });
      this.comp.refreshVhcUnassignedList();
    }
    
    editClick(item: any){
      //console.log(item);
      this.vhc=item;
      this.ModalTitle = "Edit Vehicle";
      this.ActivateAddEditVhcComponent = true;
      console.log(this.vhc);
      //console.log(this.Drivers);
      //console.log(this.ActivateAddEditVhcComponent);
    }  

    location(item: any){
      //console.log(item);
      this.loc=item;
      this.ModalTitle = "Vehicle Location";
      this.ActivateViewLocation = true;
      console.log(this.loc);
      //console.log(this.Drivers);
      //console.log(this.ActivateAddEditVhcComponent);
    }  

    addClick(){
      this.vhc={
        id: 0,
        plate:"",
        model:"",
        year:"",
        color: "",
        photo: "http://localhost:59853/Photos/carRed.svg",
      };
      console.log(this.vhc);
      this.ModalTitle = "Add Vehicle";
      this.ActivateAddEditVhcComponent = true;
      //console.log(this.ActivateAddEditDrvComponent);
    }

    closeClick(){
      this.ActivateAddEditVhcComponent = false;
      //console.log(this.ActivateAddEditDrvComponent);
      this.refreshVhcList();
    }

    deleteClick(item: any){
      console.log(item.id);
      if(confirm('Are you sure?')){
        this.service.deleteVehicle(item.id).subscribe(data=>{
          alert(data.toString());
          this.refreshVhcList();
        });
      }
    }
  

}
