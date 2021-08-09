import { Component, OnInit } from '@angular/core';
import {AlcolimitsService} from '../../../alcolimits.service'

@Component({
  selector: 'app-show-drv',
  templateUrl: './show-drv.component.html',
  styleUrls: ['./show-drv.component.css']
})
export class ShowDrvComponent implements OnInit {

  constructor(private service:AlcolimitsService) { }

    Drivers: any=[]; 
    DriverList: any=[]; 
    ModalTitle!: string;
    ActivateAddEditDrvComponent:Boolean=false; 
    drv :any;  
    LCK:any = "Vehicle Locked";

    ngOnInit(): void {
      this.refreshDrvList();
    }
  
    refreshDrvList(){
      this.service.getDrivers().subscribe(data=>{
        this.Drivers=data;
      });
      //this.service.getDriverList().subscribe(data=>{
        //this.DriverList=data;
      //});
    }
    
    editClick(item: any){
      //console.log(item);
      this.drv=item;
      this.ModalTitle = "Edit Driver";
      this.ActivateAddEditDrvComponent = true;
      //console.log(this.drv);
      //console.log(this.Drivers);
      console.log(this.ActivateAddEditDrvComponent);
    }  

    addClick(){
      this.drv={
        id: 0,
        firstName:"",
        middleName:"",
        lastName:"",
        profilePhoto: "https://alcolimitstest.azurewebsites.net/Photos/placeholder.jpg",
        licensePhoto: "https://alcolimitstest.azurewebsites.net/Photos/placeholder.jpg",
        plate: ""
      };
      console.log(this.drv);
      this.ModalTitle = "Add Driver";
      this.ActivateAddEditDrvComponent = true;
      //onsole.log(this.ActivateAddEditDrvComponent);
    }
  
    closeClick(){
      this.ActivateAddEditDrvComponent = false;
      //console.log(this.ActivateAddEditDrvComponent);
      this.refreshDrvList();
    }

    deleteClick(item: any){
      console.log(item.id);
      if(confirm('Are you sure?')){
        this.service.deleteDriver(item.id).subscribe(data=>{
          alert(data.toString());
          this.refreshDrvList();
        });
      }
    }

}
