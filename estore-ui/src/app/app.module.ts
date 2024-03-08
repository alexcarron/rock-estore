import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './modules/app-routing.module';
import { AppComponent } from './app.component';
import { RocksComponent } from './components/rocks/rocks.component';
import { FormsModule } from '@angular/forms';
import { NgFor } from '@angular/common';
import { RockDetailComponent } from './components/rock-detail/rock-detail.component';
import { MessagesComponent } from './components/messages/messages.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { HttpClientModule } from '@angular/common/http';
import { RockSearchComponent } from './components/rock-search/rock-search.component';
import { LogoutComponent } from './logout/logout.component';

@NgModule({
  declarations: [
    AppComponent,
    RocksComponent,
    RockDetailComponent,
    MessagesComponent,
    DashboardComponent,
    RockSearchComponent,
    LogoutComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
		FormsModule,
		NgFor,
		HttpClientModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }