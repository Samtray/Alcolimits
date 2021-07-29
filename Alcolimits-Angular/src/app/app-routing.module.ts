import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DriversComponent } from './components/drivers/drivers.component' 
import { VehiclesComponent } from './components/vehicles/vehicles.component' 
import { DashboardComponent } from './components/dashboard/dashboard.component'


const routes: Routes = [

{path: 'dashboard',component:DashboardComponent},
{path: 'drivers', component:DriversComponent},
{path: 'vehicles', component:VehiclesComponent},
{ path: '', component: DashboardComponent },
{ path: '**', component: DashboardComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
