import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RocksComponent } from '../components/rocks/rocks.component';
import { DashboardComponent } from '../components/dashboard/dashboard.component';
import { RockDetailComponent } from '../components/rock-detail/rock-detail.component';
import { LoginComponent } from '../components/login/login.component';
import { LogoutComponent } from '../components/logout/logout.component';
import { CartComponent } from '../components/cart/cart.component';

const routes: Routes = [
	{path: '', redirectTo: '/login', pathMatch: 'full'},
  {path: 'rocks', component: RocksComponent},
	{path: 'dashboard', component: DashboardComponent},
  {path: 'cart', component: CartComponent},
  {path: 'detail/:id', component: RockDetailComponent},
  {path: 'login', component: LoginComponent},
	{path: 'logout', component: LogoutComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}