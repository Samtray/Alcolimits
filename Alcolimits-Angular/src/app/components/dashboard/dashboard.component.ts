import { Component, OnInit, ElementRef, ViewChild, AfterViewInit } from '@angular/core';
import { Chart, registerables } from 'chart.js';
import {AlcolimitsService} from './../../alcolimits.service';
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, AfterViewInit {

  alh1: any=[];
  alh2: any=[];
  alh3: any=[];
  vhc1: any=[];
  vhc2: any=[];
  vhc3: any=[];
  chart: any=[];
  chart2: any=[];
  constructor(private service:AlcolimitsService, private elementRef: ElementRef) { 
    Chart.register(...registerables);
  }
  @ViewChild('canvas') canvas!: ElementRef;
  @ViewChild('canvas2') canvas2!: ElementRef;
  ngOnInit(): void {
    this.service.getALH1().subscribe(data=>{
      this.alh1=data;
    });
    this.service.getALH2().subscribe(data=>{
      this.alh2=data;
    });
    this.service.getALH3().subscribe(data=>{
      this.alh3=data;
    });
    this.service.getVHC1().subscribe(data=>{
      this.vhc1=data;
    });
    this.service.getVHC2().subscribe(data=>{
      this.vhc2=data;
    });
    this.service.getVHC3().subscribe(data=>{
      this.vhc3=data;
      
    });
    //this.viewdata();
  }

  refreshChart(){
    this.chart.destroy();
    this.chart2.destroy();
    this.service.getALH1().subscribe(data=>{
      this.alh1=data;
    });
    this.service.getALH2().subscribe(data=>{
      this.alh2=data;
    });
    this.service.getALH3().subscribe(data=>{
      this.alh3=data;
    });
    this.service.getVHC1().subscribe(data=>{
      this.vhc1=data;
    });
    this.service.getVHC2().subscribe(data=>{
      this.vhc2=data;
    });
    this.service.getVHC3().subscribe(data=>{
      this.vhc3=data;
      
    });
    //this.viewdata();
  }



  ngAfterViewInit() {
    setTimeout(() => {
      Chart.defaults.font.size = 16;
      Chart.defaults.borderColor = '#ccc';
      Chart.defaults.color = '#fff';		
      this.chart = new Chart(this.canvas.nativeElement.getContext('2d'), {
        type: 'doughnut',
        data: {
          labels: [
            'No Alcohol',
            'Some Alcohol',
            'High Alcohol'
          ],
          datasets: [{
            label: 'My First Dataset',
            data: [this.alh1.value, this.alh2.value, this.alh3.value],
            backgroundColor: [
              '#4BDD5E',
              '#FEF84F',
              '#E14B4B'
            ],
            hoverOffset: 3
          }],
        }, options: {
          plugins: {
            legend: {
              labels: {
                font: {
                  size: 16
                },
                color: 'white',
                }
              }
            }
          }
      });
      this.chart2 = new Chart(this.canvas2.nativeElement.getContext('2d'), {
        type: 'bar',
        data: {
          labels: ["Currently Driving","Not Driving","Locked Vehicles"],
          datasets: [{
            label: 'Today',
            data: [this.vhc2.value, this.vhc1.value, this.vhc3.value],
            backgroundColor: [
              '#4BDD5E',
              '#FEF84F',
              '#E14B4B'
            ],
            borderColor: [
              '#4BDD5E',
              '#FEF84F',
              '#E14B4B'
            ],
            borderWidth: 1
          }]
        },options: {
          plugins: {
            legend: {
              labels: {
                font: {
                  size: 16
                },
                color: 'white',
                }
              }
            }, scales: {
              y: {
                beginAtZero: true,
              }
            }
          },
      });
    }, 1000);
   }
}


