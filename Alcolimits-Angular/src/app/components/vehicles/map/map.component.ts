import { Component, OnInit, Input } from '@angular/core';
import { environment } from 'src/environments/environment';

import * as Mapboxgl from 'mapbox-gl';


@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {
  
  map!: Mapboxgl.Map;
  @Input() loc: any;
  id: number | undefined
  address: string | undefined 
  longitude: any
  latitude: any
  constructor() { }

  ngOnInit(): void {
    this.loadLocation();
    (Mapboxgl as any).accessToken = environment.mapboxKey;
    this.map= new Mapboxgl.Map({
    container: 'map', // container id
    style: 'mapbox://styles/mapbox/streets-v11',
    center: [this.longitude, this.latitude], // starting position
    zoom: 16 // starting zoom

});
    this.marker(this.longitude, this.latitude);
    this.map.addControl(new Mapboxgl.NavigationControl());
    
  }

  marker(lng: number, lat: number){
    var marker = new Mapboxgl.Marker({
      draggable: true
      })
      .setLngLat([lng, lat])
      .addTo(this.map);

      marker.on('drag', () =>{
        console.log(marker.getLngLat())
      })

    }

    loadLocation(){
    console.log(this.loc);
    this.id = this.loc.id;
    this.longitude = this.loc.longitude;
    this.latitude = this.loc.latitude;
    this.address = this.loc.address;
    }

}
