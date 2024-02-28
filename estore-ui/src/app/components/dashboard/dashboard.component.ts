import { Component, OnInit } from '@angular/core';
import { RockService } from '../../services/rock/rock.service';
import { Rock } from '../../models/Rock';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: [ './dashboard.component.css' ]
})
export class DashboardComponent implements OnInit {
  rocks: Rock[] = [];

  constructor(private rockService: RockService) { }

  ngOnInit(): void {
    this.retrieveRocks();
  }

  retrieveRocks(): void {
    this.rockService.getRocks()
      .subscribe(rocks => this.rocks = rocks.slice(1, 5));
  }
}