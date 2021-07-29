
import { formatDate } from '@angular/common';
import { Component, OnInit, Input} from '@angular/core';
import { AlcolimitsService } from '../../../alcolimits.service';


@Component({
  selector: 'app-add-vhc',
  templateUrl: './add-vhc.component.html',
  styleUrls: ['./add-vhc.component.css']
})
export class AddVhcComponent implements OnInit {

  constructor(private service:AlcolimitsService) { }
  
  @Input() vhc: any;
  id: number | undefined 
  plate: string | undefined
  model:string | undefined
  year: string | undefined
  color: string | undefined
  photo!: string;
  //vehiclePlates: any=[] ;

  ngOnInit(): void {
    this.loadDetails();
    console.log(this.vhc);
    /*this.service.getVehiclePlates().subscribe((data:any)=>{
      this.vehiclePlates=data;
      console.log(data)});*/
  }

  loadDetails(){
    this.id = this.vhc.id;
    this.plate = this.vhc.plate; 
    this.model = this.vhc.model; 
    this.year = this.vhc.year; 
    this.color = this.vhc.color; 
    this.photo = this.vhc.photo; 
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
