import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { DriversComponent } from './components/drivers/drivers.component';
import { ShowDrvComponent } from './components/drivers/show-drv/show-drv.component';
import { AddDrvComponent } from './components/drivers/add-drv/add-drv.component';
import { VehiclesComponent } from './components/vehicles/vehicles.component';
import { ShowVhcComponent } from './components/vehicles/show-vhc/show-vhc.component';
import { AddVhcComponent } from './components/vehicles/add-vhc/add-vhc.component';
import { ShowUnassignedComponent } from './components/vehicles/show-unassigned/show-unassigned.component';
import {HttpClientModule} from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { EditUnassignedComponent } from './components/vehicles/edit-unassigned/edit-unassigned.component';
import { MapComponent } from './components/vehicles/map/map.component'


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    DashboardComponent,
    DriversComponent,
    ShowDrvComponent,
    AddDrvComponent,
    VehiclesComponent,
    ShowVhcComponent,
    AddVhcComponent,
    ShowUnassignedComponent,
    EditUnassignedComponent,
    MapComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    HttpClientModule,
  
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
