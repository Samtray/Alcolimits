
import { formatDate } from '@angular/common';
import { Component, OnInit, Input} from '@angular/core';
import { AlcolimitsService } from '../../../alcolimits.service';

@Component({
  selector: 'app-add-drv',
  templateUrl: './add-drv.component.html',
  styleUrls: ['./add-drv.component.css']
})
export class AddDrvComponent implements OnInit {

  constructor(private service:AlcolimitsService) { }
  
  @Input() drv: any;
  id: number | undefined 
  firstName: string | undefined
  middleName:string | undefined
  lastName: string | undefined
  profilePhoto: string | undefined
  licensePhoto: string | undefined
  plate: string | undefined

  vehiclePlates: any=[] ;

  ngOnInit(): void {
    //console.log(this.id);
    this.loadDetails();
    this.service.getVehiclePlates().subscribe((data:any)=>{
      this.vehiclePlates=data;
      console.log(data)});
  }

  loadDetails(){
    this.id = this.drv.id
    this.firstName = this.drv.firstName; 
    this.middleName = this.drv.middleName; 
    this.lastName = this.drv.lastName; 
    this.profilePhoto = this.drv.profilePhoto; 
    this.licensePhoto = this.drv.licensePhoto; 
    //this.plate = this.drv.vehicle.plate;
  }

  addDriver(){
    var val = {firstName:this.firstName, 
      middleName:this.middleName,
      lastName: this.lastName,
      profilePhoto: this.profilePhoto,
      licensePhoto: this.licensePhoto,
      vehiclePlate: this.plate
    };
    console.log(val);
    this.service.addDriver(val).subscribe(res=>{
      alert(res.toString());
    });
  }

   updateDriver(){
    var val = {
      id: this.id,
      firstName: this.firstName, 
      middleName:this.middleName,
      lastName: this.lastName,
      profilePhoto: this.profilePhoto,
      licensePhoto: this.licensePhoto,
      vehiclePlate: this.plate};
      console.log(val);
      this.service.updateDriver(val).subscribe(res=>{
      alert(res.toString());
      });
   }
    
   uploadProfilePhoto(event: any){
    var file = event.target.files[0];
    const formData: FormData = new FormData();
    formData.append('uploadedFile', file, file.name);
    this.service.uploadPhoto(formData).subscribe((data: any)=>{
      this.profilePhoto = data.toString();
      //this.PhotoFilePath = this.service.Photos + this.PhotoFileName;
      });
   }

   uploadLicensePhoto(event: any){
    var file = event.target.files[0];
    const formData: FormData = new FormData();
    formData.append('uploadedFile', file, file.name);
    this.service.uploadPhoto(formData).subscribe((data: any)=>{
      this.licensePhoto = data.toString();
      //this.PhotoFilePath = this.service.Photos + this.PhotoFileName;
      });
    }

}
