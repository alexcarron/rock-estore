import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common'; // Add CommonModule import

import { AppRoutingModule } from './modules/app-routing.module';
import { AppComponent } from './app.component';
import { RocksComponent } from './components/rocks/rocks.component';
import { FormsModule } from '@angular/forms';
import { RockDetailComponent } from './components/rock-detail/rock-detail.component';
import { MessagesComponent } from './components/messages/messages.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { HttpClientModule } from '@angular/common/http';
import { RockSearchComponent } from './components/rock-search/rock-search.component';
import { LoginComponent } from './components/login/login.component';
import { LogoutComponent } from './components/logout/logout.component';
import { CartComponent } from './components/cart/cart.component';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr'; // Import ToastrModule from 'ngx-toastr' package

@NgModule({
  declarations: [
    AppComponent,
    RocksComponent,
    RockDetailComponent,
    MessagesComponent,
    DashboardComponent,
    RockSearchComponent,
    LoginComponent,
    LogoutComponent,
    CartComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    CommonModule, // Add CommonModule to imports
    HttpClientModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(), // ToastrModule added
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }