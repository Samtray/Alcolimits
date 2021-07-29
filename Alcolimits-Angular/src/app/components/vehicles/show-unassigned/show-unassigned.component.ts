import { Component, OnInit } from '@angular/core';
import {AlcolimitsService} from '../../../alcolimits.service';
import { Observable,Subscription, interval  } from 'rxjs';

@Component({
  selector: 'app-unassigned-status',
  templateUrl: './show-unassigned.component.html',
  styleUrls: ['./show-unassigned.component.css']
})
export class ShowUnassignedComponent implements OnInit {

  constructor(private service:AlcolimitsService) { }
    
    //Vehicle: any=[]; 
    //DriverList: any=[]; 
    updateSubscription: Subscription | undefined;
    ModalTitle!: string;
    ActivateAddEditVhcUnComponent:Boolean=false; 
    vhcu :any;  
    Unassigned: any=[];

    ngOnInit(): void {
      this.updateSubscription = interval(1000).subscribe(
        (val) => { this.refreshVhcUnassignedList()});
      //this.refreshVhcUnassignedList();
      //console.log(this.Unassigned);
    }
  
    refreshVhcUnassignedList(){
      this.service.getUnassigned().subscribe(data=>{
        this.Unassigned=data;
      });
    }
    
    editClick(item: any){
      //console.log(item);
      this.vhcu=item;
      this.ModalTitle = "Edit Vehicle";
      this.ActivateAddEditVhcUnComponent = true;
      console.log(this.vhcu);
      //console.log(this.Drivers);
      //console.log(this.ActivateAddEditVhcComponent);
    }  

    /*addClick(){
      this.vhc={
        id: 0,
        plate:"",
        model:"",
        year:"",
        color: "",
        photo: "http://localhost:59853/Photos/steering-wheel.svg",
      };
      console.log(this.vhc);
      this.ModalTitle = "Add Vehicle";
      this.ActivateAddEditVhcComponent = true;
      //console.log(this.ActivateAddEditDrvComponent);
    }*/

    closeClickUn(){
      this.ActivateAddEditVhcUnComponent = false;
      //console.log(this.ActivateAddEditDrvComponent);
      this.refreshVhcUnassignedList();
    }

    deleteClick(item: any){
      console.log(item.id);
      if(confirm('Are you sure?')){
        this.service.deleteVehicle(item.id).subscribe(data=>{
          alert(data.toString());
          this.refreshVhcUnassignedList();
        });
      }
    }
  

}

