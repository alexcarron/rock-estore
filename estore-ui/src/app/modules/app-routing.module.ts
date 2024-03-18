import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RocksComponent } from '../components/rocks/rocks.component';
import { DashboardComponent } from '../components/dashboard/dashboard.component';
import { RockDetailComponent } from '../components/rock-detail/rock-detail.component';
import { LoginComponent } from '../components/login/login.component';

const routes: Routes = [
	{path: '', redirectTo: '/dashboard', pathMatch: 'full'},
  {path: 'rocks', component: RocksComponent},
	{path: 'dashboard', component: DashboardComponent},
  {path: 'detail/:id', component: RockDetailComponent},
  {path: 'login', component: LoginComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}