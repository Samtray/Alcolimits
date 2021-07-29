import { Component, OnInit, Input } from '@angular/core';
import { AlcolimitsService } from '../../../alcolimits.service';
@Component({
  selector: 'app-edit-unassigned',
  templateUrl: './edit-unassigned.component.html',
  styleUrls: ['./edit-unassigned.component.css']
})
export class EditUnassignedComponent implements OnInit {
 
  constructor(private service:AlcolimitsService) { }
  
  @Input() vhcu: any;
  id: number | undefined 
  plate: string | undefined
  model:string | undefined
  year: string | undefined
  color: string | undefined
  photo!: string;
  //vehiclePlates: any=[] ;

  ngOnInit(): void {
    this.loadDetails();
    console.log(this.vhcu);
    /*this.service.getVehiclePlates().subscribe((data:any)=>{
      this.vehiclePlates=data;
      console.log(data)});*/
  }

  loadDetails(){
    this.id = this.vhcu.id;
    this.plate = this.vhcu.plate; 
    this.model = this.vhcu.model; 
    this.year = this.vhcu.year; 
    this.color = this.vhcu.color; 
    this.photo = this.vhcu.photo; 
  }

  addVehicle(){
    var val = {
      plate: this.plate, 
      model: this.model, 
      year: this.year, 
      color: this.color, 
      photo: this.photo 
    };
    console.log(val);
    this.service.addVehicle(val).subscribe(res=>{
      alert(res.toString());
    });
  }

   updateVehicle(){
    var val = {
      id: this.id,
      plate: this.plate, 
      model: this.model, 
      year: this.year, 
      color: this.color, 
      photo: this.photo };
      console.log(val);
      this.service.updateVehicle(val).subscribe(res=>{
      alert(res.toString());
      });
   }
    
   uploadPhoto(event: any){
    var file = event.target.files[0];
    const formData: FormData = new FormData();
    formData.append('uploadedFile', file, file.name);
    this.service.uploadPhoto(formData).subscribe((data: any)=>{
      this.photo = data.toString();
      //this.PhotoFilePath = this.service.Photos + this.PhotoFileName;
      });
   }


}
